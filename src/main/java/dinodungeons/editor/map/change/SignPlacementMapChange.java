package dinodungeons.editor.map.change;

import java.util.Collection;

import dinodungeons.game.data.map.ScreenMap;
import dinodungeons.game.data.map.objects.EmptyMapObject;
import dinodungeons.game.data.map.objects.MapObject;
import dinodungeons.game.data.map.objects.SignMapObject;
import dinodungeons.game.gameobjects.text.TextBoxContent;

public class SignPlacementMapChange extends AbstractMapChange {
	
	SignType signType;
	
	TextBoxContent[] contents;
	
	MapObject previousObject;
	
	private boolean shouldRevert;

	public SignPlacementMapChange(int x, int y, SignType signType, Collection<TextBoxContent> contents) {
		super(x, y);
		this.signType = signType;
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
			SignMapObject sign = new SignMapObject();
			sign.setSignType(signType);
			sign.setTextBoxContent(contents);
			previousObject = map.getMapObjectForPosition(getX(), getY());
			map.setMapObjectForPosition(getX(), getY(), sign);
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
	
	public enum SignType{
		STONE_BLOCK("STN_BLK"),
		SIGN("SIGN");
		
		private String stringRepresentation;
		
		private SignType(String stringRepresentation) {
			this.stringRepresentation = stringRepresentation;
		}
		
		public String getStringRepresentation() {
			return stringRepresentation;
		}
		
		public static SignType getByStringRepresentation(String stringRepresentation) {
			for(SignType type : values()) {
				if(type.stringRepresentation.equals(stringRepresentation)) {
					return type;
				}
			}
			return STONE_BLOCK;
		}
	}

}
