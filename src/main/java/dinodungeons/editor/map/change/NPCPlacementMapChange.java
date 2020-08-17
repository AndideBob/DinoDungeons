package dinodungeons.editor.map.change;

import java.util.Collection;

import dinodungeons.game.data.map.ScreenMap;
import dinodungeons.game.data.map.objects.EmptyMapObject;
import dinodungeons.game.data.map.objects.MapObject;
import dinodungeons.game.data.map.objects.NonPlayerCharacterMapObject;
import dinodungeons.game.data.map.objects.NonPlayerCharacterMapObject.NPCType;
import dinodungeons.game.gameobjects.text.TextBoxContent;

public class NPCPlacementMapChange extends AbstractMapChange {
	
	NPCType npcType;
	
	TextBoxContent[] contents;
	
	MapObject previousObject;
	
	private boolean shouldRevert;

	public NPCPlacementMapChange(int x, int y, NPCType npcType, Collection<TextBoxContent> contents) {
		super(x, y);
		this.npcType = npcType;
		this.contents = new TextBoxContent[contents.size()];
		int counter = 0;
		for(TextBoxContent tbc : contents) {
			this.contents[counter++] = tbc;
		}
		previousObject = null;
		shouldRevert = false;
	}

	@Override
	public void applyTo(ScreenMap map) {
		if(!shouldRevert) {
			NonPlayerCharacterMapObject npc = new NonPlayerCharacterMapObject();
			npc.setNPCType(npcType);;
			npc.setTextBoxContent(contents);
			previousObject = map.getMapObjectForPosition(getX(), getY());
			map.setMapObjectForPosition(getX(), getY(), npc);
		}
		else {
			if(previousObject == null) {
				map.setMapObjectForPosition(getX(), getY(), new EmptyMapObject());
			}
			else {
				map.setMapObjectForPosition(getX(), getY(), previousObject);
			}
		}
		
	}

	@Override
	public void revert() {
		shouldRevert = !shouldRevert;
	}
}
