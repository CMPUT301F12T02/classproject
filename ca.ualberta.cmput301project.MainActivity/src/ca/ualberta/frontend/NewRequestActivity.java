package ca.ualberta.frontend;

import ca.ualberta.backend.Task;
import ca.ualberta.frontend.R;
import android.app.Activity;
import android.app.Dialog;
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
	    Task task = create_task();
	    Dialog dialog = new Dialog(this);
	    if (task.getDescription().isEmpty()){
	    	dialog.setTitle("Missing description");
	    	dialog.show();
	    	return;
	    } else if (task.getOwner().isEmpty()){
	    	dialog.setTitle("Missing email");
	    	dialog.show();
	    	return;
	    }
	    
	    Intent intent = new Intent();
	    Bundle b = new Bundle();
		b.putSerializable("task", task);
		intent.putExtras(b);
		intent.setClass(this, RequestSummaryActivity.class);
    	
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
        EditText ownerEmailEditText = (EditText) findViewById(R.id.user_email); 
        String ownerEmail = ownerEmailEditText.getText().toString();
        
        EditText destxt = (EditText) findViewById(R.id.task_description); 
        String desc = destxt.getText().toString();
        
        boolean photo_needed = false;
        final CheckBox checkphoto = (CheckBox) findViewById(R.id.require_photos);
        if (checkphoto.isChecked()) {
             photo_needed = true;
        }
        
        Task newtask = new Task(ownerEmail, desc, photo_needed);
        
        return newtask;
    };
}
