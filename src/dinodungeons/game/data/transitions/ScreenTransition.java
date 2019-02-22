package dinodungeons.game.data.transitions;

public class ScreenTransition {

	private String destinationMapID;
	
	private int destinationXPosition;
	
	private int destinationYPosition;
	
	private TransitionType transitionType;
	
	public ScreenTransition(String destinationMapID, int destinationXPosition, int destinationYPosition,
			TransitionType transitionType) {
		this.destinationMapID = destinationMapID;
		this.destinationXPosition = destinationXPosition;
		this.destinationYPosition = destinationYPosition;
		this.transitionType = transitionType;
	}
	
	public String getDestinationMapID() {
		return destinationMapID;
	}

	public int getDestinationXPosition() {
		return destinationXPosition;
	}

	public int getDestinationYPosition() {
		return destinationYPosition;
	}

	public TransitionType getTransitionType() {
		return transitionType;
	}
	
}
