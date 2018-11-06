package cz.metacentrum.perun.finder.persistence.exceptions;

/**
 * Expcetion represents that specified relation is not of valid type.
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class IllegalRelationException extends Exception {

	public IllegalRelationException() {
		super();
	}

	public IllegalRelationException(String s) {
		super(s);
	}

	public IllegalRelationException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public IllegalRelationException(Throwable throwable) {
		super(throwable);
	}

	protected IllegalRelationException(String s, Throwable throwable, boolean b, boolean b1) {
		super(s, throwable, b, b1);
	}
}
