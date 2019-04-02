package dinodungeons.editor;

import java.util.ArrayList;
import java.util.Collection;

import dinodungeons.editor.ui.UIElement;
import dinodungeons.editor.ui.pointer.MouseHandler;

public class EditorUtil {

	public static Collection<UIElement> initializeEditorUI(){
		ArrayList<UIElement> uiElements = new ArrayList<>();
		//MousePointer
		uiElements.add(MouseHandler.getInstance());
		
		
		return uiElements;
	}

}
