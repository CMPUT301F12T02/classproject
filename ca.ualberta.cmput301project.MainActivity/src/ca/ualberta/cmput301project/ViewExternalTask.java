package ca.ualberta.cmput301project;

import java.sql.Date;
import java.util.ArrayList;
<<<<<<< HEAD

=======
>>>>>>> 46ff95540b8dcf37d40253c6851898bcb7453ef1
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
<<<<<<< HEAD
=======
        
>>>>>>> 46ff95540b8dcf37d40253c6851898bcb7453ef1
        refresh();
    }
    
    public void onRestart(Bundle savedInstanceState) {
        super.onRestart();
        refresh();
<<<<<<< HEAD
=======

        ArrayList<Task> taskList = readAllExtTasks();
        setListAdapter(new ArrayAdapter<Task>(this,android.R.layout.simple_list_item_1, (Task[]) taskList.toArray()));
>>>>>>> 46ff95540b8dcf37d40253c6851898bcb7453ef1
    }
    
    private void refresh() {
        ArrayList<Task> tasks = readAllExtTasks();
        setListAdapter(new ArrayAdapter<Task>(this,android.R.layout.simple_list_item_1,tasks));
    }
    
    public void onListItemClick(ListView parent,View v, int position,long id){
<<<<<<< HEAD
=======

>>>>>>> 46ff95540b8dcf37d40253c6851898bcb7453ef1
        ArrayList<Task> tasks = readAllExtTasks();
        Task clickedTask = tasks.get(position);
        
        Intent intent = new Intent();
        Bundle b = new Bundle();
<<<<<<< HEAD
                b.putSerializable("task", clickedTask);
                intent.putExtras(b);
                intent.setClass(this, FulfillTask.class);
                startActivity(intent);
=======
        b.putSerializable("task", clickedTask);
        intent.putExtras(b);
        intent.setClass(this, FulfillTask.class);
        startActivity(intent);
>>>>>>> 46ff95540b8dcf37d40253c6851898bcb7453ef1
    }
    private ArrayList<Task> readAllExtTasks(){
        ArrayList<Task> tasks = new ArrayList<Task>();
        JSONArray jarray = null;
        try
        {
            JSONObject json = new JSONObject(ExternalTaskManager.readAllTasks());
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
                Date timestamp = (Date)content.get("timestamp");
                Task task = new Task(description, reqPhoto, reqAudio, timestamp);
                tasks.add(task);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return tasks;
    }
}