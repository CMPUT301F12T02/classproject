package ca.ualberta.cmput301project;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RequestSummary extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestsummary);
        
        Button saveLocal = (Button) findViewById(R.id.savelocal_button);
        //Button shareRequest = (Button) findViewById(R.id.post_button);
        saveLocal.setOnClickListener(this);
        //shareRequest.setOnClickListener(this);
    }

    public void onClick(View v){
    	Intent intent;
    	switch (v.getId()){
			case R.id.savelocal_button:
				intent = new Intent(this, StoredTasks.class);
				break;
			case R.id.post_button:
				intent = new Intent();
				break;
			default:
				return;
    	}
    	
		startActivity(intent);
		finish();
    }
}
