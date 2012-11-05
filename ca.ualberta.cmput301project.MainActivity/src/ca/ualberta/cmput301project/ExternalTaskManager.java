package ca.ualberta.cmput301project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;



public class ExternalTaskManager
{
    static private String baseURL = "http://crowdsourcer.softwareprocess.es/F12/CMPUT301F12T02/";
    static StringBuilder builder = new StringBuilder();
    private static HttpClient httpclient = new DefaultHttpClient();
    public ExternalTaskManager(){
        
    }
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
    public static String readAllTasks() {
        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair("action", "list"));
        ExternalTaskManager ext = new ExternalTaskManager();
        internetFetch ifetch = ext.new internetFetch(nvps);
        Context c = null;
        String rtv = ifetch.execute(c).toString();
        return rtv;
    }
    
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
    
<<<<<<< HEAD
    public static String addTask(Task task) {
=======
    public static class addTask extends AsyncTask<Task, Void, String>{
        @Override
        protected String doInBackground(Task... tasks) {
        HttpResponse response = null;
        String rtv = null;
>>>>>>> 46ff95540b8dcf37d40253c6851898bcb7453ef1
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
            object.put("timestamp", task.gettimestamp());
          } catch (JSONException e) {
            e.printStackTrace();
          }
            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("action", "post"));
            nvps.add(new BasicNameValuePair("summary", "TaskFinished"));
            nvps.add(new BasicNameValuePair("content", object.toString()));
            nvps.add(new BasicNameValuePair("description", "TaskPosted"));
            Context c = null;
            ExternalTaskManager ext = new ExternalTaskManager();
            internetFetch ifetch = ext.new internetFetch(nvps);
            String rtv = ifetch.execute(c).toString();
            return rtv;
    }
        
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
            oldContent.put("updatesummary", task.getResDescription());
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
