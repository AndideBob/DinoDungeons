package dinodungeons.editor.ui;

import dinodungeons.game.data.gameplay.InputInformation;

public abstract class UIElement {
	
	private static long UIElementIDCounter = 0L;
	
	private long id;
	
	protected UIElement() {
		id = getNextUIElementID();
	}

	public abstract void update(InputInformation inputInformation);
	
	public abstract void draw();
	
	public final long getID() {
		return id;
	}
	
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UIElement other = (UIElement) obj;
		if (id != other.id)
			return false;
		return true;
	}

	private static long getNextUIElementID() {
		long value = UIElementIDCounter;
		UIElementIDCounter++;
		return value;
	}
}
