package cz.metacentrum.perun.finder.service;

/**
 * Exception thrown when entity on higher level cannot have specified child entity
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class IncorrectSourceEntityException extends Exception {

	public IncorrectSourceEntityException() {
		super();
	}

	public IncorrectSourceEntityException(String s) {
		super(s);
	}

	public IncorrectSourceEntityException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public IncorrectSourceEntityException(Throwable throwable) {
		super(throwable);
	}

	protected IncorrectSourceEntityException(String s, Throwable throwable, boolean b, boolean b1) {
		super(s, throwable, b, b1);
	}
}
