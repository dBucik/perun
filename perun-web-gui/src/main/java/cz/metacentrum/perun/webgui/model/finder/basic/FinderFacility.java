package cz.metacentrum.perun.webgui.model.finder.basic;

import cz.metacentrum.perun.webgui.model.finder.FinderEntity;

/**
 * Overlay for Facility from GeneralFinder
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class FinderFacility extends FinderEntity {

	protected FinderFacility() { }

	public final native String getName() /*-{
        return this.name;
    }-*/;

	public final native String getDescription() /*-{
        return this.description;
    }-*/;

}
