package ca.ualberta.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import ca.ualberta.frontend.RequestSummaryActivity;
import ca.ualberta.frontend.ViewExternalTaskActivity;

/** ExternalTaskManager handles all online access for storing and
 * accessing tasks.  It has functions for each of the Crowdsourcer
 * commands, except for nuke, in the interest of not setting it off
 * accidentally.  It also contains a private class that handles http
 * access by starting a new stream to access the internet.
 * @author kerr2
 *
 */

public class ExternalTaskManager
{
    private static HttpClient httpclient = new DefaultHttpClient();
    public ExternalTaskManager(){
        
    }
    /** internetFetch is a class that extends the AsyncTask
     * class to be able to access the internet in a separate
     * stream.  It assembles the URL from the NameValuePair
     * given in other classes, with actions such as action,
     * summary, description, content, and id.
     * @author kerr2
     *
     */
    private class internetFetch extends AsyncTask<Context, Void, String>{

        private StringBuilder builder = new StringBuilder();
        private HttpPost httpPost = new HttpPost("http://crowdsourcer.softwareprocess.es/F12/CMPUT301F12T02/");
        public internetFetch(List <NameValuePair> nvps){
            UrlEncodedFormEntity urlefe;
            try
            {
                urlefe = new UrlEncodedFormEntity(nvps);
                httpPost.setEntity(urlefe);
            } catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
        }
        
        protected String doInBackground(Context... c){
            HttpResponse response = null;
            try{
            response = (HttpResponse) httpclient.execute(httpPost);
            } catch (UnsupportedEncodingException e){
                e.printStackTrace();
            } catch (ClientProtocolException e){
                e.printStackTrace();
            } catch (IOException e){       
                e.printStackTrace();
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream is = null;
                try
                {
                    is = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                } catch (IllegalStateException e)
                {
                    e.printStackTrace();
                } catch (IOException e)
                {       
                    e.printStackTrace();
                }
            }
            return builder.toString();
        }        
        protected void onPostExecute(String rtv){
        }
    }
    /** readAllTasks implements the list action of
     * Crowdsourcer, and returns all tasks listed on
     * the Crowdsourcer site.
     * @return String
     */
    public static String readAllTasks() {
        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair("action", "list"));
        ExternalTaskManager ext = new ExternalTaskManager();
        internetFetch ifetch = ext.new internetFetch(nvps);
        Context c = null;
        String rtv = null;
        try
        {
            rtv = ifetch.execute(c).get();
        } catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rtv;
    }
    /** readTask uses the get action from
     * Crowdsourcer to return an action given a
     * string id.
     * @param String id
     * @return
     */
    public static String readTask(String id){
        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair("action", "get"));
        nvps.add(new BasicNameValuePair("id", id));
        Context c = null;
        ExternalTaskManager ext = new ExternalTaskManager();
        internetFetch ifetch = ext.new internetFetch(nvps);
        String rtv = null;
        try
        {
            rtv = ifetch.execute(c).get();
        } catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rtv;
    }
    /** removeTask uses the remove action from
     * Crowdsourcer to remove a task from the
     * service when given a string id.
     * @param String id
     * @return String
     */
    public static String removeTask(String id){
        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair("action", "remove"));
        nvps.add(new BasicNameValuePair("id", id));
        Context c = null;
        ExternalTaskManager ext = new ExternalTaskManager();
        internetFetch ifetch = ext.new internetFetch(nvps);
        String rtv = null;
        try
        {
            rtv = ifetch.execute(c).get();
        } catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rtv;
    }
    
    /** addTask uses the post action from
     * Crowdsourcer to add a task to the
     * service when given a Task object
     * @param Task task
     * @return String
     */
    public static String addTask(Task task) {
        String reqPhoto;
        if(task.getReqPhoto()){
            reqPhoto = "true";
        }
        else {reqPhoto = "false";}
        JSONObject object = new JSONObject();
        try {
            object.put("description", task.getDescription());
            object.put("reqPhoto", reqPhoto);
            object.put("timestamp", task.getTimestamp());
            object.put("likes", task.getLikes());
            object.put("owner", task.getOwner());
          } catch (JSONException e) {
            e.printStackTrace();
          }
            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("action", "post"));
            nvps.add(new BasicNameValuePair("summary", "TaskFinished"));
            nvps.add(new BasicNameValuePair("description", "TaskPosted"));
            nvps.add(new BasicNameValuePair("content", object.toString()));
            Context c = null;
            ExternalTaskManager ext = new ExternalTaskManager();
            internetFetch ifetch = ext.new internetFetch(nvps);
            String rtv = ifetch.execute(c).toString();
            return rtv;
    }
    /** updateTask uses the update action from
     * Crowdsourcer, and updates a task from the
     * service when given the updated Task object
     * and the string id.
     * @param Task task
     * @param String id
     */
    public static void updateTask(Task task, String id){
        JSONObject object = null;
        try
        {
            object = new JSONObject(readTask(id));
        } catch (JSONException e1)
        {
            e1.printStackTrace();
        }
        JSONObject oldContent = null;
        try
        {
            oldContent = new JSONObject(object.getString("content"));
        } catch (JSONException e1)
        {
            e1.printStackTrace();
        }
        try {
            oldContent.put("likes", task.incrementLikes());
            System.out.print(oldContent);
            System.out.println(task.getLikes());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair("action", "update"));
        nvps.add(new BasicNameValuePair("id", id));
        nvps.add(new BasicNameValuePair("summary", "Task Liked"));
        nvps.add(new BasicNameValuePair("content", oldContent.toString()));
        Context c = null;
        ExternalTaskManager ext = new ExternalTaskManager();
        internetFetch ifetch = ext.new internetFetch(nvps);
        String rtv = ifetch.execute(c).toString();
    }
	/**
	 * @uml.property  name="requestSummaryActivity"
	 * @uml.associationEnd  inverse="externalTaskManager:ca.ualberta.frontend.RequestSummaryActivity"
	 */
	private RequestSummaryActivity requestSummaryActivity;
	/**
	 * Getter of the property <tt>requestSummaryActivity</tt>
	 * @return  Returns the requestSummaryActivity.
	 * @uml.property  name="requestSummaryActivity"
	 */
	public RequestSummaryActivity getRequestSummaryActivity() {
		return requestSummaryActivity;
	}
	/**
	 * Setter of the property <tt>requestSummaryActivity</tt>
	 * @param requestSummaryActivity  The requestSummaryActivity to set.
	 * @uml.property  name="requestSummaryActivity"
	 */
	public void setRequestSummaryActivity(
			RequestSummaryActivity requestSummaryActivity) {
				this.requestSummaryActivity = requestSummaryActivity;
			}
	/**
	 * @uml.property  name="task"
	 * @uml.associationEnd  inverse="externalTaskManager:ca.ualberta.backend.Task"
	 */
	private Task task;
	/**
	 * Getter of the property <tt>task</tt>
	 * @return  Returns the task.
	 * @uml.property  name="task"
	 */
	public Task getTask() {
		return task;
	}
	/**
	 * Setter of the property <tt>task</tt>
	 * @param task  The task to set.
	 * @uml.property  name="task"
	 */
	public void setTask(Task task) {
		this.task = task;
	}
	/**
	 * @uml.property  name="viewExternalTaskActivity"
	 * @uml.associationEnd  inverse="externalTaskManager:ca.ualberta.frontend.ViewExternalTaskActivity"
	 */
	private ViewExternalTaskActivity viewExternalTaskActivity;
	/**
	 * Getter of the property <tt>viewExternalTaskActivity</tt>
	 * @return  Returns the viewExternalTaskActivity.
	 * @uml.property  name="viewExternalTaskActivity"
	 */
	public ViewExternalTaskActivity getViewExternalTaskActivity() {
		return viewExternalTaskActivity;
	}
	/**
	 * Setter of the property <tt>viewExternalTaskActivity</tt>
	 * @param viewExternalTaskActivity  The viewExternalTaskActivity to set.
	 * @uml.property  name="viewExternalTaskActivity"
	 */
	public void setViewExternalTaskActivity(
			ViewExternalTaskActivity viewExternalTaskActivity) {
		this.viewExternalTaskActivity = viewExternalTaskActivity;
	}
}
