package ca.ualberta.cmput301project;

import java.io.Serializable;
import java.util.Date;



public class Task implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String description;
    private boolean req_photo;
    private boolean req_audio;
    private boolean complete;
    
    private Date timestamp;
    
    private String result_description = "No description";
    private String result_photofile = "none";
    private String result_audiofile = "none";
    
    public Task (){
        
    }
    
    public Task (String description, boolean req_photo, boolean req_audio, Date timestamp){
        this.description = description;
        this.req_photo = req_photo;
        this.req_audio = req_audio;
        this.timestamp = timestamp;
    }
    
    public String getDescription(){
        return description;
    }
    public Date gettimestamp(){
        return timestamp;
    }
    public boolean getcomplete(){
        return complete;
    }
    public boolean getReqPhoto(){
        return req_photo;
    }
    public boolean getReqAudio(){
        return req_audio;
    }
    public String getResDescription(){
        return result_description;
    }
    public String getResPhotoName(){
        return result_photofile;
    }
    public String getResAudioName(){
        return result_audiofile;
    }
    // I don't think we need this atm.....
    public void setResult(String desc, String photo, String audio){
        result_description = desc;
        if (photo != "none"){
            result_photofile = photo;
        }
        if (audio != "none")
            result_audiofile = audio;
    }
    
    public boolean equals(Task task) {
    	if (((this.getDescription()).equals(task.getDescription()))
    			&&(((this.gettimestamp()).toString()).equals((task.gettimestamp()).toString()))
    			&&(this.getcomplete() == task.getcomplete())
    			&&(this.getReqPhoto() == task.getReqPhoto())
    			&&(this.getReqAudio() == task.getReqAudio())) {
    		return true;
    	} else {
    		return false;
    	}
    }
}
