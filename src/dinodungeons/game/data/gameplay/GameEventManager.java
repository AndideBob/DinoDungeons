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
	
	private GameEventManager(){
		occuredEvents = new HashSet<>();
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
