package dinodungeons.editor.ui.buttons.mapchange;

import dinodungeons.editor.Editor;
import dinodungeons.editor.map.change.MapChangeType;
import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.ButtonSprite;
import dinodungeons.editor.ui.groups.buttons.UIButtonGroup;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.gameobjects.player.ItemID;

public class CollectableItemChangeButton extends BaseButton {

	private Editor editorHandle;
	
	private UIButtonGroup belongingButtonGroup;
	
	private ItemID itemID;
	
	public CollectableItemChangeButton(int positionX, int positionY, final Editor editorHandle, final UIButtonGroup belongingButtonGroup, ItemID itemID) {
		super(positionX, positionY, getButtonSpriteForItemID(itemID));
		this.itemID = itemID;
		this.editorHandle = editorHandle;
		this.belongingButtonGroup = belongingButtonGroup;
	}

	@Override
	protected void onClick() {
		belongingButtonGroup.unpressAll();
		setPressed(true);
		editorHandle.setMapChange(MapChangeType.COLLECTABLE_ITEM, itemID.getSaveRepresentation());
	}
	
	private static ButtonSprite getButtonSpriteForItemID(ItemID itemID){
		switch (itemID) {
		case BOMB:
			return ButtonSprite.ITEM_BOMB;
		case BOOMERANG:
			return ButtonSprite.ITEM_BOOMERANG;
		case CLUB:
			return ButtonSprite.ITEM_CLUB;
		case ITEM_2:
			return ButtonSprite.ITEM_2;
		case ITEM_4:
			return ButtonSprite.ITEM_4;
		case ITEM_5:
			return ButtonSprite.ITEM_5;
		case ITEM_6:
			return ButtonSprite.ITEM_6;
		case ITEM_7:
			return ButtonSprite.ITEM_7;
		case ITEM_8:
			return ButtonSprite.ITEM_8;
		case ITEM_9:
			return ButtonSprite.ITEM_9;
		case ITEM_A:
			return ButtonSprite.ITEM_A;
		case ITEM_B:
			return ButtonSprite.ITEM_B;
		case ITEM_D:
			return ButtonSprite.ITEM_D;
		case ITEM_E:
			return ButtonSprite.ITEM_E;
		case ITEM_F:
			return ButtonSprite.ITEM_F;
		case MIRROR:
			return ButtonSprite.ITEM_MIRROR;
		}
		return ButtonSprite.SELECTION_COLLECTABLE_ITEMS;
	}

	@Override
	protected void updateInternal(InputInformation inputInformation) {
		//Do nothing
	}

	@Override
	protected void drawInternal() {
		//Do nothing
	}

}
