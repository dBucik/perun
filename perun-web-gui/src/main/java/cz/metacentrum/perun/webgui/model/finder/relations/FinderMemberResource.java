package cz.metacentrum.perun.webgui.model.finder.relations;

import cz.metacentrum.perun.webgui.model.finder.FinderObject;

/**
 * Overlay for MemberResource from GeneralFinder
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class FinderMemberResource extends FinderObject {

	protected FinderMemberResource() { }

	public final native int getMemberId() /*-{
        return this.memberId;
    }-*/;

	public final native int getResourceId() /*-{
        return this.resourceId;
    }-*/;
}
