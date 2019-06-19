package dinodungeons.game.gameobjects.text;

import dinodungeons.game.data.DinoDungeonsConstants;
import lwjgladapter.logging.Logger;

public class TextBoxContent {

	private String[] lines;
	
	private int numberOfCharacters;
	
	public TextBoxContent() {
		lines = new String[DinoDungeonsConstants.textboxLineAmount];
	}

	public void setLine(int index, String content){
		if(index < 0 || index >= lines.length){
			Logger.logError("Tried to set line '" + index + "' of textbox, which is out of bounds!");
			return;
		}
		if(content == null){
			Logger.logError("Tried to set line '" + index + "' to NULL!");
			lines[index] = "";
		}
		else if(content.length() > DinoDungeonsConstants.textboxLettersPerLine){
			Logger.logError("Tried to set line '" + index + "' of textbox with a text of length " + content.length() + " while only " + DinoDungeonsConstants.textboxLettersPerLine + " characters are allowed!");
			lines[index] = content.substring(0, DinoDungeonsConstants.textboxLettersPerLine);
		}
		else{
			lines[index] = content;
		}
		updateNumberOfTotalCharacters();
	}
	
	private void updateNumberOfTotalCharacters(){
		numberOfCharacters = 0;
		for(String line : lines){
			if(line != null){
				numberOfCharacters += line.length();
			}
		}
	}
	
	public int getNumberOfCharacters(){
		return numberOfCharacters;
	}
	
	public String getLine(int index){
		if(index >= lines.length){
			Logger.logError("Tried to access line '" + index + "' but only " + DinoDungeonsConstants.textboxLineAmount + " lines are allowed!");
		}
		else if(lines[index] != null){
			return lines[index];
		}
		return "";
	}
}
