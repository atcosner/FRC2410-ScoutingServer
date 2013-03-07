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

public class MainPitUploadGUI extends JFrame implements ActionListener,WindowListener
{
	static JTable clientsTable;
	JTextArea teams;
	JLabel cC;
	JButton startScouting;
	static int numConnected = 0;
	
	public MainPitUploadGUI(JPanel p1)
	{
		//Call to Super to Set Title
		super("FRC 2410 Bluetooth Server");
		
		//Set Background Color
		p1.setBackground(Color.GRAY);
		
		//Create Components
		cC = new JLabel("Connected Clients");
	    TableModel dataModel = new DefaultTableModel(new Object[]{"Bluetooth Name","Bluetooth Address"},6);
	    clientsTable = new JTable(dataModel);
	    clientsTable.setEnabled(false);
	    JScrollPane scrollPane = new JScrollPane(clientsTable);
	    startScouting = new JButton("Begin Pit Upload");
	    
	    //Add Components to Panel
	    p1.add(cC);
	    p1.add(scrollPane);
	    p1.add(startScouting);
	    
	    //Setup Insets and Positioning
	    Insets insets = p1.getInsets();
	    Dimension size = cC.getPreferredSize();
	    cC.setBounds(10 + insets.left, 10 + insets.top, size.width, size.height);
	    
	    scrollPane.setBounds(10 + insets.left, 30 + insets.top, 600, 119);
	    
	    startScouting.setBounds(10 + insets.left, 160 + insets.top, 600, 50);
	    
	    //Create Listeners for Button
	    startScouting.addActionListener(this);
	    
	    //Add Window Listener
	    addWindowListener(this);
	    
		//Make GUI Visible and Such
		add(p1);
		setSize(630,250);
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		//Conditionals to Validate Which button was pressed
		if(arg0.getActionCommand().equals("Begin Pit Upload"))
		{
			//Validate at least one COnnected Client
			if(numConnected >= 1)
			{
				//Send Match Data to Clients
				if(MainThread.uploadQueues[0] != null)
				{
					MainThread.uploadQueues[0].push(1);
				}
				if(MainThread.uploadQueues[1] != null)
				{
					MainThread.uploadQueues[1].push(1);
				}
				if(MainThread.uploadQueues[2] != null)
				{
					MainThread.uploadQueues[2].push(1);
				}
				if(MainThread.uploadQueues[3] != null)
				{
					MainThread.uploadQueues[3].push(1);
				}
				if(MainThread.uploadQueues[4] != null)
				{
					MainThread.uploadQueues[4].push(1);
				}
				if(MainThread.uploadQueues[5] != null)
				{
					MainThread.uploadQueues[5].push(1);
				}

				//Start GUI To Show Client Connection Status
				ScoutingUploadGUI sG = new ScoutingUploadGUI(new JPanel(null),MainThread.uploadStatusFields,numConnected,"Pit");
				sG.setVisible(true);

				//Close MainGUI
				hideWindow();
			}
			else
			{
				JFrame frame = new JFrame();
				JOptionPane.showMessageDialog(frame,"Please Have at least 1 Connected Clients Before You Initiate Scouting","Scouting Error",JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void hideWindow()
	{
		setVisible(false);
	}
	
	public static void addConnectedClient(String btName, String btAddress)
	{
		boolean wasAdded = false;
		
		for(int i = 0;i<=5;i++)
		{
			if(wasAdded == false)
			{
				if(clientsTable.getModel().getValueAt(i, 0).equals(null))
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
				System.out.println("Found Null Value at: " + k);
				if(k != 5)
				{
					if(clientsTable.getModel().getValueAt(k+1, 0) != null)
					{
						System.out.println("Found Occupied Row at: " + (k+1));
						String btName = clientsTable.getModel().getValueAt(k+1, 0).toString();
						String btAddress = clientsTable.getModel().getValueAt(k+1, 1).toString();
						clientsTable.getModel().setValueAt(btName, k, 0);
						clientsTable.getModel().setValueAt(btAddress, k, 1);
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
			if(MainThread.uploadConnectionThreads[k] == null && added == false)
			{
				MainThread.uploadConnectionThreads[k] = con;
				added = true;
			}
		}
		
		if(added = false)
		{
			return 99;
		}
		return num;
	}
	
	public static void freeRescources(int qI, int sI)
	{
		//Set Occupied Resources to false
		MainThread.usedUploadQueues[qI] = false;
		MainThread.usedUploadStatusArea[sI] = false;
		
		//Clear Status Area
		MainThread.uploadStatusFields[sI].setText(null);
	}

	@Override
	public void windowActivated(WindowEvent arg0) 
	{
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) 
	{
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) 
	{
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) 
	{
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) 
	{
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) 
	{
		
	}

	@Override
	public void windowOpened(WindowEvent arg0)
	{
		
	}
}
