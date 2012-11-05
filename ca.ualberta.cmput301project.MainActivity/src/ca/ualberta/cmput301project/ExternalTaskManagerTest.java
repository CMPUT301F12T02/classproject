package ca.ualberta.cmput301project;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import junit.framework.TestCase;


public class ExternalTaskManagerTest extends TestCase
{

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
        Date today = new Date(System.currentTimeMillis());
        Task task = new Task("JUnit test", false, false, today);
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
        Date today = new Date(System.currentTimeMillis());
        Task task = new Task("JUnit test", false, false, today);
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
        Date today = new Date(System.currentTimeMillis());
        Task task = new Task("JUnit test", false, false, today);
        assert(ExternalTaskManager.addTask(task) != null);

    }

    public void testUpdateTask()
    {

        fail("Not yet implemented");
    }

}
