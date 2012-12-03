package ca.ualberta.frontend;

import ca.ualberta.frontend.R;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button viewStoredId = (Button) findViewById(R.id.viewstored_button);
        Button newRequestId = (Button) findViewById(R.id.newrequest_button);
        newRequestId.setOnClickListener(this);
        viewStoredId.setOnClickListener(this);
    }

    public void onClick(View v){
    	Intent intent;
    	switch (v.getId()){
			case R.id.newrequest_button:
				intent = new Intent(MainActivity.this, NewRequestActivity.class);
				Log.d("Main", "New intent: NewRequestActivity");
				break;
			case R.id.viewstored_button:
				intent = new Intent(MainActivity.this, StoredTasksActivity.class);
				Log.d("Main", "New intent: StoredTasksActivity");
				break;
			default:
				intent = new Intent();
				break;
    	}
    	Log.d("Main", "Start Activity");
		try {
			MainActivity.this.startActivity(intent);
		} catch (ActivityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	/** 
	 * @uml.property name="newRequestActivity"
	 * @uml.associationEnd aggregation="composite" inverse="mainActivity:ca.ualberta.frontend.NewRequestActivity"
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
	 * @uml.property  name="storedTasksActivity"
	 * @uml.associationEnd  aggregation="composite" inverse="mainActivity:ca.ualberta.frontend.StoredTasksActivity"
	 */
	private StoredTasksActivity storedTasksActivity;

	/**
	 * Getter of the property <tt>storedTasksActivity</tt>
	 * @return  Returns the storedTasksActivity.
	 * @uml.property  name="storedTasksActivity"
	 */
	public StoredTasksActivity getStoredTasksActivity() {
		return storedTasksActivity;
	}

	/**
	 * Setter of the property <tt>storedTasksActivity</tt>
	 * @param storedTasksActivity  The storedTasksActivity to set.
	 * @uml.property  name="storedTasksActivity"
	 */
	public void setStoredTasksActivity(StoredTasksActivity storedTasksActivity) {
		this.storedTasksActivity = storedTasksActivity;
	}
}
