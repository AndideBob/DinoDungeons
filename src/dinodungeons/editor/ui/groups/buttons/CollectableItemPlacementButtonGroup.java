package dinodungeons.editor.ui.groups.buttons;

import java.util.ArrayList;
import java.util.Collection;

import dinodungeons.editor.Editor;
import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.mapchange.CollectableItemChangeButton;
import dinodungeons.game.gameobjects.player.ItemID;

public class CollectableItemPlacementButtonGroup extends UIButtonGroup {
	
	public CollectableItemPlacementButtonGroup(final Editor editorHandle){
		super(editorHandle);
	}

	@Override
	protected Collection<? extends BaseButton> initializeButtons(final Editor editorHandle) {
		ArrayList<BaseButton> buttons = new ArrayList<>();
		buttons.add(new CollectableItemChangeButton(256, 208, editorHandle, this, ItemID.CLUB));
		buttons.add(new CollectableItemChangeButton(272, 208, editorHandle, this, ItemID.BOOMERANG));
		buttons.add(new CollectableItemChangeButton(288, 208, editorHandle, this, ItemID.ITEM_2));
		buttons.add(new CollectableItemChangeButton(304, 208, editorHandle, this, ItemID.BOMB));
		buttons.add(new CollectableItemChangeButton(256, 192, editorHandle, this, ItemID.ITEM_4));
		buttons.add(new CollectableItemChangeButton(272, 192, editorHandle, this, ItemID.ITEM_5));
		buttons.add(new CollectableItemChangeButton(288, 192, editorHandle, this, ItemID.ITEM_6));
		buttons.add(new CollectableItemChangeButton(304, 192, editorHandle, this, ItemID.ITEM_7));
		buttons.add(new CollectableItemChangeButton(256, 176, editorHandle, this, ItemID.ITEM_8));
		buttons.add(new CollectableItemChangeButton(272, 176, editorHandle, this, ItemID.ITEM_9));
		buttons.add(new CollectableItemChangeButton(288, 176, editorHandle, this, ItemID.ITEM_A));
		buttons.add(new CollectableItemChangeButton(304, 176, editorHandle, this, ItemID.ITEM_B));
		buttons.add(new CollectableItemChangeButton(256, 160, editorHandle, this, ItemID.MIRROR));
		buttons.add(new CollectableItemChangeButton(272, 160, editorHandle, this, ItemID.ITEM_D));
		buttons.add(new CollectableItemChangeButton(288, 160, editorHandle, this, ItemID.ITEM_E));
		buttons.add(new CollectableItemChangeButton(304, 160, editorHandle, this, ItemID.ITEM_F));
		return buttons;
	}

}
