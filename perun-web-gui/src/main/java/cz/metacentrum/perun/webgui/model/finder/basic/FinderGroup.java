package cz.metacentrum.perun.webgui.model.finder.basic;

import cz.metacentrum.perun.webgui.model.finder.FinderEntity;

/**
 * Overlay for Group from GeneralFinder
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class FinderGroup extends FinderEntity {

	protected FinderGroup() {
	}

	public final native String getName() /*-{
        return this.name;
    }-*/;

	public final native String getDescription() /*-{
        return this.description;
    }-*/;

    public final native int getVoId() /*-{
    	return this.voId;
	}-*/;

	public final native int getParentGroupId() /*-{
    	return this.parentGroupId;
	}-*/;
}
