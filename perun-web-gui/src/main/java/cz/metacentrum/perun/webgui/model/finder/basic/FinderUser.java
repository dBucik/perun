package cz.metacentrum.perun.webgui.model.finder.basic;

import cz.metacentrum.perun.webgui.model.finder.FinderEntity;

/**
 * Overlay for User from GeneralFinder
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class FinderUser extends FinderEntity {

	protected FinderUser() { }

	public final native String getFirstName() /*-{
        return this.firstName;
    }-*/;

	public final native String getMiddleName() /*-{
        return this.middleName;
    }-*/;

	public final native String getLastName() /*-{
        return this.lastName;
    }-*/;

	public final native String getTitleBefore() /*-{
        return this.titleBefore;
    }-*/;

	public final native String getTitleAfter() /*-{
        return this.titleAfter;
    }-*/;

	public final native boolean isServiceAcc() /*-{
        return this.serviceAcc;
    }-*/;

	public final native boolean isSponsoredAcc() /*-{
        return this.sponsoredAcc;
    }-*/;
}
