package ca.ualberta.cmput301project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
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



public class ExternalTaskManager
{
    static private String baseURL = "http://crowdsourcer.softwareprocess.es/F12/CMPUT301F12T02/";
    static StringBuilder builder = new StringBuilder();
    static HttpPost httpPost = new HttpPost(baseURL);
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
    private static void internetFetch(HttpGet httpGet){
        try {

            HttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
              HttpEntity entity = response.getEntity();
              InputStream content = entity.getContent();
              BufferedReader reader = new BufferedReader(new InputStreamReader(content));
              String line;
              while ((line = reader.readLine()) != null) {
                builder.append(line);
              }
            }
          } catch (ClientProtocolException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          }
    }
    public static String readAllTasks() {
        HttpGet httpGet = new HttpGet(baseURL+"?action=list");
        internetFetch(httpGet);
        return builder.toString();
    }
    
    public static String readTask(String id){
        HttpGet httpGet = new HttpGet(baseURL+"?action=get&id="+id);
        internetFetch(httpGet);
        JSONObject obj;
        String content = null;
        try
        {
            obj = new JSONObject(builder.toString());
            content = obj.getString("content");
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return content;
    }
    public static String removeTask(String id){
        HttpGet httpGet = new HttpGet(baseURL+"?action=remove&id="+id);
        internetFetch(httpGet);
        return builder.toString();
    }
    public static String addTask(Task task){
        HttpResponse response = null;
        String rtv = null;
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
        List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("action", "post"));
        nvps.add(new BasicNameValuePair("summary", "Task Finished"));
        nvps.add(new BasicNameValuePair("content", object.toString()));

        try
        {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            response = (HttpResponse) httpclient.execute(httpPost);
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        } catch (ClientProtocolException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream is = null;
            try
            {
                is = entity.getContent();
            } catch (IllegalStateException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            rtv = convertStreamToString(is);
        }
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
        List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("action", "update"));
        nvps.add(new BasicNameValuePair("summary", "Task Finished"));
        nvps.add(new BasicNameValuePair("content", oldContent.toString()));

        try
        {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        try
        {
            HttpResponse response = httpclient.execute(httpPost);
        } catch (ClientProtocolException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
