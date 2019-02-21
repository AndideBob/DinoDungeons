package dinodungeons.game.gameobjects;

import java.util.Collection;
import java.util.HashSet;

import lwjgladapter.physics.collision.base.Collider;

public abstract class GameObject {
	
	private static long id_counter = 0;
	private long id;
	private HashSet<GameObjectTag> collisionTags;
	
	protected GameObjectTag tag;
	
	public GameObject(GameObjectTag tag){
		this.id = id_counter++;
		this.tag = tag;
		this.collisionTags = new HashSet<>();
	}
	
	public final long getID(){
		return id;
	}
	
	public final GameObjectTag getTag(){
		return tag;
	}

	public abstract void update(long deltaTimeInMs);
	
	public abstract void draw();
	
	public abstract Collection<Collider> getColliders();
	
	public final boolean hasCollisionWithObjectWithTag(GameObjectTag tag){
		return collisionTags.contains(tag);
	}
	
	public final void clearCollisionTags(){
		collisionTags.clear();
	}
	
	public final void addCollisionTag(GameObjectTag tag){
		collisionTags.add(tag);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameObject other = (GameObject) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
