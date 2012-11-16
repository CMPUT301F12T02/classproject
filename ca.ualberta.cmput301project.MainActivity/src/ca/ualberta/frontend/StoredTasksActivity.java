package ca.ualberta.frontend;

import ca.ualberta.frontend.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;

public class StoredTasksActivity extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storedtasks);
        
        Button viewLocaldId = (Button) findViewById(R.id.local_button);
        Button viewComId = (Button) findViewById(R.id.com_button);
        viewLocaldId.setOnClickListener(this);
        viewComId.setOnClickListener(this);
    }
        
    public void onClick(View v){
    	//Needs to get request information,
    	// to populate task description box
    	// to gray out appropriate get media button
    	Intent intent;
    	
        switch (v.getId()){
	        case R.id.local_button:
	        	intent = new Intent(this, ViewLocalTaskActivity.class);
	        	break;
	        case R.id.com_button:
	        	intent = new Intent(this, ViewExternalTaskActivity.class);
	        	break;
	        default:
	        	intent = new Intent();
	        	break;
        }
            startActivity(intent);
    }
}
