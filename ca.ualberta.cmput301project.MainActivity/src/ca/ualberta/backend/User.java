package ca.ualberta.backend;

<<<<<<< HEAD
import ca.ualberta.backend.SQLiteHandler;
import java.sql.*;
=======
import android.util.Log;
>>>>>>> 22314e0068d7bd1447ce6b69d96ce9944ec7c647

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
	private boolean LoggedIn;

	public User(){
		username = "john_doe@example.com";
		password = "asdf";
		LoggedIn = false;
		Log.d("User", "User() constructor");
	}
	public User(String new_name, String new_password){
		username = new_name;
		password = new_password;
		LoggedIn = false;
		Log.d("User", "User(String, String) constructor");
	}
	
	public void setUsername(String newname){
		username = newname;
	}
	public void setPassword(String newpassword){
		password = newpassword;
	}
	public void setLoggedIn(boolean login_status){
		LoggedIn = login_status;
	}
	
	public String getUsername(){
		return username;
	}
	public String getPassword(){
		return password;
	}
	public boolean getLoggedIn(){
		return LoggedIn;
	}
}
