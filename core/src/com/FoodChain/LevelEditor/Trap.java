package com.FoodChain.LevelEditor;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Trap{
	
	public enum TRAP_TYPE {
		REGULAR_TRAP,
		SHEEP_TRAP,
		WOLF_TRAP,
	}
	
	private static String REGULAR_TRAP = "assets/REGULAR_TRAP.png";
	private static String SHEEP_TRAP = "assets/SHEEP_TRAP.png";
	private static String WOLF_TRAP = "assets/WOLF_TRAP.png";
	protected static Texture regularTrapTexture = null;
	protected static Texture sheepTrapTexture = null;
	protected static Texture wolfTrapTexture = null;
	private String type;
	
	public Trap(Texture texture, String type) {
		this.type=type;
	}
	
	/** 
	 * Preloads the assets for the Traps.
	 * 
	 * All trap objects use one of three textures, so this is a static method.  
	 * This keeps us from loading the same images multiple times for more than one 
	 * Trap object.
	 *
	 * The asset manager for LibGDX is asynchronous.  That means that you
	 * tell it what to load and then wait while it loads them.  This is 
	 * the first step: telling it what to load.
	 * 
	 * @param manager Reference to global asset manager.
	 */
	public static void PreLoadContent(AssetManager manager) {
		manager.load(REGULAR_TRAP,Texture.class);
		manager.load(SHEEP_TRAP,Texture.class);
		manager.load(WOLF_TRAP,Texture.class);
	}
	
	/**
     * Load the texture for the player
     * This must be called before any calls to Player.draw()
     * 
     * @param manager an AssetManager
     */
	public static void LoadContent(AssetManager manager) {
		if (manager.isLoaded(REGULAR_TRAP)) {
			regularTrapTexture = manager.get(REGULAR_TRAP,Texture.class);
			regularTrapTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		} else {
			regularTrapTexture = null;  // Failed to load
		}
		if (manager.isLoaded(SHEEP_TRAP)) {
			sheepTrapTexture = manager.get(SHEEP_TRAP,Texture.class);
			sheepTrapTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		} else {
			sheepTrapTexture = null;  // Failed to load
		}
		if (manager.isLoaded(WOLF_TRAP)) {
			wolfTrapTexture = manager.get(WOLF_TRAP,Texture.class);
			wolfTrapTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		} else {
			wolfTrapTexture = null; // Failed to load
		}
	}
    
    
    public void draw(GameCanvas canvas){
        //TODO
    	}	
}
