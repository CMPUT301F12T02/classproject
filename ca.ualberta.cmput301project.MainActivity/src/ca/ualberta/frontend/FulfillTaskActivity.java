package ca.ualberta.frontend;

import ca.ualberta.backend.Email;
import ca.ualberta.backend.ExternalTaskManager;
import ca.ualberta.backend.LocalTaskManager;
import ca.ualberta.backend.Task;
import ca.ualberta.frontend.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
        boolean requestAudio = oldtask.getReqAudio();

        Button photoButton = (Button) findViewById(R.id.get_image);
        Button audioButton = (Button) findViewById(R.id.get_audio);
        Button doneButton = (Button) findViewById(R.id.taskdone);
        Button draftButton = (Button) findViewById(R.id.save_progress);
        Button addToFavouritesButton = (Button) findViewById(R.id.add_to_favourites);
        Button removeFromFavouritesButton = (Button) findViewById(R.id.remove_from_favourites);
        
        photoButton.setClickable(false);
        audioButton.setClickable(false);
        //Logic to gray-out Button so it's non-selectable
        if (requestPhotos){
        	photoButton.setOnClickListener(this);
        	photoButton.setClickable(true);
        } else {
        	photoButton.setTextColor(getResources().getColor(R.color.White));
        }
        
        if (requestAudio){
         	audioButton.setOnClickListener(this);
        	audioButton.setClickable(true);
        } else {
        	audioButton.setTextColor(getResources().getColor(R.color.White));
        }
        //Buttons to exit activity
        doneButton.setOnClickListener(this);
        draftButton.setOnClickListener(this);
        addToFavouritesButton.setOnClickListener(this);
        removeFromFavouritesButton.setOnClickListener(this);
        
        if (file.equals("FAVOURITES") || LocalTaskManager.existsFavourite(oldtask, this)) {
        	addToFavouritesButton.setVisibility(View.INVISIBLE);
        } else {
        	removeFromFavouritesButton.setVisibility(View.INVISIBLE);
        }
    }
    
    public void onClick(View v){
    	Bundle extras = getIntent().getExtras();
    	String file = extras.getString("file");
    	EditText answerBox = (EditText) findViewById(R.id.answer_text);
		String answer = answerBox.getText().toString();
    	
    	Task oldtask = (Task) getIntent().getSerializableExtra("task");
    	String photofile = oldtask.getResPhotoName();
    	String audiofile = oldtask.getResAudioName();
    	
    	Task newtask = oldtask.cloneTask();
    	
    	Intent intent;
    	switch (v.getId()){
    		case R.id.get_audio:
    			intent = new Intent();
    			startActivity(intent);
    			break;
    		case R.id.get_image:
    			intent = new Intent(this, TakePhotoActivity.class);
    			startActivity(intent);
    			break;
    		case R.id.save_progress:
    			newtask.setResult(answer, photofile, audiofile);
    			
    			if (file.equals("EXTERNAL")) {
	    			//the task was taken from the webservice and needs to be saved in drafts
    				LocalTaskManager.saveDraft(newtask, this);
    			} else {
    				//the task will be updated in all instances it exists in
	    			LocalTaskManager.replaceFavourite(oldtask, newtask, this);
	    			LocalTaskManager.replaceLocalTask(oldtask, newtask, this);
	    			LocalTaskManager.replaceDraft(oldtask, newtask, this);
    			}
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
    		case R.id.taskdone:
    			newtask.setResult(answer, photofile, audiofile);
    			
    			Dialog dialog = new Dialog(this);
    			
    			if ((newtask.getReqPhoto() && newtask.getResPhotoName().equals("none"))||(newtask.getReqAudio() && newtask.getResAudioName().equals("none"))) {
    				//task is not completed
    				
    				dialog.setTitle("Task not completed");
    				dialog.show();
    			} else {
    				//task is completed, remove it and send it
    				
    				dialog.setTitle("Task successfully completed");
    				dialog.show();
	    	    	
	    	    	LocalTaskManager.deleteLocalTask(oldtask, this);
		    		LocalTaskManager.deleteDraft(oldtask, this);
		    		LocalTaskManager.deleteFavourite(oldtask, this);
		    		
		    		Intent emailIntent = Email.CreateEmailIntent(newtask);
		    		emailIntent = Intent.createChooser(emailIntent, "Choose email service");
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
