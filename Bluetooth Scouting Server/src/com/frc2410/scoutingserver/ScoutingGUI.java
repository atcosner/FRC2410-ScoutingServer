package com.frc2410.scoutingserver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ScoutingGUI extends JFrame implements ActionListener
{
	JTextArea[] statusFields = null;
	JTextArea device1;
	JTextArea device2;
	JTextArea device3;
	JTextArea device4;
	JTextArea device5;
	JTextArea device6;
	JTextArea matchNumberField;
	JLabel connectedClients;
	JLabel matchNumber;
	JLabel device1L;
	JLabel device2L;
	JLabel device3L;
	JLabel device4L;
	JLabel device5L;
	JLabel device6L;
	JTextField mNumber;
	JButton terminateScouting;
	
	public ScoutingGUI()
	{
		
	}		
	
	public ScoutingGUI(JPanel p1,int matchNum,JTextArea[] devices)
	{
		//Call to Super to Set Title
		super("FRC 2410 Bluetooth Server");
		
		//Set Background Color
		p1.setBackground(Color.GRAY);
		
		//Set Variables to Parameters
		statusFields = devices;
		
		//Create Components
		device1 = statusFields[0];
		device2 = statusFields[1];
		device3 = statusFields[2];
		device4 = statusFields[3];
		device5 = statusFields[4];
		device6 = statusFields[5];
		matchNumberField = new JTextArea();
		connectedClients = new JLabel("Connected Clients");
		matchNumber = new JLabel("Match Number");
		device1L = new JLabel("Device 1");
		device2L = new JLabel("Device 2");
		device3L = new JLabel("Device 3");
		device4L = new JLabel("Device 4");
		device5L = new JLabel("Device 5");
		device6L = new JLabel("Device 6");
		mNumber = new JTextField(20);
		terminateScouting = new JButton("Terminate Scouting");
		
		//Add Components to the Panel
		p1.add(matchNumber);
		p1.add(mNumber);
		p1.add(connectedClients);
		p1.add(device1);
		p1.add(device2);
		p1.add(device3);
		p1.add(device4);
		p1.add(device5);
		p1.add(device6);
		p1.add(device1L);
		p1.add(device2L);
		p1.add(device3L);
		p1.add(device4L);
		p1.add(device5L);
		p1.add(device6L);
		p1.add(terminateScouting);
		
		//Setup Insets and Positioning
	    Insets insets = p1.getInsets();
	    Dimension size = matchNumber.getPreferredSize();
	    matchNumber.setBounds(10 + insets.left, 10 + insets.top, size.width, size.height);
	    
	    mNumber.setBounds(10 + insets.left, 30 + insets.top, 100, size.height);
	    mNumber.setEnabled(false);
	    mNumber.setText(String.valueOf(matchNum));
	    
	    size = connectedClients.getPreferredSize();
	    connectedClients.setBounds(10 + insets.left, 70 + insets.top, size.width, size.height);
	    
	    size = device1L.getPreferredSize();
	    device1L.setBounds(10 + insets.left, 90 + insets.top, size.width, size.height);
	    device1.setBounds(10 + insets.left, 110 + insets.top, 200, 200);
	    device1.setEnabled(false);
	    
	    size = device2L.getPreferredSize();
	    device2L.setBounds(230 + insets.left, 90 + insets.top, size.width, size.height);
	    device2.setBounds(230 + insets.left, 110 + insets.top, 200, 200);
	    device2.setEnabled(false);
	    
	    size = device3L.getPreferredSize();
	    device3L.setBounds(450 + insets.left, 90 + insets.top, size.width, size.height);
	    device3.setBounds(450 + insets.left, 110 + insets.top, 200, 200);
	    device3.setEnabled(false);
	    
	    size = device4L.getPreferredSize();
	    device4L.setBounds(10 + insets.left, 310 + insets.top, size.width, size.height);
	    device4.setBounds(10 + insets.left, 330 + insets.top, 200, 200);
	    device4.setEnabled(false);
	    
	    size = device5L.getPreferredSize();
	    device5L.setBounds(230 + insets.left, 310 + insets.top, size.width, size.height);
	    device5.setBounds(230 + insets.left, 330 + insets.top, 200, 200);
	    device5.setEnabled(false);
	    
	    size = device6L.getPreferredSize();
	    device6L.setBounds(450 + insets.left, 310 + insets.top, size.width, size.height);
	    device6.setBounds(450 + insets.left, 330 + insets.top, 200, 200);
	    device6.setEnabled(false);
	    
	    terminateScouting.setBounds(10 + insets.left, 550 + insets.top, 650, 50);
	    
	    terminateScouting.addActionListener(this);
	    
		//Make GUI Visible and Set Size
		add(p1);
		setSize(750,700);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		//Terminate Scouting was Pressed
		//Nullify Communication Threads
		for(int k = 0;k<=5;k++)
		{
			MainThread.connectionThreads[k] = null;
		}
		
		//Close Window
		closeWindow();
		
		//Shut Down System
		System.exit(0);
	}
	
	private void closeWindow()
	{
		this.dispose();
	}
}
