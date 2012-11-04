package ca.ualberta.cmput301project;

import java.util.Date;



import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
			        create_task();
				intent = new Intent(this, RequestSummary.class);
				break;
			default:
				intent = new Intent();
				break;
    	}
    	
		startActivity(intent);
		finish();
    }
    
    public void create_task(){
        
        EditText destxt = (EditText) findViewById(R.id.task_description); 
        String desc = destxt.getText().toString();
        
        boolean photo_needed = false;
        final CheckBox checkphoto = (CheckBox) findViewById(R.id.require_photos);
        if (checkphoto.isChecked()) {
             photo_needed = true;
        }
        
        boolean audio_needed = false;
        final CheckBox checkaudio = (CheckBox) findViewById(R.id.require_audio);
        if (checkaudio.isChecked()) {
             audio_needed = true;
        }
        
        Date today = new Date(System.currentTimeMillis());
        
        Task newtask = new Task(desc, photo_needed, audio_needed, today);

        MainActivity.thelist.add(newtask);
        MainActivity.task_count++;
    };
}
