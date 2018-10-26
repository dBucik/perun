package cz.metacentrum.perun.finder.persistence.exceptions;

public class IncorrectCoreAttributeTypeException extends Exception {
	public IncorrectCoreAttributeTypeException() {
		super();
	}

	public IncorrectCoreAttributeTypeException(String s) {
		super(s);
	}

	public IncorrectCoreAttributeTypeException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public IncorrectCoreAttributeTypeException(Throwable throwable) {
		super(throwable);
	}

	protected IncorrectCoreAttributeTypeException(String s, Throwable throwable, boolean b, boolean b1) {
		super(s, throwable, b, b1);
	}
}
