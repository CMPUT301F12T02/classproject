package ca.ualberta.frontend;

import ca.ualberta.backend.User;
import ca.ualberta.frontend.R;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;

/** User interface for app user to register as new user or login as existing user
 * 
 * @author pqin
 *
 */
public class LoginActivity extends Activity implements OnClickListener {
	User user = new User();
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
    }

	public void onClick(View v) {
		EditText nameField = (EditText) findViewById(R.id.userField);
		EditText passField = (EditText) findViewById(R.id.passField);
		String username = nameField.getText().toString();
		String password = passField.getText().toString();
		
		switch(v.getId()){
			case R.id.userRegisterButton:
				user.Register(username, password);
				break;
			case R.id.userLoginButton:
				user.Login(username, password);
				break;
			default:
				break;
		}
	}

}
