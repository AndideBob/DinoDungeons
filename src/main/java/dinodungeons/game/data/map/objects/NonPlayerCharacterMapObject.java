package dinodungeons.game.data.map.objects;

import java.util.Arrays;
import java.util.Collection;

import dinodungeons.game.gameobjects.text.TextBoxContent;
import dinodungeons.gfx.sprites.SpriteID;

public class NonPlayerCharacterMapObject extends MapObject {
	
	NPCType npcType;
	
	TextBoxContent[] contents;
	
	public NonPlayerCharacterMapObject() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getEditorInfo() {
		return npcType.getStringRepresentation();
	}

	public NPCType getNPCType() {
		return npcType;
	}

	public void setNPCType(NPCType npcType) {
		this.npcType = npcType;
	}
	
	public Collection<TextBoxContent> getTextBoxContent() {
		return Arrays.asList(contents);
	}
	
	public void setTextBoxContent(TextBoxContent... contents) {
		this.contents = new TextBoxContent[contents.length];
		for(int i = 0; i < contents.length; i++) {
			this.contents[i] = contents[i];
		}
	}
	
	public void setTextBoxContent(Collection<TextBoxContent> contents) {
		this.contents = new TextBoxContent[contents.size()];
		int counter = 0;
		for(TextBoxContent tbc :contents) {
			this.contents[counter++] = tbc;
		}
	}
	
	public enum NPCType{
		DEFAULT_OLD_MAN(SpriteID.NPC_OLD_MAN ,"OLD_MAN"),
		DEFAULT_TIKI_VILLAGER_A(SpriteID.NPC_TIKI_VILAGER_A ,"TIKI_VILLAGER_A"),
		DEFAULT_TIKI_VILLAGER_B(SpriteID.NPC_TIKI_VILAGER_B ,"TIKI_VILLAGER_B");
		
		private SpriteID spriteID;
		
		private String stringRepresentation;
		
		private NPCType(SpriteID spriteID, String stringRepresentation) {
			this.stringRepresentation = stringRepresentation;
			this.spriteID = spriteID;
		}
		
		public SpriteID getSpriteID() {
			return spriteID;
		}
		
		public String getStringRepresentation() {
			return stringRepresentation;
		}
		
		public static NPCType getByStringRepresentation(String stringRepresentation) {
			for(NPCType type : values()) {
				if(type.stringRepresentation.equals(stringRepresentation)) {
					return type;
				}
			}
			return DEFAULT_OLD_MAN;
		}
		
		public static boolean isDefault(NPCType npcType){
			//TODO: Special NPCs
			return true;
		}
	}
}
