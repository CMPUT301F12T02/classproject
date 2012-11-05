package ca.ualberta.cmput301project;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewExternalTask extends ListActivity{
    //public final static String EXTRA_MESSAGE = "ca.ualberta.cmput301project.MESSAGE";
        
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasklist);
        
        Task[] taskList = readAllExtTasks();
        setListAdapter(new ArrayAdapter<Task>(this,android.R.layout.simple_list_item_1, Arrays.asList(taskList)));
    }
    public void onListItemClick(ListView parent,View v, int position,long id){
        /* Backup, trying to get it to work with ArrayList<Task>
         * 
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
        */
    	//Attempt to create ArrayList<Task> from array Task[]
    	ArrayList<Task> tasks = (ArrayList<Task>) Arrays.asList(readAllExtTasks());
    	Task clickedTask = tasks.get(position);
    	
    	Intent intent = new Intent();
    	Bundle b = new Bundle();
		b.putSerializable("task", clickedTask);
		intent.putExtras(b);
		intent.setClass(this, FulfillTask.class);
		startActivity(intent);
    }
    private Task[] readAllExtTasks(){
        JSONArray jarray = null;
        try
        {
            jarray = new JSONArray(ExternalTaskManager.readAllTasks());
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        Task[] taskList = new Task[jarray.length()+1];
        for (int i = 0; i < jarray.length()+1; i++){
            try
            {
                JSONObject obj = jarray.getJSONObject(i);
                JSONObject content = obj.getJSONObject("content");
                String description = content.getString("desciption");
                boolean reqPhoto = Boolean.valueOf(content.getString("reqPhoto"));
                boolean reqAudio = Boolean.valueOf(content.getString("reqAudio"));
                Date timestamp = (Date)content.get("timestamp");
                Task task = new Task(description, reqPhoto, reqAudio, timestamp);
                taskList[i] = task;
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return taskList;
    }
}