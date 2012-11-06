package ca.ualberta.cmput301project;

import java.sql.*;

public class Authentication {
	private Authentication() {}
	
	public static Connection createConnection() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		Connection connection = null;
		
		Class<?> driverClass = Class.forName("oracle.jdbc.driver.OracleDriver");
		DriverManager.registerDriver((Driver) driverClass.newInstance());
		connection = DriverManager.getConnection("jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS", "caciula", "c301rulez");
        
		return connection;
	}
	
	public static boolean createNewUser(Connection connection, String username, String password) throws SQLException {
		Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select count(*) from android_users where username = '"+username+"'");
        
        if (resultSet.getInt(1) == 1) {
        	return false;
        } else {
        	statement.executeQuery("insert into android_users values('"+username+"', '"+password+"')");
        	return true;
        }
	}
	
	public static boolean logIn(Connection connection, String username, String password) throws SQLException {
		Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select count(*) from android_users where username = '"+username+"' and password = '"+password+"'");
		
        if (resultSet.getInt(1) == 1) {
        	return true;
        } else {
        	return false;
        }
	}
	
	public static void closeConnection(Connection connection) throws SQLException {
		connection.close();
	}
}
