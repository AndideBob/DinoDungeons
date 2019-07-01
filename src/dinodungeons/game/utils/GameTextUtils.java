package dinodungeons.game.utils;

import java.util.ArrayList;

import dinodungeons.game.gameobjects.player.ItemID;
import dinodungeons.game.gameobjects.text.TextBoxContent;
import lwjgladapter.logging.Logger;

public class GameTextUtils {

	public static String getNumberWithLeadingZeroes(int number, int amountOfDigits){
		String numberString = "" + number;
		if(numberString.length() > amountOfDigits){
			Logger.logError("Trying to display " + number + " in only " + amountOfDigits + " digits! Value was cut off!");
			numberString = numberString.substring(0,amountOfDigits);
		}
		String leadingZeros = "";
		for(int i = 0; i < amountOfDigits; i++){
			leadingZeros += "0";
		}
		return (leadingZeros + numberString).substring(numberString.length());
	}
	
	public static ArrayList<String> splitTextToLines(String text, int lineLength){
		ArrayList<String> lines = new ArrayList<>();
		String[] words = text.split(" ");
		String line = "";
		for(String word : words){
			if(line.length() + word.length() + 1 > lineLength){
				lines.add(line);
				line = "";
			}
			line += word + " ";
		}
		if(!line.isEmpty()){
			lines.add(line);
		}
		return lines;
	}

	public static ArrayList<TextBoxContent> getItemCollectionTextBox(ItemID item) {
		TextBoxContent collectionPage = new TextBoxContent();
		TextBoxContent descriptionPage = new TextBoxContent();
		switch(item) {
		case BOMB:
			collectionPage.setText("You found Bombs!");
			descriptionPage.setText("Use them to blow up fragile objects. But don't stand near them when they blow!");
			break;
		case BOOMERANG:
			collectionPage.setText("You found the Boomerang!");
			descriptionPage.setText("It will always return to you and it can even help you collect hard to reach things!");
			break;
		case CLUB:
			collectionPage.setText("You found the Club!");
			descriptionPage.setText("A cavepersons best friend! Now go smash something!");
			break;
		case ITEM_4:
			break;
		case ITEM_5:
			break;
		case ITEM_6:
			break;
		case ITEM_7:
			break;
		case ITEM_8:
			break;
		case ITEM_9:
			break;
		case ITEM_A:
			break;
		case ITEM_B:
			break;
		case ITEM_D:
			break;
		case ITEM_E:
			break;
		case ITEM_F:
			break;
		case MIRROR:
			collectionPage.setText("You found Mirror!");
			descriptionPage.setText("It helps you see things that are nomally hidden!");
			break;
		case TORCH:
			collectionPage.setText("You found Fire!");
			descriptionPage.setText("It can be used to light candles and burn all kinds of things!");
			break;
		default:
			break;
		}
		ArrayList<TextBoxContent> result = new ArrayList<>();
		result.add(collectionPage);
		result.add(descriptionPage);
		return result;
	}
}
