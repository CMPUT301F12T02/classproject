package ca.ualberta.backend;

import ca.ualberta.backend.Authentication;
import java.sql.*;

/** Checks username and password entered in LoginActivity,
 *  indicating logged in user by username.
 *  Opens/closes database connection to check if user exists or not.
 * 
 * @author pqin
 *
 */
public class User {
	private String username;
	private String password;
	private Connection dbconnection;
	private boolean LoggedIn;
	
	public User(){
		username = "John Doe";
		password = "12345";
		dbconnection = null;
		LoggedIn = false;
	}
	private void setUsername(String newname){
		username = newname;
	}
	private void setPassword(String newpassword){
		password = newpassword;
	}
	private void setStatus(boolean login_status){
		LoggedIn = login_status;
	}
	public String getUsername(){
		return username;
	}
	public String getPassword(){
		return password;
	}
	public boolean getStatus(){
		return LoggedIn;
	}
	public String Register(String username, String password){
		String status = "";
		try {
			if (Authentication.createNewUser(dbconnection, username, password)){
				status = "Created new user \'" + username + "\'.";
			} else {
				status = "User \'" + username + "\' already exists.";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = "SQL Exception occurred";
		}
		return status;
	}
	public String Login(String username, String password){
		String status = "";
		try {
			if (Authentication.logIn(dbconnection, username, password)){
				if (getStatus() == false){
					status = "\'" + username + "\' login successful";
					setUsername(username);
					setPassword(password);
					setStatus(true);
				} else {
					status = "Already logged in.";
				}
			} else {
				status = "\'" + username + "\' login failed.";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = "SQL Exception occurred";
		}
		return status;
	}
	public void Logout(){
		if (getStatus()){
			setUsername("");
			setPassword("");
			setStatus(false);
		}
	}
}
