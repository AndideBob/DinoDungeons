package dinodungeons.game.data.exceptions;

import lwjgladapter.datatypes.LWJGLAdapterException;

public abstract class DinoDungeonsException extends LWJGLAdapterException {

	private static final long serialVersionUID = 232787538992378959L;

	public DinoDungeonsException(String message) {
		super(message);
	}

	public DinoDungeonsException(Throwable cause) {
		super(cause);
	}

	public DinoDungeonsException(String message, Throwable cause) {
		super(message, cause);
	}

	public DinoDungeonsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
