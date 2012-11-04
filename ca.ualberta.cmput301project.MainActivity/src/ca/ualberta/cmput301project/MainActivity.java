package ca.ualberta.cmput301project;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
    
    static ArrayList<Task> thelist = new ArrayList<Task>();
    static ArrayList<Task> locallist = new ArrayList<Task>();
    static ArrayList<Task> communitylist = new ArrayList<Task>();
    static int task_count = 0;
    static int local_count = 0;
    static int community_count = 0;
    static int list_index = 0;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button viewStoredId = (Button) findViewById(R.id.viewstored_button);
        Button newRequestId = (Button) findViewById(R.id.newrequest_button);
        viewStoredId.setOnClickListener(this);
        newRequestId.setOnClickListener(this);
    }

    public void onClick(View v){
    	Intent intent;
    	switch (v.getId()){
			case R.id.newrequest_button:
				intent = new Intent(this, NewRequest.class);
				break;
			case R.id.viewstored_button:
				intent = new Intent(this, StoredTasks.class);
				break;
			default:
				intent = new Intent();
				break;
    	}
		startActivity(intent);
    }
}
