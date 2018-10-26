package cz.metacentrum.perun.finder.persistence.exceptions;

/**
 * Exception thrown when provided attribute type cannot be parsed.
 */
public class AttributeTypeException extends Exception {

	public AttributeTypeException() {
		super();
	}

	public AttributeTypeException(String s) {
		super(s);
	}

	public AttributeTypeException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public AttributeTypeException(Throwable throwable) {
		super(throwable);
	}

	protected AttributeTypeException(String s, Throwable throwable, boolean b, boolean b1) {
		super(s, throwable, b, b1);
	}
}
