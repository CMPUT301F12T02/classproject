package ca.ualberta.cmput301project;

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
    static private String baseURL = "http://crowdsourcer.softwareprocess.es/F12/CMPUT301F12T02/";
    static private StringBuilder builder = new StringBuilder();
    private static HttpClient httpclient = new DefaultHttpClient();
    public ExternalTaskManager(){
        
    }
    /** convertStreamToString converts an InputStream object to a
     * String object.
     * @param InputStream is
     * @return String
     */
    private static  String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
                while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                }
        } 
        catch (IOException e) {
                e.printStackTrace();
        } 
        finally {
                try {
                        is.close();
                } 
                catch (IOException e) {
                        e.printStackTrace();
                }
        }
        return sb.toString();
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
                        System.out.println(line);
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
            System.out.println(builder.toString());
            try
            {
                Thread.sleep(2000, 0);
            } catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
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
        System.out.println(rtv);
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
        nvps.add(new BasicNameValuePair("action", "list"));
        nvps.add(new BasicNameValuePair("id", id));
        Context c = null;
        ExternalTaskManager ext = new ExternalTaskManager();
        internetFetch ifetch = ext.new internetFetch(nvps);
        String rtv = ifetch.execute(c).toString();
        JSONObject obj;
        String content = null;
        try
        {
            obj = new JSONObject(rtv);
            content = obj.getString("content");
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return content;
    }
    /** removeTask uses the remove action from
     * Crowdsourcer to remove a task from the
     * service when given a string id.
     * @param String id
     * @return String
     */
    public static String removeTask(String id){
        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair("action", "list"));
        nvps.add(new BasicNameValuePair("id", id));
        Context c = null;
        ExternalTaskManager ext = new ExternalTaskManager();
        internetFetch ifetch = ext.new internetFetch(nvps);
        String rtv = ifetch.execute(c).toString();
        return rtv;
    }
    /** addTask uses the post action from
     * Crowdsourcer to add a task to the
     * service when given a Task object
     * @param Task task
     * @return String
     */
    public static String addTask(Task task) {
        String reqPhoto, reqAudio;
        if(task.getReqPhoto()){
            reqPhoto = "true";
        }
        else {reqPhoto = "false";}
        if(task.getReqAudio()){
            reqAudio = "true";
        }
        else {reqAudio = "false";}
        JSONObject object = new JSONObject();
        try {
            object.put("description", task.getDescription());
            object.put("reqPhoto", reqPhoto);
            object.put("reqAudio", reqAudio);
            object.put("timestamp", task.getTimestamp());
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
            oldContent.put("updatesummary", task.getResAnswer());
            oldContent.put("photofile", task.getResPhotoName());
            oldContent.put("audiofile", task.getResAudioName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair("action", "update"));
        nvps.add(new BasicNameValuePair("summary", "Task Finished"));
        nvps.add(new BasicNameValuePair("content", oldContent.toString()));
        Context c = null;
        ExternalTaskManager ext = new ExternalTaskManager();
        internetFetch ifetch = ext.new internetFetch(nvps);
        String rtv = ifetch.execute(c).toString();
    }
}
