package ca.ualberta.cmput301project;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;


public class NewRequest extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newrequest);
        
        Button createRequest = (Button) findViewById(R.id.createrequest_button);
        createRequest.setOnClickListener(this);
    }

    public void onClick(View v){
    	Intent intent;
    	switch (v.getId()){
			case R.id.createrequest_button:
				intent = new Intent(this, RequestSummary.class);
				break;
			default:
				intent = new Intent();
				break;
    	}
    	
		startActivity(intent);
		finish();
    }
}
