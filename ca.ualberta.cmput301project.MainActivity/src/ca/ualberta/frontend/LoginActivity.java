package ca.ualberta.frontend;

import ca.ualberta.frontend.R;
import ca.ualberta.backend.SQLiteHandler;
import ca.ualberta.backend.User;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.database.SQLException;

/** User interface for app user to register as new user or login as existing user.
 * 
 * @author pqin
 *
 */
public class LoginActivity extends Activity implements OnClickListener {
	private SQLiteHandler db = new SQLiteHandler(this);
	private User user = new User();
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Login", "Called super.onCreate()");
        
        setContentView(R.layout.activity_login);
        Log.d("Login", "setContentView Layout");
        
        Button loginUserButton = (Button) findViewById(R.id.userLoginButton);
        Button registerUserButton = (Button) findViewById(R.id.userRegisterButton);
        loginUserButton.setOnClickListener(this);
        registerUserButton.setOnClickListener(this);
        
        try {
			db.open();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("Login", "db.open() threw SQLException");
		}
    }
    @Override
    public void onStop(){
    	db.close();
    }

	public void onClick(View v) {
		EditText nameField = (EditText) findViewById(R.id.userField);
		EditText passField = (EditText) findViewById(R.id.passField);
		TextView statusField = (TextView) findViewById(R.id.loginStatus);
		String username = nameField.getText().toString();
		String password = passField.getText().toString();
		user.setUsername(username);
		user.setPassword(password);

		switch(v.getId()){
			case R.id.userRegisterButton:
				if (db.AddRecord(user) == 0){
					statusField.setText("New user registered.");
				} else {
					statusField.setText("Failed to register new user.");
				}
				break;
			case R.id.userLoginButton:
				//TODO: click crashes app
				if (db.FindRecord(user) == 0){
					statusField.setText("User logged in.");
					user.setLoggedIn(true);
				} else {
					statusField.setText("Login failure.");
					user.setLoggedIn(false);
				}
				break;
			default:
				break;
		}
	}

}
