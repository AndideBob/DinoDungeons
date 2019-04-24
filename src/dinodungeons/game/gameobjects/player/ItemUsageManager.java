package dinodungeons.game.gameobjects.player;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.PlayerInventoryManager;
import dinodungeons.game.data.gameplay.inventory.CollectableType;
import dinodungeons.game.gameobjects.GameObjectManager;
import dinodungeons.game.gameobjects.base.GameObject;
import dinodungeons.game.gameobjects.item.ItemBombObject;
import dinodungeons.game.gameobjects.item.ItemBoomerangObject;
import dinodungeons.game.gameobjects.item.ItemClubObject;
import dinodungeons.game.gameobjects.item.ItemTorchFireObject;
import dinodungeons.sfx.sound.SoundEffect;
import dinodungeons.sfx.sound.SoundManager;

public class ItemUsageManager {
	
	private int positionX;
	
	private int positionY;
	
	private int currentDirection;

	public ItemUsageManager() {
		positionX = 0;
		positionY = 0;
		currentDirection = DinoDungeonsConstants.directionDown;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public void setCurrentDirection(int currentDirection) {
		this.currentDirection = currentDirection;
	}

	public GameObject useItem(ItemID usedItem){
		GameObject weaponObject = null;
		if(usedItem == null) {
			return weaponObject;
		}
		switch (usedItem) {
		case CLUB:
			SoundManager.getInstance().playSoundEffect(SoundEffect.HIT_CLUB);
			weaponObject = new ItemClubObject(positionX, positionY, currentDirection);
			GameObjectManager.getInstance().addGameObjectToCurrentMap(weaponObject);
			break;
		case BOOMERANG:
			if(!GameObjectManager.getInstance().doesBoomerangExist()) {
				weaponObject = new DropingWeaponObject(DinoDungeonsConstants.dropItemDuration);
				GameObjectManager.getInstance().addGameObjectToCurrentMap(weaponObject);
				GameObjectManager.getInstance().addBoomerangObjectToCurrentMap(new ItemBoomerangObject(positionX + 8, positionY + 8, currentDirection));
			}
			break;
		case TORCH:
			weaponObject = new DropingWeaponObject(DinoDungeonsConstants.dropItemDuration);
			GameObjectManager.getInstance().addGameObjectToCurrentMap(weaponObject);
			GameObjectManager.getInstance().addGameObjectToCurrentMap(new ItemTorchFireObject(positionX, positionY, currentDirection));
			break;
		case BOMB:
			if(!PlayerInventoryManager.getInstance().isEmpty(CollectableType.BOMBS)) {
				PlayerInventoryManager.getInstance().decrease(CollectableType.BOMBS, 1);
				weaponObject = new DropingWeaponObject(DinoDungeonsConstants.dropItemDuration);
				GameObjectManager.getInstance().addGameObjectToCurrentMap(weaponObject);
				GameObjectManager.getInstance().addGameObjectToCurrentMap(new ItemBombObject(positionX, positionY));
			}
			break;
		}
		return weaponObject;
	}

}
