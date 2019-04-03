package dinodungeons.editor.ui.input;

import dinodungeons.editor.Editor;
import dinodungeons.editor.EditorControlUtil;
import dinodungeons.editor.ui.UIElement;
import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.window.ButtonWindowCancel;
import dinodungeons.editor.ui.buttons.window.ButtonWindowConfirm;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import dinodungeons.gfx.text.DrawTextManager;
import lwjgladapter.input.ButtonState;
import lwjgladapter.input.InputManager;
import lwjgladapter.input.KeyboardKey;

public class TextInputWindow extends UIElement implements EditorWindow {
	
	private static final int windowWidth = 128;
	private static final int windowHeight = 68;
	
	private BaseButton confirmButton;
	private BaseButton cancelButton;
	private TextInputLine inputLine;
	
	private int positionX;
	
	private int positionY;
	
	private boolean isOpen;
	
	private InputUsage usage;
	private String prompt;
	private int promptXPosition;
	
	private Editor editorHandle;

	public TextInputWindow(int positionX, int positionY, Editor editorHandle) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.editorHandle = editorHandle;
		confirmButton = new ButtonWindowConfirm(positionX + windowWidth - 24, positionY + 8, this);
		cancelButton = new ButtonWindowCancel(positionX + 8, positionY + 8, this);
		inputLine = new TextInputLine(positionX + 44, positionY + 32, 4);
		setPrompt("Input");
		close();
	}

	@Override
	public void update(InputInformation inputInformation) {
		if(isOpen){
			inputLine.update(inputInformation);
			confirmButton.update(inputInformation);
			cancelButton.update(inputInformation);
			if(isOpen){
				if(InputManager.instance.getKeyState(KeyboardKey.KEY_ENTER) == ButtonState.RELEASED){
					closeConfirm();
				}
				else if(EditorControlUtil.getEscapePressed()){
					closeCancel();
				}
			}
		}
	}

	@Override
	public void draw() {
		if(isOpen){
			SpriteManager.getInstance().getSprite(SpriteID.BACKGROUNDS).draw(0, positionX, positionY, windowWidth, windowHeight);
			DrawTextManager.getInstance().drawText(positionX + promptXPosition, positionY + 50, prompt, 12);
			inputLine.draw();
			confirmButton.draw();
			cancelButton.draw();
		}
	}
	
	public void setPrompt(String prompt){
		if(prompt.length() > 12){
			this.prompt = prompt.substring(0,12);
		}
		else{
			this.prompt = prompt;
		}
		promptXPosition = 64 - (int)((0.5f * prompt.length()) * 10);
	}
	
	public void setUsage(InputUsage usage){
		this.usage = usage;
	}
	
	@Override
	public void closeConfirm(){
		editorHandle.reactToInput(inputLine.getInput(), usage);
		close();
	}
	
	@Override
	public void closeCancel(){
		editorHandle.reactToInput("", InputUsage.NOTHING);
		close();
	}
	
	@Override
	public void open(){
		isOpen = true;
		confirmButton.setColliderActive(true);
		cancelButton.setColliderActive(true);
	}
	
	private void close(){
		isOpen = false;
		confirmButton.setColliderActive(false);
		cancelButton.setColliderActive(false);
	}

}
