package cz.metacentrum.perun.webgui.model.finder.relations;

import cz.metacentrum.perun.webgui.model.finder.FinderObject;

/**
 * Overlay for GroupResource from GeneralFinder
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class FinderGroupResource extends FinderObject {

	protected FinderGroupResource() {
	}

	public final native int getGroupId() /*-{
        return this.groupId;
    }-*/;

	public final native int getResourceId() /*-{
        return this.resourceId;
    }-*/;
}
