package com.FoodChain.LevelEditor;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.FoodChain.LevelEditor.MainUI.command;

public class PatrolUI extends JFrame implements ActionListener{

	public enum command{NONE,SELECT,WP1,WP2,WP3,WP4,CLEAR}
	
	
	private JPanel mainPanel;
	private JButton selectAnimal;
	private JButton wp1;
	private JButton wp2;
	private JButton wp3;
	private JButton wp4;
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
		clear = 		   new JButton("Clear Waypoints");
		
		mainPanel.add(selectAnimal);		mainPanel.add(clear);
		mainPanel.add(wp1); 				mainPanel.add(wp2);
		mainPanel.add(wp3); 				mainPanel.add(wp4);
		
		selectAnimal.addActionListener(this);
		wp1.			 addActionListener(this); 
		wp2.			 addActionListener(this);
		wp3.			 addActionListener(this); 
		wp4.			 addActionListener(this);
		clear. 		 addActionListener(this);
		
		this.add(mainPanel);
		com = command.NONE;
	}
	
	public command getCommand(){
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
		} else if (arg0.getSource() == clear){
			JOptionPane.showMessageDialog(null, "If you are sure you want to delete "+ 
												"all waypoints for the currently selected animal " + 
												"click anywhere on the map");
			com = command.CLEAR;
		}
	}
}
