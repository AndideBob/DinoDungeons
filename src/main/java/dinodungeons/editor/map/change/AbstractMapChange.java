package dinodungeons.editor.map.change;

import dinodungeons.game.data.map.ScreenMap;

public abstract class AbstractMapChange implements MapChange {

	private int x;
	private int y;
	
	public AbstractMapChange(int x, int y) {
		this.x = x;
		this.y = y;
	}

	protected int getX() {
		return x;
	}
	
	protected int getY() {
		return y;
	}
	
	@Override
	public abstract void applyTo(ScreenMap map);
	
	@Override
	public abstract void revert();

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		AbstractMapChange other = (AbstractMapChange) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
