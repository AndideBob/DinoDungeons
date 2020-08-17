package dinodungeons.editor.map.factories;

import dinodungeons.editor.map.change.SignPlacementMapChange;
import dinodungeons.editor.map.change.SignPlacementMapChange.SignType;
import dinodungeons.game.gameobjects.text.TextBoxContent;

import java.util.ArrayList;

import dinodungeons.editor.map.change.AbstractMapChange;

public class SignMapChangeFactory extends AbstractMapChangeFactory{

	private ArrayList<TextBoxContent> textBoxes;
	
	private SignType signType;
	
	public SignMapChangeFactory() {
		this.signType = SignType.SIGN;
		this.textBoxes = new ArrayList<>();
	}

	@Override
	public AbstractMapChange buildMapChange(int x, int y) {
		return new SignPlacementMapChange(x, y, signType, textBoxes);
	}
	
	@Override
	protected void handleParam(String param, int index) {
		if(index == 0){
			signType = SignType.getByStringRepresentation(param);
		}
		else if(index == 1){
			textBoxes = TextBoxContent.parseStringToMultiple(param);
		}
		
	}
	
	

}
