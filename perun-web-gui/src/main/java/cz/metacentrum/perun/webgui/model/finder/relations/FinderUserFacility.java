package cz.metacentrum.perun.webgui.model.finder.relations;

import cz.metacentrum.perun.webgui.model.finder.FinderObject;

/**
 * Overlay for UserFacility from GeneralFinder
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class FinderUserFacility extends FinderObject {

	protected FinderUserFacility() { }

	public final native int getUserId() /*-{
        return this.userId;
    }-*/;

	public final native int getFacilityId() /*-{
        return this.facilityId;
    }-*/;
}
