package dinodungeons.editor.ui.groups.buttons;

import java.util.ArrayList;
import java.util.Collection;

import dinodungeons.editor.Editor;
import dinodungeons.editor.map.change.SignPlacementMapChange.SignType;
import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.mapchange.NPCChangeButton;
import dinodungeons.editor.ui.buttons.mapchange.SignChangeButton;
import dinodungeons.game.data.map.objects.NonPlayerCharacterMapObject.NPCType;
import dinodungeons.gfx.text.DrawTextManager;

public class SignPlacementButtonGroup extends UIButtonGroup {
	
	public SignPlacementButtonGroup(final Editor editorHandle){
		super(editorHandle);
	}

	@Override
	protected Collection<? extends BaseButton> initializeButtons(final Editor editorHandle) {
		ArrayList<BaseButton> buttons = new ArrayList<>();
		//SIGNS
		buttons.add(new SignChangeButton(256, 208, editorHandle, this, SignType.SIGN));
		buttons.add(new SignChangeButton(272, 208, editorHandle, this, SignType.STONE_BLOCK));
		//NPCs
		buttons.add(new NPCChangeButton(256, 182, editorHandle, this, NPCType.DEFAULT_OLD_MAN));
		buttons.add(new NPCChangeButton(272, 182, editorHandle, this, NPCType.DEFAULT_TIKI_VILLAGER_A));
		buttons.add(new NPCChangeButton(288, 182, editorHandle, this, NPCType.DEFAULT_TIKI_VILLAGER_B));
		return buttons;
	}
	
	@Override
	public void draw(){
		super.draw();
		DrawTextManager.getInstance().drawText(256, 198, "NPC", 5);
	}

}
