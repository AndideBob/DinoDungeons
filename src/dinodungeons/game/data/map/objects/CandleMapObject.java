package dinodungeons.game.data.map.objects;

import dinodungeons.game.data.gameplay.RoomEvent;

public class CandleMapObject extends MapObject {
	
	RoomEvent triggeredSwitch;
	
	public CandleMapObject() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getEditorInfo() {
		return "Candle" + triggeredSwitch.getStringRepresentation();
	}

	public RoomEvent getTriggeredSwitch() {
		return triggeredSwitch;
	}

	public void setTriggeredSwitch(RoomEvent triggeredSwitch) {
		this.triggeredSwitch = triggeredSwitch;
	}
}
