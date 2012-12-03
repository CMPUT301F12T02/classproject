package ca.ualberta.frontend;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import ca.ualberta.backend.Email;
import ca.ualberta.backend.ExternalTaskManager;
import ca.ualberta.backend.LocalTaskManager;
import ca.ualberta.backend.Task;
/**
 * FulfillTaskActivity is the interface to submit all
 * required media for the task, 'fulfilling' the task
 * @author pqin
 *
 */
public class FulfillTaskActivity extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulfilltask);
        String file = null;
        Bundle extras = getIntent().getExtras();
    	file = extras.getString("file");

        Task oldtask = (Task) getIntent().getSerializableExtra("task");
        
        TextView requirements = (TextView) findViewById(R.id.question_text);
        requirements.setText(oldtask.getDescription());
        
        //Show old answer, if any
        EditText answerBox = (EditText) findViewById(R.id.answer_text);
        answerBox.setText(oldtask.getResAnswer());

        //If image/audio required, make button clickable.
        boolean requestPhotos = oldtask.getReqPhoto();

        Button photoButton = (Button) findViewById(R.id.get_image);
        Button likeButton = (Button) findViewById(R.id.like_button);
        Button doneButton = (Button) findViewById(R.id.taskdone);
        Button saveButton = (Button) findViewById(R.id.save_progress);
        Button addToFavouritesButton = (Button) findViewById(R.id.add_to_favourites);
        Button removeFromFavouritesButton = (Button) findViewById(R.id.remove_from_favourites);
        Button removeTask = (Button) findViewById(R.id.remove_task);
        
        photoButton.setClickable(false);
        likeButton.setClickable(false);
        //Logic to gray-out Button so it's non-selectable
        if (requestPhotos){
        	photoButton.setOnClickListener(this);
        	photoButton.setClickable(true);
        } else {
        	photoButton.setTextColor(getResources().getColor(R.color.White));
        }
        
        if (file.equals("EXTERNAL")){
         	likeButton.setOnClickListener(this);
        	likeButton.setClickable(true);
        } else {
        	likeButton.setTextColor(getResources().getColor(R.color.White));
        }
        
        if (file.equals("EXTERNAL") || file.equals("DRAFTS")) {
        	saveButton.setText("Save Draft");
        }
        
        if (file.equals("LOCAL")) {
        	saveButton.setText("Save Local");
        }
        
        if (file.equals("FAVOURITES")) {
        	saveButton.setText("Save Favourite");
        }
        
        //Buttons to exit activity
        doneButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        addToFavouritesButton.setOnClickListener(this);
        removeFromFavouritesButton.setOnClickListener(this);
        removeTask.setOnClickListener(this);
        
        if (file.equals("FAVOURITES") || LocalTaskManager.existsFavourite(oldtask, this)) {
        	addToFavouritesButton.setVisibility(View.GONE);
        } else {
        	removeFromFavouritesButton.setVisibility(View.GONE);
        }
        
        if (file.equals("FAVOURITES") || file.equals("EXTERNAL")) {
        	removeTask.setVisibility(View.GONE);
        }
    }
    
    public void onClick(View v){
    	Bundle extras = getIntent().getExtras();
    	String file = extras.getString("file");
    	EditText answerBox = (EditText) findViewById(R.id.answer_text);
		String answer = answerBox.getText().toString();
    	
    	Task oldtask = (Task) getIntent().getSerializableExtra("task");
    	ArrayList<Bitmap> photofile = oldtask.getResPhoto();
    	String audiofile = oldtask.getResAudio();
    	
    	Task newtask = oldtask.cloneTask();
    	
    	Intent intent;
    	switch (v.getId()){
    		case R.id.like_button:
    		        String ident = oldtask.getID();
    			ExternalTaskManager.updateTask(oldtask, ident);
    			break;
    		case R.id.get_image:
    			intent = new Intent(this, TakePhotoActivity.class);
    			startActivity(intent);
    			
    			
    			break;
    		case R.id.save_progress:
    			newtask.setResult(answer, photofile, audiofile);
    			
    			if (LocalTaskManager.existsDraft(oldtask, this)) {
    				LocalTaskManager.replaceDraft(oldtask, newtask, this);
    			} else {
    				LocalTaskManager.saveDraft(newtask, this);
    			}
    			
	    		LocalTaskManager.replaceFavourite(oldtask, newtask, this);
	    		LocalTaskManager.replaceLocalTask(oldtask, newtask, this);
    			
    			finish();
    			break;
    		case R.id.add_to_favourites:
    			newtask.setResult(answer, photofile, audiofile);
    			
    			if (file.equals("EXTERNAL")) {
    				//the task was taken from the webservice and needs to be added or updated to drafts
    				if (LocalTaskManager.existsDraft(oldtask, this)) {
    					LocalTaskManager.replaceDraft(oldtask, newtask, this);
    				} else {
    					LocalTaskManager.saveDraft(newtask, this);
    				}
    			} else {
    				//the task existed locally, and needs to be updated everywhere it exists
    				LocalTaskManager.replaceLocalTask(oldtask, newtask, this);
    				LocalTaskManager.replaceDraft(oldtask, newtask, this);
    			}
    			
    			//if it isn't in favourites already, saves it there; else updates it
    			if (LocalTaskManager.existsFavourite(oldtask, this)) {
    				LocalTaskManager.replaceFavourite(oldtask, newtask, this);
    			} else {
    				LocalTaskManager.saveFavourite(newtask, this);
    			}
    			
    			finish();
    			break;
    		case R.id.remove_from_favourites:
    			LocalTaskManager.deleteFavourite(oldtask, this);
    			
    			finish();
    			break;
    		case R.id.remove_task:
    			LocalTaskManager.deleteDraft(oldtask, this);
    			LocalTaskManager.deleteLocalTask(oldtask, this);
    			LocalTaskManager.deleteFavourite(oldtask, this);
    			
    			finish();
    			break;
    		case R.id.taskdone:
    			newtask.setResult(answer, photofile, audiofile);
    			
    			Dialog dialog = new Dialog(this);
    			if (newtask.getResAnswer().length()==0){
    				dialog.setTitle("Missing text");
    				dialog.show();
    			} else if ((newtask.getReqPhoto() && newtask.getResPhoto() == null)) {
    				//No picture taken
    				dialog.setTitle("Missing photo");
    				dialog.show();
    			} else if (newtask.getReqAudio() && newtask.getResAudio().equals("none")){
    				//No audio
    				dialog.setTitle("Missing audio");
    				dialog.show();
    			} else {
    				//task is completed, remove it and send it back to owner
    				
    				dialog.setTitle("Task successfully completed");
    				dialog.show();
	    	    	
	    	    	LocalTaskManager.deleteLocalTask(oldtask, this);
		    		LocalTaskManager.deleteDraft(oldtask, this);
		    		LocalTaskManager.deleteFavourite(oldtask, this);
		    		
		    		Intent emailIntent = Email.SendEmail(newtask);
		    		startActivity(emailIntent);
		    		
		    		finish();
	    	    	if (file.equals("EXTERNAL")) {
	    	    	    String id = oldtask.getID();
	    	    	    ExternalTaskManager.removeTask(id);
	    	    	    finish();
	    	    	} else {
		    	    	LocalTaskManager.deleteLocalTask(oldtask, this);
			    		LocalTaskManager.deleteDraft(oldtask, this);
			    		LocalTaskManager.deleteFavourite(oldtask, this);
			    		
			    		finish();
	    	    	}
    			}
    			break;
    	}
    }
}
