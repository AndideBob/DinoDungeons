package dinodungeons.gfx.text;

import lwjgladapter.gfx.SpriteMap;

public class LetterTileMap extends SpriteMap {

	public LetterTileMap(String filename, int tileSizeX, int tileSizeY) {
		super(filename, tileSizeX, tileSizeY);
	}
	
	public void draw(Letter letter, int posX, int posY){
		super.draw(letter.getIdOnTileMap(), posX, posY);
	}

}
