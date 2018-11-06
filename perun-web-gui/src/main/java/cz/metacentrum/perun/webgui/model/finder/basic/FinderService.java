package cz.metacentrum.perun.webgui.model.finder.basic;

import cz.metacentrum.perun.webgui.model.finder.FinderEntity;

/**
 * Overlay for Service from GeneralFinder
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class FinderService extends FinderEntity {

	protected FinderService() {	}

	public final native String getName() /*-{
        return this.name;
    }-*/;

    public final native String getDescription() /*-{
    	return this.description;
	}-*/;

	public final native int getDelay() /*-{
		return this.delay;
	}-*/;

	public final native int getRecurrence() /*-{
		return this.recurrence;
	}-*/;

	public final native boolean isEnalbed() /*-{
		return this.enabled;
	}-*/;

	public final native String getScript() /*-{
		return this.script;
	}-*/;
}
