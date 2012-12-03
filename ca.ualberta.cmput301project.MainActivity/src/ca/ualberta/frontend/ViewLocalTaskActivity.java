package ca.ualberta.frontend;

import java.util.ArrayList;

import ca.ualberta.backend.LocalTaskManager;
import ca.ualberta.backend.Task;
import ca.ualberta.frontend.R;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
/**
 * Arranges local tasks into a clickable list,
 * clicking displays the specific task
 * @author fessehay
 *
 */
public class ViewLocalTaskActivity extends ListActivity {
	final private String LOCAL_FILE = "LOCAL";
	final private String FAVOURITE_FILE = "FAVOURITES";
	final private String DRAFT_FILE = "DRAFTS";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasklist);
        
        refresh();
    }
    
    @Override
    public void onRestart() {
    	super.onRestart();
    	refresh();
    }
    @Override
    public void onResume() {
    	super.onResume();
    	refresh();
    }
    
    private void refresh() {
    	Bundle extras = getIntent().getExtras();
    	String file = extras.getString("file");
    	
    	ArrayList<Task> tasks;
    	
    	if (file.equals(LOCAL_FILE)) {
    		tasks = LocalTaskManager.loadLocalTasks(this);
    		setTitle("My Tasks");
    	} else if (file.equals(FAVOURITE_FILE)) {
    		tasks = LocalTaskManager.loadFavourites(this);
    		setTitle("My Favourite Tasks");
    	} else if (file.equals(DRAFT_FILE)){
    		tasks = LocalTaskManager.loadDrafts(this);
    		setTitle("My Drafts");
    	} else {
    		tasks = new ArrayList<Task>();
    		tasks.clear();
    		setTitle("Unknown");
    	}
    	Log.d("file", "Matched file: " + file);
    	
        setListAdapter(new ArrayAdapter<Task>(this,android.R.layout.simple_expandable_list_item_1,tasks));

    }
    
    /* (non-Javadoc)
     * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
     */
    /* (non-Javadoc)
     * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
     */
    public void onListItemClick(ListView parent,View v, int position,long id){
    	Bundle extras = getIntent().getExtras();
    	String file = extras.getString("file");
    	
    	ArrayList<Task> tasks;
    	
    	if (file.equals(LOCAL_FILE)) {
    		tasks = LocalTaskManager.loadLocalTasks(this);
    	} else if (file.equals(FAVOURITE_FILE)) {
    		tasks = LocalTaskManager.loadFavourites(this);
    	} else if (file.equals(DRAFT_FILE)) {
    		tasks = LocalTaskManager.loadDrafts(this);
    	} else {
    		return;
    	}
    	
    	Task clickedTask = tasks.get(position);
    	
    	Intent intent = new Intent();
    	Bundle b = new Bundle();
		b.putSerializable("task", clickedTask);
		intent.putExtras(b);
		intent.putExtra("file", file);
		intent.setClass(this, FulfillTaskActivity.class);
		startActivity(intent);
    }

	/**
	 * @uml.property  name="storedTasksActivity"
	 * @uml.associationEnd  inverse="viewLocalTaskActivity:ca.ualberta.frontend.StoredTasksActivity"
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

	/**
	 * @uml.property  name="fulfillTaskActivity"
	 * @uml.associationEnd  aggregation="composite" inverse="viewLocalTaskActivity:ca.ualberta.frontend.FulfillTaskActivity"
	 */
	private FulfillTaskActivity fulfillTaskActivity;

	/**
	 * Getter of the property <tt>fulfillTaskActivity</tt>
	 * @return  Returns the fulfillTaskActivity.
	 * @uml.property  name="fulfillTaskActivity"
	 */
	public FulfillTaskActivity getFulfillTaskActivity() {
		return fulfillTaskActivity;
	}

	/**
	 * Setter of the property <tt>fulfillTaskActivity</tt>
	 * @param fulfillTaskActivity  The fulfillTaskActivity to set.
	 * @uml.property  name="fulfillTaskActivity"
	 */
	public void setFulfillTaskActivity(FulfillTaskActivity fulfillTaskActivity) {
		this.fulfillTaskActivity = fulfillTaskActivity;
	}

	/**
	 * @uml.property  name="localTaskManager"
	 * @uml.associationEnd  inverse="viewLocalTaskActivity:ca.ualberta.backend.LocalTaskManager"
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
}