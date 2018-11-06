package cz.metacentrum.perun.webgui.model.finder.basic;

import cz.metacentrum.perun.webgui.model.finder.FinderEntity;

/**
 * Overlay for Vo from GeneralFinder
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class FinderVo extends FinderEntity {

	protected FinderVo() { }

	public final native String getName() /*-{
        return this.name;
    }-*/;

	public final native String getShortName() /*-{
        return this.shortName;
    }-*/;
}
