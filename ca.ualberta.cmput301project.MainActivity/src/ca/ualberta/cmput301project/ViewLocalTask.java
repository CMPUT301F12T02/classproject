package ca.ualberta.cmput301project;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewLocalTask extends ListActivity{
	public final static String REQPHOTO = "ca.ualberta.cmput301project.PHOTO";
	public final static String REQAUDIO = "ca.ualberta.cmput301project.AUDIO";
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasklist);
        
        setListAdapter(new ArrayAdapter<Task>(this,android.R.layout.simple_list_item_1,MainActivity.locallist));
    }
    public void onListItemClick(ListView parent,View v, int position,long id){
    	//list index set to position of clicked entry
    	MainActivity.list_index = position;
        Intent intent = new Intent (this, FulfillTask.class);
    	//find task that was clicked
    	Task clickedTask = MainActivity.thelist.get(MainActivity.list_index);
        //send requirements with intent
        intent.putExtra(REQPHOTO, clickedTask.getReqPhoto());
        intent.putExtra(REQAUDIO, clickedTask.getReqAudio());
        
        startActivity(intent);
    }
}