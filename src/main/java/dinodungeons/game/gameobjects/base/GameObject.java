package dinodungeons.game.gameobjects.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import dinodungeons.game.data.gameplay.InputInformation;
import lwjgladapter.physics.collision.base.Collider;
import lwjgladapter.physics.collision.base.Collision;

public abstract class GameObject {
	
	private static long id_counter = 0;
	private long id;
	private HashMap<Long, ArrayList<CollisionInformation>> collisionInformation;
	
	protected GameObjectTag tag;
	
	public GameObject(GameObjectTag tag){
		this.id = id_counter++;
		this.tag = tag;
		this.collisionInformation = new HashMap<>();
	}
	
	public final long getID(){
		return id;
	}
	
	public final GameObjectTag getTag(){
		return tag;
	}

	public abstract void update(long deltaTimeInMs, InputInformation inputInformation);
	
	public abstract void draw(int anchorX, int anchorY);
	
	public abstract Collection<Collider> getColliders();
	
	public final Collection<GameObjectTag> getCollisionTagsForSpecificCollider(Long colliderID){
		HashSet<GameObjectTag> tags = new HashSet<>();
		if(collisionInformation.containsKey(colliderID)){
			for(CollisionInformation info : collisionInformation.get(colliderID)){
				tags.add(info.getTagOfOther());
			}
		}
		return tags;
	}
	
	public final boolean hasCollisionWithObjectWithTag(GameObjectTag... tags){
		for(GameObjectTag tag : tags){
			if(!getCollisionsWithObjectsWithTag(tag).isEmpty()){
				return true;
			}
		}
		return false;
	}
	
	public final Collection<Collision> getCollisionsWithObjectsWithTag(GameObjectTag tag){
		HashSet<Collision> collisions = new HashSet<>();
		for(ArrayList<CollisionInformation> aL : collisionInformation.values()){
			for(CollisionInformation info : aL){
				if(info.getTagOfOther().equals(tag)){
					collisions.add(info.getActualCollision());
				}
			}
		}
		return collisions;
	}
	
	public final void clearCollisionInformation(){
		for(ArrayList<CollisionInformation> aL : collisionInformation.values()){
			aL.clear();
		}
		collisionInformation.clear();
	}
	
	public final void addCollisionInformation(Long colliderID, CollisionInformation information){
		if(!collisionInformation.containsKey(colliderID)){
			collisionInformation.put(colliderID, new ArrayList<>());
		}
		collisionInformation.get(colliderID).add(information);
	}
	
	public void delete(){
		for(Collider c : getColliders()){
			c.unregister();
		}
	}
	
	public boolean isTemporary() {
		return false;
	}
	
	public void resetOnRoomEntry(){
		//Reset on room entry
	}
	
	public boolean shouldBeDeleted(){
		return false;
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