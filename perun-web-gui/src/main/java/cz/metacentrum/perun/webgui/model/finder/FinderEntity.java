package cz.metacentrum.perun.webgui.model.finder;

/**
 * Basic overlay for entity from GeneralFinder
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public abstract class FinderEntity extends FinderObject {

	protected FinderEntity() { }

	public final native int getId() /*-{
    	return this.id;
	}-*/;

}
