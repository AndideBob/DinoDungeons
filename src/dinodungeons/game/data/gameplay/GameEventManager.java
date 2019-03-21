package dinodungeons.game.data.gameplay;

import java.util.HashSet;

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
		occuredEvents.add(eventKey);
	}

	public boolean hasEventOccured(String eventKey) {
		return occuredEvents.contains(eventKey);
	}

}
