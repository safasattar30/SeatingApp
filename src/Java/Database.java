package Java;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

import com.mysql.jdbc.CommunicationsException;

/**
 * Provides connection to the database and contains functions to send queries to the database.
 */

/**
 * HOW TO USE:
 * Database db = Database.getDatabase()
 * This will retrieve the instance of the Database that is currently running.
 * Use db.query() for queries that return results (ex: SELECT * FROM...)
 * Use db.updateQuery() for queries that do not return results (ex: INSERT INTO...)
 * 
 * @author WdNnD
 * Singleton class
 */
public class Database {
	
	private static final Logger logger = Logger.getLogger(Database.class.getName());   

	private static Database instance = null;
	
	private final String HOST = "jdbc:mysql://mysql2.cs.stonybrook.edu:3306/ssattar";
	private final String USERNAME = "ssattar";
	private final String PASSWORD = "108862829";
	
	private Connection conn;

	private Database()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			logger.warning("Could not find MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}

		logger.info("MySQL JDBC Driver Found");
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(HOST, USERNAME, PASSWORD);

		}
		catch (CommunicationsException e) {
			logger.warning("Connectoin Failed! Could not establish connection with server. Please make sure your internet connection is valid and that the server is active, as well as the output console for any further unformation.");
			e.printStackTrace();
			return;
		}
		catch (SQLException e) {
			logger.warning("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
		try {
			if (conn != null) {
				logger.info("Database connection established");
				this.conn = conn;
			} else {
				logger.info("Failed to make connection");
			}

		} catch(Exception e){
			e.printStackTrace();
		}
		
		Statement statement = null;
		try {
			statement = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			logger.warning("Unable to create SQL statement.");
			e1.printStackTrace();
		}
		
		try {
			boolean hasResultSet = statement.execute("USE ssattar");
		} catch (SQLException e) {
			logger.warning("Unable to execute statement: \"USE ssattar\"");
			e.printStackTrace();
		}
	}

	/*
	 * Returns an instance of the DB anywhere in the project
	 */
	public static Database getDatabase() {
		if (instance == null) {
			instance = new Database();
		}
		return instance;
	}
	
	public static void createDatabase() {
		getDatabase();
	}
	
	/*
	 * function to send queries and receive the list returned from the DB
	 */
	public List<Map<String,Object>> query(String queryString) {
		logger.fine("Preparing to execute SQL query to retrieve data.");
		logger.info("QUERY STRING: " + queryString);
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		ResultSet rs = null;
		try {
			Statement statement = conn.createStatement();
			rs = statement.executeQuery(queryString);
			ResultSetMetaData rsmd = rs.getMetaData();
			int numColumns = rsmd.getColumnCount();
			while (rs.next()) {
				Map<String,Object> map = new HashMap<String,Object>();
				for (int i = 1; i <= numColumns; i++) {
					map.put(rsmd.getColumnName(i), rs.getObject(i));
				}
				results.add(map);
			}
			logger.info("Query successfully executed.");

		} catch (SQLException e) {
			logger.warning("Error executing query. Please check query and try again.");
			e.printStackTrace();
		}
		return results;
	}
	
	public ResultSet queryRS(String queryString) {
		logger.fine("Preparing to execute SQL query to retrieve data.");
		logger.info("QUERY STRING: " + queryString);
		ResultSet rs = null;
		try {
			Statement statement = conn.createStatement();
			rs = statement.executeQuery(queryString);
			logger.info("Query successfully executed.");

		} catch (SQLException e) {
			logger.warning("Error executing query. Please check query and try again.");
			e.printStackTrace();
		}
		return rs;
	}
	
	/*
	 * Sends a sql query to update the database
	 */
	public int updateQuery(String queryString) {
		logger.fine("Preparing to execute SQL query to update database.");
		logger.info("QUERY STRING: " + queryString);
		int returnVal = 0;
		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(queryString);
			returnVal = statement.executeUpdate();
			logger.info("Query successfully executed.");
		} catch (SQLException e) {
			logger.warning("fine executing query. Please check query and try again.");
			e.printStackTrace();
		}
		return returnVal;
	}

}

