package ca.ualberta.cmput301project;

import java.io.Serializable;
import java.util.Date;


public class Task implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String description;
    private boolean isPhotoRequired;
    private boolean isAudioRequired;
    private boolean isComplete;
    private Date timestamp;
    
    private String result_answer = "no answer";
    private String result_photofile = "none";
    private String result_audiofile = "none";
    
    public Task(String description, boolean isPhotoRequired, boolean isAudioRequired) {
    	this.description = description;
        this.isPhotoRequired = isPhotoRequired;
        this.isAudioRequired = isAudioRequired;
        this.isComplete = false;
        this.timestamp = new java.util.Date();
    }
    
    public String getDescription() {
    	return this.description;
    }
    
    public boolean getReqPhoto() {
    	return this.isPhotoRequired;
    }
    
    public boolean getReqAudio() {
    	return this.isAudioRequired;
    }
    
    public boolean getComplete() {
    	return this.isComplete;
    }
    
    public Date getTimestamp() {
    	return this.timestamp;
    }
    
    public String getResAnswer() {
        return this.result_answer;
    }
    
    public String getResPhotoName() {
        return this.result_photofile;
    }
    
    public String getResAudioName() {
        return this.result_audiofile;
    }
    
    public void setResult(String answer, String photofile, String audiofile) {
    	this.result_answer = answer;
    	this.result_photofile = photofile;
    	this.result_audiofile = audiofile;
    }
    
    public boolean equals(Task task) {
    	if (((this.description).equals(task.description))
    			&&(((this.timestamp).toString()).equals((task.timestamp).toString()))
    			&&(this.isComplete == task.isComplete)
    			&&(this.isPhotoRequired == task.isPhotoRequired)
    			&&(this.isAudioRequired == task.isAudioRequired)
    			&&(this.result_answer == task.result_answer)
    			&&(this.result_photofile == task.result_photofile)
    			&&(this.result_audiofile == task.result_audiofile)) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public Task cloneTask(){
    	Task newTask = new Task(this.description, this.isPhotoRequired, this.isAudioRequired);
    	newTask.isComplete = this.isComplete;
    	newTask.timestamp = this.timestamp;
    	newTask.result_answer = this.result_answer;
    	newTask.result_photofile = this.result_photofile;
    	newTask.result_audiofile = this.result_audiofile;
    	return newTask;
    }
}
