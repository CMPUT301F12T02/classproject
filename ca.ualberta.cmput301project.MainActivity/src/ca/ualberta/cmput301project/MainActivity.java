package ca.ualberta.cmput301project;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button loginId = (Button) findViewById(R.id.LoginFromMain);
        Button viewStoredId = (Button) findViewById(R.id.viewstored_button);
        Button newRequestId = (Button) findViewById(R.id.newrequest_button);
        loginId.setOnClickListener(this);
        viewStoredId.setOnClickListener(this);
        newRequestId.setOnClickListener(this);
    }

    public void onClick(View v){
    	Intent intent;
    	switch (v.getId()){
			case R.id.newrequest_button:
				intent = new Intent(MainActivity.this, NewRequestActivity.class);
				Log.d("Main", "New intent: NewRequestActivity");
				break;
			case R.id.viewstored_button:
				intent = new Intent(MainActivity.this, StoredTasksActivity.class);
				Log.d("Main", "New intent: StoredTasksActivity");
				break;
			case R.id.LoginFromMain:
				/*Note from Peter: startActivity() throws exception when trying to
				 * start LoginActivity.
				 * LogCat:
				 * Tag		Text
				 * Main 	New intent: LoginActivity
				 * Main 	Start Activity
				 * System.err	android.content.ActivityNotFoundException:
				 * 				No Activity found to handle Intent...
				 * */
				intent = new Intent(MainActivity.this, LoginActivity.class);
				Log.d("Main", "New intent: LoginActivity");
			default:
				intent = new Intent();
				break;
    	}
    	Log.d("Main", "Start Activity");
		try {
			MainActivity.this.startActivity(intent);
		} catch (ActivityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
