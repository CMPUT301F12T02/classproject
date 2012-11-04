package ca.ualberta.cmput301project;

import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

public class FulfillTask extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulfilltask);
        printinfo();
        
        //Get task requirements from intent
        Intent intent = getIntent();
        String requestDescription = intent.getStringExtra(ViewLocalTask.REQDESCRIPTION);
        boolean requestPhotos = intent.getBooleanExtra(ViewLocalTask.REQPHOTO, false);
        boolean requestAudio = intent.getBooleanExtra(ViewLocalTask.REQAUDIO, false);
        //display requirements in TextView
        TextView requirements = (TextView) findViewById(R.id.requirements);
        requirements.setText(requestDescription);
        Button photoButton = (Button) findViewById(R.id.get_image);
        photoButton.setClickable(requestPhotos);
        Button audioButton = (Button) findViewById(R.id.get_audio);
        audioButton.setClickable(requestAudio);
      //gray out button if false
        if (!requestPhotos){
        	photoButton.setTextColor(0xF0F0F0);
        }
        if (!requestAudio){
        	audioButton.setTextColor(0xF0F0F0);
        }
    }
    
    public void onClick(View v){
    	Intent intent;
    	switch (v.getId()){
    		case R.id.get_audio:
    			break;
    		case R.id.get_image:
    			break;
    		case R.id.taskdone:
    			//save changes
    			//return to list
    			break;
    		case R.id.save_draft:
    			//save changes
    			//return to list
    			break;
    		default:
    			return;
    	}
    }
    
    public void printinfo()
    {
        TextView textdes;
        textdes =  (TextView)findViewById(R.id.requirements); 
        textdes.setText(MainActivity.locallist.get(MainActivity.list_index).getDescription());
        
        if (MainActivity.locallist.get(MainActivity.list_index).getReqAudio() == true){
            Toast.makeText(getApplicationContext(), "Audio Required", Toast.LENGTH_SHORT).show();
        }
        if (MainActivity.locallist.get(MainActivity.list_index).getReqPhoto() == true){
            Toast.makeText(getApplicationContext(), "Photo Required", Toast.LENGTH_SHORT).show();
        }
    }
}
