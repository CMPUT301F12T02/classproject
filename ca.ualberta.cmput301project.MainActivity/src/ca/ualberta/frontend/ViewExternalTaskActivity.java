package ca.ualberta.frontend;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ca.ualberta.backend.ExternalTaskManager;
import ca.ualberta.backend.Task;
import ca.ualberta.frontend.R;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/** ViewExternalTaskActivity is a (currently incomplete)
 * class that handles displaying the list of tasks
 * obtained from the Crowdsourcer service, and handles
 * displaying individual tasks.
 * @author kerr2
 *
 */
public class ViewExternalTaskActivity extends ListActivity{
        public final static String REQDESCRIPTION = "ca.ualberta.cmput301project.DESCRIPTION";
        public final static String REQPHOTO = "ca.ualberta.cmput301project.PHOTO";
        public final static String REQAUDIO = "ca.ualberta.cmput301project.AUDIO";
        public final static String EXTRA_MESSAGE = "ca.ualberta.cmput301project.MESSAGE";
        private ArrayList<Task> tasks;
        private ArrayList<String> ids;
    
        /** onCreate calls the superclass constructor,
         * sets content view, and starts the custom display.
         * @param Bundle savedInstanceState
         */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasklist);


        refresh();
    }
    /** onRestart calls the refresh method to continuously
     * handle display.
     * @param Bundle savedInstanceState
     */
    
    public void onRestart() {
        super.onRestart();
        refresh();
    }
    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }
    /** refresh gets the tasks from readAllExtTasks()
     * and sets the list adapter
     */
    private void refresh() {
        tasks = readAllExtTasks();
        System.out.println(tasks.toString());
        setListAdapter(new ArrayAdapter<Task>(this,android.R.layout.simple_list_item_1,tasks));
    }
    /**onListItemClick handles the selection of a task from
     * the list view.
     * @param ListView parent
     * @param View v
     * @param int position
     * @param long id
     */
    
    public void onListItemClick(ListView parent,View v, int position,long id){
        Task clickedTask = tasks.get(position);
        Intent intent = new Intent();
        Bundle b = new Bundle();
        b.putSerializable("task", clickedTask);
        intent.putExtras(b);
        intent.putExtra("file", "EXTERNAL");
        intent.setClass(this, FulfillTaskActivity.class);
        startActivity(intent);
    }
    /** readAllExtTasks calls the ExternalTaskManager to 
     * get all the tasks from the Crowdsourcer service.  It
     * keeps a list of ids in case a task is selected, and
     * then converts the JSONArray into an ArrayList of tasks. 
     * @return ArrayList<Task>
     */
    private ArrayList<Task> readAllExtTasks(){
        ArrayList<Task> tasks = new ArrayList<Task>();
        JSONArray jarray = null;
        try
        {
            String result = ExternalTaskManager.readAllTasks();
            jarray = new JSONArray(result);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        for (int i = 0; i < jarray.length(); i++){
            try
            {
            	//We added an owner attribute to task, so account for that! :D
                String tojson = null;
            	String owner = "John Doe"; //DUMMY OWNER
                JSONObject obj = jarray.getJSONObject(i);
                String id = (String) obj.get("id");
                tojson = ExternalTaskManager.readTask(id);
                JSONObject fullTask = new JSONObject(tojson);
                JSONObject content = fullTask.getJSONObject("content");
                String description = content.getString("description");
                boolean reqPhoto = Boolean.valueOf(content.getString("reqPhoto"));
                boolean reqAudio = Boolean.valueOf(content.getString("reqAudio"));
                Task task = new Task(owner, description, reqPhoto, reqAudio, id);
                tasks.add(task);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return tasks;
    }
}