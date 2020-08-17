package dinodungeons.game.data.gameplay;

import java.util.HashSet;

import lwjgladapter.logging.Logger;

public class GameEventManager {

	private static GameEventManager instance;
	
	public static GameEventManager getInstance(){
		if(instance == null){
			instance = new GameEventManager();
		}
		return instance;
	}
	
	private HashSet<String> occuredEvents;
	private HashSet<RoomEvent> roomEvents;
	
	private GameEventManager(){
		occuredEvents = new HashSet<>();
		roomEvents = new HashSet<>();
		roomEvents.add(RoomEvent.NONE);
	}
	
	public void clearRoomEvents(){
		roomEvents.clear();
		roomEvents.add(RoomEvent.NONE);
	}
	
	public boolean hasRoomEventOccured(RoomEvent roomEvent){
		return roomEvents.contains(roomEvent);
	}
	
	public void activateRoomEvent(RoomEvent roomEvent){
		if(!roomEvents.contains(roomEvent)){
			Logger.logDebug(roomEvent.toString() + " was triggered!");
			roomEvents.add(roomEvent);
		}
		activateRelativeRoomEvents();
	}
	
	private void activateRelativeRoomEvents(){
		if(roomEvents.contains(RoomEvent.SWITCH_A) && roomEvents.contains(RoomEvent.SWITCH_B)){
			roomEvents.add(RoomEvent.SWITCH_AB);
		}
		if(roomEvents.contains(RoomEvent.SWITCH_AB) && roomEvents.contains(RoomEvent.SWITCH_C)){
			roomEvents.add(RoomEvent.SWITCH_ABC);
		}
		if(roomEvents.contains(RoomEvent.SWITCH_ABC) && roomEvents.contains(RoomEvent.SWITCH_D)){
			roomEvents.add(RoomEvent.SWITCH_ABCD);
		}
	}
	
	public void markEventAsOccured(String eventKey) {
		if(!occuredEvents.contains(eventKey)){
			Logger.logDebug("Event '" + eventKey + "' occured!");
			occuredEvents.add(eventKey);
		}
	}

	public boolean hasEventOccured(String eventKey) {
		return occuredEvents.contains(eventKey);
	}

}
