package dinodungeons.gfx.text;

public class DrawTextManager {
	
	private static final int letterTileWidth = 8;
	private static final int letterTileHeight = 8;
	
	private static final int letterSpacing = 2;

	private LetterTileMap letterMap;
	
	public DrawTextManager(String textTexturePath){
		letterMap = new LetterTileMap(textTexturePath, letterTileWidth, letterTileHeight);
	}
	
	public void DrawText(int posX, int posY, String text, int maxLength){
		int count = 0;
		String actualText = text;
		if(text.length() > maxLength){
			actualText = text.substring(0, maxLength);
		}
		for(char c : actualText.toCharArray()){
			Letter letter = Letter.fromCharacter(c);
			if(!letter.equals(Letter.SPACE)){
				letterMap.draw(letter, posX + count * (letterTileWidth + letterSpacing), posY);
			}
			count++;
		}
	}

}
