package cz.metacentrum.perun.webgui.model.finder.basic;

import cz.metacentrum.perun.webgui.model.finder.FinderEntity;

/**
 * Overlay for ExtSource from GeneralFinder
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class FinderExtSource extends FinderEntity {

	protected FinderExtSource() {
	}

	public final native String getName() /*-{
    	return this.name;
	}-*/;

	public final native String getType() /*-{
    	return this.type;
	}-*/;
}
