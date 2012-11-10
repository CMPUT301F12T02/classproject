package ca.ualberta.cmput301project;

import android.os.Bundle;
import android.util.Log;
import android.app.Activity;

public class LoginActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Login", "Called super.onCreate()");
        setContentView(R.layout.activity_login);
        Log.d("Login", "setContentView Layout");
    }

}
