package cz.metacentrum.perun.webgui.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;

/**
 * Interface for small icon resources.
 *
 * Example:
 * SmallIcons icons = GWT.create(SmallIcons.class);
 * Image img = new Image(icons.acceptIcon());
 *
 * DO NOT EDIT CONTENTS OF THIS FILE MANUALLY!
 * Instead, generate it with gen.php file.
 *
 * @author Vaclav Mach <374430@mail.muni.cz>
 */
public interface GraphQL extends ClientBundle {
	public static final GraphQL INSTANCE =  GWT.create(GraphQL.class);


	@Source("graphql/init.js")
	TextResource init();
}
