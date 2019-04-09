package dinodungeons.editor.ui;

import java.util.ArrayList;

import dinodungeons.editor.Editor;
import dinodungeons.editor.EditorState;
import dinodungeons.editor.ui.buttons.filemanagement.ButtonLoadMap;
import dinodungeons.editor.ui.buttons.filemanagement.ButtonNewMap;
import dinodungeons.editor.ui.buttons.filemanagement.ButtonSaveMap;
import dinodungeons.editor.ui.buttons.selection.BaseLayerSelectionButton;
import dinodungeons.editor.ui.buttons.selection.CollectableItemPlacementSelectionButton;
import dinodungeons.editor.ui.buttons.selection.SelectionButton;
import dinodungeons.editor.ui.buttons.selection.SwitchPlacementSelectionButton;
import dinodungeons.editor.ui.input.InputUsage;
import dinodungeons.editor.ui.input.TextInputWindow;
import dinodungeons.editor.ui.pointer.MouseHandler;
import dinodungeons.game.data.gameplay.InputInformation;

public class EditorUIHandler {
	
	private ArrayList<UIElement> uiElements;
	
	private TextInputWindow textInputWindow;

	public EditorUIHandler(Editor editorHandle) {
		initialize(editorHandle);
	}
	
	private void initialize(Editor editorHandle){
		uiElements = new ArrayList<>();
		//MousePointer
		uiElements.add(MouseHandler.getInstance());
		//New/Load/Save
		uiElements.add(new ButtonNewMap(0, 224, editorHandle));
		uiElements.add(new ButtonLoadMap(16, 224, editorHandle));
		uiElements.add(new ButtonSaveMap(32, 224, editorHandle));
		//Placement Selections
		uiElements.add(new BaseLayerSelectionButton(64, 224, this, editorHandle));
		uiElements.add(new CollectableItemPlacementSelectionButton(80, 224, this, editorHandle));
		uiElements.add(new SwitchPlacementSelectionButton(96, 224, this, editorHandle));
		//InputWindow (add last)
		textInputWindow = new TextInputWindow(96, 102, editorHandle);
		uiElements.add(textInputWindow);
		switchOffAllSelections();
	}
	
	public void switchOffAllSelections(){
		for(UIElement element : uiElements){
			if(element instanceof SelectionButton){
				((SelectionButton) element).select(false);
			}
		}
	}
	
	public void update(EditorState editorState, InputInformation currentInput){
		switch(editorState){
		case DEFAULT:
			updateAll(currentInput);
			break;
		case WAIT_FOR_INPUT:
			updateInputWindow(currentInput);
			break;
		}
		
	}
	
	private void updateAll(InputInformation currentInput){
		for(UIElement uiElement : uiElements) {
			uiElement.update(currentInput);
		}
	}
	
	private void updateInputWindow(InputInformation currentInput){
		MouseHandler.getInstance().update(currentInput);
		textInputWindow.update(currentInput);
	}
	
	public void draw(){
		for(UIElement uiElement : uiElements) {
			uiElement.draw();
		}
	}
	
	public void openInputWindow(String prompt, InputUsage usage){
		textInputWindow.setPrompt(prompt);
		textInputWindow.setUsage(usage);
		textInputWindow.open();
	}

}
