package dinodungeons.editor.ui.input;

import dinodungeons.editor.Editor;
import dinodungeons.editor.EditorControlUtil;
import dinodungeons.editor.ui.UIElement;
import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.window.ButtonWindowCancel;
import dinodungeons.editor.ui.buttons.window.ButtonWindowConfirm;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.gameobjects.text.TextBoxContent;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;
import dinodungeons.gfx.text.DrawTextManager;
import lwjgladapter.input.ButtonState;
import lwjgladapter.input.InputManager;
import lwjgladapter.input.KeyboardKey;

public class PageTextInputWindow extends UIElement implements EditorWindow {
	
	private static final int windowWidth = 224;
	private static final int windowHeight = 80;
	
	private BaseButton confirmButton;
	private BaseButton cancelButton;
	private TextInputLine inputLine1;
	private TextInputLine inputLine2;
	private TextInputLine inputLine3;
	private TextInputLine inputLine4;
	private TextInputLine inputLine5;
	
	private int positionX;
	
	private int positionY;
	
	private boolean isOpen;
	
	private String prompt;
	private int promptXPosition;
	
	private int currentPage;
	
	private Editor editorHandle;

	public PageTextInputWindow(int positionX, int positionY, Editor editorHandle) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.editorHandle = editorHandle;
		
		
		inputLine1 = new TextInputLine(positionX + 8, positionY + 64, 21);
		inputLine2 = new TextInputLine(positionX + 8, positionY + 54, 21);
		inputLine3 = new TextInputLine(positionX + 8, positionY + 44, 21);
		inputLine4 = new TextInputLine(positionX + 8, positionY + 34, 21);
		inputLine5 = new TextInputLine(positionX + 8, positionY + 24, 21);
		
		confirmButton = new ButtonWindowConfirm(positionX + windowWidth - 24, positionY + 8, this);
		cancelButton = new ButtonWindowCancel(positionX + 8, positionY + 8, this);
		
		setPrompt("Input");
		close();
	}

	@Override
	public void update(InputInformation inputInformation) {
		if(isOpen){
			inputLine1.update(inputInformation);
			inputLine2.update(inputInformation);
			inputLine3.update(inputInformation);
			inputLine4.update(inputInformation);
			inputLine5.update(inputInformation);
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
			inputLine1.draw();
			inputLine1.draw();
			inputLine1.draw();
			inputLine1.draw();
			inputLine1.draw();
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
	
	public void setCurrentPage(int currentPage){
		this.currentPage = currentPage;
	}
	
	public void setInput(TextBoxContent content){
		inputLine1.setInput(content.getLine(0));
		inputLine2.setInput(content.getLine(1));
		inputLine3.setInput(content.getLine(2));
		inputLine4.setInput(content.getLine(3));
		inputLine5.setInput(content.getLine(4));
	}
	
	@Override
	public void closeConfirm(){
		TextBoxContent result = new TextBoxContent();
		result.setLine(0, inputLine1.getInput());
		result.setLine(1, inputLine2.getInput());
		result.setLine(2, inputLine3.getInput());
		result.setLine(3, inputLine4.getInput());
		result.setLine(4, inputLine5.getInput());
		//TODO: Actually react to the input using currentPage to know which page you are on
		//editorHandle.reactToInput(inputLine.getInput(), usage);
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
		inputLine1.setColliderActive(true);
		inputLine1.setSelected(true);
		inputLine2.setColliderActive(true);
		inputLine3.setColliderActive(true);
		inputLine4.setColliderActive(true);
		inputLine5.setColliderActive(true);
	}
	
	private void close(){
		isOpen = false;
		confirmButton.setColliderActive(false);
		cancelButton.setColliderActive(false);
		inputLine1.setColliderActive(false);
		inputLine2.setColliderActive(false);
		inputLine3.setColliderActive(false);
		inputLine4.setColliderActive(false);
		inputLine5.setColliderActive(false);
	}

}
