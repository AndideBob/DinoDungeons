package dinodungeons.editor.ui.buttons.mapchange;

import dinodungeons.editor.Editor;
import dinodungeons.editor.map.change.SignPlacementMapChange.SignType;
import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.ButtonSprite;
import dinodungeons.editor.ui.groups.buttons.UIButtonGroup;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.gameobjects.text.TextBoxContent;

public class SignChangeButton extends BaseButton {
	
	private UIButtonGroup belongingButtonGroup;
	
	private Editor editorHandle;
	
	private SignType signType;
	
	public SignChangeButton(int positionX, int positionY, final Editor editorHandle, final UIButtonGroup belongingButtonGroup, SignType signType) {
		super(positionX, positionY, getButtonSpriteForSignType(signType));
		this.signType = signType;
		this.editorHandle = editorHandle;
		this.belongingButtonGroup = belongingButtonGroup;
	}

	@Override
	protected void onClick() {
		belongingButtonGroup.unpressAll();
		//setPressed(true);
		editorHandle.waitForPageInput(signType, "Sign", new TextBoxContent());
	}
	
	private static ButtonSprite getButtonSpriteForSignType(SignType signType){
		switch (signType) {
		case SIGN:
			return ButtonSprite.SIGN_WOODEN;
		case STONE_BLOCK:
			return ButtonSprite.SIGN_STONE;
		}
		return ButtonSprite.CANCEL;
	}

	@Override
	protected void updateInternal(InputInformation inputInformation) {
		//Do nothing
	}

	@Override
	protected void drawInternal() {
		//Do nothing
	}
	
	public enum StaticObjectType{
		IMMOVABLE_BLOCK,
		SPIKES_METAL,
		SPIKES_WOOD,
		DESTRUCTABLE_GRASS,
		DESTRUCTABLE_STONE
	}

}
