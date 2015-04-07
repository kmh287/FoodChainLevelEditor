package com.FoodChain.LevelEditor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tile{

	protected static enum tileType{GRASS, BUSH, TREE, WATER, DIRT,
								   N_SHORE, NE_SHORE, E_SHORE, SE_SHORE,
								   S_SHORE, SW_SHORE, W_SHORE, NW_SHORE,
								   N_GRASS, NE_GRASS, E_GRASS, SE_GRASS, 
								   S_GRASS, SW_GRASS, W_GRASS, NW_GRASS}
    public tileType type;
    
    public Tile(TextureRegion texture, float x, float y, float width,
		float height, tileType type) {
		this.type = type;
		// TODO Auto-generated constructor stub
	}
    
    public tileType getType() {
    		return type;
    }
}
