package com.FoodChain.LevelEditor;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.FoodChain.LevelEditor.MainUI.command;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PatrolUI extends JFrame implements ActionListener{

	public enum command{NONE,SELECT,WP1,WP2,WP3,WP4,WP5,WP6,WP7,WP8,CLEAR}
	
	
	private JPanel mainPanel;
	private JButton selectAnimal;
	private JButton wp1;
	private JButton wp2;
	private JButton wp3;
	private JButton wp4;
	private JButton wp5;
	private JButton wp6;
	private JButton wp7;
	private JButton wp8;
	private JButton clear;
	private command com;
	
	public PatrolUI(){
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(5,1));
		
		selectAnimal = new JButton("Select Animal");
		wp1 = 		   new JButton("Waypoint 1");
		wp2 = 		   new JButton("Waypoint 2");
		wp3 = 		   new JButton("Waypoint 3");
		wp4 = 		   new JButton("Waypoint 4");
		wp5 = 		   new JButton("Waypoint 5");
		wp6 = 		   new JButton("Waypoint 6");
		wp7 = 		   new JButton("Waypoint 7");
		wp8 = 		   new JButton("Waypoint 8");
		clear = 		   new JButton("Clear Waypoints");
		
		mainPanel.add(selectAnimal);		mainPanel.add(clear);
		mainPanel.add(wp1); 				mainPanel.add(wp2);
		mainPanel.add(wp3); 				mainPanel.add(wp4);
		mainPanel.add(wp5); 				mainPanel.add(wp6);
		mainPanel.add(wp7); 				mainPanel.add(wp8);
		
		selectAnimal.addActionListener(this);
		wp1.			 addActionListener(this); 
		wp2.			 addActionListener(this);
		wp3.			 addActionListener(this); 
		wp4.			 addActionListener(this);
		wp5.			 addActionListener(this); 
		wp6.			 addActionListener(this);
		wp7.			 addActionListener(this); 
		wp8.			 addActionListener(this);
		clear. 		 addActionListener(this);
		
		this.add(mainPanel);
		com = command.NONE;
	}
	
	public command getCommand(){
		//Allow user to press numbers to select waypoints
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) return command.WP1;
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) return command.WP2;
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) return command.WP3;
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_4)) return command.WP4;
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_5)) return command.WP5;
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_6)) return command.WP6;
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_7)) return command.WP7;
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_8)) return command.WP8;
		return this.com;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == selectAnimal){
			com = command.SELECT;
		} else if (arg0.getSource() == wp1){
			com = command.WP1;
		} else if (arg0.getSource() == wp2){
			com = command.WP2;
		} else if (arg0.getSource() == wp3){
			com = command.WP3;
		} else if (arg0.getSource() == wp4){
			com = command.WP4;
		} else if (arg0.getSource() == wp5){
			com = command.WP5;
		} else if (arg0.getSource() == wp6){
			com = command.WP6;
		} else if (arg0.getSource() == wp7){
			com = command.WP7;
		} else if (arg0.getSource() == wp8){
			com = command.WP8;
		} else if (arg0.getSource() == clear){
			JOptionPane.showMessageDialog(null, "If you are sure you want to delete "+ 
												"all waypoints for the currently selected animal " + 
												"click anywhere on the map");
			com = command.CLEAR;
		}
	}
}
