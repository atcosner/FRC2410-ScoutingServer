package com.frc2410.scoutingserver;

import java.sql.*;

public class DatabaseHelper 
{
	static String sDriverName = "org.sqlite.JDBC";
	static String dbURL = "jdbc:sqlite:scoutingData.db";//;create=true";
	static Connection conn = null;
	
	public DatabaseHelper()
	{
		//Connect to Database
		createConnection();
		
		//Check for Table Existence
		
		//Create Table for Scouting Data
		createTable();
	}
	
    private static void createConnection()
    {
        try
        {
            Class.forName("org.sqlite.JDBC").newInstance();
            //Get a connection
            conn = DriverManager.getConnection(dbURL); 
        }
        catch (Exception except)
        {
            except.printStackTrace();
        }
    }
    
    private static void createTable()
    {
        Statement sta;
		try 
		{
		      sta = conn.createStatement(); 
		      int count = sta.executeUpdate(
		        "CREATE TABLE Scouting_Data (MATCH_NUMBER INT, TEAM_NUMBER INT, ALLIANCE_COLOR VARCHAR(5), TEAM_SCORE INT, ALLIANCE_SCORE INT, TEAM_PENALTIES INT, ALLIANCE_PENALTIES INT, " +
		        "SHOOTING_STATUS BOOLEAN, SHOOTER_ONE_POINT BOOLEAN, SHOOTER_TWO_POINT BOOLEAN, SHOOTER_THREE_POINT BOOLEAN, SHOOTER_FIVE_POINT BOOLEAN, SHOOTER_TEAM_PENALTIES INT, SHOOTER_ALLIANCE_PENALTIES INT, SHOOTER_ONE_POINT_SHOTS_MADE INT, SHOOTER_TWO_POINT_SHOTS_MADE INT, SHOOTER_THREE_POINT_SHOTS_MADE INT, SHOOTER_FIVE_POINT_SHOTS_MADE INT, SHOOTER_ONE_POINT_SHOTS_TAKEN INT, SHOOTER_TWO_POINT_SHOTS_TAKEN INT, SHOOTER_THREE_POINT_SHOTS_TAKEN INT, SHOOTER_FIVE_POINT_SHOTS_TAKEN INT, " +
		        "CLIMBING_STATUS BOOLEAN, CLIMBING_LEVEL_ONE BOOLEAN, CLIMBING_LEVEL_TWO BOOLEAN, CLIMBING_LEVEL_THREE BOOLEAN, CLIMBING_SUCESSFUL_CLIMBS INT, CLIMBING_TOTAL_ATTEMPTS INT, CLIMBING_TEAM_PENALTIES INT, CLIMBING_ALLIANCE_PENALTIES INT," +
		        "AUTONOMOUS_STATUS BOOLEAN, AUTNOMOUS_TEAM_PENALTIES INT, AUTONOMOUS_ALLIANCE_PENALTIES INT, AUTONOMOUS_ONE_POINT BOOLEAN, AUTONOMOUS_TWO_POINT BOOLEAN, AUTONOMOUS_THREE_POINT BOOLEAN, AUTONOMOUS_FIVE_POINT BOOLEAN, AUTONOMOUS_ONE_POINT_SHOTS_MADE INT, AUTONOMOUS_TWO_POINT_SHOTS_MADE INT, AUTONOMOUS_THREE_POINT_SHOTS_MADE INT, AUTONOMOUS_FIVE_POINT_SHOTS_MADE INT, AUTONOMOUS_ONE_POINT_SHOTS_TAKEN INT, AUTONOMOUS_TWO_POINT_SHOTS_TAKEN INT, AUTONOMOUS_THREE_POINT_SHOTS_TAKEN INT, AUTONOMOUS_FIVE_POINT_SHOTS_TAKEN INT," +
		        "DEFENSE_STATUS BOOLEAN, DEFENSE_RANK INT, CLIMB_ASSIST_STATUS BOOLEAN, HUMAN_PLAYER_STATUS BOOLEAN, HUMAN_PLAYER_PENALTIES INT, HUMAN_PLAYER_SHOTS_MADE INT, HUMAN_PLAYER_SHOTS_TAKEN INT, " +
		        "MOVEMENT_DESCRIPTION VARCHAR(500), ADDITIONAL_COMMENTS VARCHAR(500))");
		      System.out.println("Table created");
		      sta.close();  
		} 
		catch (SQLException e) 
		{
			//Problem with Table Creation
			//Most Likely Table was Already Created
			System.out.println("Error During Table Creation");
		} 
    }
    
    public static synchronized void insertScoutingData(String[] scoutData)
    {
    	Statement stmt = null;
        try
        {
            stmt = conn.createStatement();
            stmt.execute("INSERT into Scouting_Data values (" + Integer.parseInt(scoutData[0]) + "," 
            + scoutData[1] + ",'" 
            		+ scoutData[2] + "'," 
            + scoutData[3] + "," 
            		+ scoutData[4] 
            		+ "," + scoutData[5] + "," 
            + scoutData[6] + "," 
            		+ scoutData[7] + "," + 
            scoutData[8] + "," + 
            		scoutData[9] + "," + 
            scoutData[10] + "," + 
            		scoutData[11] + "," + 
            scoutData[12] + "," + 
            		scoutData[13] + "," + 
            scoutData[14] + "," + 
            		scoutData[15] + "," + 
            scoutData[16] + "," + 
            		scoutData[17] + "," + 
            scoutData[18] + "," + 
            		scoutData[19] + "," + 
            scoutData[20] + "," + 
            		scoutData[21] + "," + 
            scoutData[22] + "," + 
            		scoutData[23] + "," + 
            scoutData[24] + "," + 
            		scoutData[25] + "," + 
            scoutData[26] + "," + 
            		scoutData[27] + "," + 
            scoutData[28] + "," + 
            		scoutData[29] + "," + 
            scoutData[30] + "," + 
            		scoutData[31] + "," + 
            scoutData[32] + "," + 
            		scoutData[33] + "," + 
            scoutData[34] + "," + 
            		scoutData[35] + "," + 
            scoutData[36] + "," + 
            		scoutData[37] + "," + 
            scoutData[38] + "," + 
            		scoutData[39] + "," + 
            scoutData[40] + "," + 
            		scoutData[41] + "," + 
            scoutData[42] + "," + 
            		scoutData[43] + "," + 
            scoutData[44] + "," + 
            		scoutData[45] + "," + 
            scoutData[46] + "," + 
            		scoutData[47] + "," + 
            scoutData[48] + "," + 
            		scoutData[49] + "," + 
            scoutData[50] + "," + 
            		scoutData[51] + ",'" + 
            scoutData[52] + "','" + 
            		scoutData[53] + "')");
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
        	//Error When Inserting Values
            sqlExcept.printStackTrace();
        }
    }
}
