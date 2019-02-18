package dinodungeons.game.data.exceptions;

public class ScreenMapIndexOutOfBounds extends DinoDungeonsException {

	private static final long serialVersionUID = -7574969425793323650L;

	public ScreenMapIndexOutOfBounds(String message) {
		super(message);
	}

	public ScreenMapIndexOutOfBounds(Throwable cause) {
		super(cause);
	}

	public ScreenMapIndexOutOfBounds(String message, Throwable cause) {
		super(message, cause);
	}

	public ScreenMapIndexOutOfBounds(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
