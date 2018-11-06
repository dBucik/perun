package cz.metacentrum.perun.webgui.model.finder.basic;

import cz.metacentrum.perun.webgui.model.finder.FinderEntity;

/**
 * Overlay for Host from GeneralFinder
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class FinderHost extends FinderEntity {

	protected FinderHost() {
	}

	public final native String getHostname() /*-{
        return this.hostname;
    }-*/;

    public final native int getFacilityId() /*-{
    	return this.facilityId;
	}-*/;

	public final native String getDescription() /*-{
		return this.description;
	}-*/;
}
