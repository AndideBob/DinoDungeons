package dinodungeons.main;

import java.util.HashSet;

import dinodungeons.editor.Editor;
import lwjgladapter.GameWindow;
import lwjgladapter.GameWindowConstants;
import lwjgladapter.datatypes.Color;
import lwjgladapter.game.Game;
import lwjgladapter.logging.Logger;

public class Main {
	
	private static boolean startAsEditor = false;
	
	public static void main(String[] args) {
		GameWindowConstants.DEFAULT_SCREEN_WIDTH = 256;
		GameWindowConstants.DEFAULT_SCREEN_HEIGHT = 256;
		GameWindow gameWindow = new GameWindow(512, 512, Color.BLACK, "The great Dreamer");
		handleArgs(args);
		Game gameToRun = startAsEditor ? new Editor() : null;
		gameWindow.run(gameToRun);
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
