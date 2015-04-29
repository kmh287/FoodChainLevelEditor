package com.FoodChain.LevelEditor;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class TileUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private Tile.tileType selected = null;
	
	private JPanel mainPanel;
	private JButton grass;
	private JButton bush;
	private JButton tree;
	private JButton water;
	private JButton dirt;
	private JButton water_n;
	private JButton water_ne;
	private JButton water_e;
	private JButton water_se;
	private JButton water_s;
	private JButton water_sw;
	private JButton water_w;
	private JButton water_nw;
	private JButton grass_n;
	private JButton grass_ne;
	private JButton grass_e;
	private JButton grass_se;
	private JButton grass_s;
	private JButton grass_sw;
	private JButton grass_w;
	private JButton grass_nw;
	
	public TileUI(){
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(5,6));
		
		grass 		= new JButton("GRASS");
		bush 		= new JButton("BUSH");
		tree 		= new JButton("TREE");
		water 		= new JButton("WATER");
		dirt 		= new JButton("DIRT");
		water_n 		= new JButton("WATER_N");
		water_ne 	= new JButton("WTER_NE");
		water_e 		= new JButton("WATER_E");
		water_se 	= new JButton("WATER_SE");
		water_s 		= new JButton("WATER_S");
		water_sw 	= new JButton("WATER_SW");
		water_w 		= new JButton("WATER_W");
		water_nw 	= new JButton("WATER_NW");
		grass_n 		= new JButton("GRASS_N");
		grass_ne 	= new JButton("GRASS_NE");
		grass_e 		= new JButton("GRASS_E");
		grass_se 	= new JButton("GRASS_SE");
		grass_s 		= new JButton("GRASS_S");
		grass_sw 	= new JButton("GRASS_SW");
		grass_w 		= new JButton("GRASS_W");
		grass_nw 	= new JButton("GRASS_NW");
		
		mainPanel.add(grass);		mainPanel.add(bush);			mainPanel.add(tree);
		mainPanel.add(water);		mainPanel.add(dirt);			mainPanel.add(water_n);
		mainPanel.add(water_ne);		mainPanel.add(water_e);		mainPanel.add(water_se);		
		mainPanel.add(water_s);		mainPanel.add(water_sw);		mainPanel.add(water_w);
		mainPanel.add(water_nw);		mainPanel.add(grass_n);		mainPanel.add(grass_ne);
		mainPanel.add(grass_e);		mainPanel.add(grass_se);		mainPanel.add(grass_s);
		mainPanel.add(grass_sw);		mainPanel.add(grass_w);		mainPanel.add(grass_nw);
		
		grass	.addActionListener(this);
		bush		.addActionListener(this);
		tree		.addActionListener(this);
		water	.addActionListener(this);
		dirt		.addActionListener(this);
		water_n	.addActionListener(this);
		water_ne.addActionListener(this);
		water_e	.addActionListener(this);
		water_se.addActionListener(this);
		water_s	.addActionListener(this);
		water_sw.addActionListener(this);
		water_w	.addActionListener(this);
		water_nw.addActionListener(this);
		grass_n	.addActionListener(this);
		grass_ne	.addActionListener(this);
		grass_e	.addActionListener(this);
		grass_se	.addActionListener(this);
		grass_s	.addActionListener(this);
		grass_sw	.addActionListener(this);
		grass_w	.addActionListener(this);
		grass_nw	.addActionListener(this);
		
		
		this.add(mainPanel);
	}

	public Tile.tileType getSelected(){
		return this.selected;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == grass){
			selected = Tile.tileType.GRASS;
		} else if (arg0.getSource() == bush){
			selected = Tile.tileType.BUSH;
		} else if (arg0.getSource() == tree){
			selected = Tile.tileType.TREE;
		} else if (arg0.getSource() == water){
			selected = Tile.tileType.WATER;
		} else if (arg0.getSource() == dirt){
			selected = Tile.tileType.DIRT;
		} else if (arg0.getSource() == water_n){
			selected = Tile.tileType.N_SHORE;
		} else if (arg0.getSource() == water_ne){
			selected = Tile.tileType.NE_SHORE;
		} else if (arg0.getSource() == water_e){
			selected = Tile.tileType.E_SHORE;
		} else if (arg0.getSource() == water_se){
			selected = Tile.tileType.SE_SHORE;
		} else if (arg0.getSource() == water_s){
			selected = Tile.tileType.S_SHORE;
		} else if (arg0.getSource() == water_sw){
			selected = Tile.tileType.SW_SHORE;
		} else if (arg0.getSource() == water_w){
			selected = Tile.tileType.W_SHORE;
		} else if (arg0.getSource() == water_nw){
			selected = Tile.tileType.NW_SHORE;
		} else if (arg0.getSource() == grass_n){
			selected = Tile.tileType.N_GRASS;
		} else if (arg0.getSource() == grass_ne){
			selected = Tile.tileType.NE_GRASS;
		} else if (arg0.getSource() == grass_e){
			selected = Tile.tileType.E_GRASS;
		} else if (arg0.getSource() == grass_se){
			selected = Tile.tileType.SE_GRASS;
		} else if (arg0.getSource() == grass_s){
			selected = Tile.tileType.S_GRASS;
		} else if (arg0.getSource() == grass_sw){
			selected = Tile.tileType.SW_GRASS;
		} else if (arg0.getSource() == grass_w){
			selected = Tile.tileType.W_GRASS;
		} else if (arg0.getSource() == grass_nw){
			selected = Tile.tileType.NW_GRASS;
		}
	}
}
