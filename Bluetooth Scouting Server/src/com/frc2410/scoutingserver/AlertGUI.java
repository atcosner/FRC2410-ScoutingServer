package com.frc2410.scoutingserver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AlertGUI extends JFrame implements ActionListener
{
	JLabel terminateWarning;
	JLabel terminateQuestion;
	JButton yesTerminate;
	JButton noTerminate;
	
	public AlertGUI(JPanel p1)
	{
		//Call to Super to Set Title
		super("Server Alert");
		
		//Set Background Color
		p1.setBackground(Color.GRAY);
		
		//Initialize Variables
		terminateWarning = new JLabel("Not All Clients have Completed Scouting!");
		terminateQuestion = new JLabel("Are you sure you want to terminate Scouting Early?");
		yesTerminate = new JButton("Yes");
		noTerminate = new JButton("No");
		
		//Add Components to JPanel
		p1.add(terminateWarning);
		p1.add(terminateQuestion);
		p1.add(yesTerminate);
		p1.add(noTerminate);
		
		//Setup Insets and Positioning
	    Insets insets = p1.getInsets();
	    Dimension size = terminateWarning.getPreferredSize();
	    terminateWarning.setBounds(10 + insets.left, 10 + insets.top, size.width, size.height);
	    
	    size = terminateQuestion.getPreferredSize();
	    terminateQuestion.setBounds(10 + insets.left, 30 + insets.top, size.width, size.height);
	    
	    yesTerminate.setBounds(10 + insets.left, 50 + insets.top, 150, 30);
	    
	    noTerminate.setBounds(10 + insets.left, 90 + insets.top, 150, 30);
	    
	    //Add ActionListner to Button
	    yesTerminate.addActionListener(new ScoutingGUI());
	    noTerminate.addActionListener(this);
	    
		//Make GUI Visible and Set Size
		add(p1);
		setSize(320,180);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		//No Was Pressed
		dispose();
	}
}
