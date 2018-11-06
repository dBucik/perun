package cz.metacentrum.perun.webgui.model.finder.relations;

import cz.metacentrum.perun.webgui.model.finder.FinderObject;

/**
 * Overlay for MemberGroup from GeneralFinder
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class FinderMemberGroup extends FinderObject {

	protected FinderMemberGroup() { }

	public final native int getMemberId() /*-{
        return this.memberId;
    }-*/;

	public final native int getGroupId() /*-{
        return this.groupId;
    }-*/;
}
