package dinodungeons.editor.ui.pointer;

import dinodungeons.editor.ui.UIElement;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import dinodungeons.gfx.text.DrawTextManager;
import lwjgladapter.physics.collision.RectCollider;

public class MouseHandler extends UIElement {

	private RectCollider clickCollider;
	
	private static MouseHandler instance;
	
	private int positionX;
	
	private int positionY;
	
	private boolean drawText;
	
	public static MouseHandler getInstance() {
		if(instance == null) {
			instance = new MouseHandler();
		}
		return instance;
	}
	
	private MouseHandler() {
		clickCollider = new RectCollider(0, 0, 3, 3);
		positionX = 0;
		positionY = 0;
	}

	@Override
	public void update(InputInformation inputInformation) {
		updateSpecial(inputInformation, true);
	}
	
	public void updateSpecial(InputInformation inputInformation, boolean drawText) {
		positionX = inputInformation.getMouseX();
		positionY = inputInformation.getMouseY();
		clickCollider.setPositionX(positionX - 1);
		clickCollider.setPositionY(positionY - 1);
		this.drawText = drawText;
	}

	@Override
	public void draw() {
		//Draw Position On Map
		SpriteManager.getInstance().getSprite(SpriteID.BACKGROUNDS).draw(0, 256, 0, 64, 10);
		if(isOnMap() && drawText){
			int x = MouseHandler.getInstance().getPositionX() / 16;
			int y = MouseHandler.getInstance().getPositionY() / 16;
			String posX = String.format("%02d", x);
			String posY = String.format("%02d", y);
			DrawTextManager.getInstance().drawText(258, 1, "X" + posX + "Y" + posY, 6);
			SpriteManager.getInstance().getSprite(SpriteID.EDITOR_SELECTOR).draw(0, x * 16, y * 16);
		}
	}
	
	public RectCollider getCollider() {
		return clickCollider;
	}

	public boolean isOnMap() {
		return positionX < 256 && positionY < 192;
	}

	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
	}

}
