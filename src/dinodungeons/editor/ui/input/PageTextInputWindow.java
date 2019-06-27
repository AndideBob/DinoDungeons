package dinodungeons.editor.ui.input;

import java.util.ArrayList;
import java.util.List;

import dinodungeons.editor.Editor;
import dinodungeons.editor.EditorControlUtil;
import dinodungeons.editor.map.change.SignPlacementMapChange.SignType;
import dinodungeons.editor.ui.UIElement;
import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.window.ButtonWindowCancel;
import dinodungeons.editor.ui.buttons.window.ButtonWindowConfirm;
import dinodungeons.editor.ui.buttons.window.ButtonWindowPageAdd;
import dinodungeons.editor.ui.buttons.window.ButtonWindowPageBackwards;
import dinodungeons.editor.ui.buttons.window.ButtonWindowPageForward;
import dinodungeons.editor.ui.buttons.window.ButtonWindowPageRemove;
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
	private static final int windowHeight = 160;
	private static final int promptYPosition = windowHeight - 18;
	
	private List<BaseButton> windowButtons;
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
	private String currentPageText = "";
	private int currentPageXPosition = 0;
	
	private SignType signType;
	
	private int currentPage;
	private ArrayList<TextBoxContent> contents;
	
	private Editor editorHandle;

	public PageTextInputWindow(int positionX, int positionY, Editor editorHandle) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.editorHandle = editorHandle;
		this.contents = new ArrayList<>();
		this.windowButtons = new ArrayList<>();
		this.signType = SignType.SIGN;
		
		inputLine1 = new TextInputLine(positionX + 8, positionY + 120, 21, true);
		inputLine2 = new TextInputLine(positionX + 8, positionY + 106, 21, true);
		inputLine3 = new TextInputLine(positionX + 8, positionY + 92, 21, true);
		inputLine4 = new TextInputLine(positionX + 8, positionY + 78, 21, true);
		inputLine5 = new TextInputLine(positionX + 8, positionY + 64, 21, true);
		
		//ConfirmButton
		windowButtons.add(new ButtonWindowConfirm(positionX + windowWidth - 24, positionY + 8, this));
		//CancelButton
		windowButtons.add(new ButtonWindowCancel(positionX + 8, positionY + 8, this));
		int middleX = positionX + windowWidth / 2;
		//PageButtons
		windowButtons.add(new ButtonWindowPageAdd(middleX - 18, positionY + 8, this));
		windowButtons.add(new ButtonWindowPageRemove(middleX + 2, positionY + 8, this));
		windowButtons.add(new ButtonWindowPageForward(middleX - 38, positionY + 8, this));
		windowButtons.add(new ButtonWindowPageBackwards(middleX + 22, positionY + 8, this));
		
		setPrompt("Input");
		resetPages();
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
			for(BaseButton button : windowButtons){
				button.update(inputInformation);
			}
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
			DrawTextManager.getInstance().drawText(positionX + promptXPosition, positionY + promptYPosition, prompt, 12);
			inputLine1.draw();
			inputLine2.draw();
			inputLine3.draw();
			inputLine4.draw();
			inputLine5.draw();
			DrawTextManager.getInstance().drawText(positionX + currentPageXPosition, positionY + 32, currentPageText, 20);
			for(BaseButton button : windowButtons){
				button.draw();
			}
			SpriteManager.getInstance().getSprite(SpriteID.BACKGROUNDS).draw(4, positionX + 8, positionY + 32, 24, 24);
			switch(signType){
			case SIGN:
				SpriteManager.getInstance().getSprite(SpriteID.SIGNS).draw(8, positionX + 12, positionY + 36);
				break;
			case STONE_BLOCK:
				SpriteManager.getInstance().getSprite(SpriteID.SIGNS).draw(0, positionX + 12, positionY + 36);
				break;
			}
		}
	}
	
	public void setSignType(SignType signType){
		this.signType = signType;
	}
	
	public void setPrompt(String prompt){
		if(prompt.length() > 12){
			this.prompt = prompt.substring(0,12);
		}
		else{
			this.prompt = prompt;
		}
		promptXPosition = (windowWidth / 2) - (int)((0.5f * prompt.length()) * 10);
	}
	
	public void setInput(TextBoxContent content){
		inputLine1.setInput(content.getLine(0));
		inputLine2.setInput(content.getLine(1));
		inputLine3.setInput(content.getLine(2));
		inputLine4.setInput(content.getLine(3));
		inputLine5.setInput(content.getLine(4));
	}
	
	public void switchPageUp() {
		saveCurrentPage();
		currentPage = (currentPage + 1) % contents.size();
		setInputToCurrentPage();
	}
	
	public void switchPageDown() {
		saveCurrentPage();
		currentPage--;
		if(currentPage < 0) {
			currentPage += contents.size();
		}
		setInputToCurrentPage();
	}
	
	public void addPageBehindCurrent() {
		saveCurrentPage();
		TextBoxContent newPage = new TextBoxContent();
		currentPage = currentPage + 1;
		contents.add(currentPage, newPage);
		setInputToCurrentPage();
	}
	
	public void deleteCurrentPage() {
		if(contents.size() > 1) {
			contents.remove(currentPage);
			if(currentPage >= contents.size()) {
				currentPage -= 1;
			}
			setInputToCurrentPage();
		}
		else {
			resetPages();
		}
	}
	
	private void setInputToCurrentPage() {
		inputLine1.setInput(contents.get(currentPage).getLine(0));
		inputLine2.setInput(contents.get(currentPage).getLine(1));
		inputLine3.setInput(contents.get(currentPage).getLine(2));
		inputLine4.setInput(contents.get(currentPage).getLine(3));
		inputLine5.setInput(contents.get(currentPage).getLine(4));
		currentPageText = "page: " + currentPage;
		currentPageXPosition = (windowWidth / 2) - (int)((0.5f * currentPageText.length()) * 10);
	}
	
	public void saveCurrentPage() {
		contents.get(currentPage).setLine(0, inputLine1.getInput());
		contents.get(currentPage).setLine(1, inputLine2.getInput());
		contents.get(currentPage).setLine(2, inputLine3.getInput());
		contents.get(currentPage).setLine(3, inputLine4.getInput());
		contents.get(currentPage).setLine(4, inputLine5.getInput());
	}
	
	@Override
	public void closeConfirm(){
		saveCurrentPage();
		editorHandle.reactToInput(signType, contents);
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
		for(BaseButton button : windowButtons){
			button.setColliderActive(true);
		}
		inputLine1.setColliderActive(true);
		inputLine1.setSelected(true);
		inputLine2.setColliderActive(true);
		inputLine3.setColliderActive(true);
		inputLine4.setColliderActive(true);
		inputLine5.setColliderActive(true);
		resetPages();
	}
	
	private void resetPages() {
		currentPage = 0;
		contents.clear();
		contents.add(new TextBoxContent());
		setInputToCurrentPage();
	}
	
	private void close(){
		isOpen = false;
		for(BaseButton button : windowButtons){
			button.setColliderActive(false);
		}
		inputLine1.setColliderActive(false);
		inputLine2.setColliderActive(false);
		inputLine3.setColliderActive(false);
		inputLine4.setColliderActive(false);
		inputLine5.setColliderActive(false);
	}

}
