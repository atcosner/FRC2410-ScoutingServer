package com.frc2410.scoutingserver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MainThread implements ActionListener
{
	private static WelcomeGUI wG;
	public static Thread connThreadMatch;
	public static Thread connThreadSMatch;
	public static Thread connThreadSPit;
	static LinkedList<Integer>[] queues = new LinkedList[6];
	static Thread[] connectionThreads = new Thread[6];
	static JTextArea[] statusFields = new JTextArea[6];
	static boolean[] usedQueues = new boolean[6];
	static boolean[] usedStatusArea = new boolean[6];
	static boolean[] usedConnectionThread = new boolean[6];
	static LinkedList<Integer>[] uploadQueues = new LinkedList[6];
	static Thread[] uploadConnectionThreads = new Thread[6];
	static JTextArea[] uploadStatusFields = new JTextArea[6];
	static boolean[] usedUploadQueues = new boolean[6];
	static boolean[] usedUploadStatusArea = new boolean[6];
	static boolean[] usedUploadConnectionThread = new boolean[6];
	public static DatabaseHelper dbHelper;

	public static void main(String[] args) 
	{
		//Set Used Queues values to False
		for(int k = 0;k<=5;k++)
		{
			usedQueues[k] = false;
			usedStatusArea[k] = false;
			usedConnectionThread[k] = false;
			usedUploadQueues[k] = false;
			usedUploadStatusArea[k] = false;
			usedUploadConnectionThread[k] = false;
		}
		
		//Populate JTextAres and Queues Arrays
		for(int k = 0;k<=5;k++)
		{
			JTextArea dev = new JTextArea();
			statusFields[k] = dev;
			JTextArea dev1 = new JTextArea();
			uploadStatusFields[k] = dev1;
			LinkedList<Integer> queue = new LinkedList<Integer>();
			queues[k] = queue;
			LinkedList<Integer> queue1 = new LinkedList<Integer>();
			uploadQueues[k] = queue1;
		}
    	
		//Build and create Welcome Screen
        javax.swing.SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
        		JPanel p = new JPanel(null);
        		wG = new WelcomeGUI(p);
        		wG.setVisible(true);
            }
        });
        
		//Create DB Helper Object
		dbHelper = new DatabaseHelper();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals("Start the Match Scouting Server"))
		{
			//Check for Valid Data
			if(wG.validData())
			{
				//Start the MainGUI
				wG.pullThePlug();
				JPanel p = new JPanel(null);
				MainGUI mG = new MainGUI(p);
				mG.setVisible(true);
			
				//Start the Bluetooth Connection Thread
				connThreadMatch = new Thread(new ConnectionListener(mG));
				connThreadMatch.start();
			}
			else
			{
				//Show Error Screen
				JFrame frame = new JFrame();
				JOptionPane.showMessageDialog(frame,"Please Enter a Valid Number as a Team Number","Team Number Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(e.getActionCommand().equals("Start the Saved Match Upload Server"))
		{
			//Check for Valid Data
			if(wG.validData())
			{
				//Start Match Upload GUI
				wG.pullThePlug();
				JPanel p = new JPanel(null);
				MainUploadGUI mG = new MainUploadGUI(p);
				mG.setVisible(true);
				
				//Start Match Upload Connection Thread
				connThreadSMatch = new Thread(new ConnectionListenerSavedMatch(mG));
				connThreadSMatch.start();
			}
			else
			{
				//Show Error Screen
				JFrame frame = new JFrame();
				JOptionPane.showMessageDialog(frame,"Please Enter a Valid Number as a Team Number","Team Number Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(e.getActionCommand().equals("Start the Saved Pit Upload Server"))
		{
			//Check for Valid Data
			if(wG.validData())
			{
				//Start Match Upload GUI
				wG.pullThePlug();
				JPanel p = new JPanel(null);
				MainPitUploadGUI mG = new MainPitUploadGUI(p);
				mG.setVisible(true);
				
				//Start Match Upload Connection Thread
				connThreadSPit = new Thread(new ConnectionListenerSavedPit(mG));
				connThreadSPit.start();
			}
			else
			{
				//Show Error Screen
				JFrame frame = new JFrame();
				JOptionPane.showMessageDialog(frame,"Please Enter a Valid Number as a Team Number","Team Number Error",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void notifyThread(int threadNumber, int mN, int tN)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(threadNumber);
		sb.append(",");
		sb.append(mN);
		sb.append(",");
		sb.append(tN);
	}
}
