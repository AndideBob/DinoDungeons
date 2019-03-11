package dinodungeons.game.utils;

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

}
