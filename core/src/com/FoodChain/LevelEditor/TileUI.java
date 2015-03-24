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
	private JButton dirt_n;
	private JButton dirt_ne;
	private JButton dirt_e;
	private JButton dirt_se;
	private JButton dirt_s;
	private JButton dirt_sw;
	private JButton dirt_w;
	private JButton dirt_nw;
	
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
		dirt_n 		= new JButton("DIRT_N");
		dirt_ne 		= new JButton("DIRT_NE");
		dirt_e 		= new JButton("DIRT_E");
		dirt_se 		= new JButton("DIRT_SE");
		dirt_s 		= new JButton("DIRT_S");
		dirt_sw 		= new JButton("DIRT_SW");
		dirt_w 		= new JButton("DIRT_W");
		dirt_nw 		= new JButton("DIRT_NW");
		
		mainPanel.add(grass);		mainPanel.add(bush);			mainPanel.add(tree);
		mainPanel.add(water);		mainPanel.add(dirt);			mainPanel.add(water_n);
		mainPanel.add(water_ne);		mainPanel.add(water_e);		mainPanel.add(water_se);		
		mainPanel.add(water_s);		mainPanel.add(water_sw);		mainPanel.add(water_w);
		mainPanel.add(water_nw);		mainPanel.add(dirt_n);		mainPanel.add(dirt_ne);
		mainPanel.add(dirt_e);		mainPanel.add(dirt_se);		mainPanel.add(dirt_s);
		mainPanel.add(dirt_sw);		mainPanel.add(dirt_w);		mainPanel.add(dirt_nw);
		
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
		dirt_n	.addActionListener(this);
		dirt_ne	.addActionListener(this);
		dirt_e	.addActionListener(this);
		dirt_se	.addActionListener(this);
		dirt_s	.addActionListener(this);
		dirt_sw	.addActionListener(this);
		dirt_w	.addActionListener(this);
		dirt_nw	.addActionListener(this);
		
		
		this.add(mainPanel);
	}

	public Tile.tileType getSelected(){
		return this.selected;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == grass){
			System.out.println("GRASS");
			selected = Tile.tileType.GRASS;
		}
		else if (arg0.getSource() == bush){
			System.out.println("BUSH");
			selected = Tile.tileType.BUSH;
		}
		else if (arg0.getSource() == tree){
			System.out.println("TREE");
			selected = Tile.tileType.TREE;
		}
		else if (arg0.getSource() == water){
			System.out.println("WATER");
			selected = Tile.tileType.WATER;
		}
		else if (arg0.getSource() == dirt){
			System.out.println("DIRT");
			selected = Tile.tileType.DIRT;
		}
		else if (arg0.getSource() == water_n){
			System.out.println("WATER_N");
			selected = Tile.tileType.N_SHORE;
		}
		else if (arg0.getSource() == water_ne){
			System.out.println("WATER_NE");
			selected = Tile.tileType.NE_SHORE;
		}
		else if (arg0.getSource() == water_e){
			System.out.println("WATER_E");
			selected = Tile.tileType.E_SHORE;
		}
		else if (arg0.getSource() == water_se){
			System.out.println("WATER_SE");
			selected = Tile.tileType.SE_SHORE;
		}
		else if (arg0.getSource() == water_s){
			System.out.println("WATER_S");
			selected = Tile.tileType.S_SHORE;
		}
		else if (arg0.getSource() == water_sw){
			System.out.println("WATER_SW");
			selected = Tile.tileType.SW_SHORE;
		}
		else if (arg0.getSource() == water_w){
			System.out.println("WATER_W");
			selected = Tile.tileType.W_SHORE;
		}
		else if (arg0.getSource() == bush){
			System.out.println("WATER_NW");
			selected = Tile.tileType.NW_SHORE;
		}
		else if (arg0.getSource() == dirt_n){
			System.out.println("N_DIRT");
			selected = Tile.tileType.N_DIRT;
		}
		else if (arg0.getSource() == dirt_ne){
			System.out.println("NE_DIRT");
			selected = Tile.tileType.NE_DIRT;
		}
		else if (arg0.getSource() == dirt_e){
			System.out.println("E_DIRT");
			selected = Tile.tileType.E_DIRT;
		}
		else if (arg0.getSource() == dirt_se){
			System.out.println("SE_DIRT");
			selected = Tile.tileType.SE_DIRT;
		}
		else if (arg0.getSource() == dirt_s){
			System.out.println("S_DIRT");
			selected = Tile.tileType.S_DIRT;
		}
		else if (arg0.getSource() == dirt_sw){
			System.out.println("SW_DIRT");
			selected = Tile.tileType.SW_DIRT;
		}
		else if (arg0.getSource() == dirt_w){
			System.out.println("W_DIRT");
			selected = Tile.tileType.W_DIRT;
		}
		else if (arg0.getSource() == dirt_nw){
			System.out.println("NW_DIRT");
			selected = Tile.tileType.NW_DIRT;
		}
	}
}
