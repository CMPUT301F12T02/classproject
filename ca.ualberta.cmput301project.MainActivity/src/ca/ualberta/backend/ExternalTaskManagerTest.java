package ca.ualberta.backend;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;


import junit.framework.TestCase;


public class ExternalTaskManagerTest extends TestCase
{
    private Task task;
    protected void setUp(){
        task = new Task("John Doe","JUnit test", false);
    }

    public void testReadAllTasks()
    {
        if (ExternalTaskManager.readAllTasks() == null){
            System.out.println("No entries stored");
        }
        else {
            System.out.println("Some entries stored");
        }
    }

    public void testReadTask()
    {
        JSONObject jobj = null;
        try
        {
            jobj = new JSONObject(ExternalTaskManager.addTask(task));
            assert(ExternalTaskManager.readTask(jobj.getString("id")) != null);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void testRemoveTask()
    {
        JSONObject jobj = null;
        try
        {
            jobj = new JSONObject(ExternalTaskManager.addTask(task));
            assert(ExternalTaskManager.removeTask(jobj.getString("id")) != null);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void testAddTask()
    {
        assert(ExternalTaskManager.addTask(task) != null);

    }

    public void testUpdateTask()
    {
        JSONObject jobj = null;
        try{
            jobj = new JSONObject(ExternalTaskManager.addTask(task));
            String id = jobj.getString("id");
            jobj = new JSONObject(ExternalTaskManager.readTask(id));
            JSONObject content = jobj.getJSONObject("content");
            String description = content.getString("description");
            boolean reqPhoto = Boolean.valueOf(content.getString("reqPhoto"));
            String owner = content.getString("owner");
            int likes = content.getInt("likes");
            DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            Date timestamp = df.parse(content.getString("timestamp"));
            Task task = new Task(description, reqPhoto, owner, timestamp, id, likes);
            
        } catch (JSONException e){
            e.printStackTrace();
        } catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        fail("UpdateTask does not currently return any values");
    }

}

