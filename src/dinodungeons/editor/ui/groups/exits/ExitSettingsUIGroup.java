package dinodungeons.editor.ui.groups.exits;

import dinodungeons.editor.ui.UIElement;
import dinodungeons.editor.ui.groups.UIGroup;
import dinodungeons.editor.ui.input.NumberInputLine;
import dinodungeons.editor.ui.input.TextInputLine;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import dinodungeons.gfx.text.DrawTextManager;

public class ExitSettingsUIGroup extends UIElement implements UIGroup {
	
	private ExitPlacementUIGroup exitPlacementUIGroup;
	
	private TextInputLine transportMapID;
	private NumberInputLine transportMapX;
	private NumberInputLine transportMapY;
	
	private boolean active;
	private int x;
	private int y;
	
	private String mapID;
	private int mapX;
	private int mapY;

	public ExitSettingsUIGroup(int positionX, int positionY, final ExitPlacementUIGroup exitPlacementUIGroup) {
		this.exitPlacementUIGroup = exitPlacementUIGroup;
		active = true;
		x = positionX;
		y = positionY;
		transportMapX = new NumberInputLine(positionX + 42, positionY + 26, 2);
		transportMapY = new NumberInputLine(positionX + 42, positionY + 14, 2);
		transportMapID = new TextInputLine(positionX + 22, positionY + 2, 4, false);
		transportMapID.setInput("0000");
		transportMapX.setInput(0);
		transportMapY.setInput(0);
		mapID = transportMapID.getInput();
		mapX = transportMapX.getInput();
		mapY = transportMapY.getInput();
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
		if(active){
			updateEditor();
		}
	}

	@Override
	public void update(InputInformation inputInformation) {
		if(active){
			transportMapID.update(inputInformation);
			transportMapX.update(inputInformation);
			transportMapY.update(inputInformation);
			String currentMapID = transportMapID.getInput();
			int currentMapX = transportMapX.getInput();
			int currentMapY = transportMapY.getInput();
			if(!currentMapID.equals(mapID)
					|| currentMapX != mapX
					|| currentMapY != mapY){
				updateEditor();
			}
		}
	}
	
	private void updateEditor(){
		mapID = transportMapID.getInput();
		mapX= transportMapX.getInput();
		mapY = transportMapY.getInput();
		exitPlacementUIGroup.setTarget(mapID, mapX, mapY);
	}
	
	

	@Override
	public void draw() {
		if(active){
			SpriteManager.getInstance().getSprite(SpriteID.BACKGROUNDS).draw(5, x, y, 64, 50);
			DrawTextManager.getInstance().drawText(x + 2, y + 2, "ID", 2);
			transportMapID.draw();
			DrawTextManager.getInstance().drawText(x + 2, y + 14, "Y", 2);
			transportMapX.draw();
			DrawTextManager.getInstance().drawText(x + 2, y + 26, "X", 2);
			transportMapY.draw();
			DrawTextManager.getInstance().drawText(x + 2, y + 38, "TARGET", 6);
		}
	}

}
