package com.frc2410.scoutingserver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class MainGUI extends JFrame implements ActionListener,WindowListener
{
	static JTable clientsTable;
	static JTextField mNumber;
	JTextArea teams;
	JLabel cC;
	JLabel mN;
	JButton setupTeams;
	JLabel teamsL;
	static JTable redTeamsTable;
	static JTable blueTeamsTable;
	JButton startScouting;
	JScrollPane scrollPaneB;
	JScrollPane scrollPaneR;
	static int numConnected = 0;
	static boolean hasMatchData = false;
	static int[] redTeams = new int[3];
	static int[] blueTeams = new int[3];
	static int matchNumberValue = 0;
	MatchSetupGUI mG;
	
	public MainGUI(JPanel p1)
	{
		//Call to Super to Set Title
		super("FRC 2410 Bluetooth Server");
		
		//Set Background Color
		p1.setBackground(Color.GRAY);
		
		//Create Setup Dialog
		mG  = new MatchSetupGUI(new JPanel(null));
		
		//Create Components
		cC = new JLabel("Connected Clients");
	    TableModel dataModel = new DefaultTableModel(new Object[]{"Bluetooth Name","Bluetooth Address"},6);
	    clientsTable = new JTable(dataModel);
	    clientsTable.setEnabled(false);
	    JScrollPane scrollPane = new JScrollPane(clientsTable);
		setupTeams = new JButton("Setup Match");
		mN = new JLabel("Match Number");
		mNumber = new JTextField();
		mNumber.setEnabled(false);
		teamsL = new JLabel("Teams");
	    TableModel dataModelR = new DefaultTableModel(new Object[]{"Red Teams"},3);
	    redTeamsTable = new JTable(dataModelR);
	    redTeamsTable.setEnabled(false);
	    scrollPaneR = new JScrollPane(redTeamsTable);
	    TableModel dataModelB = new DefaultTableModel(new Object[]{"Blue Teams"},3);
	    blueTeamsTable = new JTable(dataModelB);
	    blueTeamsTable.setEnabled(false);
	    scrollPaneB = new JScrollPane(blueTeamsTable);
	    startScouting = new JButton("Begin Scouting");
	    startScouting.addActionListener(this);
	    
	    //Add Components to Panel
	    p1.add(cC);
	    p1.add(scrollPane);
	    p1.add(setupTeams);
	    p1.add(mN);
	    p1.add(mNumber);
	    p1.add(teamsL);
	    p1.add(scrollPaneR);
	    p1.add(scrollPaneB);
	    p1.add(startScouting);
	    
	    //Setup Insets and Positioning
	    Insets insets = p1.getInsets();
	    Dimension size = cC.getPreferredSize();
	    cC.setBounds(10 + insets.left, 10 + insets.top, size.width, size.height);
	    
	    scrollPane.setBounds(10 + insets.left, 30 + insets.top, 600, 119);
	    
	    setupTeams.setBounds(10 + insets.left, 160 + insets.top, 600, 50);
	    
	    size = mN.getPreferredSize();
	    mN.setBounds(10 + insets.left, 230 + insets.top, size.width, size.height);
	    
	    mNumber.setBounds(10 + insets.left, 250 + insets.top, size.width, size.height);
	    
	    size = teamsL.getPreferredSize();
	    teamsL.setBounds(10 + insets.left, 280 + insets.top, size.width, size.height);
	    
	    size = teamsL.getPreferredSize();
	    teamsL.setBounds(10 + insets.left, 280 + insets.top, size.width, size.height);
	    
	    scrollPaneR.setBounds(10 + insets.left, 300 + insets.top, 250, 71);
	    
	    scrollPaneB.setBounds(350 + insets.left, 300 + insets.top, 250, 71);
	    
	    startScouting.setBounds(10 + insets.left, 390 + insets.top, 600, 50);
	    
	    //Create Listeners for Buttons
	    setupTeams.addActionListener(this);
	    startScouting.addActionListener(this);
	    
	    
		//Make GUI Visible and Such
		add(p1);
		setSize(630,490);
	}
	
	public static void addTeams(ArrayList<String> teams)
	{
		
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		//Conditionals to Validate Which button was pressed
		if(arg0.getActionCommand().equals("Setup Match"))
		{
			//Start Match Configure Screen
			mG.setVisible(true);
		}
		else if(arg0.getActionCommand().equals("Begin Scouting"))
		{
			/*
			//Validate 6 Connected Clients
			if(numConnected == 6)
			{
			*/
				//Validate Match Data
				if(hasMatchData)
				{
					//Send Match Data to Clients
					if(MainThread.queues[0] != null)
					{
						MainThread.queues[0].push(redTeams[0]);
						System.out.println("Pushed");
					}
					if(MainThread.queues[1] != null)
					{
						MainThread.queues[1].push(redTeams[1]);
						System.out.println("Pushed");
					}
					if(MainThread.queues[2] != null)
					{
						MainThread.queues[2].push(redTeams[2]);
						System.out.println("Pushed");
					}
					if(MainThread.queues[3] != null)
					{
						MainThread.queues[3].push(blueTeams[0]);
						System.out.println("Pushed");
					}
					if(MainThread.queues[4] != null)
					{
						MainThread.queues[4].push(blueTeams[1]);
						System.out.println("Pushed");
					}
					if(MainThread.queues[5] != null)
					{
						MainThread.queues[5].push(blueTeams[2]);
						System.out.println("Pushed");
					}
					
					//Start GUI To Show Client Connection Status
					JPanel p3 = new JPanel(null);
					ScoutingGUI sG = new ScoutingGUI(p3,matchNumberValue,MainThread.statusFields);
					sG.setVisible(true);
					
					//Close MainGUI
					closeWindow();
				}
				else
				{
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame,"Please Add Match Data Before Initiating Scouting","Scouting Error",JOptionPane.ERROR_MESSAGE);
				}
			/*
			}
			else
			{
				JFrame frame = new JFrame();
				JOptionPane.showMessageDialog(frame,"Please Have 6 Connected Clients Before You Initiate Scouting","Scouting Error",JOptionPane.ERROR_MESSAGE);
			}
			*/
		}
		else if(arg0.getActionCommand().equals("Submit Data"))
		{
			System.out.println("Submit Button was Pressed");
			mG.dispose();
		}
	}

	public void closeWindow()
	{
		dispose();
	}
	
	public static void addConnectedClient(String btName, String btAddress)
	{
		boolean wasAdded = false;
		
		for(int i = 0;i<=5;i++)
		{
			if(wasAdded == false)
			{
				if(clientsTable.getModel().getValueAt(i, 0) == null)
				{
					clientsTable.getModel().setValueAt(btName, i, 0);
					clientsTable.getModel().setValueAt(btAddress, i, 1);
					numConnected++;
					wasAdded = true;
				}
			}
		}
	}
	
	public static void removeConnectedClient(String btN)
	{
		for(int i = 0;i<=5;i++)
		{
			if(clientsTable.getModel().getValueAt(i, 0) != null)
			{
				if(clientsTable.getModel().getValueAt(i, 0).toString().equals(btN))
				{
					//Clear Device
					clientsTable.getModel().setValueAt(null, i, 0);
					clientsTable.getModel().setValueAt(null, i, 1);
					numConnected--;
					
					//Make Sure Table has no Breaks
					cleanUpTable();
					
					//Remove Thread from Threads Array
				}
			}
		}
	}
	
	public static void cleanUpTable() 
	{
		//Loop through every row in the table
		for(int k = 0;k<=5;k++)
		{
			if(clientsTable.getModel().getValueAt(k, 0) == null)
			{
				if(k != 5)
				{
					if(clientsTable.getModel().getValueAt(k+1, 0) != null)
					{
						clientsTable.getModel().setValueAt(clientsTable.getModel().getValueAt(k+1, 0), k, 0);
						clientsTable.getModel().setValueAt(clientsTable.getModel().getValueAt(k+1, 1), k, 1);
						clientsTable.getModel().setValueAt(null, k+1, 0);
						clientsTable.getModel().setValueAt(null, k+1, 1);
					}
				}
			}
		}
	}

	public static int addConnectionThread(Thread con)
	{
		boolean added = false;
		int num = 0;
		
		for(int k = 0;k<=5;k++)
		{
			if(MainThread.connectionThreads[k] == null && added == false)
			{
				MainThread.connectionThreads[k] = con;
				added = true;
			}
		}
		
		System.out.println("Added? " + added);
		if(added = false)
		{
			return 99;
		}
		return num;
	}
	
	public static void setMatchData(int mN, int r1, int r2, int r3, int b1, int b2, int b3)
	{
		matchNumberValue = mN;
		mNumber.setText(String.valueOf(mN));
		redTeamsTable.getModel().setValueAt(r1, 0, 0);
		redTeams[0] = r1;
		redTeamsTable.getModel().setValueAt(r2, 1, 0);
		redTeams[1] = r2;
		redTeamsTable.getModel().setValueAt(r3, 2, 0);
		redTeams[2] = r3;
		blueTeamsTable.getModel().setValueAt(b1, 0, 0);
		blueTeams[0] = b1;
		blueTeamsTable.getModel().setValueAt(b2, 1, 0);
		blueTeams[1] = b2;
		blueTeamsTable.getModel().setValueAt(b3, 2, 0);
		blueTeams[2] = b3;
		hasMatchData = true;
	}
	
	public static boolean isTeamRed(int team)
	{
		for(int k = 0;k<=2;k++)
		{
			if(redTeams[k] == team)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		return false;
	}
	
	public static void freeRescources(int qI, int sI)
	{
		//Set Occupied Resources to false
		MainThread.usedQueues[qI] = false;
		MainThread.usedStatusArea[sI] = false;
		
		//Clear Status Area
		MainThread.statusFields[sI].setText(null);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) 
	{
		System.exit(0);
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
