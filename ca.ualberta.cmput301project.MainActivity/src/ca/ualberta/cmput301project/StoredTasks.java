package ca.ualberta.cmput301project;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;

public class StoredTasks extends Activity implements OnClickListener {

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
        Intent intent;
        switch (v.getId()){
	        case R.id.local_button:
	        	intent = new Intent(this, ViewLocalTask.class);
	        	break;
	        case R.id.com_button:
	        	/* Peter:
	        	 * Clicking this button crashes app
	        	 * */
	        	intent = new Intent(this, ViewExternalTask.class);
	        	break;
	        default:
	        	intent = new Intent();
	        	break;
        }
            startActivity(intent);
    }
}
