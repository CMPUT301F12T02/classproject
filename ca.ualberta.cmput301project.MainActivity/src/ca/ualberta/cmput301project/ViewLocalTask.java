package ca.ualberta.cmput301project;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewLocalTask extends ListActivity{
	public final static String EXTRA_MESSAGE = "ca.ualberta.cmput301project.MESSAGE";
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasklist);
        
        setListAdapter(new ArrayAdapter<Task>(this,android.R.layout.simple_list_item_1,MainActivity.locallist));
    }
    public void onListItemClick(ListView parent,View v, int position,long id){
    	//list index set to position of clicked entry
    	MainActivity.list_index = position;
    	//find task that was clicked
    	Task clickedTask = MainActivity.thelist.get(MainActivity.list_index);
    	//get task requirements
    	String requirements = clickedTask.getDescription();
    	
        Intent intent = new Intent (this, FulfillTask.class);
        //send requirements with intent
        intent.putExtra(EXTRA_MESSAGE, requirements);
        
        startActivity(intent);
    }
}