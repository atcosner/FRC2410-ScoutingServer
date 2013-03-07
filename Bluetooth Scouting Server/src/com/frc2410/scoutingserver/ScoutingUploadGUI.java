package com.frc2410.scoutingserver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ScoutingUploadGUI extends JFrame implements ActionListener,WindowListener
{
	JTextArea[] statusFields = null;
	JTextArea device1;
	JTextArea device2;
	JTextArea device3;
	JTextArea device4;
	JTextArea device5;
	JTextArea device6;
	JLabel connectedClients;
	JLabel device1L;
	JLabel device2L;
	JLabel device3L;
	JLabel device4L;
	JLabel device5L;
	JLabel device6L;
	JButton terminateScouting;
	JLabel clientsNumL;
	static JTextField clientsNum;
	JLabel completedClientsL;
	static JTextField completedClients;
	static int numFinished = 0;	
	AlertGUI aG;
	static int devicesUploading;
	String uploadType;
	
	public ScoutingUploadGUI()
	{
		
	}
	
	public ScoutingUploadGUI(JPanel p1,JTextArea[] devices,int devicesU,String uT)
	{
		//Call to Super to Set Title
		super("FRC 2410 Bluetooth Server");
		
		//Set Background Color
		p1.setBackground(Color.GRAY);
		
		//Set Variables to Parameters
		statusFields = devices;
		devicesUploading = devicesU;
		uploadType = uT;
		
		//Create Components
		device1 = statusFields[0];
		device2 = statusFields[1];
		device3 = statusFields[2];
		device4 = statusFields[3];
		device5 = statusFields[4];
		device6 = statusFields[5];
		connectedClients = new JLabel("Connected Clients");
		device1L = new JLabel("Device 1");
		device2L = new JLabel("Device 2");
		device3L = new JLabel("Device 3");
		device4L = new JLabel("Device 4");
		device5L = new JLabel("Device 5");
		device6L = new JLabel("Device 6");
		terminateScouting = new JButton("Terminate Scouting");
		clientsNumL =  new JLabel("Clients Uploading");
		clientsNum = new JTextField(20);
		completedClientsL =  new JLabel("Clients That Have Completed Upload");
		completedClients = new JTextField(20);
		
		//Add Components to the Panel
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
		p1.add(clientsNumL);
		p1.add(clientsNum);
		p1.add(completedClientsL);
		p1.add(completedClients);
		
		//Setup Insets and Positioning
	    Insets insets = p1.getInsets();
	    
	    Dimension size = connectedClients.getPreferredSize();
	    connectedClients.setBounds(10 + insets.left, 70 + insets.top, size.width, size.height);
	    
	    size = clientsNumL.getPreferredSize();
	    clientsNumL.setBounds(10 + insets.left, 10 + insets.top, size.width, size.height);
	    
	    clientsNum.setBounds(10 + insets.left, 30 + insets.top, 100, size.height);
	    clientsNum.setEnabled(false);
	    clientsNum.setText(String.valueOf(devicesUploading));
	    
	    size = completedClientsL.getPreferredSize();
	    completedClientsL.setBounds(150 + insets.left, 10 + insets.top, size.width, size.height);
	    
	    completedClients.setBounds(150 + insets.left, 30 + insets.top, 100, size.height);
	    completedClients.setEnabled(false);
	    completedClients.setText(String.valueOf(numFinished));
	    
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
	    
	    terminateScouting.setBounds(10 + insets.left, 550 + insets.top, 640, 50);
	    
	    //Add Action Listener
	    terminateScouting.addActionListener(this);
	    
	    //Add Window Listener
	    addWindowListener(this);
	    
		//Make GUI Visible and Set Size
		add(p1);
		setSize(670,670);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals("Terminate Scouting"))
		{
			//Terminate Scouting Button was Pressed
			//Check if Good Quit or not
			if(uploadType.equals("Pit"))
			{
				if(MainPitUploadGUI.numConnected == numFinished)
				{
					//Nullify Communication Threads
					for(int k = 0;k<=5;k++)
					{
						MainThread.uploadConnectionThreads[k] = null;
					}

					//Close Window
					closeWindow();

					//Shut Down System
					System.exit(0);
				}
				else
				{
					//Alert User To Problems Before Termination
					aG = new AlertGUI(new JPanel(null));
					aG.setVisible(true);
				}
			}
			else
			{
				if(MainUploadGUI.numConnected == numFinished)
				{
					//Nullify Communication Threads
					for(int k = 0;k<=5;k++)
					{
						MainThread.uploadConnectionThreads[k] = null;
					}

					//Close Window
					closeWindow();

					//Shut Down System
					System.exit(0);
				}
				else
				{
					//Alert User To Problems Before Termination
					aG = new AlertGUI(new JPanel(null));
					aG.setVisible(true);
				}
			}
		}
		else if(e.getActionCommand().equals("Yes"))
		{
			//User want to Terminate Early
			
			//Nullify Connection Threads
			for(int k = 0;k<=5;k++)
			{
				MainThread.uploadConnectionThreads[k] = null;
			}

			//Close Window
			closeWindow();

			//Shut Down System
			System.exit(0);
		}
	}
	
	private void closeWindow()
	{
		this.dispose();
	}
	
	public static void updateScoutingClientsFields()
	{
		//Set Client Fields to Values
		//Refresh if Changed
	    clientsNum.setText(String.valueOf(devicesUploading));
	    completedClients.setText(String.valueOf(numFinished));
	    
	    //Check to See if Scouting has been Completed
	    if(devicesUploading==numFinished)
	    {
	    	//Scouting Has Been Completed by all available devices
	    	//Display Message
	    	JFrame f1 = new JFrame();
	    	JOptionPane.showMessageDialog(f1,"Upload has been completed by all Connected Devices! \nYou may now safely press the Terminate Scouting Button!");
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

