package com.FoodChain.LevelEditor;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ActorUI extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private JPanel mainPanel;
	private JButton pig;
	private JButton wolf;
	private JButton owl;
	private JButton hunter;
	private JButton deleteAnimal;
	
	private Actor.actorType selected = null;
	
	public ActorUI(){
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(5,1));
		
		pig 				= new JButton("Pig");
		wolf 			= new JButton("Wolf");
		owl 				= new JButton("Owl");
		hunter 			= new JButton("Hunter");
		deleteAnimal 	= new JButton("Delete Animal");
		
		mainPanel.add(pig);			mainPanel.add(wolf);
		mainPanel.add(owl);			mainPanel.add(hunter);
		mainPanel.add(deleteAnimal);
		
		pig				.addActionListener(this);
		wolf				.addActionListener(this);
		owl				.addActionListener(this);
		hunter			.addActionListener(this);
		deleteAnimal		.addActionListener(this);
		
		this.add(mainPanel);
	}
	
	public Actor.actorType getSelected(){
		return this.selected;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == pig){
			this.selected = Actor.actorType.PIG;
		}
		else if (arg0.getSource() == wolf){
			this.selected = Actor.actorType.WOLF;
		}
		else if (arg0.getSource() == owl){
			this.selected = Actor.actorType.OWL;
		}
		else if (arg0.getSource() == hunter){
			this.selected = Actor.actorType.HUNTER;
		}
		else if (arg0.getSource() == deleteAnimal){
			//Special case, needs to be checked in calling module
			this.selected = null;
		}
	}

}
