package ca.ualberta.cmput301project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
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
				intent = new Intent(this, NewRequestActivity.class);
				break;
			case R.id.viewstored_button:
				intent = new Intent(this, StoredTasksActivity.class);
				break;
			default:
				intent = new Intent();
				break;
    	}
		startActivity(intent);
    }
}
