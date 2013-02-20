package com.frc2410.scoutingserver;

import java.io.File;
import java.sql.*;

public class DatabaseHelper 
{
	static String sDriverName = "org.sqlite.JDBC";
	static String dbURL = "jdbc:sqlite:c:\\FRCScoutingData\\scoutingData.db";
	static Connection conn = null;

	public DatabaseHelper()
	{
		//Create Folder is it does not exist
		File rootPath = new File("C:\\FRCScoutingData");
		if(!rootPath.exists())
		{
			rootPath.mkdirs();
		}
		
		//Connect to Database
		createConnection();

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
		      
		      //Create Table Statement
		      int count = sta.executeUpdate(
		        "CREATE TABLE if not exists Scouting_Data (MATCH_NUMBER INT, TEAM_NUMBER INT, ALLIANCE_COLOR VARCHAR(5), TEAM_SCORE INT, ALLIANCE_SCORE INT, TEAM_PENALTIES INT, ALLIANCE_PENALTIES INT, " +
		        "SHOOTING_STATUS INT, SHOOTER_ONE_POINT INT, SHOOTER_TWO_POINT INT, SHOOTER_THREE_POINT INT, SHOOTER_FIVE_POINT INT, SHOOTER_TEAM_PENALTIES INT, SHOOTER_ALLIANCE_PENALTIES INT, SHOOTER_ONE_POINT_SHOTS_MADE INT, SHOOTER_TWO_POINT_SHOTS_MADE INT, SHOOTER_THREE_POINT_SHOTS_MADE INT, SHOOTER_FIVE_POINT_SHOTS_MADE INT, SHOOTER_ONE_POINT_SHOTS_TAKEN INT, SHOOTER_TWO_POINT_SHOTS_TAKEN INT, SHOOTER_THREE_POINT_SHOTS_TAKEN INT, SHOOTER_FIVE_POINT_SHOTS_TAKEN INT, " +
		        "CLIMBING_STATUS INT, CLIMBING_LEVEL_ONE INT, CLIMBING_LEVEL_TWO INT, CLIMBING_LEVEL_THREE INT, CLIMBING_SUCESSFUL_CLIMBS INT, CLIMBING_TOTAL_ATTEMPTS INT, CLIMBING_TEAM_PENALTIES INT, CLIMBING_ALLIANCE_PENALTIES INT," +
		        "AUTONOMOUS_STATUS INT, AUTNOMOUS_TEAM_PENALTIES INT, AUTONOMOUS_ALLIANCE_PENALTIES INT, AUTONOMOUS_ONE_POINT INT, AUTONOMOUS_TWO_POINT INT, AUTONOMOUS_THREE_POINT INT, AUTONOMOUS_FIVE_POINT INT, AUTONOMOUS_ONE_POINT_SHOTS_MADE INT, AUTONOMOUS_TWO_POINT_SHOTS_MADE INT, AUTONOMOUS_THREE_POINT_SHOTS_MADE INT, AUTONOMOUS_FIVE_POINT_SHOTS_MADE INT, AUTONOMOUS_ONE_POINT_SHOTS_TAKEN INT, AUTONOMOUS_TWO_POINT_SHOTS_TAKEN INT, AUTONOMOUS_THREE_POINT_SHOTS_TAKEN INT, AUTONOMOUS_FIVE_POINT_SHOTS_TAKEN INT," +
		        "DEFENSE_STATUS INT, DEFENSE_RANK INT, CLIMB_ASSIST_STATUS INT, HUMAN_PLAYER_STATUS INT, HUMAN_PLAYER_PENALTIES INT, HUMAN_PLAYER_SHOTS_MADE INT, HUMAN_PLAYER_SHOTS_TAKEN INT, " +
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
            //Insert table Statement
            stmt.execute("INSERT INTO Scouting_Data VALUES (" + Integer.parseInt(scoutData[0]) + "," 
            + scoutData[1] + ",'" 
            		+ scoutData[2] + "'," 
            + scoutData[3] + "," 
            		+ scoutData[4] + "," 
            + scoutData[5] + "," 
            + scoutData[6] + "," 
            		+ booleanToInt(Boolean.parseBoolean(scoutData[7])) + "," + 
            				booleanToInt(Boolean.parseBoolean(scoutData[8])) + "," + 
            						booleanToInt(Boolean.parseBoolean(scoutData[9])) + "," + 
            								booleanToInt(Boolean.parseBoolean(scoutData[10])) + "," + 
            										booleanToInt(Boolean.parseBoolean(scoutData[11])) + "," + 
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
            		booleanToInt(Boolean.parseBoolean(scoutData[22])) + "," + 
            				booleanToInt(Boolean.parseBoolean(scoutData[23])) + "," + 
            						booleanToInt(Boolean.parseBoolean(scoutData[24])) + "," + 
            								booleanToInt(Boolean.parseBoolean(scoutData[25])) + "," + 
            scoutData[26] + "," + 
            		scoutData[27] + "," + 
            scoutData[28] + "," + 
            		scoutData[29] + "," + 
            		booleanToInt(Boolean.parseBoolean(scoutData[30])) + "," + 
            		scoutData[31] + "," + 
            scoutData[32] + "," + 
            booleanToInt(Boolean.parseBoolean(scoutData[33])) + "," + 
            		booleanToInt(Boolean.parseBoolean(scoutData[34])) + "," + 
            				booleanToInt(Boolean.parseBoolean(scoutData[35])) + "," + 
            						booleanToInt(Boolean.parseBoolean(scoutData[36])) + "," + 
            		scoutData[37] + "," + 
            scoutData[38] + "," + 
            		scoutData[39] + "," + 
            scoutData[40] + "," + 
            		scoutData[41] + "," + 
            scoutData[42] + "," + 
            		scoutData[43] + "," + 
            scoutData[44] + "," + 
            booleanToInt(Boolean.parseBoolean(scoutData[45])) + "," + 
            scoutData[46] + "," + 
            booleanToInt(Boolean.parseBoolean(scoutData[47])) + "," + 
            		booleanToInt(Boolean.parseBoolean(scoutData[48])) + "," + 
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
    
    private static int booleanToInt(boolean value)
    {
    	if(value)
    	{
    		return 1;
    	}
    	else
    	{
    		return 0;
    	}
    }
}
