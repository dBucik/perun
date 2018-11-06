package cz.metacentrum.perun.webgui.model.finder;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
import cz.metacentrum.perun.webgui.model.Attribute;

/**
 * Overlay for Attribute from GeneralFinder
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class FinderAttribute extends JavaScriptObject {
	public static int counter = 0;

	protected FinderAttribute() { }

	/**
	 * Get whole name of attribute (URN)
	 *
	 * @return whole name of attribute
	 */
	public final native String getName() /*-{
        return this.name;
    }-*/;

	/**
	 * Gets value of attribute
	 *
	 * @return value of attribute
	 */
	public final native String getValue() /*-{
        if (this.value == null) { return "null"; }
        return this.value.toString();
    }-*/;

	public native final boolean getValueAsBoolean() /*-{
        return this.value;
    }-*/;

	public native final JsArrayString getValueAsJsArray() /*-{
        return this.value;
    }-*/;

	public native final JavaScriptObject getValueAsJso() /*-{
        return this.value;
    }-*/;

	public native final Object getValueAsObject() /*-{
        return this.value;
    }-*/;

	/**
	 * Compares to another object
	 * @param o Object to compare
	 * @return true, if they are the same
	 */
	public final boolean equals(Attribute o) {
		return o.getName().equals(this.getName());
	}

}
