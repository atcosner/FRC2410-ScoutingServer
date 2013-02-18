package com.frc2410.scoutingserver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import javax.swing.*;

public class WelcomeGUI extends JFrame implements WindowListener
{
	public static boolean readyToMove = false;
	JTextField statsQuestion;
	
	public WelcomeGUI(JPanel p1)
	{
		//Call to Super Class
		super("FRC 2410 Scouting Application");

		//Set Background Color
		p1.setBackground(Color.GRAY);
		
		//Set our Tracker Variable
		readyToMove = false;
		
		//Create all of the Components
		JLabel header1 = new JLabel("Welcome to the FRC Bluetooth Scouting Server");
		header1.setFont(new Font("Serif", Font.BOLD, 20));
		
		JLabel header2 = new JLabel("Created by Team 2410 BV CAPS Metal Mustangs");
		header2.setFont(new Font("Serif", Font.BOLD, 20));
		
		JLabel statsQuestionL = new JLabel("What Team are you on?");
		statsQuestion = new JTextField(4);
		
		JButton startButton = new JButton("Start the Server");
		
		//Add First Header Text
		p1.add(header1);
		
		//Add Second Header Text
		p1.add(header2);
		
		//Add Analytics Question
		p1.add(statsQuestionL);
		p1.add(statsQuestion);
		
		//Add Button to Start Component
		p1.add(startButton);
		
		//Create Absolute Positioning
		Insets insets = p1.getInsets();
		Dimension size = header1.getPreferredSize();
		header1.setBounds(100 + insets.left, 30 + insets.top,size.width, size.height);
	
		size = header2.getPreferredSize();
		header2.setBounds(95 + insets.left, 50 + insets.top,size.width, size.height);
		
		size = statsQuestionL.getPreferredSize();
		statsQuestionL.setBounds(240 + insets.left, 100 + insets.top,size.width, size.height);
		
		size = statsQuestion.getPreferredSize();
		statsQuestion.setBounds(280 + insets.left, 120 + insets.top,size.width, size.height);
		
		
		size = startButton.getPreferredSize();
		startButton.setBounds(245 + insets.left, 200 + insets.top,size.width, size.height);
		
		//Add a Listener to the Start button
		startButton.addActionListener(new MainThread());
		
		//Make GUI Visible and Such
		add(p1);
		setSize(630,300);
	}
	
	public boolean getReadyStatus()
	{
		return readyToMove;
	}
	
    public void pullThePlug() 
    {
        WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
    }

	public boolean validData() 
	{
		int teamNumber = 0;
		try
		{
			teamNumber = Integer.parseInt(statsQuestion.getText());
			return true;
		}
		catch (NumberFormatException e)
		{
			return false;
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) 
	{
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
