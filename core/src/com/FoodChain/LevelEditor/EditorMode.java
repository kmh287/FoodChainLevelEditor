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
	
    //Number of tree tiles to add to the boundary
    private static int BOUNDARY_TREE_TILES = 15;
    
    private static int ADD_BOUNDARY_OFFSET = 0;
    private static int REMOVE_BOUNDARY_OFFSET = 1;
	
	private AssetManager assetManager;
	private GameCanvas canvas;
	private GameMap map;
	private TileUI tui;
	private MainUI mui;
	private ActorUI aui;
	private PatrolUI pui;
	private ObjectiveUI oui;
	private String objective;
	private enum mode {NONE, PLACE_TILES, PLACE_ACTORS, OBJECTIVE_MODE, PATROL_MODE}
	private mode currentMode;
	private Vector2 tmp;
	
	//For only drawing the selected animal's patrol path
	private int selectedAnimalIndex = -1;
	
	/**Variables ot handle autosave
	Autosave happens every 30 seconds
	Autosaving should not occur if the map has not changed
	This is so re-opening the editor does not overwirte the
	autosave.**/
	int ticks;
	boolean changed;
	
	private String BLACKBAR = "assets/blackbar.png";
	private String FLAG1 = "assets/flag1.png";
	private String FLAG2 = "assets/flag2.png";
	private String FLAG3 = "assets/flag3.png";
	private String FLAG4 = "assets/flag4.png";
	private String SELECTION = "assets/selection.png";
	
	private Texture blackbar_tex;
	private Texture flag1;
	private Texture flag2;
	private Texture flag3;
	private Texture flag4;
	private Texture selection;
	
	private void LoadContent(AssetManager manager){
		manager.load(BLACKBAR, Texture.class);
		manager.load(FLAG1, Texture.class);
		manager.load(FLAG2, Texture.class);
		manager.load(FLAG3, Texture.class);
		manager.load(FLAG4, Texture.class);
		manager.load(SELECTION, Texture.class);
		manager.finishLoading();
		blackbar_tex = (Texture) ((manager.isLoaded(BLACKBAR)) ? manager.get(BLACKBAR) 	: null);
		flag1		 = (Texture) ((manager.isLoaded(FLAG1)) 	   ? manager.get(FLAG1) 		: null);
		flag2		 = (Texture) ((manager.isLoaded(FLAG2)) 	   ? manager.get(FLAG2) 		: null);
		flag3		 = (Texture) ((manager.isLoaded(FLAG3)) 	   ? manager.get(FLAG3) 		: null);
		flag4		 = (Texture) ((manager.isLoaded(FLAG4)) 	   ? manager.get(FLAG4) 		: null);
		selection 	 = (Texture) ((manager.isLoaded(SELECTION))? manager.get(SELECTION) : null);
	}
	
	public EditorMode(GameCanvas canvas){
		this.canvas = canvas;
		assetManager = new AssetManager();
		initializeDefaultMap();
		LoadContent(assetManager);
		map.LoadContent(assetManager);
		Actor.LoadContent(assetManager);
		tmp = new Vector2();
		
		ticks = 0;
		changed = false;
		
		//Default objective is catch 0 pigs and 0 wolves
		objective = "0&0";
		
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
		tui.setLocation(0,0);
		
		//Actor UI
		aui = new ActorUI();
		aui.setTitle("Actors");
		aui.setSize(600,200);
		aui.setLocation(0,310);
		
		//Objective UI
		oui = new ObjectiveUI();
		oui.setTitle("Objectives");
		oui.setSize(600,200);
		oui.setLocation(0,310);
		
		//Patrol UI
		pui = new PatrolUI();
		pui.setTitle("Patrol Paths");
		pui.setSize(600,200);
		pui.setLocation(0,310);
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
		List<List<Vector2>> patrolPaths = new ArrayList<List<Vector2>>();
		Vector2 defaultHunterStart = new Vector2(16, 8);
		map = new GameMap(layout, animals, coordinates, patrolPaths, defaultHunterStart, objective);
		selectedAnimalIndex = -1;
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
	/**
	 * Update the actor positions to account for the addition
	 * of the boundary.
	 * 
	 * @param arg 0 if adding the boundary, 1 if removing
	 * 		  all other ints will cause an exception
	 */
	private void updatePositionsForBoundary(int arg){
		if (arg != 0 && arg != 1) throw new IllegalArgumentException();
		int offset = (arg == 0) ? BOUNDARY_TREE_TILES : -1*BOUNDARY_TREE_TILES;
		
		//Animal coordinates
		List<Vector2> coords = map.getCoordinates();
		Iterator<Vector2> it = coords.iterator();
		while (it.hasNext()){
			Vector2 curr = it.next();
			curr.add(offset, offset);
		}
		
		//Hunter
		Vector2 hunterCoords = map.getHunterStartingCoordinate();
		hunterCoords.add(offset,offset);
		
		//Waypoints
		List<List<Vector2>> patrolPaths = map.getPatrolPaths();
		Iterator<List<Vector2>> it1 = patrolPaths.iterator();
		while (it1.hasNext()){
			List<Vector2> currPath = it1.next();
			Iterator<Vector2> it2 = currPath.iterator();
			while (it2.hasNext()){
				Vector2 curr = it2.next();
				curr.add(offset, offset);
			}
		}
	}
	
	private void save(String filename){
		
		//Should not be reachable
		if (map.getHunterStartingCoordinate() == null){
			JOptionPane.showMessageDialog(null, "No hunter on map. Save aborted");
			return;
		}
		
		addBoundary(map);
		updatePositionsForBoundary(ADD_BOUNDARY_OFFSET);
		
		try {
			MapManager.MapToGson(map, filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Problem saving map. Contact Kevin");
		} catch (IOException e) {
			System.out.println("Problem saving map. Contact Kevin");
			e.printStackTrace();
		}
		
		//For non-autosaves, print the layout to the console
		//Also check for an objective that isn't 0 pigs and 0 wolves
		if (!filename.equals("autosave")){
			System.out.println(map.toString());
			if (map.getObjective().equals("0&0")){
				JOptionPane.showMessageDialog(null, 
						"Objective is set to 0 pigs and 0 wolves.\n"
					  + "The map has been saved, but this level will "
					  + "NOT load properly into FoodChain.");
			}
		}
		
		//Remove boundary so the editor doesn't freak out
		removeBoundary(map);
		updatePositionsForBoundary(REMOVE_BOUNDARY_OFFSET);
	}
	
	private void load(String filename){
		
		try {
			map = MapManager.GsonToMap(filename);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File not found. Load aborted");
			System.out.println("Problem loading map. Contact Kevin if unresolvable");
			return;
		}
		removeBoundary(map);
		updatePositionsForBoundary(REMOVE_BOUNDARY_OFFSET);
		oui.setObjectiveString(map.getObjective());
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
		case LOAD_AUTOSAVE:
			load("autosave");
			break;
		case TILE_MODE:
			currentMode = mode.PLACE_TILES;
			aui.setVisible(false);
			tui.setVisible(true);
			oui.setVisible(false);
			pui.setVisible(false);
			selectedAnimalIndex = -1;
			break;
		case ACTOR_MODE:
			currentMode = mode.PLACE_ACTORS;
			aui.setVisible(true);
			tui.setVisible(false);
			oui.setVisible(false);
			pui.setVisible(false);
			selectedAnimalIndex = -1;
			break;
		case OBJECTIVE_MODE:
			currentMode = mode.OBJECTIVE_MODE;
			aui.setVisible(false);
			tui.setVisible(false);
			oui.setVisible(true);
			pui.setVisible(false);
			selectedAnimalIndex = -1;
			break;
		case PATROL_MODE:
			currentMode = mode.PATROL_MODE;
			aui.setVisible(false);
			tui.setVisible(false);
			oui.setVisible(false);
			pui.setVisible(true);
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
		List<List<Vector2>> patrolPaths = map.getPatrolPaths();
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
			patrolPaths.remove(index);
		}
		
	}
	
	public int waypointNumToInt(PatrolUI.command com){
		switch(com){
		case WP1: 
			return 1;
		case WP2:
			return 2;
		case WP3:
			return 3;
		case WP4:
			return 4;
		default:
			return -1;
		}
	}
	
	public void update(float delta){
		if (ticks++ % 1800 == 0 && changed){
			save("autosave");
			System.out.println("Autosaving...");
		}
		
		
		handleMainUICommand();
		
		//Update the objective
		map.setObjective(oui.getObjectiveString());
		
		int xPos = Gdx.input.getX();
		int yPos = Gdx.graphics.getHeight() - Gdx.input.getY();
		//If the left mouse button isn't being pressed, no need to update anything
		//The same is true if the current mode is NONE
		
		//One exception is for clearing waypoints, in which case the screen must be updated
		//even if the user hasn't clicked on it.
		
		if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT) || 
			this.currentMode == mode.NONE){
			return;
		}
		
		changed = true;
		
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
					
					//Add Coordinate
					Vector2 newCoordinate = new Vector2(mapX, mapY);
					List<Vector2> newCoordinates = map.getCoordinates();
					newCoordinates.add(newCoordinate);
					
					//Add patrol path
					List<List<Vector2>> patrolPaths = map.getPatrolPaths();
					List<Vector2> path = new ArrayList<Vector2>();
					if(selected == Actor.actorType.OWL){
						//Add two waypoints in on top of the owl
						//This saves the user form having to put this into the level
						path.add(new Vector2(mapX, mapY));
						path.add(new Vector2(mapX, mapY));
					}
					patrolPaths.add(path);	//Paths must be made in patrol mode
				}
			}
		}
		else if (currentMode == mode.PATROL_MODE){
			PatrolUI.command com = pui.getCommand();
			mapX = map.screenXToMap(xPos);
			mapY = map.screenYToMap(yPos);
			if (com == PatrolUI.command.NONE) return;
			if (com == PatrolUI.command.SELECT){
				List<Vector2> coordinates = map.getCoordinates();
				tmp.set(mapX, mapY);
				int index = -1;
				for (int i = 0; i < coordinates.size(); ++i){
					if (coordinates.get(i).equals(tmp)){
						index = i;
						break;
					}
				}
				if (index != -1){
					selectedAnimalIndex = index;
					//Now we can add to this naimal's patrol paths
				}
			}
			else if (com == PatrolUI.command.CLEAR){
				if (selectedAnimalIndex == -1){
					JOptionPane.showMessageDialog(null, "No animal selected");
					return;
				} else{
					map.getPatrolPaths().get(selectedAnimalIndex).clear();
				}
			}
			//If the command is waypoint placement
			else{
				if (selectedAnimalIndex == -1){
					JOptionPane.showMessageDialog(null, "No animal selected.");
					return;
				} else{
					List<Vector2> waypoints = map.getPatrolPaths().get(selectedAnimalIndex);
					//Do not let the user add a waypoint if the previous ones haven't been set
					if (waypointNumToInt(com) > waypoints.size()+1){
						JOptionPane.showMessageDialog(null, "Previous waypoints have not been set");
					} else{
						//If list isn't long enough, add new waypoint to it
						if (waypointNumToInt(com) == waypoints.size()+1){
							waypoints.add(new Vector2(mapX, mapY));
						}
						//otherwise, overwrite old one
						else{
							waypoints.get(waypointNumToInt(com)-1).set(mapX,mapY);
						}
					}
					
					//FOR DEBUGGING ONLY
//					if (waypoints.size() > 4){
//						System.out.println("Waypoint length is bugged: " + waypoints.size());
//					}
//					for (int i = 0; i < waypoints.size(); ++i){
//						if (waypoints.get(i) == null){
//							System.out.println("Null waypoint detected at index: " + i);
//						} else{
//							System.out.println(waypoints.get(i).toString());
//						}
//					}
				}
			}
		}
	}
	
	private Texture getFlagTexFromIndexNumber(int i){
		if (i < 0 || i > 3){
			throw new IllegalArgumentException();
		}
		switch(i){
		case 0:
			return flag1;
		case 1:
			return flag2;
		case 2:
			return flag3;
		default:
			return flag4;
		}
	}
	/**
	 * Draw the waypoints for the currently selected animal
	 * Also draw rectangle around the selected animal
	 */
	private void drawWaypoints(){
		if (selectedAnimalIndex == -1) return;
		List<Vector2> waypoints = map.getPatrolPaths().get(selectedAnimalIndex);
		//Size must be in 0...4
		for (int i = 0; i < waypoints.size(); ++i){
			Texture tex = getFlagTexFromIndexNumber(i);
			Vector2 curr = waypoints.get(i);
			canvas.draw(tex, map.mapXToScreen((int)curr.x), 
							 map.mapYToScreen((int)curr.y));
		}
		
		Vector2 coord = map.getCoordinates().get(selectedAnimalIndex);
		canvas.draw(selection, map.mapXToScreen((int)coord.x), 
				 			   map.mapYToScreen((int)coord.y));
		
	}
	
	public void draw(float delta){
		canvas.begin();
		canvas.draw(blackbar_tex, 0, 0);
		map.draw(canvas);
		drawWaypoints();
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
