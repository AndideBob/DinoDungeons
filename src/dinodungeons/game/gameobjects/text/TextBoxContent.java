package dinodungeons.game.gameobjects.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.utils.GameTextUtils;
import lwjgladapter.logging.Logger;

public class TextBoxContent {

	private String[] lines;
	
	private int numberOfCharacters;
	
	public TextBoxContent() {
		lines = new String[DinoDungeonsConstants.textboxLineAmount];
		for(int i = 0; i < lines.length; i++){
			lines[i] = "";
		}
		updateNumberOfTotalCharacters();
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
	
	@Override
	public String toString(){
		return parseToString(this);
	}
	
	public static String parseToString(TextBoxContent content) {
		StringBuilder builder = new StringBuilder();
		builder.append("<");
		builder.append(content.lines[0]);
		for(int i = 1; i < content.lines.length; i++) {
			builder.append("-");
			builder.append(content.lines[i]);
		}
		builder.append(">");
		return builder.toString();
	}
	
	public static TextBoxContent parseFromString(String string) {
		if(!string.startsWith("<") || !string.endsWith(">")) {
			throw new IllegalArgumentException("Could not parse '" + string + "' to TextBoxContent");
		}
		string = string.substring(1, string.length() - 1);
		String[] parts = string.split("-");
		TextBoxContent result = new TextBoxContent();
		for(int i = 0; i < parts.length && i < DinoDungeonsConstants.textboxLineAmount; i++) {
			result.setLine(i, parts[i]);
		}
		return result;
	}
	
	public static String parseMultipleToString(Collection<TextBoxContent> content) {
		StringBuilder builder = new StringBuilder();
		builder.append(content.stream().map(n -> parseToString(n)).collect(Collectors.joining("|")));
		return builder.toString();
	}
	
	public static ArrayList<TextBoxContent> parseStringToMultiple(String string) {
		String[] parts = string.split("\\|");
		ArrayList<TextBoxContent> result = new ArrayList<>();
		for(String s : parts) {
			result.add(parseFromString(s));
		}
		return result;
	}

	public void setText(String text) {
		ArrayList<String> textLines = GameTextUtils.splitTextToLines(text, DinoDungeonsConstants.textboxLettersPerLine);
		for(int i = 0; i < lines.length; i++) {
			if(textLines.isEmpty() || i >= textLines.size()) {
				setLine(i, "");
			}
			else {
				setLine(i, textLines.get(i));
			}
		}
	}
}
