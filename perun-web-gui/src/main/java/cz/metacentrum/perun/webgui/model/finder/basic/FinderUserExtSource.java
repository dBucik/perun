package cz.metacentrum.perun.webgui.model.finder.basic;

import cz.metacentrum.perun.webgui.model.finder.FinderEntity;

/**
 * Overlay for UserExtSource from GeneralFinder
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class FinderUserExtSource extends FinderEntity {

	protected FinderUserExtSource() { }

	public final native int getUserId() /*-{
        return this.userId;
    }-*/;

	public final native String getLoginExt() /*-{
        return this.loginExt;
    }-*/;

	public final native int getExtSourceId() /*-{
        return this.extSourceId;
    }-*/;

	public final native int getLoa() /*-{
        return this.loa;
    }-*/;

	public final native int getLastAccess() /*-{
        return this.lastAccess;
    }-*/;
}
