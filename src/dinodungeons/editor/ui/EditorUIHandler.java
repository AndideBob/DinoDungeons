package dinodungeons.editor.ui;

import java.util.ArrayList;

import dinodungeons.editor.Editor;
import dinodungeons.editor.EditorState;
import dinodungeons.editor.map.EditorMapManager;
import dinodungeons.editor.map.change.SignPlacementMapChange.SignType;
import dinodungeons.editor.ui.buttons.filemanagement.ButtonLoadMap;
import dinodungeons.editor.ui.buttons.filemanagement.ButtonNewMap;
import dinodungeons.editor.ui.buttons.filemanagement.ButtonSaveMap;
import dinodungeons.editor.ui.buttons.selection.BaseLayerSelectionButton;
import dinodungeons.editor.ui.buttons.selection.CollectableItemPlacementSelectionButton;
import dinodungeons.editor.ui.buttons.selection.DoorPlacementSelectionButton;
import dinodungeons.editor.ui.buttons.selection.EnemyPlacementSelectionButton;
import dinodungeons.editor.ui.buttons.selection.EraserSelectionButton;
import dinodungeons.editor.ui.buttons.selection.ExitPlacementSelectionButton;
import dinodungeons.editor.ui.buttons.selection.SelectionButton;
import dinodungeons.editor.ui.buttons.selection.SignPlacementSelectionButton;
import dinodungeons.editor.ui.buttons.selection.StaticObjectPlacementSelectionButton;
import dinodungeons.editor.ui.buttons.selection.SwitchPlacementSelectionButton;
import dinodungeons.editor.ui.buttons.selection.TileSetSelectionButton;
import dinodungeons.editor.ui.groups.general.MapSettingsUIGroup;
import dinodungeons.editor.ui.input.InputUsage;
import dinodungeons.editor.ui.input.PageTextInputWindow;
import dinodungeons.editor.ui.input.TextInputWindow;
import dinodungeons.editor.ui.pointer.MouseHandler;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.map.objects.NonPlayerCharacterMapObject.NPCType;
import dinodungeons.game.gameobjects.text.TextBoxContent;

public class EditorUIHandler {
	
	private ArrayList<UIElement> uiElements;
	
	private TextInputWindow textInputWindow;
	
	private PageTextInputWindow pageTextInputWindow;

	public EditorUIHandler(final Editor editorHandle, final EditorMapManager mapManagerHandle) {
		initialize(editorHandle, mapManagerHandle);
	}
	
	private void initialize(final Editor editorHandle, final EditorMapManager mapManagerHandle){
		uiElements = new ArrayList<>();
		//MousePointer
		uiElements.add(MouseHandler.getInstance());
		//New/Load/Save
		uiElements.add(new ButtonNewMap(0, 224, editorHandle));
		uiElements.add(new ButtonLoadMap(16, 224, editorHandle));
		uiElements.add(new ButtonSaveMap(32, 224, editorHandle));
		//Erase
		uiElements.add(new EraserSelectionButton(48, 224, this, editorHandle));
		//Placement Selections
		uiElements.add(new TileSetSelectionButton(64, 224, this, editorHandle, mapManagerHandle));
		uiElements.add(new BaseLayerSelectionButton(80, 224, this, editorHandle));
		uiElements.add(new CollectableItemPlacementSelectionButton(96, 224, this, editorHandle));
		uiElements.add(new SwitchPlacementSelectionButton(112, 224, this, editorHandle));
		uiElements.add(new DoorPlacementSelectionButton(128, 224, this, editorHandle));
		uiElements.add(new ExitPlacementSelectionButton(144, 224, this, editorHandle));
		uiElements.add(new StaticObjectPlacementSelectionButton(160, 224, this, editorHandle));
		uiElements.add(new EnemyPlacementSelectionButton(176, 224, this, editorHandle));
		uiElements.add(new SignPlacementSelectionButton(192, 224, this, editorHandle));
		//Map Settings
		uiElements.add(new MapSettingsUIGroup(256, 10, editorHandle, mapManagerHandle));
		//InputWindow (add last)
		textInputWindow = new TextInputWindow(96, 102, editorHandle);
		pageTextInputWindow = new PageTextInputWindow(16, 16, editorHandle);
		uiElements.add(textInputWindow);
		uiElements.add(pageTextInputWindow);
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
		MouseHandler.getInstance().updateSpecial(currentInput, false);;
		textInputWindow.update(currentInput);
		pageTextInputWindow.update(currentInput);
	}
	
	public void draw(){
		for(UIElement uiElement : uiElements) {
			uiElement.draw();
		}
	}
	
	public void openInputWindow(String prompt, InputUsage usage, String prefilledInput){
		textInputWindow.setPrompt(prompt);
		textInputWindow.setUsage(usage);
		textInputWindow.setInput(prefilledInput);
		textInputWindow.open();
	}
	
	public void openPageInputWindow(SignType signType, String prompt, TextBoxContent prefilledInput) {
		pageTextInputWindow.setPrompt(prompt);
		pageTextInputWindow.setInput(prefilledInput);
		pageTextInputWindow.setSignType(signType);
		pageTextInputWindow.open();
	}
	
	public void openPageInputWindow(NPCType npcType, String prompt, TextBoxContent prefilledInput) {
		pageTextInputWindow.setPrompt(prompt);
		pageTextInputWindow.setInput(prefilledInput);
		pageTextInputWindow.setNPCType(npcType);
		pageTextInputWindow.open();
	}

}
