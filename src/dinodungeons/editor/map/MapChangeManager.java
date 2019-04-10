package dinodungeons.editor.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import dinodungeons.editor.EditorControlUtil;
import dinodungeons.editor.EditorState;
import dinodungeons.editor.map.change.AbstractMapChange;
import dinodungeons.editor.map.change.MapChangeType;
import dinodungeons.editor.map.factories.BaseLayerMapChangeFactory;
import dinodungeons.editor.map.factories.MapChangeFactory;
import dinodungeons.editor.map.factories.MapChangeFactoryUtil;
import dinodungeons.editor.ui.pointer.MouseHandler;
import dinodungeons.game.data.gameplay.InputInformation;
import lwjgladapter.input.ButtonState;

public class MapChangeManager {
	
	private ArrayList<HashSet<AbstractMapChange>> changes;
	
	private MapChangeFactory currentChangeFactory;
	
	private HashSet<AbstractMapChange> currentChanges;
	private int currentChangeState;
	
	private boolean changing;
	
	private boolean canChange;

	public MapChangeManager() {
		currentChanges = new HashSet<>();
		changes = new ArrayList<>();
		resetCurrentChangeState();
		changing = false;
		setMapChange(MapChangeType.NONE);
	}
	
	public void setMapChange(MapChangeType mapChangeType, String... params) {
		currentChangeFactory = MapChangeFactoryUtil.getMapChangeFactory(mapChangeType);
		if(currentChangeFactory == null){
			canChange = false;
		}
		else{
			canChange = true;
			currentChangeFactory.handleParams(params);
		}
	}
	
	public void update(EditorState editorState, InputInformation inputInformation){
		if(editorState == EditorState.DEFAULT){
			currentChanges.clear();
			if(!changing){
				if(EditorControlUtil.getUndo()){
					undo();
				}
				else if(EditorControlUtil.getRedo()){
					redo();
				}
				else{
					checkForChanges(inputInformation);
					applyNewCurrentChanges();
				}
			}
			else if(inputInformation.getLeftMouseButton() == ButtonState.RELEASED
					|| inputInformation.getLeftMouseButton() == ButtonState.UP){
				changing = false;
				advanceCurrentChangeState();
			}
			else if (changing){
				checkForChanges(inputInformation);
				applyNewCurrentChanges();
			}
		}
	}

	private void checkForChanges(InputInformation inputInformation) {
		if(canChange &&
				MouseHandler.getInstance().isOnMap() && 
				(inputInformation.getLeftMouseButton() == ButtonState.PRESSED
				|| inputInformation.getLeftMouseButton() == ButtonState.DOWN)){
			changing = true;
			int x = MouseHandler.getInstance().getPositionX() / 16;
			int y = MouseHandler.getInstance().getPositionY() / 16;
			AbstractMapChange change = currentChangeFactory.buildMapChange(x, y);
			if(!currentChanges.contains(change)){
				currentChanges.add(change);
			}
		}
	}

	private void undo() {
		if(currentChangeState > 0){
			currentChangeState--;
			for(AbstractMapChange change : changes.get(currentChangeState)){
				change.revert();
				currentChanges.add(change);
			}
		}
	}
	
	private void redo() {
		if(currentChangeState < changes.size()-1){
			for(AbstractMapChange change : changes.get(currentChangeState)){
				change.revert();
				currentChanges.add(change);
			}
			currentChangeState++;
		}
	}
	
	private void applyNewCurrentChanges(){
		changes.get(currentChangeState).addAll(currentChanges);
	}
	
	private void advanceCurrentChangeState() {
		//Resetting for undone Changes
		for(int i = changes.size()-1; i > currentChangeState;i--){
			changes.remove(i);
		}
		changes.add(new HashSet<>());
		currentChangeState++;
	}
	
	public void resetCurrentChangeState(){
		currentChangeState = 0;
		changes.clear();
		changes.add(new HashSet<>());
	}
	
	public Collection<AbstractMapChange> getNewMapChanges(){
		return Collections.unmodifiableCollection(currentChanges);
	}
	
	public void draw(){
		
	}

}
