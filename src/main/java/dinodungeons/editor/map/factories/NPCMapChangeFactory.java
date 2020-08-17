package dinodungeons.editor.map.factories;

import dinodungeons.game.data.map.objects.NonPlayerCharacterMapObject.NPCType;
import dinodungeons.game.gameobjects.text.TextBoxContent;

import java.util.ArrayList;

import dinodungeons.editor.map.change.AbstractMapChange;
import dinodungeons.editor.map.change.NPCPlacementMapChange;

public class NPCMapChangeFactory extends AbstractMapChangeFactory{

	private ArrayList<TextBoxContent> textBoxes;
	
	private NPCType npcType;
	
	public NPCMapChangeFactory() {
		this.npcType = NPCType.DEFAULT_OLD_MAN;
		this.textBoxes = new ArrayList<>();
	}

	@Override
	public AbstractMapChange buildMapChange(int x, int y) {
		return new NPCPlacementMapChange(x, y, npcType, textBoxes);
	}
	
	@Override
	protected void handleParam(String param, int index) {
		if(index == 0){
			npcType = NPCType.getByStringRepresentation(param);
		}
		else if(index == 1){
			textBoxes = TextBoxContent.parseStringToMultiple(param);
		}
		
	}
	
	

}
