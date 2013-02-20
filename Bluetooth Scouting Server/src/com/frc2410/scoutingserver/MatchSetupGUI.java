package com.frc2410.scoutingserver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MatchSetupGUI extends JFrame implements ActionListener
{
	JLabel directions;
	JLabel matchN;
	static JTextField matchNumber;
	JLabel teams;
	JLabel redL;
	static JTextField red1;
	static JTextField red2;
	static JTextField red3;
	JLabel blueL;
	static JTextField blue1;
	static JTextField blue2;
	static JTextField blue3;
	JLabel one;
	JLabel two;
	JLabel three;
	JLabel oneB;
	JLabel twoB;
	JLabel threeB;
	JButton submit;
	
	public MatchSetupGUI(JPanel p1)
	{
		//Call to Super Class
		super("FRC 2410 Bluetooth Server");
		
		//Set Background Color
		p1.setBackground(Color.GRAY);
		
		//Create Components
		directions = new JLabel("Please Enter the Required Match Info Below!");
		matchN = new JLabel("Match Number");
		matchNumber = new JTextField();
		teams = new JLabel("Teams in the Match");
		redL = new JLabel("Red Alliance");
		red1 = new JTextField();
		red2 = new JTextField();
		red3 = new JTextField();
		blueL = new JLabel("Blue Alliance");
		blue1 = new JTextField();
		blue2 = new JTextField();
		blue3 = new JTextField();
		one =  new JLabel("1:");
		two =  new JLabel("2:");
		three =  new JLabel("3:");
		oneB =  new JLabel("1:");
		twoB =  new JLabel("2:");
		threeB =  new JLabel("3:");
		submit = new JButton("Submit Data");
		
		//Add Components to the Panel
		p1.add(directions);
		p1.add(matchN);
		p1.add(matchNumber);
		p1.add(teams);
		p1.add(redL);
		p1.add(red1);
		p1.add(red2);
		p1.add(red3);
		p1.add(blueL);
		p1.add(blue1);
		p1.add(blue2);
		p1.add(blue3);
		p1.add(one);
		p1.add(two);
		p1.add(three);
		p1.add(oneB);
		p1.add(twoB);
		p1.add(threeB);
		p1.add(submit);
		
	    //Setup Insets and Positioning
	    Insets insets = p1.getInsets();
	    Dimension size = directions.getPreferredSize();
	    directions.setBounds(10 + insets.left, 10 + insets.top, size.width, size.height);
	    
	    size = matchN.getPreferredSize();
	    matchN.setBounds(10 + insets.left, 30 + insets.top, size.width, size.height);
	    
	    size = matchNumber.getPreferredSize();
	    matchNumber.setBounds(10 + insets.left, 50 + insets.top, 100, size.height);
	    
	    size = teams.getPreferredSize();
	    teams.setBounds(10 + insets.left, 90 + insets.top, size.width, size.height);
	    
	    size = redL.getPreferredSize();
	    redL.setBounds(10 + insets.left, 110 + insets.top, size.width, size.height);
	    
	    size = one.getPreferredSize();
	    one.setBounds(10 + insets.left, 130 + insets.top, size.width, size.height);
	    
	    size = red1.getPreferredSize();
	    red1.setBounds(30 + insets.left, 130 + insets.top, 100, size.height);
	    
	    size = two.getPreferredSize();
	    two.setBounds(10 + insets.left, 150 + insets.top, size.width, size.height);
	    
	    size = red2.getPreferredSize();
	    red2.setBounds(30 + insets.left, 150 + insets.top, 100, size.height);
	    
	    size = three.getPreferredSize();
	    three.setBounds(10 + insets.left, 170 + insets.top, size.width, size.height);
	    
	    size = red3.getPreferredSize();
	    red3.setBounds(30 + insets.left, 170 + insets.top, 100, size.height);
	    
	    size = blueL.getPreferredSize();
	    blueL.setBounds(170 + insets.left, 110 + insets.top, size.width, size.height);
	    
	    size = oneB.getPreferredSize();
	    oneB.setBounds(170 + insets.left, 130 + insets.top, size.width, size.height);
	    
	    size = blue1.getPreferredSize();
	    blue1.setBounds(190 + insets.left, 130 + insets.top, 100, size.height);
	    
	    size = twoB.getPreferredSize();
	    twoB.setBounds(170 + insets.left, 150 + insets.top, size.width, size.height);
	    
	    size = blue2.getPreferredSize();
	    blue2.setBounds(190 + insets.left, 150 + insets.top, 100, size.height);
	    
	    size = threeB.getPreferredSize();
	    threeB.setBounds(170 + insets.left, 170 + insets.top, size.width, size.height);
	    
	    size = blue3.getPreferredSize();
	    blue3.setBounds(190 + insets.left, 170 + insets.top, 100, size.height);
	    
	    submit.setBounds(10 + insets.left, 230 + insets.top, 300, 40);
	    
	    submit.addActionListener(this);
	    
		//Add Panel and Set Size
		add(p1);
		setSize(350,350);
	}

	public void addMatchData(int matchN,int redTeam1,int redTeam2,int redTeam3,int blueTeam1,int blueTeam2,int blueTeam3)
	{
		matchNumber.setText(String.valueOf(matchN));
		red1.setText(String.valueOf(redTeam1));
		red2.setText(String.valueOf(redTeam2));
		red3.setText(String.valueOf(redTeam3));
		blue1.setText(String.valueOf(blueTeam1));
		blue2.setText(String.valueOf(blueTeam2));
		blue3.setText(String.valueOf(blueTeam3));
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		int matchN  = 0;
		int redTeam1  = 0;
		int redTeam2  = 0;
		int redTeam3  = 0;
		int blueTeam1  = 0;
		int blueTeam2  = 0;
		int blueTeam3  = 0;
		try
		{
			matchN = Integer.parseInt(matchNumber.getText());
			redTeam1 = Integer.parseInt(red1.getText());
			redTeam2 = Integer.parseInt(red2.getText());
			redTeam3 = Integer.parseInt(red3.getText());
			blueTeam1 = Integer.parseInt(blue1.getText());
			blueTeam2 = Integer.parseInt(blue2.getText());
			blueTeam3 = Integer.parseInt(blue3.getText());
			MainGUI.setMatchData(matchN, redTeam1, redTeam2, redTeam3, blueTeam1, blueTeam2, blueTeam3);
			closeWindow();
		}
		catch (NumberFormatException e1)
		{
			//Number Parse Error
			//Display Error Dialog
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame,"Please Enter Valid numbers for Match Data","Match Data Error",JOptionPane.ERROR_MESSAGE);
		}
		System.out.println("Done");
	}
	public void closeWindow()
	{
		dispose();
	}
}
