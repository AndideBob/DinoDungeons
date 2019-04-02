package dinodungeons.editor.ui;

import dinodungeons.game.data.gameplay.InputInformation;

public interface UIElement {

	public void update(InputInformation inputInformation);
	
	public void draw();
}
