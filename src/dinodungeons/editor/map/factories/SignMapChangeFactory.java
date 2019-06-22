package dinodungeons.editor.map.factories;

import dinodungeons.editor.map.change.SignPlacementMapChange;
import dinodungeons.editor.map.change.SignPlacementMapChange.SignType;
import dinodungeons.game.gameobjects.text.TextBoxContent;

import java.util.HashMap;

import dinodungeons.editor.map.change.AbstractMapChange;

public class SignMapChangeFactory extends AbstractMapChangeFactory{

	private HashMap<Integer, TextBoxContent> textBoxes;
	
	private SignType signType;
	
	public SignMapChangeFactory() {
		this.signType = SignType.SIGN;
	}

	@Override
	public AbstractMapChange buildMapChange(int x, int y) {
		return new SignPlacementMapChange(x, y, signType, textBoxes.values());
	}
	
	@Override
	protected void handleParam(String param, int index) {
		if(index == 0){
			signType = SignType.getByStringRepresentation(param);
		}
		else if(index == 1){
			String[] contents = param.split("|");
			for(int i = 0; i < contents.length; i++) {
				textBoxes.put(i, TextBoxContent.parseFromString(contents[i]));
			}
		}
		
	}
	
	

}
