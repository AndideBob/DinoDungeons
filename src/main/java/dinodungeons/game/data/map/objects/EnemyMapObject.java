package dinodungeons.game.data.map.objects;

import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;

public class EnemyMapObject extends MapObject {
	
	EnemyType enemyType;
	
	public EnemyMapObject() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getEditorInfo() {
		return getEnemyName(enemyType);
	}

	public EnemyType getEnemyType() {
		return enemyType;
	}

	public void setEnemyType(EnemyType enemyType) {
		this.enemyType = enemyType;
	}
	
	public static String getEnemyName(EnemyType enemyType){
		switch (enemyType) {
		case GREEN_BAT:
			return "GREEN_BAT";
		case TRICERABLOB:
			return "TRICERABLOB";
		case CROCDROP:
			return "CROCDROP";
		}
		return "Enemey";
	}

	public void draw(int x, int y) {
		switch (enemyType) {
		case GREEN_BAT:
			SpriteManager.getInstance().getSprite(SpriteID.ENEMY_BAT_GREEN).draw(0, x - 4, y);
			break;
		case TRICERABLOB:
			SpriteManager.getInstance().getSprite(SpriteID.ENEMY_TRICERABLOB).draw(0, x, y);
			break;
		case CROCDROP:
			SpriteManager.getInstance().getSprite(SpriteID.ENEMY_CROCDROP).draw(0, x + 2, y + 1);
			break;
		}
	}
	
	public enum EnemyType{
		GREEN_BAT("0001"),
		TRICERABLOB("0002"),
		CROCDROP("0003");
		
		private String saveRepresentation;
		
		private EnemyType(String saveRepresentation){
			this.saveRepresentation = saveRepresentation;
		}

		public String getSaveRepresentation() {
			return saveRepresentation;
		}

		public static EnemyType getEnemyTypeBySaveRepresentation(String saveRepresentation){
			for(EnemyType id : values()){
				if(id.getSaveRepresentation().equals(saveRepresentation)){
					return id;
				}
			}
			return GREEN_BAT;
		}
	}
}
