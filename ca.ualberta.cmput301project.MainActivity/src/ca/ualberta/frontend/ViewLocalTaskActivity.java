package ca.ualberta.frontend;

import java.util.ArrayList;

import ca.ualberta.backend.LocalTaskManager;
import ca.ualberta.backend.Task;
import ca.ualberta.frontend.R;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
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
    	
    	if (file.equals("LOCAL")) {
    		tasks = LocalTaskManager.loadLocalTasks(this);
    		
    	} else if (file.equals("FAVOURITES")) {
    		tasks = LocalTaskManager.loadFavourites(this);
    	} else {
    		tasks = LocalTaskManager.loadDrafts(this);
    	}
    	
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
    	
    	if (file.equals("LOCAL")) {
    		tasks = LocalTaskManager.loadLocalTasks(this);
    	} else if (file.equals("FAVOURITES")) {
    		tasks = LocalTaskManager.loadFavourites(this);
    	} else {
    		tasks = LocalTaskManager.loadDrafts(this);
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
}