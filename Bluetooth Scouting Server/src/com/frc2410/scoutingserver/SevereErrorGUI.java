package com.frc2410.scoutingserver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SevereErrorGUI extends JFrame implements ActionListener
{
	JLabel errorMsg;
	JLabel errorMsg1;
	JLabel errorMsg2;
	JButton exit;
	
	public SevereErrorGUI(JPanel p1)
	{
		//Super call to Set Title
		super("Severe Bluetooth Error");
		
		//Close MainGUI to prevent workaround on Error Dialog

		//Set Background Color
		p1.setBackground(Color.GRAY);
		
		//Initialize Components
		errorMsg = new JLabel("Bluetooth could not be initialized!");
		errorMsg1 = new JLabel("Server cannot be used without Bluetooth!");
		errorMsg2 = new JLabel("Press OK below to exit the server!");
		exit = new JButton("OK");
		
		//Add Components to Panel
		p1.add(errorMsg);
		p1.add(errorMsg1);
		p1.add(errorMsg2);
		p1.add(exit);
		
		//Setup Insets and Positioning
	    Insets insets = p1.getInsets();
	    Dimension size = errorMsg.getPreferredSize();
	    errorMsg.setBounds(10 + insets.left, 10 + insets.top, size.width, size.height);
	    
	    size = errorMsg1.getPreferredSize();
	    errorMsg1.setBounds(10 + insets.left, 30 + insets.top, size.width, size.height);
	    
	    size = errorMsg2.getPreferredSize();
	    errorMsg2.setBounds(10 + insets.left, 50 + insets.top, size.width, size.height);
	    
	    exit.setBounds(30 + insets.left, 70 + insets.top, 100, 30);
	    
	    //Add ActionListner to Button
	    exit.addActionListener(this);
	    
		//Make GUI Visible and Set Size
		add(p1);
		setSize(300,150);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getActionCommand().equals("OK"))
		{
			//Exit Entire Program
			System.exit(0);
		}
	}
}
