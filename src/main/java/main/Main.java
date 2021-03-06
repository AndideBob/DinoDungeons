package main;

import java.util.HashSet;

import dinodungeons.editor.Editor;
import dinodungeons.game.DinoDungeons;
import lwjgladapter.GameWindow;
import lwjgladapter.GameWindowConstants;
import lwjgladapter.datatypes.Color;
import lwjgladapter.logging.Logger;

public class Main {
	
	private static boolean startAsEditor = false;
	
	public static void main(String[] args) {
		handleArgs(args);
		if(startAsEditor){
			GameWindowConstants.DEFAULT_SCREEN_WIDTH = 320;
			GameWindowConstants.DEFAULT_SCREEN_HEIGHT = 240;
			GameWindow gameWindow = new GameWindow(640, 480, Color.BLACK, "Dino Dungeons Editor", 30);
			gameWindow.run(new Editor());
		}
		else{
			GameWindowConstants.DEFAULT_SCREEN_WIDTH = 256;
			GameWindowConstants.DEFAULT_SCREEN_HEIGHT = 256;
			GameWindow gameWindow = new GameWindow(512, 512, Color.BLACK, "Dino Dungeons", 60);
			gameWindow.run(new DinoDungeons());
		}
	}
	
	private static void handleArgs(String[] args){
		HashSet<String> argSet = new HashSet<>();
		String logString = "";
		for(int i = 0; i < args.length; i++){
			logString += args[i];
			argSet.add(args[i]);
			if(i < args.length - 1){
				logString += ",";
			}
		}
		Logger.logDebug("Arguments:[" + logString + "]");
		if(argSet.contains("-editor")){
			startAsEditor = true;
		}
	}

}
