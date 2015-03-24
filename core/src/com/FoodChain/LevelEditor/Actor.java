package com.FoodChain.LevelEditor;

import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public abstract class Actor{ 
	
	private static String PIG_TEX = "assets/pig.png";
	private static String WOLF_TEX = "assets/wolf.png";
	private static String HUNTER_TEX = "assets/player.png";
	
	private static Texture pigTexture;
	private static Texture wolfTexture;
	private static Texture hunterTexture;
	
    public enum actorType{
    		HUNTER,
		PIG, 
		WOLF
	}
    
    public static void LoadContent(AssetManager manager){
    		manager.load(PIG_TEX, Texture.class);
    		manager.load(WOLF_TEX, Texture.class);
    		manager.load(HUNTER_TEX, Texture.class);
    		
    		manager.finishLoading();
    		
    		pigTexture = (Texture) (manager.isLoaded(PIG_TEX) ? manager.get(PIG_TEX) : null);
    		wolfTexture = (Texture) (manager.isLoaded(WOLF_TEX) ? manager.get(WOLF_TEX) : null);
    		hunterTexture = (Texture) (manager.isLoaded(HUNTER_TEX) ? manager.get(HUNTER_TEX) : null);
    }
    
    public static void draw(GameCanvas canvas, GameMap map){

    		List<actorType> actors = map.getActorTypeList();
    		List<Vector2> coordinates = map.getCoordinates();
    		Vector2 hunterStart = map.getHunterStartingCoordinate();
    	
    		if (actors.size() != coordinates.size()){
    			System.out.println("Attempting to draw lists of unequal size. Fatal error. Contact Kevin");
    			System.out.println("Actor drawing will stop now.");
    			return;
    		}
    		
    		Texture tex;
    		
    		for (int i = 0; i < actors.size(); ++i){
    			tex = getTextureFromActorType(actors.get(i));
    			Vector2 coord = coordinates.get(i);
    			canvas.draw(tex, map.mapXToScreen((int)coord.x), map.mapYToScreen((int)coord.y));
    		}
    		
    		tex = hunterTexture;
    		if (hunterStart != null){
    			canvas.draw(tex, map.mapXToScreen((int)hunterStart.x), 
    							 map.mapYToScreen((int)hunterStart.y));
    		}
    		
    }
    
    public static Texture getTextureFromActorType(actorType a){
    		switch(a){
    		case PIG:
    			return pigTexture;
    		case WOLF:
    			return wolfTexture;
    		case HUNTER:
    			return hunterTexture;
    		default:
    			System.out.println("Invalid actor type detected. Contact Kevin.");
    			return null;
    		}
    }
}