package dinodungeons.game.data.map;

import dinodungeons.game.data.exceptions.ScreenMapIndexOutOfBounds;

public class ScreenMap {

	private String id;
	private int sizeX;
	private int sizeY;
	
	private String transitionUpID;
	private String transitionRightID;
	private String transitionDownID;
	private String transitionLeftID;
	
	public static final ScreenMap defaultMap = new ScreenMap("0000", 16, 12);
	
	private int[][] baseLayer;
	
	private BaseLayerTile[][] baseLayerTiles;
	
	public ScreenMap(String id, int sizeX, int sizeY) {
		this.id = id;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		baseLayer = new int[sizeX][sizeY];
		baseLayerTiles = new BaseLayerTile[sizeX][sizeY];
		updateBaseLayerTiles();
		//Entrances
		transitionDownID = "0000";
		transitionLeftID = "0000";
		transitionRightID = "0000";
		transitionUpID = "0000";
	}
	
	public void updateBaseLayerTiles(){
		for(int y = 0; y < sizeY; y++){
			for(int x = 0; x < sizeX; x++){
				if(isFloorTile(baseLayer[x][y])){
					baseLayerTiles[x][y] = getFloorBaseLayerTile(x, y);
				}
				else if(baseLayer[x][y] == ScreenMapConstants.BASE_LAYER_BORDER){
					baseLayerTiles[x][y] = getBorderBaseLayerTile(x,y);
				}
				else if(baseLayer[x][y] == ScreenMapConstants.BASE_LAYER_STAIRS){
					baseLayerTiles[x][y] = BaseLayerTile.STAIRS;
				}
				else{
					baseLayerTiles[x][y] = getWallBaseLayerTile(x, y);
				}
			}
		}
	}
	
	public String getID() {
		return id;
	}

	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}
	
	public String getTransitionLeftID() {
		return transitionLeftID;
	}

	public void setTransitionLeftID(String transitionLeftID) {
		this.transitionLeftID = transitionLeftID;
	}

	public String getTransitionUpID() {
		return transitionUpID;
	}

	public void setTransitionUpID(String transitionUpID) {
		this.transitionUpID = transitionUpID;
	}

	public String getTransitionRightID() {
		return transitionRightID;
	}

	public void setTransitionRightID(String transitionRightID) {
		this.transitionRightID = transitionRightID;
	}

	public String getTransitionDownID() {
		return transitionDownID;
	}

	public void setTransitionDownID(String transitionDownID) {
		this.transitionDownID = transitionDownID;
	}

	public int getBaseLayerValueForPosition(int x, int y) {
		return baseLayer[x][y];
	}

	public BaseLayerTile getBaseLayerTileForPosition(int x, int y) {
		return baseLayerTiles[x][y];
	}

	private BaseLayerTile getFloorBaseLayerTile(int x, int y){
		switch(baseLayer[x][y]){
		case ScreenMapConstants.BASE_LAYER_FLOOR_A:
			return BaseLayerTile.FLOOR_NORMAL;
		case ScreenMapConstants.BASE_LAYER_FLOOR_B:
			return BaseLayerTile.FLOOR_ALT_A;
		case ScreenMapConstants.BASE_LAYER_FLOOR_C:
			return BaseLayerTile.FLOOR_ALT_B;
		}
		return BaseLayerTile.NOTHING;
	}
	
	private BaseLayerTile getBorderBaseLayerTile(int x, int y) {
		boolean uIsBorder = y == sizeY-1 || !isFloorTile(baseLayer[x][y+1]);
		boolean dIsBorder = y == 0 || !isFloorTile(baseLayer[x][y-1]);
		boolean lIsBorder = x == 0 || !isFloorTile(baseLayer[x-1][y]);
		boolean rIsBorder = x == sizeX-1 || !isFloorTile(baseLayer[x+1][y]);
		boolean ulIsBorder = x == 0 || y == sizeY-1 || !isFloorTile(baseLayer[x-1][y+1]);
		boolean urIsBorder = x == sizeX-1 || y == sizeY-1 || !isFloorTile(baseLayer[x+1][y+1]);
		boolean blIsBorder = x == 0 || y == 0 || !isFloorTile(baseLayer[x-1][y-1]);
		boolean brIsBorder = x == sizeX-1 || y == 0 || !isFloorTile(baseLayer[x+1][y-1]);
		
		//Outter Corners
		if(!uIsBorder){
			if(!lIsBorder){
				return BaseLayerTile.BORDER_TOP_LEFT_OUT;
			}
			else if(!rIsBorder){
				return BaseLayerTile.BORDER_TOP_RIGHT_OUT;
			}
			else{
				return BaseLayerTile.BORDER_UP;
			}
		}
		else if(!dIsBorder){
			if(!lIsBorder){
				return BaseLayerTile.BORDER_BOTTOM_LEFT_OUT;
			}
			else if(!rIsBorder){
				return BaseLayerTile.BORDER_BOTTOM_RIGHT_OUT;
			}
			else{
				return BaseLayerTile.BORDER_DOWN;
			}
		}
		//Inner Corners
		if(uIsBorder){
			if(lIsBorder){
				if(!ulIsBorder){
					return BaseLayerTile.BORDER_TOP_LEFT_IN;
				}
			}
			if(rIsBorder){
				if(!urIsBorder){
					return BaseLayerTile.BORDER_TOP_RIGHT_IN;
				}
			}
		}
		if(dIsBorder){
			if(lIsBorder){
				if(!blIsBorder){
					return BaseLayerTile.BORDER_BOTTOM_LEFT_IN;
				}
			}
			if(rIsBorder){
				if(!brIsBorder){
					return BaseLayerTile.BORDER_BOTTOM_RIGHT_IN;
				}
			}
		}
		//SideWays Borders
		if(uIsBorder && dIsBorder){
			if(!lIsBorder){
				return BaseLayerTile.BORDER_LEFT;
			}
			else if(!rIsBorder){
				return BaseLayerTile.BORDER_RIGHT;
			}
		}
		return BaseLayerTile.BORDER_FULL;
	}
	
	private BaseLayerTile getWallBaseLayerTile(int x, int y){
		boolean uIsWall = y == sizeY-1 || !isFloorTile(baseLayer[x][y+1]);
		boolean dIsWall = y == 0 || !isFloorTile(baseLayer[x][y-1]);
		boolean lIsWall = x == 0 || !isFloorTile(baseLayer[x-1][y]);
		boolean rIsWall = x == sizeX-1 || !isFloorTile(baseLayer[x+1][y]);
		boolean ulIsWall = x == 0 || y == sizeY-1 || !isFloorTile(baseLayer[x-1][y+1]);
		boolean urIsWall = x == sizeX-1 || y == sizeY-1 || !isFloorTile(baseLayer[x+1][y+1]);
		boolean blIsWall = x == 0 || y == 0 || !isFloorTile(baseLayer[x-1][y-1]);
		boolean brIsWall = x == sizeX-1 || y == 0 || !isFloorTile(baseLayer[x+1][y-1]);
		//Outter Corners
		if(!uIsWall){
			if(!lIsWall){
				return BaseLayerTile.WALL_TOP_LEFT_OUT;
			}
			else if(!rIsWall){
				return BaseLayerTile.WALL_TOP_RIGHT_OUT;
			}
			else{
				return BaseLayerTile.WALL_UP;
			}
		}
		else if(!dIsWall){
			if(!lIsWall){
				return BaseLayerTile.WALL_BOTTOM_LEFT_OUT;
			}
			else if(!rIsWall){
				return BaseLayerTile.WALL_BOTTOM_RIGHT_OUT;
			}
			else{
				return BaseLayerTile.WALL_DOWN;
			}
		}
		//Inner Corners
		if(uIsWall){
			if(lIsWall){
				if(!ulIsWall){
					return BaseLayerTile.WALL_TOP_LEFT_IN;
				}
			}
			if(rIsWall){
				if(!urIsWall){
					return BaseLayerTile.WALL_TOP_RIGHT_IN;
				}
			}
		}
		if(dIsWall){
			if(lIsWall){
				if(!blIsWall){
					return BaseLayerTile.WALL_BOTTOM_LEFT_IN;
				}
			}
			if(rIsWall){
				if(!brIsWall){
					return BaseLayerTile.WALL_BOTTOM_RIGHT_IN;
				}
			}
		}
		//SideWays Walls
		if(uIsWall && dIsWall){
			if(!lIsWall){
				return BaseLayerTile.WALL_LEFT;
			}
			else if(!rIsWall){
				return BaseLayerTile.WALL_RIGHT;
			}
		}
		return BaseLayerTile.NOTHING;
	}
	
	private boolean isFloorTile(int i) {
		return i == ScreenMapConstants.BASE_LAYER_FLOOR_A ||
				i == ScreenMapConstants.BASE_LAYER_FLOOR_B ||
				i == ScreenMapConstants.BASE_LAYER_FLOOR_C;
	}

	public void setBaseLayer(int posX, int posY, int tile) throws ScreenMapIndexOutOfBounds{
		if(baseLayer.length > posX && baseLayer[0].length > posY){
			baseLayer[posX][posY] = tile;
		}
		else{
			throw new ScreenMapIndexOutOfBounds("Tried to access [" + posX + "," + posY + "] but Map is only of Size [" + sizeX + "," + sizeY + "]");
		}
	}
}
