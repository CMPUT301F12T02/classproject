package ca.ualberta.frontend;

import ca.ualberta.frontend.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;

/**StoredTasksActivity is a menu where users can select
 * a method of viewing their saved tasks, as well as online tasks.
 * @author pqin, fessehay
 *
 */
public class StoredTasksActivity extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storedtasks);
        
        Button viewLocaldId = (Button) findViewById(R.id.local_button);
        Button viewComId = (Button) findViewById(R.id.com_button);
        Button viewFavouritesdId = (Button) findViewById(R.id.favourites_button);
        Button viewDraftsId = (Button) findViewById(R.id.drafts_button);
        Button viewRandomId = (Button) findViewById(R.id.random_button);
        
        viewLocaldId.setOnClickListener(this);
        viewComId.setOnClickListener(this);
        viewFavouritesdId.setOnClickListener(this);
        viewDraftsId.setOnClickListener(this);
        viewRandomId.setOnClickListener(this);
    }
        
    public void onClick(View v){
    	//Needs to get request information,
    	// to populate task description box
    	// to gray out appropriate get media button
    	Intent intent = null;
    	
        switch (v.getId()){
	        case R.id.local_button:
	        	intent = new Intent(this, ViewLocalTaskActivity.class);
	        	intent.putExtra("file", "LOCAL");
	        	break;
	        case R.id.favourites_button:
	        	intent = new Intent(this, ViewLocalTaskActivity.class);
	        	intent.putExtra("file", "FAVOURITES");
	        	break;
	        case R.id.drafts_button:
	        	intent = new Intent(this, ViewLocalTaskActivity.class);
	        	intent.putExtra("file", "DRAFTS");
	        	break;
	        case R.id.com_button:
	        	intent = new Intent(this, ViewExternalTaskActivity.class);
	        	intent.putExtra("file", "EXTERNAL");
	        	break;
	        case R.id.random_button:
	                intent = new Intent(this, ViewExternalTaskActivity.class);
	                intent.putExtra("file", "RANDOM");
	                break;
        }
        startActivity(intent);
    }

	/** 
	 * @uml.property name="mainActivity"
	 * @uml.associationEnd inverse="storedTasksActivity:ca.ualberta.frontend.MainActivity"
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
	 * @uml.property name="viewExternalTaskActivity"
	 * @uml.associationEnd aggregation="shared" inverse="storedTasksActivity:ca.ualberta.frontend.ViewExternalTaskActivity"
	 */
	private ViewExternalTaskActivity viewExternalTaskActivity;

	/** 
	 * Getter of the property <tt>viewExternalTaskActivity</tt>
	 * @return  Returns the viewExternalTaskActivity.
	 * @uml.property  name="viewExternalTaskActivity"
	 */
	public ViewExternalTaskActivity getViewExternalTaskActivity() {
		return viewExternalTaskActivity;
	}

	/** 
	 * Setter of the property <tt>viewExternalTaskActivity</tt>
	 * @param viewExternalTaskActivity  The viewExternalTaskActivity to set.
	 * @uml.property  name="viewExternalTaskActivity"
	 */
	public void setViewExternalTaskActivity(
			ViewExternalTaskActivity viewExternalTaskActivity) {
				this.viewExternalTaskActivity = viewExternalTaskActivity;
			}

	/** 
	 * @uml.property name="viewLocalTaskActivity"
	 * @uml.associationEnd aggregation="shared" inverse="storedTasksActivity:ca.ualberta.frontend.ViewLocalTaskActivity"
	 */
	private ViewLocalTaskActivity viewLocalTaskActivity;

	/** 
	 * Getter of the property <tt>viewLocalTaskActivity</tt>
	 * @return  Returns the viewLocalTaskActivity.
	 * @uml.property  name="viewLocalTaskActivity"
	 */
	public ViewLocalTaskActivity getViewLocalTaskActivity() {
		return viewLocalTaskActivity;
	}

	/** 
	 * Setter of the property <tt>viewLocalTaskActivity</tt>
	 * @param viewLocalTaskActivity  The viewLocalTaskActivity to set.
	 * @uml.property  name="viewLocalTaskActivity"
	 */
	public void setViewLocalTaskActivity(
			ViewLocalTaskActivity viewLocalTaskActivity) {
				this.viewLocalTaskActivity = viewLocalTaskActivity;
			}
}
