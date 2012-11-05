package ca.ualberta.cmput301project;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
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
    	Task current_task = new Task();
    	switch (v.getId()){
			case R.id.savelocal_button:
			        current_task = MainActivity.thelist.get(MainActivity.task_count -1);
			        Toast.makeText(getApplicationContext(), "Task Added To System(Local)", Toast.LENGTH_SHORT).show();
			        MainActivity.locallist.add(current_task);
			        MainActivity.local_count++;
				intent = new Intent(this, StoredTasks.class);
				break;
			case R.id.post_button:
			        current_task = MainActivity.thelist.get(MainActivity.task_count -1);
			        Toast.makeText(getApplicationContext(), "Task Added To System(Community)", Toast.LENGTH_SHORT).show();
			        ExternalTaskManager.addTask(current_task);
			        MainActivity.community_count++;
				intent = new Intent(this, StoredTasks.class);
				break;
			default:
				return;
    	}
    	
		startActivity(intent);
		finish();
    }
    public void post_description(){
        Task current_task = MainActivity.thelist.get(MainActivity.task_count -1);
        String details = current_task.getDescription();
        TextView text;
        text =  (TextView)findViewById(R.id.requestsummary);
        text.setText(details);
    };
}
