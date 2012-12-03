package ca.ualberta.backend;

import java.io.Serializable;
import java.util.ArrayList;

import android.graphics.Bitmap;

/** Task is the data class for Tasks.
 * handles storage and easy transferring of
 * tasks between methods
 * @author kerr2
 *
 */

public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    private String description;
    private boolean isPhotoRequired;
    private boolean isAudioRequired;
    private String ownerEmail;
    private java.util.Date timestamp;
    private String id;
    private int likes;
    
    private String result_answer;
    private ArrayList<Bitmap> result_photo;
    private String result_audio;
    
    public Task(String description, boolean isPhotoRequired, boolean isAudioRequired, String ownerEmail, java.util.Date timestamp, String id) {
    	this.description = description;
        this.isPhotoRequired = isPhotoRequired;
        this.isAudioRequired = isAudioRequired;
        this.ownerEmail = ownerEmail;
        this.timestamp = timestamp;
        this.id = id;
        this.likes = 0;
    }
    
    public Task(String newOwner, String description, boolean isPhotoRequired, boolean isAudioRequired) {
    	this.description = description;
        this.isPhotoRequired = isPhotoRequired;
        this.isAudioRequired = isAudioRequired;
        this.ownerEmail = newOwner;
        this.timestamp = new java.util.Date();
        this.id = "local";
    }

    public String getDescription() {
    	return this.description;
    }
    
    public void setDescription(String description) {
    	this.description = description;
    }
    
    public boolean getReqPhoto() {
    	return this.isPhotoRequired;
    }
    
    public void setReqPhoto(Boolean isPhotoRequired) {
    	this.isPhotoRequired = isPhotoRequired;
    }
    
    public boolean getReqAudio() {
    	return this.isAudioRequired;
    }
    
    public void setReqAudio(Boolean isAudioRequired) {
    	this.isAudioRequired = isAudioRequired;
    }
    
    public String getOwner() {
    	return this.ownerEmail;
    }

    public void setOwner(String ownerEmail) {
    	this.ownerEmail = ownerEmail;
    }
    
    public java.util.Date getTimestamp() {
    	return this.timestamp;
    }
    
    public void setTimestamp(java.util.Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getResAnswer() {
        return this.result_answer;
    }
    
    public void setResAnswer(String result_answer) {
    	this.result_answer = result_answer;
    }

    public ArrayList<Bitmap> getResPhoto() {
        return this.result_photo;
    }
    
    public void setResPhoto(ArrayList<Bitmap> result_photo) {
    	this.result_photo = result_photo;
    }

    public String getResAudio() {
        return this.result_audio;
    }
    
    public void setResAudio(String result_audio) {
    	this.result_audio = result_audio;
    }
    
    public String getID() {
        return this.id;
    }
    
    public void setID(String id) {
        this.id = id;
    }
    
    public int incrementLikes(){
        likes++;
        return likes;
    }

    public int getLikes(){
        return likes;
    }
    
    public Task cloneTask() {
    	Task task = new Task(this.description, this.isPhotoRequired, this.isAudioRequired, this.ownerEmail, this.timestamp, this.id);
    	task.setResult(this.result_answer, this.result_photo, this.result_audio);
    	return task;
    }
    
    public void setResult(String answer, ArrayList<Bitmap> result_photo, String result_audio) {
    	this.setResAnswer(answer);
    	this.setResPhoto(result_photo);
    	this.setResAudio(result_audio);
    }
    
    public boolean isEqualTo(Task task) {
    	if ((this.timestamp.toString()).equals(task.timestamp.toString())) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    @Override
    public String toString() {
        return this.description;
    }
}
