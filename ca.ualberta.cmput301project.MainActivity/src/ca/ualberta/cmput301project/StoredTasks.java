package ca.ualberta.cmput301project;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.content.Intent;

public class StoredTasks extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storedtasks);
    }
    
	//http://developer.android.com/guide/topics/ui/layout/listview.html
	//http://www.mkyong.com/android/android-listview-example/
    
    public void onClick(View v){
    	//Needs to get request information,
    	// to populate task description box
    	// to gray out appropriate get media button
    	Intent intent = new Intent(this, FulfillTask.class);
    	startActivity(intent);
    }
}
