package cz.metacentrum.perun.finder.service;

public class InputParseException extends Exception {

	public InputParseException() {
		super();
	}

	public InputParseException(String s) {
		super(s);
	}

	public InputParseException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public InputParseException(Throwable throwable) {
		super(throwable);
	}

	protected InputParseException(String s, Throwable throwable, boolean b, boolean b1) {
		super(s, throwable, b, b1);
	}
}
