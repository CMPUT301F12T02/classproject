package ca.ualberta.cmput301project;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;
//import android.content.Intent;
import android.content.Intent;

public class StoredTasks extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storedtasks);
    }
    public void onClick(View v){
    	Intent intent = new Intent(this, FulfillTask.class);
    	startActivity(intent);
    }
}
