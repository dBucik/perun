package cz.metacentrum.perun.webgui.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.TextResource;

/**
*/
public interface AceEditor extends ClientBundle {
	public static final AceEditor INSTANCE = GWT.create(AceEditor.class);

	@Source("ace/ace.css")
	CssResource aceCss();

	@Source("ace/aceInit.js")
	TextResource aceJsInit();

}
