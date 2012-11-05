package ca.ualberta.cmput301project;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewExternalTask extends ListActivity{
        public final static String REQDESCRIPTION = "ca.ualberta.cmput301project.DESCRIPTION";
        public final static String REQPHOTO = "ca.ualberta.cmput301project.PHOTO";
        public final static String REQAUDIO = "ca.ualberta.cmput301project.AUDIO";
        //public final static String EXTRA_MESSAGE = "ca.ualberta.cmput301project.MESSAGE";
        
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasklist);

        refresh();
    }
    
    public void onRestart(Bundle savedInstanceState) {
        super.onRestart();
        refresh();
    }
    
    private void refresh() {
        ArrayList<Task> tasks = readAllExtTasks();
        setListAdapter(new ArrayAdapter<Task>(this,android.R.layout.simple_list_item_1,tasks));
    }
    
    public void onListItemClick(ListView parent,View v, int position,long id){

        ArrayList<Task> tasks = readAllExtTasks();
        Task clickedTask = tasks.get(position);
        
        Intent intent = new Intent();
        Bundle b = new Bundle();
        b.putSerializable("task", clickedTask);
        intent.putExtras(b);
        intent.setClass(this, FulfillTaskActivity.class);
        startActivity(intent);
    }
    private ArrayList<Task> readAllExtTasks(){
        ArrayList<Task> tasks = new ArrayList<Task>();
        JSONArray jarray = null;
        try
        {
            String result = ExternalTaskManager.readAllTasks();
            System.out.println(result);
            JSONObject json = new JSONObject(result);
            JSONArray name = json.names();
            jarray = json.toJSONArray(name);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        for (int i = 0; i < jarray.length()+1; i++){
            try
            {
                JSONObject obj = jarray.getJSONObject(i);
                JSONObject content = obj.getJSONObject("content");
                String description = content.getString("desciption");
                boolean reqPhoto = Boolean.valueOf(content.getString("reqPhoto"));
                boolean reqAudio = Boolean.valueOf(content.getString("reqAudio"));
                Task task = new Task(description, reqPhoto, reqAudio);
                tasks.add(task);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return tasks;
    }
}