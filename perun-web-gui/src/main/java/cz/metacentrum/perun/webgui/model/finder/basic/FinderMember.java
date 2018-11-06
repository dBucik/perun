package cz.metacentrum.perun.webgui.model.finder.basic;

import cz.metacentrum.perun.webgui.model.finder.FinderEntity;

/**
 * Overlay for Member from GeneralFinder
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class FinderMember extends FinderEntity {

	protected FinderMember() { }

	public native final int getUserId() /*-{
		return this.userId;
	}-*/;

	public native final int getVoId() /*-{
		return this.voId;
	}-*/;

	public native final boolean isSponsored() /*-{
		return this.sponsored;
	}-*/;
}
