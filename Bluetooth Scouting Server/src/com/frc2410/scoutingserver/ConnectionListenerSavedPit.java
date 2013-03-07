package com.frc2410.scoutingserver;

import java.io.IOException;
import java.util.LinkedList;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ConnectionListenerSavedPit implements Runnable
{
	MainPitUploadGUI m;
	
	public ConnectionListenerSavedPit(MainPitUploadGUI mG)
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

		if(streamConnNotifier != null)
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
						if(MainThread.usedUploadQueues[k] == false && addedQ == false)
						{
							threadsQueue = MainThread.uploadQueues[k];
							MainThread.usedUploadQueues[k] = true;
							queueIndex = k;
							addedQ = true;
						}
					}
					
					//Get JTextArea that is available
					boolean addedTA = false;
					for(int k = 0;k<=5;k++)
					{
						if(MainThread.usedUploadStatusArea[k] == false && addedTA == false)
						{
							statusArea = MainThread.uploadStatusFields[k];
							MainThread.usedUploadStatusArea[k] = true;
							statusIndex = k;
							addedTA = true;
						}
					}
					
					//If Queue or JTextArea is Null, that we can't accept a connection
					if(threadsQueue != null)
					{
						if(statusArea != null)
						{
							Thread conn = new Thread(new CommunicationThreadUploadPit(connection,threadsQueue,statusArea,queueIndex,statusIndex));
							//Make Sure we don't have over 6 Connections
							if(MainPitUploadGUI.addConnectionThread(conn) != 99)
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