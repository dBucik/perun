package cz.metacentrum.perun.webgui.model.finder;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

/**
 * Basic overlay for object from GeneralFinder
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public abstract class FinderObject extends JavaScriptObject {

	protected FinderObject() { }

	public final native JsArray<FinderAttribute> getAttributes() /*-{
		return this.attributes;
	}-*/;

	public final native String getEntityType() /*-{
		return this.entityType;
	}-*/;

}