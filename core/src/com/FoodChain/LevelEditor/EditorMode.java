package com.FoodChain.LevelEditor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;


public class EditorMode implements Screen{
	private AssetManager assetManager;
	private GameCanvas canvas;
	private GameMap map;
	private TileUI tui;
	private MainUI mui;
	private ActorUI aui;
	private enum mode {NONE, PLACE_TILES, PLACE_ACTORS}
	private mode currentMode;
	private Vector2 tmp;
	
	private String BLACKBAR = "assets/blackbar.png";
	private Texture blackbar_tex;
	
	private void LoadContent(AssetManager manager){
		manager.load(BLACKBAR, Texture.class);
		manager.finishLoading();
		blackbar_tex = (Texture) ((manager.isLoaded(BLACKBAR)) ? manager.get(BLACKBAR) : null);
	}
	
    //Number of tree tiles to add to the boundary
    int BOUNDARY_TREE_TILES = 10;
	
	public EditorMode(GameCanvas canvas){
		this.canvas = canvas;
		assetManager = new AssetManager();
		initializeDefaultMap();
		LoadContent(assetManager);
		map.LoadContent(assetManager);
		Actor.LoadContent(assetManager);
		tmp = new Vector2();
		
		//Main UI
		mui = new MainUI();
		mui.setTitle("FoodChain Level Editor");
		mui.setSize(600, 200);
		mui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mui.setLocation(610,0);
		mui.setVisible(true);
		
		//Tile UI
		tui = new TileUI();
		tui.setTitle("Tiles");
		tui.setSize(600,300);
		tui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tui.setLocation(0,0);
		
		//Actor UI
		aui = new ActorUI();
		aui.setTitle("Actors");
		aui.setSize(600,200);
		aui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aui.setLocation(0,310);
	}
	
	private void initializeDefaultMap(){
		Tile.tileType layout[][] = new Tile.tileType[16][32];
		for (int i = 0; i < layout.length; ++i){
			for (int j = 0; j < layout[0].length; ++j){
				layout[i][j] = Tile.tileType.GRASS;
			}
		}
		
		List<Actor.actorType> animals = new ArrayList<Actor.actorType>();
		List<Vector2> coordinates = new ArrayList<Vector2>();
		
		map = new GameMap(layout, animals, coordinates, null);
	}
	
	private void addBoundary(GameMap map){
		//Add boundary around the map
		Tile.tileType[][] layout = map.getLayout();
		int oldMapXLength = layout[0].length;
		int oldMapYLength = layout.length;
		int newMapXLength = oldMapXLength + 2 * BOUNDARY_TREE_TILES;
		int newMapYLength = oldMapYLength + 2 * BOUNDARY_TREE_TILES;
		int b = BOUNDARY_TREE_TILES;
		Tile.tileType[][] newLayout = new Tile.tileType[newMapYLength][newMapXLength];
													   
		for (int i = 0; i < newMapYLength; ++i){
			for (int j = 0; j < newMapXLength; ++j){
				if (i < b || j < b || i >= oldMapYLength+b || j >= oldMapXLength+b){
					newLayout[i][j] = Tile.tileType.TREE;
				} else {
					System.out.println("x: " + (j+b) + "y: " + (i+b));
					newLayout[i][j] = layout[i-b][j-b];
				}
			}
		}
		
		map.setLayout(newLayout);
	}
	
	private void removeBoundary(GameMap map){		
		//Remove the boundary from the map
		Tile.tileType[][] layout = map.getLayout();
		int newMapXLength = layout[0].length - 2 * BOUNDARY_TREE_TILES;
		int newMapYLength = layout.length - 2 * BOUNDARY_TREE_TILES;
		int b = BOUNDARY_TREE_TILES;
		Tile.tileType[][] newLayout = new Tile.tileType[newMapYLength][newMapXLength];
													   
		for (int i = 0; i < newMapYLength; ++i){
			for (int j = 0; j < newMapXLength; ++j){
				newLayout[i][j] = layout[i+b][j+b];
			}
		}
		
		map.setLayout(newLayout);
	}
	
	private void save(String filename){
		
		addBoundary(map);
		
		try {
			//System.out.println(map.toString());
			MapManager.MapToGson(map, filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Problem saving map. Contact Kevin");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Problem saving map. Contact Kevin");
			e.printStackTrace();
		}
		
		//Remove boundary so the editor doesn't freak out
		removeBoundary(map);
	}
	
	private void load(String filename){
		
		try {
			map = MapManager.GsonToMap(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Problem loading map. Contact Kevin");
		}
		//System.out.println(map.toString());
		removeBoundary(map);
	}
	
	private void handleMainUICommand(){
		MainUI.command com = mui.getCommand();
		String filename;
		switch(com){
		case NEW_NOSAVE:
			initializeDefaultMap();
			//System.out.println("New map, no save");
			break;
		case NEW_SAVE:
			filename = JOptionPane.showInputDialog("Specify a filename");
			if (filename != null){
				save(filename);
				initializeDefaultMap();
			}
			//System.out.println("New map, yes save");
			break;
		case SAVE:
			filename = JOptionPane.showInputDialog("Specify a filename");
			if (filename != null){
				save(filename);
			}
			break;
		case LOAD:
			filename = JOptionPane.showInputDialog("Specify a filename");
			if (filename != null){
				load(filename);
			}
			break;
		case TILE_MODE:
			currentMode = mode.PLACE_TILES;
			aui.setVisible(false);
			tui.setVisible(true);
			break;
		case ACTOR_MODE:
			currentMode = mode.PLACE_ACTORS;
			aui.setVisible(true);
			tui.setVisible(false);
			break;
		default:
			//Do nothing
			break;
		}
		//Must be called to reset the command for the next frame
		mui.resetCommand();
	}
	
	private boolean tileOccupied(int mapX, int mapY){
		List<Vector2> coordinates = map.getCoordinates();
		Vector2 hunterStart = map.getHunterStartingCoordinate();
		tmp.set(mapX, mapY);
		Iterator<Vector2> it = coordinates.iterator();
		while (it.hasNext()){
			Vector2 curr = it.next();
			if (curr.equals(tmp)) return true;
		}
		
		//If none of the animals are on the tile, check the hunter
		//if the hunter isn't, then the tile is clear
		if (hunterStart == null) return false;
		if (hunterStart.equals(tmp)) return true;
		return false;
	}
	
	private void deleteAnimal(int mapX, int mapY){
		//Iterate through coordinates, find right one then delete
		List<Vector2> coordinates = map.getCoordinates();
		List<Actor.actorType> animals = map.getActorTypeList();
		tmp.set(mapX, mapY);
		int index = -1;
		for (int i = 0; i < coordinates.size(); ++i){
			if (coordinates.get(i).equals(tmp)){
				index = i;
				break;
			}
		}
		if (index == -1) return;
		else{
			coordinates.remove(index);
			animals.remove(index);
		}
		
	}
	
	public void update(float delta){
		handleMainUICommand();
		int xPos = Gdx.input.getX();
		int yPos = Gdx.graphics.getHeight() - Gdx.input.getY();
		//If the left mouse button isn't being pressed, no need to update anything
		//The same is true if the current mode is NONE
		if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT) || 
			this.currentMode == mode.NONE){
			return;
		}
		
		int mapX, mapY;
		if (currentMode == mode.PLACE_TILES){
			Tile.tileType selected = tui.getSelected();
			mapX = map.screenXToMap(xPos);
			mapY = map.screenYToMap(yPos);
			map.setTileToNewType(mapX, mapY, selected);
		} else if (currentMode == mode.PLACE_ACTORS){
			Actor.actorType selected = aui.getSelected();
			mapX = map.screenXToMap(xPos);
			mapY = map.screenYToMap(yPos);
			//Delete animals case
			if (selected == null){
				deleteAnimal(mapX, mapY);
			} else if (selected == Actor.actorType.HUNTER){
				//Hunter requires special logic as there can only be one
				map.setHunterStartingCoordinate(new Vector2(mapX,mapY));
			} else {
				if (!tileOccupied(mapX, mapY)){
					//Add animal
					List<Actor.actorType> newAnimalList = map.getActorTypeList();
					newAnimalList.add(selected);
					map.setActorTypeList(newAnimalList);
					
					//Add Coordinate
					Vector2 newCoordinate = new Vector2(mapX, mapY);
					List<Vector2> newCoordinates = map.getCoordinates();
					newCoordinates.add(newCoordinate);
					map.setCoordinates(newCoordinates);
				}
			}
		}
	}
	
	public void draw(float delta){
		canvas.begin();
		canvas.draw(blackbar_tex, 0, 0);
		map.draw(canvas);
		Actor.draw(canvas, map);
		canvas.end();
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		update(delta);
		draw(delta);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
