package ca.ualberta.frontend;

import ca.ualberta.frontend.R;
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
        
        Button viewStoredId = (Button) findViewById(R.id.viewstored_button);
        Button newRequestId = (Button) findViewById(R.id.newrequest_button);
<<<<<<< HEAD
=======
        
>>>>>>> d1bbd80dcd38d2bdb507e37a2eb60b3f047bc805
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
