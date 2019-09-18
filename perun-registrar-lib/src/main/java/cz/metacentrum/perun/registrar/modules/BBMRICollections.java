package cz.metacentrum.perun.registrar.modules;

import com.google.common.base.Strings;
import cz.metacentrum.perun.core.api.*;
import cz.metacentrum.perun.core.api.exceptions.AlreadyMemberException;
import cz.metacentrum.perun.core.api.exceptions.AttributeNotExistsException;
import cz.metacentrum.perun.core.api.exceptions.ExternallyManagedException;
import cz.metacentrum.perun.core.api.exceptions.GroupNotExistsException;
import cz.metacentrum.perun.core.api.exceptions.InternalErrorException;
import cz.metacentrum.perun.core.api.exceptions.MemberNotExistsException;
import cz.metacentrum.perun.core.api.exceptions.NotGroupMemberException;
import cz.metacentrum.perun.core.api.exceptions.PerunException;
import cz.metacentrum.perun.core.api.exceptions.PrivilegeException;
import cz.metacentrum.perun.core.api.exceptions.UserNotExistsException;
import cz.metacentrum.perun.core.api.exceptions.VoNotExistsException;
import cz.metacentrum.perun.core.api.exceptions.WrongAttributeAssignmentException;
import cz.metacentrum.perun.core.api.exceptions.WrongAttributeValueException;
import cz.metacentrum.perun.core.api.exceptions.WrongReferenceAttributeValueException;
import cz.metacentrum.perun.registrar.RegistrarManager;
import cz.metacentrum.perun.registrar.RegistrarModule;
import cz.metacentrum.perun.registrar.exceptions.CantBeApprovedException;
import cz.metacentrum.perun.registrar.exceptions.RegistrarException;
import cz.metacentrum.perun.registrar.model.Application;
import cz.metacentrum.perun.registrar.model.ApplicationFormItemData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Registration module for BBMRI Collections
 * Module:
 * 1. reads input with collection IDs and checks, whether groups representing collections exist
 *    - group representing collection has attribute CollectionID assigned and value represents the ID
 * 2. adds users to the appropriate groups
 *
 * NOTE!!!: Groups representing collections must be subgroups of Group to which module is assigned!
 *
 * @author Jiri Mauritz <jirmaurtiz@gmail.com> (original)
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz> (modifications)
 */
public class BBMRICollections implements RegistrarModule {

	private final static Logger log = LoggerFactory.getLogger(BBMRICollections.class);

	private static final String COLLECTION_IDS_FIELD = "Comma or new-line separated list of IDs of collections you are representing:";
	private static final String COLLECTION_ID_ATTR_NAME = "urn:perun:group:attribute-def:def:collectionID";
	private static final String REPRESENTATIVES_GROUP_NAME = "representatives";
	private static final String ADD_NEW_COLLECTIONS_GROUP_NAME = "addNewCollections";

	private RegistrarManager registrar;

	@Override
	public void setRegistrar(RegistrarManager registrar) {
		this.registrar = registrar;
	}

	@Override
	public List<ApplicationFormItemData> createApplication(PerunSession user, Application application, List<ApplicationFormItemData> data) {
		return data;
	}

	/**
	 * Find groups representing collections by input. Groups are looked for in subgroups
	 * of group the module is assigned to. Add user as a member to the groups.
	 *
	 * @param session who approves the application
	 * @param app application
	 * @return unchanged application
	 * @throws PerunException in case of internal error in Perun
	 */
	@Override
	public Application approveApplication(PerunSession session, Application app) throws VoNotExistsException, UserNotExistsException, PrivilegeException, MemberNotExistsException, InternalErrorException, RegistrarException, GroupNotExistsException, AttributeNotExistsException, WrongAttributeAssignmentException, ExternallyManagedException, WrongAttributeValueException, WrongReferenceAttributeValueException, NotGroupMemberException {
		// get perun and beans from session
		Perun perun = session.getPerun();
		Vo vo = app.getVo();
		User user = app.getUser();
		Member member = perun.getMembersManager().getMemberByUser(session, vo, user);

		// get the field of application with the collections
		Set<String> collectionIDsInApplication = getCollectionIDsFromApplication(session, app);

		// get map of collection IDs to group from Perun
		Group directoryGroup = app.getGroup();
		Map<String, List<Group>> collectionIDsToGroupsMap = getCollectionIDsToGroupsMap(session, perun, directoryGroup);

		// add user to all groups from the field on application
		for (String collectionID : collectionIDsInApplication) {
			List<Group> collections = collectionIDsToGroupsMap.get(collectionID);
			if (collections == null || collections.isEmpty()) {
				log.debug("There are no groups for collectionID: {}", collectionID);
			} else {
				// add user to the groups
				for (Group collection: collections) {
					try {
						perun.getGroupsManager().addMember(session, collection, member);
					} catch (AlreadyMemberException ex) {
						// ignore
					}
				}
			}
		}

		if (app.getGroup() != null && app.getGroup().getName().equals(ADD_NEW_COLLECTIONS_GROUP_NAME)) {
			perun.getGroupsManager().removeMember(session, app.getGroup(), member);
		}

		return app;
	}

	@Override
	public Application rejectApplication(PerunSession session, Application app, String reason) {
		return app;
	}


	@Override
	public Application beforeApprove(PerunSession session, Application app) {
		return app;
	}

	/**
	 * Checks whether all collection IDs found in user input really exists in Perun.
	 * If not, CantBeApproved exception is thrown.
	 *
	 * @param session who approves the application
	 * @param app     unchanged application
	 * @throws CantBeApprovedException if at least one collection ID does not exist in Perun
	 */
	public void canBeApproved(PerunSession session, Application app) throws PerunException {
		// get perun and beans from session
		Perun perun = session.getPerun();
		Vo vo = app.getVo();

		// get all collection IDs from Perun
		Group collectionsGroup = app.getGroup();
		Set<String> collectionIDsInPerun = getCollectionIDs(session, perun, collectionsGroup);


		// get the field of application with the collections
		Set<String> collectionIDsInApplication = getCollectionIDsFromApplication(session, app);

		// get non-existing collections
		collectionIDsInApplication.removeAll(collectionIDsInPerun);

		// difference must be empty
		if (!collectionIDsInApplication.isEmpty()) {
			throw new CantBeApprovedException("Collections " + collectionIDsInApplication + " do not exist." +
					"If you approve the application, these collections will be skipped.", "", "", "", true);
		}
	}

	@Override
	public void canBeSubmitted(PerunSession session, Map<String, String> params) {

	}

	/**
	 * Gets collection IDs from a field on the application form with short name.
	 *
	 * @return collection IDs set
	 */
	private Set<String> getCollectionIDsFromApplication(PerunSession session, Application app) throws RegistrarException, PrivilegeException, InternalErrorException {
		String collectionsString = null;
		List<ApplicationFormItemData> formData = registrar.getApplicationDataById(session, app.getId());
		for (ApplicationFormItemData field : formData) {
			if (COLLECTION_IDS_FIELD.equals(field.getShortname())) {
				collectionsString = field.getValue();
				break;
			}
		}

		if (collectionsString == null) {
			throw new InternalErrorException("There is no field with collection IDs on the registration form.");
		}

		// get set of collection IDs from application
		Set<String> collectionIDsInApplication = new HashSet<>();
		for (String collection : collectionsString.split("[,\n ]+")) {
			collectionIDsInApplication.add(collection.trim());
		}

		return collectionIDsInApplication;
	}

	/**
	 * Gets collections as map of collectionID => Group.
	 *
	 * @return Map of collection IDs to group.
	 */
	private Map<String, List<Group>> getCollectionIDsToGroupsMap (PerunSession session, Perun perun, Group collectionsGroup) throws GroupNotExistsException, WrongAttributeAssignmentException, InternalErrorException, AttributeNotExistsException, PrivilegeException {
		Map<String, List<Group>> collectionIDsToGroupMap = new HashMap<>();
		for (Group group : perun.getGroupsManager().getSubGroups(session, collectionsGroup)) {
			for (Group subgroup : perun.getGroupsManager().getSubGroups(session, group)) {
				if (REPRESENTATIVES_GROUP_NAME.equals(subgroup.getShortName())) {
					Attribute collectionIDAttr = perun.getAttributesManager().getAttribute(session, subgroup, COLLECTION_ID_ATTR_NAME);
					if (collectionIDAttr == null || Strings.isNullOrEmpty(collectionIDAttr.valueAsString())) {
						continue;
					}

					String collectionID = collectionIDAttr.valueAsString();
					if (collectionIDsToGroupMap.containsKey(collectionID)) {
						List<Group> groupList = collectionIDsToGroupMap.get(collectionID);
						groupList.add(group);
					} else {
						List<Group> groupList = new ArrayList<>();
						groupList.add(group);
						collectionIDsToGroupMap.put(collectionID, groupList);
					}
				}
			}
		}

		return collectionIDsToGroupMap;
	}

	private Set<String> getCollectionIDs(PerunSession session, Perun perun, Group collectionsGroup) throws InternalErrorException, PrivilegeException, WrongAttributeAssignmentException, AttributeNotExistsException, GroupNotExistsException {
		return getCollectionIDsToGroupsMap(session, perun, collectionsGroup).keySet();
	}
}
