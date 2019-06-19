package dinodungeons.game.gameobjects.text;

import java.util.Collection;
import java.util.Collections;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.base.GameObjectTag;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import dinodungeons.gfx.text.DrawTextManager;
import lwjgladapter.input.ButtonState;
import lwjgladapter.physics.collision.base.Collider;

public class TextBoxObject extends GameObject {
	
	private TextBoxContent content;
	
	private float charactersShowing;
	private boolean allTextShowing;
	
	private boolean blinking;
	private int blinkTimer;
	
	private boolean closed;

	public TextBoxObject(TextBoxContent content) {
		super(GameObjectTag.TEXT_BOX);
		this.content = content;
		charactersShowing = 0;
		allTextShowing = content.getNumberOfCharacters() <= 0;
		closed = false;
	}

	@Override
	public void update(long deltaTimeInMs, InputInformation inputInformation) {
		if(!allTextShowing){
			charactersShowing += 1.0 * DinoDungeonsConstants.textboxCharactersPerSecond / deltaTimeInMs;
			if(charactersShowing >= content.getNumberOfCharacters()){
				allTextShowing = true;
			}
			else if(inputInformation.getA().equals(ButtonState.PRESSED) ||
					inputInformation.getStart().equals(ButtonState.PRESSED)){
				allTextShowing = true;
			}
		}
		else{
			blinkTimer += deltaTimeInMs;
			if(blinkTimer >= 350){
				blinkTimer -= 350;
				blinking = !blinking;
			}
			if(inputInformation.getA().equals(ButtonState.PRESSED) ||
					inputInformation.getStart().equals(ButtonState.PRESSED)){
				closed = true;
			}
		}
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		drawFrame(anchorX, anchorY);
		drawText(anchorX, anchorY);
	}
	
	private void drawFrame(int anchorX, int anchorY){
		int x = anchorX + DinoDungeonsConstants.textboxPosX;
		int y = anchorY + DinoDungeonsConstants.textboxPosY;		
		SpriteManager.getInstance().getSprite(SpriteID.BACKGROUNDS).draw(0, x + 4 , y + 4, DinoDungeonsConstants.textboxWidth - 8, DinoDungeonsConstants.textboxHeight - 8);
		for(int curY = 0; curY < DinoDungeonsConstants.textboxHeight; curY += 8){
			for(int curX = 0; curX < DinoDungeonsConstants.textboxWidth; curX += 8){
				int currentPart = getFramePart(curX, curY);
				if(currentPart >= 0){
					SpriteManager.getInstance().getSprite(SpriteID.UI_BORDERS).draw(currentPart, x + curX, y + curY);
				}
			}
		}
	}
	
	private void drawText(int anchorX, int anchorY){
		int x = anchorX + DinoDungeonsConstants.textboxPosX + 8;
		if(allTextShowing){
			for(int i = 0; i < DinoDungeonsConstants.textboxLineAmount; i++){
				int y = anchorY + DinoDungeonsConstants.textboxPosY + 8 + (4 - i) * 10;
				DrawTextManager.getInstance().drawText(x, y, content.getLine(i), DinoDungeonsConstants.textboxLettersPerLine);
			}
			if(blinking){
				DrawTextManager.getInstance().drawText(x + DinoDungeonsConstants.textboxWidth - 24, anchorY + DinoDungeonsConstants.textboxPosY + 8, "*", 1);
			}
		}
		else{
			int line = 0;
			int remainingCharacters = (int)Math.floor(charactersShowing);
			while(remainingCharacters > 0){
				int y = anchorY + DinoDungeonsConstants.textboxPosY + 8 + (4 - line) * 10;
				String lineText = content.getLine(line);
				if(lineText.length() > remainingCharacters){
					lineText = lineText.substring(0,remainingCharacters);
					remainingCharacters = 0;
				}
				else{
					remainingCharacters -= lineText.length();
					line++;
					if(line >= DinoDungeonsConstants.textboxLineAmount){
						remainingCharacters = 0;
					}
				}
				DrawTextManager.getInstance().drawText(x, y, lineText, DinoDungeonsConstants.textboxLettersPerLine);
			}
		}
	}
	
	private int getFramePart(int x, int y){
		if(x == 0){
			if(y == 0){
				return 12;
			}
			else if(y >= DinoDungeonsConstants.textboxHeight - 8){
				return 10;
			}
			else{
				return 5;
			}
		}
		else if(x >= DinoDungeonsConstants.textboxWidth - 8){
			if(y == 0){
				return 13;
			}
			else if(y >= DinoDungeonsConstants.textboxHeight - 8){
				return 11;
			}
			else{
				return 5;
			}
		}
		else if(y == 0 || y >= DinoDungeonsConstants.textboxHeight - 8){
			return 4;
		}
		return -1;
	}

	@Override
	public Collection<Collider> getColliders() {
		return Collections.emptyList();
	}

	@Override
	public boolean shouldBeDeleted(){
		return closed;
	}
}
