package ca.ualberta.frontend;



import ca.ualberta.backend.ExternalTaskManager;
import ca.ualberta.backend.LocalTaskManager;
import ca.ualberta.backend.Task;
import ca.ualberta.frontend.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Gives a preview of the request and lets users
 * decide where to save it
 * @author pqin
 *
 */
public class RequestSummaryActivity extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestsummary);
        post_description();
        
        Button saveLocal = (Button) findViewById(R.id.savelocal_button);
        Button shareRequest = (Button) findViewById(R.id.post_button);
        saveLocal.setOnClickListener(this);
        shareRequest.setOnClickListener(this);

    }

    public void onClick(View v){
    	Intent intent;
    	Task current_task = (Task) getIntent().getSerializableExtra("task");
    	switch (v.getId()){
			case R.id.savelocal_button:
				LocalTaskManager.saveLocalTask(current_task, this);
			        Toast.makeText(getApplicationContext(), "Task Added To System(Local)", Toast.LENGTH_SHORT).show();
				intent = new Intent(this, StoredTasksActivity.class);
				break;
			case R.id.post_button:
			        ExternalTaskManager.addTask(current_task);
			        Toast.makeText(getApplicationContext(), "Task Added To System(Community)", Toast.LENGTH_SHORT).show();
				intent = new Intent(this, StoredTasksActivity.class);
				break;
			default:
				return;
    	}
		startActivity(intent);
		finish();
    }
    /**
     * post_description posts the description of the task to the top of the screen
     * @author fessehay
     */
    public void post_description(){
    	Task current_task = (Task) getIntent().getSerializableExtra("task");
        String details = current_task.getDescription();
        TextView text;
        text =  (TextView)findViewById(R.id.requestsummary);
        text.setText(details);
    }
	/**
	 * @uml.property  name="newRequestActivity"
	 * @uml.associationEnd  inverse="requestSummaryActivity:ca.ualberta.frontend.NewRequestActivity"
	 */
	private NewRequestActivity newRequestActivity;
	/**
	 * Getter of the property <tt>newRequestActivity</tt>
	 * @return  Returns the newRequestActivity.
	 * @uml.property  name="newRequestActivity"
	 */
	public NewRequestActivity getNewRequestActivity() {
		return newRequestActivity;
	}

	/**
	 * Setter of the property <tt>newRequestActivity</tt>
	 * @param newRequestActivity  The newRequestActivity to set.
	 * @uml.property  name="newRequestActivity"
	 */
	public void setNewRequestActivity(NewRequestActivity newRequestActivity) {
		this.newRequestActivity = newRequestActivity;
	}
	/**
	 * @uml.property  name="localTaskManager"
	 * @uml.associationEnd  inverse="requestSummaryActivity:ca.ualberta.backend.LocalTaskManager"
	 */
	private LocalTaskManager localTaskManager;
	/**
	 * Getter of the property <tt>localTaskManager</tt>
	 * @return  Returns the localTaskManager.
	 * @uml.property  name="localTaskManager"
	 */
	public LocalTaskManager getLocalTaskManager() {
		return localTaskManager;
	}

	/**
	 * Setter of the property <tt>localTaskManager</tt>
	 * @param localTaskManager  The localTaskManager to set.
	 * @uml.property  name="localTaskManager"
	 */
	public void setLocalTaskManager(LocalTaskManager localTaskManager) {
		this.localTaskManager = localTaskManager;
	}
	/**
	 * @uml.property  name="externalTaskManager"
	 * @uml.associationEnd  inverse="requestSummaryActivity:ca.ualberta.backend.ExternalTaskManager"
	 */
	private ExternalTaskManager externalTaskManager;
	/**
	 * Getter of the property <tt>externalTaskManager</tt>
	 * @return  Returns the externalTaskManager.
	 * @uml.property  name="externalTaskManager"
	 */
	public ExternalTaskManager getExternalTaskManager() {
		return externalTaskManager;
	}

	/**
	 * Setter of the property <tt>externalTaskManager</tt>
	 * @param externalTaskManager  The externalTaskManager to set.
	 * @uml.property  name="externalTaskManager"
	 */
	public void setExternalTaskManager(ExternalTaskManager externalTaskManager) {
		this.externalTaskManager = externalTaskManager;
	};
}
