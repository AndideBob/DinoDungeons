package dinodungeons.editor.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import dinodungeons.editor.EditorControlUtil;
import dinodungeons.editor.map.change.MapChange;
import dinodungeons.game.data.gameplay.InputInformation;
import lwjgladapter.input.ButtonState;

public class MapChangeManager {
	
	private ArrayList<Collection<MapChange>> changes;
	
	private ArrayList<MapChange> currentChanges;
	private int currentChangeState;
	
	private boolean changing;

	public MapChangeManager() {
		currentChanges = new ArrayList<>();
		resetCurrentChangeState();
		changing = false;
	}
	
	public void update(InputInformation inputInformation){
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
	}

	private void checkForChanges(InputInformation inputInformation) {
		//TODO: Actually changing map
	}

	private void undo() {
		if(currentChangeState > 0){
			currentChangeState--;
			for(MapChange change : changes.get(currentChangeState)){
				change.revert();
				currentChanges.add(change);
			}
		}
	}
	
	private void redo() {
		if(currentChangeState < changes.size()-1){
			currentChangeState++;
			for(MapChange change : changes.get(currentChangeState)){
				change.revert();
				currentChanges.add(change);
			}
		}
	}
	
	private void applyNewCurrentChanges(){
		changes.get(currentChangeState).addAll(currentChanges);
	}
	
	private void advanceCurrentChangeState() {
		//Resetting for undone Changes
		for(int i = changes.size()-1; i > currentChangeState;i++){
			changes.remove(i);
		}
		changes.add(new ArrayList<>());
		currentChangeState++;
	}
	
	public void resetCurrentChangeState(){
		currentChangeState = 0;
		changes.clear();
		changes.add(new ArrayList<>());
	}
	
	public Collection<MapChange> getNewMapChanges(){
		return Collections.unmodifiableList(currentChanges);
	}
	
	public void draw(){
		
	}

}
