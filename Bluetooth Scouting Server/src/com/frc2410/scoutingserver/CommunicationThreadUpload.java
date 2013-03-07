package com.frc2410.scoutingserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import javax.bluetooth.RemoteDevice;
import javax.microedition.io.StreamConnection;
import javax.swing.JTextArea;

public class CommunicationThreadUpload implements Runnable
{
	StreamConnection connection;
	RemoteDevice device;
	InputStream inStream;
	BufferedReader bReader;
	OutputStream outStream;
	PrintWriter pWriter;
	String clientMessage;
	String deviceName;
	boolean clientConnected = true;
	private LinkedList<Integer> threadsInfo;
	private static final class Lock { }
	private final Object lock = new Lock();
	JTextArea statusField;
	int queueIndex = 0;
	int statusIndex = 0;
	String uploadType;
	
	public CommunicationThreadUpload(StreamConnection con, LinkedList<Integer> threadsInfo2,JTextArea sF,int qI,int sI)
	{
		threadsInfo = threadsInfo2;
		connection = con;
		statusField = sF;
		queueIndex = qI;
		statusIndex = sI;
		try 
		{
			//Get Bluetooth device with the Bluetooth Connection
			device = RemoteDevice.getRemoteDevice(connection);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public void run() 
	{
		//Get Bluetooth Device Address
		String btA = device.getBluetoothAddress();
		System.out.println("Remote device address: " + btA);
        statusField.append("Remote device address: " + btA + "\n");
        String btN = "";
        
        try 
        {
        	//Get Bluetooth Device Name
        	btN = device.getFriendlyName(true);
        	deviceName = btN;
        	System.out.println("Remote device name: " + btN);
        	statusField.append("Remote device name: " + btN + "\n");
		} 
        catch (IOException e) 
        {
			e.printStackTrace();
			clientConnected = false;
			MainUploadGUI.removeConnectedClient(btN);
			MainUploadGUI.freeRescources(queueIndex, statusIndex);
		}
        
        //Add Client to Main Table
        MainUploadGUI.addConnectedClient(btN, btA);
        
        try
        {
        	//Open Input Stream and Reader
        	inStream = connection.openInputStream();
	        bReader = new BufferedReader(new InputStreamReader(inStream));
	        statusField.append("Created the Input Stream\n");
        }
        catch(IOException e)
        {
        	e.printStackTrace();
        	clientConnected = false;
        	MainUploadGUI.removeConnectedClient(btN);
        	MainUploadGUI.freeRescources(queueIndex, statusIndex);
        }
        
        try
        {
        	//Open Output Stream and Writer
        	outStream = connection.openOutputStream();
	        pWriter = new PrintWriter(new OutputStreamWriter(outStream));
	        statusField.append("Created the Ouput Stream\n");
	        sendData("hello");
	        statusField.append("Sent hello to Client\n");
        }
        catch(IOException e)
        {
        	e.printStackTrace();
        	clientConnected = false;
        	MainUploadGUI.removeConnectedClient(btN);
        	MainUploadGUI.freeRescources(queueIndex, statusIndex);
        	statusField.append("Client Disconnected\n");
        }
        
        try 
        {
        	//Wait For Client Message
			clientMessage = bReader.readLine();
			System.out.println("Client Sent: " + clientMessage);
			statusField.append("Client Sent: " + clientMessage + "\n");
		} 
        catch (IOException e) 
        {
			e.printStackTrace();
			//Client has disconnected
			clientConnected = false;
			
			//Remove Client from List
			MainUploadGUI.removeConnectedClient(deviceName);
			MainUploadGUI.freeRescources(queueIndex, statusIndex);
			statusField.append("Client Disconnected\n");
		}
        
        //Make Sure Client is still Connected
        if(clientConnected)
        {
        	//Analyze String for Command
        	if(clientMessage.equals("ready"))
        	{  	
        		System.out.println("Client sent ready");
        		//Continue to Ping Client until Queue has been filled
        		String clientResponceP = "";
        		do
        		{
                	//Send Ping
                	System.out.println("Sent Ping");
                	sendData("1");
                	
                	//Wait for Response from Client
                	try 
                	{
        				clientResponceP = bReader.readLine();
        				System.out.println("Client Sent: " + clientResponceP);
        			} 
                	catch (IOException e) 
                	{
                		//Client Disconnected
            			//Remove Client from List
            			MainUploadGUI.removeConnectedClient(deviceName);
            			MainUploadGUI.freeRescources(queueIndex, statusIndex);
            			statusField.append("Client Disconnected\n");
                		System.out.println("Connection Failed on Reading");
                		clientConnected = false;
                		break;
        			}
                	
                	if(clientResponceP != null)
                	{
                		//Analyze Client Responses
                		System.out.println("Analyzing Client Responce");
                		if(clientResponceP.equals("2"))
                		{
                			System.out.println("Recieved Ping");
                			//Correct Ping Response
                			//Wait for 3 seconds before next Ping
                			try 
                			{
                				synchronized (lock) 
                				{
                					System.out.println("Sleeping");
                					lock.wait(3000);
                				}
                			} 
                			catch (InterruptedException e) 
                			{
                				//Wait was Interupted
                				e.printStackTrace();
                			}
                			System.out.println("Woken Up");
                		}
                	}
                	else
                	{
                		//Client Disconnected
            			//Remove Client from List
            			MainUploadGUI.removeConnectedClient(deviceName);
            			MainUploadGUI.freeRescources(queueIndex, statusIndex);
            			statusField.append("Client Disconnected\n");
            			clientConnected = false;
                	}
        		}
        		while(threadsInfo.peek() == null);
        		
        		if(clientConnected)
        		{
        			//Retrieve Queue Information
        			int teamNumber = threadsInfo.remove();

        			//Send Match Data to Client
        			System.out.println("Sending Transmit Code");
        			sendData("transmit");
        			statusField.append("Sending Transmit Code\n");
        		}
        	}
        	else if(clientMessage.equals("disconnect"))
        	{
        		//Remove Client From List
        		//Close Streams
        		MainUploadGUI.removeConnectedClient(btN);
        		MainUploadGUI.freeRescources(queueIndex, statusIndex);
        		inStream = null;
        		outStream = null;
        		statusField.append("Client Disconnected\n");
        	}
        }
        else
        {
        	//Client Has Disconnected
    		//Remove Client From List
    		//Close Streams
    		MainUploadGUI.removeConnectedClient(btN);
    		MainUploadGUI.freeRescources(queueIndex, statusIndex);
    		inStream = null;
    		outStream = null;
    		statusField.append("Client Disconnected\n");
        }

        if(clientConnected)
        {
        	//Ready to receive Uploaded Match
        	System.out.println("Parsing Scouting Data");
        	String uploadType = "";
        	
        	//Read Upload Type
        	try 
        	{
        		uploadType = bReader.readLine();
        	} 
        	catch (IOException e) 
        	{
        		//Error
        		//Client Has Disconnected
        		clientConnected = false;
        		MainUploadGUI.removeConnectedClient(btN);
        		MainUploadGUI.freeRescources(queueIndex, statusIndex);
        	}

        	String uploadData = "";
        	//Read Upload Data
        	try 
        	{
        		uploadData = bReader.readLine();
        	} 
        	catch (IOException e) 
        	{
        		//Error
        		//Client Has Disconnected
        		clientConnected = false;
        		MainUploadGUI.removeConnectedClient(btN);
        		MainUploadGUI.freeRescources(queueIndex, statusIndex);
        	}
        	
        	//Print Upload Data
        	System.out.println(uploadType);
        	System.out.println(uploadData);
        	
        	//Send Acknowledgment to Server
        	sendData("4");

        	//Display Status in Text Area
        	statusField.append("Recieved Upload Data\n");
        	statusField.append("Upload Complete\n");
        	statusField.append("Inserting Data Into Database\n");

        	//Create String[] from Match Scouting Data
        	String[] splitUploadData = uploadData.split("-");

        	//Check for type of Uploaded Data
        	if(uploadType.equals("Match"))
        	{
        		//User Uploaded A Match
            	//Insert Upload Data into the Database
            	DatabaseHelper.insertMatchData(splitUploadData);
            	statusField.append("Database Insert Complete\n");

            	//Close Connection
            	try 
            	{
            		connection.close();
            	} 
            	catch (IOException e) 
            	{
            		//Error On Connection Close
            		e.printStackTrace();
            	}
        	}
        	else if(uploadType.equals("Pit"))
        	{
        		//User Uploaded Pit Data
            	//Insert Upload Data into the Database
            	DatabaseHelper.insertPitData(splitUploadData);
            	statusField.append("Database Insert Complete\n");

            	//Close Connection
            	try 
            	{
            		connection.close();
            	} 
            	catch (IOException e) 
            	{
            		//Error On Connection Close
            		e.printStackTrace();
            	}
        	}
        	else
        	{
        		//Unknown Upload Type
        	}
        	
        	statusField.append("Upload Complete\n");
        	ScoutingUploadGUI.numFinished++;

        	//Update Scouting Fields
        	ScoutingUploadGUI.updateScoutingClientsFields();
        }
	}
	
	private void sendData(String data)
	{
		//Wire Data with NewLine Character
        pWriter.write(data + "\n");
        
        //Flush Buffer to make sure Data was Sent
        pWriter.flush();
	}
}
