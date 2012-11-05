package ca.ualberta.cmput301project;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FulfillTaskActivity extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulfilltask);

        Task oldtask = (Task) getIntent().getSerializableExtra("task");
        
        TextView requirements = (TextView) findViewById(R.id.question_text);
        requirements.setText(oldtask.getDescription());
        
        //DEBUG: answer is saved
        EditText answerBox = (EditText) findViewById(R.id.answer_text);
        answerBox.setText(oldtask.getResAnswer());
        //Note from Gabe: this commented part below was added before a commit I made that just obtains a task
        //from the caller; I commented it so that whoever made this change didn't lose their progress!

        //If image/audio required, make button clickable.
        boolean requestPhotos = oldtask.getReqPhoto();
        boolean requestAudio = oldtask.getReqAudio();

        Button photoButton = (Button) findViewById(R.id.get_image);
        Button audioButton = (Button) findViewById(R.id.get_audio);
        Button doneButton = (Button) findViewById(R.id.taskdone);
        Button draftButton = (Button) findViewById(R.id.save_draft);
        
        //Button audioButton = (Button) findViewById(R.id.get_audio);

        photoButton.setClickable(false);
        //audioButton.setClickable(false);
        //Logic to gray-out Button so it's non-selectable
        if (requestPhotos){
        	photoButton.setOnClickListener(this);
        	photoButton.setClickable(true);
        } else {
        	photoButton.setTextColor(getResources().getColor(R.color.White));
        }
        
        if (requestAudio){
        //	audioButton.setOnClickListener(this);
        //	audioButton.setClickable(true);
        } else {
        	audioButton.setTextColor(getResources().getColor(R.color.White));
        }
        //Buttons to exit activity
        doneButton.setOnClickListener(this);
        draftButton.setOnClickListener(this);
    }
    
    public void onClick(View v){
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
    		case R.id.taskdone:
    			EditText answerBox = (EditText) findViewById(R.id.answer_text);
    			String answer = answerBox.getText().toString();

    			newtask.setComplete(true);
    			newtask.setResult(answer, photofile, audiofile);
    			LocalTaskManager.replaceLocalTask(oldtask, newtask, this);
    			finish();
    		case R.id.save_draft:
    			finish();
    	}
    	
    }
}
