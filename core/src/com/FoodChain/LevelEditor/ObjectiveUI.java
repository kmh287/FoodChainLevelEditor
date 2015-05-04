package com.FoodChain.LevelEditor;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ObjectiveUI extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int PIGS = 0;
	private int WOLVES = 1;
	
	private JPanel mainPanel;
	private JButton numPigsToCatch;
	private JButton numWolvesToCatch;
	private String objective = "0&0";
	
	public ObjectiveUI(){
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(5,1));
		
		numPigsToCatch 	 = new JButton("Pig objective");
		numWolvesToCatch = new JButton("Wolf objective");
		
		mainPanel.add(numPigsToCatch);		mainPanel.add(numWolvesToCatch);
		
		numPigsToCatch  .addActionListener(this);
		numWolvesToCatch.addActionListener(this);
		
		this.add(mainPanel);
	}
		
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == numPigsToCatch){
			int numPigs = 0;
			while (true){
				try{
					numPigs = Integer.parseInt(JOptionPane.showInputDialog("How many pigs should the hunter catch on this level? (non-negative integer)"));
					if (numPigs < 0 || numPigs > 100){
						JOptionPane.showMessageDialog(null, "Integer must be in 0...100");
					}
					else{
						break;
					}
				}
				catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "That was not an integer");
				}
			}
			setObjectiveString(numPigs, getNum(WOLVES));
		}
		else if (arg0.getSource() == numWolvesToCatch){
			int numWolves = 0;
			while (true){
				try{
					numWolves = Integer.parseInt(JOptionPane.showInputDialog("How many wolves should the hunter catch on this level? (non-negative integer)"));
					if (numWolves < 0 || numWolves > 100){
						JOptionPane.showMessageDialog(null, "Integer must be in 0...100");
					}
					else{
						break;
					}
				}
				catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "That was not an integer");
				}
			}
			setObjectiveString(getNum(PIGS), numWolves);
		}
	}

	private void setObjectiveString(int numPigs, int numWolves) {
		objective = numPigs + "&" + numWolves;
	}
	
	public String getObjectiveString(){
		return objective;
	}
	
	public void setObjectiveString(String obj){
		objective = obj;
	}

	private int getNum(int animalType) {
		//String is guaranteed to only have ints, so no need for try/catch
		return Integer.parseInt(objective.split("&")[animalType]);
	}
}
