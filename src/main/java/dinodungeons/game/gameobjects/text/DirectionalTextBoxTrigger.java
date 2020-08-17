package dinodungeons.game.gameobjects.text;

import java.util.Collection;

public class DirectionalTextBoxTrigger extends TextBoxTrigger {

	private TriggerReactor reactor;
	private int direction;
	
	public DirectionalTextBoxTrigger(int posX, int posY, int width, int height,
			TriggerReactor reactor, int direction,
			Collection<TextBoxContent> textBoxContents) {
		super(posX, posY, width, height, textBoxContents);
		this.reactor = reactor;
		this.direction = direction;
	}

	@Override
	protected void trigger() {
		reactor.trigger(direction);
		super.trigger();
	}
}
