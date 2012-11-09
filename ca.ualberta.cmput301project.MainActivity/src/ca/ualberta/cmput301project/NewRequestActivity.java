package ca.ualberta.cmput301project;

import ca.ualberta.Tasks.Task;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


public class NewRequestActivity extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newrequest);
        
        Button createRequest = (Button) findViewById(R.id.createrequest_button);
        createRequest.setOnClickListener(this);
    }

    public void onClick(View v){
    	Intent intent = new Intent();
    	switch (v.getId()){
			case R.id.createrequest_button:
			        Task task = create_task();
			        
			    Bundle b = new Bundle();
				b.putSerializable("task", task);
				intent.putExtras(b);
				intent.setClass(this, RequestSummaryActivity.class);
				startActivity(intent);
				break;
			default:
				intent = new Intent();
				break;
    	}
    	
		startActivity(intent);
		finish();
    }
    /** create_task creates an object task using the checkboxes and description as
     * the variables and then constructing the Object.
     * 
     *  @return Task
     *  @author fessehay
     */
    public Task create_task(){
        String owner = "John Doe";
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
        
        Task newtask = new Task(owner, desc, photo_needed, audio_needed);
        
        return newtask;
    };
}
