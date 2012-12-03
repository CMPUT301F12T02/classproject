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
    }
	/** 
	 * @uml.property name="mainActivity"
	 * @uml.associationEnd inverse="newRequestActivity:ca.ualberta.frontend.MainActivity"
	 */
	private MainActivity mainActivity;
	/** 
	 * Getter of the property <tt>mainActivity</tt>
	 * @return  Returns the mainActivity.
	 * @uml.property  name="mainActivity"
	 */
	public MainActivity getMainActivity() {
		return mainActivity;
	}

	/** 
	 * Setter of the property <tt>mainActivity</tt>
	 * @param mainActivity  The mainActivity to set.
	 * @uml.property  name="mainActivity"
	 */
	public void setMainActivity(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}
	/** 
	 * @uml.property name="requestSummaryActivity"
	 * @uml.associationEnd aggregation="shared" inverse="newRequestActivity:ca.ualberta.frontend.RequestSummaryActivity"
	 */
	private RequestSummaryActivity requestSummaryActivity;
	/** 
	 * Getter of the property <tt>requestSummaryActivity</tt>
	 * @return  Returns the requestSummaryActivity.
	 * @uml.property  name="requestSummaryActivity"
	 */
	public RequestSummaryActivity getRequestSummaryActivity() {
		return requestSummaryActivity;
	}

	/** 
	 * Setter of the property <tt>requestSummaryActivity</tt>
	 * @param requestSummaryActivity  The requestSummaryActivity to set.
	 * @uml.property  name="requestSummaryActivity"
	 */
	public void setRequestSummaryActivity(
			RequestSummaryActivity requestSummaryActivity) {
				this.requestSummaryActivity = requestSummaryActivity;
			}
	/**
	 * @uml.property  name="task"
	 * @uml.associationEnd  inverse="newRequestActivity:ca.ualberta.backend.Task"
	 */
	private Task task;
	/**
	 * Getter of the property <tt>task</tt>
	 * @return  Returns the task.
	 * @uml.property  name="task"
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * Setter of the property <tt>task</tt>
	 * @param task  The task to set.
	 * @uml.property  name="task"
	 */
	public void setTask(Task task) {
		this.task = task;
	};
}
