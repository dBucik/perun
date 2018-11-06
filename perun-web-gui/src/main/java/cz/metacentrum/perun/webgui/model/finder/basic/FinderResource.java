package cz.metacentrum.perun.webgui.model.finder.basic;

import cz.metacentrum.perun.webgui.model.finder.FinderEntity;

/**
 * Overlay for Resource from GeneralFinder
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class FinderResource extends FinderEntity {

	protected FinderResource() { }

	public final native int getFacilityId() /*-{
    	return this.facilityId;
	}-*/;

	public final native String getName() /*-{
        return this.name;
    }-*/;

    public final native String getDescription() /*-{
    	return this.description;
    }-*/;

    public final native int getVoId() /*-{
    	return this.voId;
    }-*/;
}
