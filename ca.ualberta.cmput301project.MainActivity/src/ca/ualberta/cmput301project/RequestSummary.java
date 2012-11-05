package ca.ualberta.cmput301project;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RequestSummary extends Activity implements OnClickListener {

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
				intent = new Intent(this, StoredTasks.class);
				break;
			case R.id.post_button:
			        Toast.makeText(getApplicationContext(), "Task Added To System(Community)", Toast.LENGTH_SHORT).show();
				intent = new Intent(this, StoredTasks.class);
				break;
			default:
				return;
    	}
    	
		startActivity(intent);
		finish();
    }
    public void post_description(){
    	Task current_task = (Task) getIntent().getSerializableExtra("task");
        String details = current_task.getDescription();
        TextView text;
        text =  (TextView)findViewById(R.id.requestsummary);
        text.setText(details);
    };
}
