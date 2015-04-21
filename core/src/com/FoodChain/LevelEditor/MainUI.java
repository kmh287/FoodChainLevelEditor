package com.FoodChain.LevelEditor;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainUI extends JFrame implements ActionListener  {

	public enum command{NO_ACTION, NEW_NOSAVE, NEW_SAVE, 
						SAVE, LOAD, LOAD_AUTOSAVE, TILE_MODE, 
						ACTOR_MODE, OBJECTIVE_MODE}
	
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JButton newMap;
	private JButton save;
	private JButton load;
	private JButton loadAutosave;
	private JButton tileMode;
	private JButton actorMode;
	private JButton objectiveMode;
	
	private command com;
	
	public MainUI(){
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(5,1));
		
		newMap 		  = new JButton("New");
		save 		  = new JButton("Save");
		load			  = new JButton("Load");
		loadAutosave  = new JButton("Restore from Autosave");
		tileMode 	  = new JButton("Tile mode");
		actorMode	  = new JButton("Actor mode");
		objectiveMode = new JButton("Objective mode");
		
		mainPanel.add(newMap);		mainPanel.add(save);	 	
		mainPanel.add(load);			mainPanel.add(loadAutosave);
		mainPanel.add(tileMode);		mainPanel.add(actorMode); 	
		mainPanel.add(objectiveMode);
		
		newMap		 .addActionListener(this);
		save			 .addActionListener(this);
		load			 .addActionListener(this);
		loadAutosave .addActionListener(this);
		tileMode		 .addActionListener(this);
		actorMode	 .addActionListener(this);
		objectiveMode.addActionListener(this);
		
		this.add(mainPanel);
		
		com = command.NO_ACTION;
	}
	
	public command getCommand(){
		return this.com;
	}
	
	public void resetCommand(){
		this.com = command.NO_ACTION;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == newMap){
			int reply = JOptionPane.showConfirmDialog(null, "Save current map?", 
													  "alert", JOptionPane.YES_NO_CANCEL_OPTION);
			//CANCEL = 2
			//NO = 1
			//YES = 0
			//Closing the box = -1
			if(reply == 1){
				this.com = command.NEW_NOSAVE;
			} else if (reply == 0){
				this.com = command.NEW_SAVE;
			} else {
				this.com = command.NO_ACTION;
			}
		}
		else if (arg0.getSource() == save){
			this.com = command.SAVE;
		}
		else if (arg0.getSource() == load){
			this.com = command.LOAD;
		}
		else if (arg0.getSource() == loadAutosave){
			this.com = command.LOAD_AUTOSAVE;
		}
		else if (arg0.getSource() == tileMode){
			this.com = command.TILE_MODE;
		}
		else if (arg0.getSource() == actorMode){
			this.com = command.ACTOR_MODE;
		}
		else if (arg0.getSource() == objectiveMode){
			this.com = command.OBJECTIVE_MODE;
		}
	}

}
