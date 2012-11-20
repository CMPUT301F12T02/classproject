package ca.ualberta.frontend;


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
        Button draftButton = (Button) findViewById(R.id.save_draft);
        
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
    }
    
    public void onClick(View v){
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
    		case R.id.save_draft:
    			newtask.setResult(answer, photofile, audiofile);
    			LocalTaskManager.replaceLocalTask(oldtask, newtask, this);
    			
    			finish();
    			break;
    		case R.id.taskdone:
    			//pop up message
    			Dialog dialog = new Dialog(this);
    	    	dialog.setTitle(newtask.getDescription());
    	    	TextView tv = new TextView(this);
    	    	tv.setText(((EditText)findViewById(R.id.answer_text)).getText().toString());
    	    	dialog.setContentView(tv);
    	    	dialog.show();
    	    	
    	    	newtask.setResult(answer, photofile, audiofile);
    	    	boolean complete = taskDone(newtask);
    			saveTask(complete, oldtask, newtask);
    			
    			finish();
    			break;
    	}
    	
    }
    private void saveTask(boolean complete, Task oldTask, Task newTask){
    	if (complete){
    		LocalTaskManager.deleteLocalTask(oldTask, this);
    	}
    }
    private boolean taskDone(Task task){
    	if (task.getReqPhoto() && task.getResPhotoName().equals("none")){
    		return false;
    	}
    	if (task.getReqAudio() && task.getResAudioName().equals("none")){
    		return false;
    	}
    	return true;
    }

}
