package dinodungeons.game.data.map.objects;

import java.util.Arrays;
import java.util.Collection;

import dinodungeons.editor.map.change.SignPlacementMapChange.SignType;
import dinodungeons.game.gameobjects.text.TextBoxContent;

public class SignMapObject extends MapObject {
	
	SignType signType;
	
	TextBoxContent[] contents;
	
	public SignMapObject() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getEditorInfo() {
		return signType.getStringRepresentation();
	}

	public SignType getSignType() {
		return signType;
	}

	public void setSignType(SignType signType) {
		this.signType = signType;
	}
	
	public Collection<TextBoxContent> getTextBoxContent() {
		return Arrays.asList(contents);
	}
	
	public void setTextBoxContent(TextBoxContent... contents) {
		this.contents = new TextBoxContent[contents.length];
		for(int i = 0; i < contents.length; i++) {
			this.contents[i] = contents[i];
		}
	}
	
	public void setTextBoxContent(Collection<TextBoxContent> contents) {
		this.contents = new TextBoxContent[contents.size()];
		int counter = 0;
		for(TextBoxContent tbc :contents) {
			this.contents[counter++] = tbc;
		}
	}
	
}
