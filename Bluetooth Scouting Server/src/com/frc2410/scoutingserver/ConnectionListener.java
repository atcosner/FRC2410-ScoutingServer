package com.frc2410.scoutingserver;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ConnectionListener implements Runnable
{
	MainGUI m;
	
	public ConnectionListener(MainGUI mG)
	{
		m = mG;
	}
	
	public void run()
	{
		//Create a UUID for SPP
        UUID uuid = new UUID("1101", true);
        
        //Create a Stream URL
        String connectionString = "btspp://localhost:" + uuid +";name=FRC 2410 Scouting Server";

        //Open Server URL
        StreamConnectionNotifier streamConnNotifier = null;
        
		try 
		{
			streamConnNotifier = (StreamConnectionNotifier)Connector.open(connectionString);
		} 
		catch (IOException e) 
		{
			//Error on Creating the Server
		}

		if(!(streamConnNotifier == null))
		{
			System.out.println("Server Started. Waiting for clients to connect...");
			while(true)
			{
				//Variables for the Communication Threads
				StreamConnection connection = null;
				LinkedList<Integer> threadsQueue = null;
				JTextArea statusArea = null;
				int queueIndex = 0;
				int statusIndex = 0;
				
				try 
				{
					//Accept and Get Connection
					connection = streamConnNotifier.acceptAndOpen();
					
					//Get Queue that is Available
					boolean addedQ = false;
					for(int k = 0;k<=5;k++)
					{
						if(MainThread.usedQueues[k] == false && addedQ == false)
						{
							threadsQueue = MainThread.queues[k];
							MainThread.usedQueues[k] = true;
							queueIndex = k;
							addedQ = true;
						}
					}
					
					//Get JTextArea that is available
					boolean addedTA = false;
					for(int k = 0;k<=5;k++)
					{
						if(MainThread.usedStatusArea[k] == false && addedTA == false)
						{
							statusArea = MainThread.statusFields[k];
							MainThread.usedStatusArea[k] = true;
							statusIndex = k;
							addedTA = true;
						}
					}
					
					//If Queue or JTextArea is Null, that we can't accept a connection
					if(!(threadsQueue == null))
					{
						if(!(statusArea == null))
						{
							Thread conn = new Thread(new CommunicationThread(connection,threadsQueue,statusArea,queueIndex,statusIndex));
							//Make Sure we don't have over 6 Connections
							if(MainGUI.addConnectionThread(conn) != 99)
							{
								//<6 Connections
								//Start Communication Thread
								conn.start();
							}
							else
							{
								//<6 Connections
								//Cannot Do anything, Nullify Connection Thread
								conn = null;
							}
						}
						else
						{
							System.out.println("Null JTextArea, Cant Accept Connection");
						}
					}
					else
					{
						System.out.println("Null Queue, Cant Accept Connection");
					}
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			} 
		}
		else
		{
			//BlueTooth is not Available, so Show Error
			JPanel p = new JPanel(null);
			SevereErrorGUI sEG = new SevereErrorGUI(p);
			sEG.setVisible(true);
			
			//Hide MainGUI
			m.hideWindow();
		}
	}
}
