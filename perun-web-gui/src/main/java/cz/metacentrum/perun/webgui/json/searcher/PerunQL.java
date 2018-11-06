package cz.metacentrum.perun.webgui.json.searcher;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.LinkElement;
import com.google.gwt.dom.client.ScriptElement;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import cz.metacentrum.perun.webgui.client.PerunWebSession;
import cz.metacentrum.perun.webgui.client.resources.GraphQL;
import cz.metacentrum.perun.webgui.json.JsonCallback;
import cz.metacentrum.perun.webgui.json.JsonCallbackEvents;
import cz.metacentrum.perun.webgui.model.PerunError;

/**
 * Searching for Facilities
 *
 * @author Vaclav Mach <374430@mail.muni.cz>
 */
public class PerunQL implements JsonCallback {

	// json url
	private JsonCallbackEvents events = new JsonCallbackEvents();

	/**
	 * Creates a new request
	 */
	public PerunQL() {
	}

	/**
	 * Creates a new request with custom events
	 * @param events
	 */
	public PerunQL(JsonCallbackEvents events) {
		this.events = events;
	}

	@Override
	public void onFinished(JavaScriptObject jso) {
		events.onFinished(jso);
	}

	@Override
	public void onError(PerunError error) {
		events.onError(error);
	}

	@Override
	public void onLoadingStart() {
		events.onLoadingStart();
	}

	@Override
	public void retrieveData() {

	}

	public Widget getWindow() {
		FlowPanel panel = new FlowPanel();
		panel.setSize("100%", "100%");
		panel.getElement().setId("graphiql");

		Document.get().getHead().appendChild(generateLink("https://cdnjs.cloudflare.com/ajax/libs/graphiql/0.11.5/graphiql.css", "stylesheet"));
		Document.get().getHead().appendChild(generateLink("https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.23.0/theme/solarized.css", "stylesheet"));

		return panel;
	}

	private LinkElement generateLink(String href, String rel) {
		LinkElement el = Document.get().createLinkElement();
		el.setHref(href);
		el.setRel(rel);

		return el;
	}
}
