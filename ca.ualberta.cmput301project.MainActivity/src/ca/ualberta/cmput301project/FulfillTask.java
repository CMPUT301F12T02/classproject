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
        //display requirements in TextView
        printinfo();
        
        //Get task requirements from intent
        Intent intent = getIntent();
        boolean requestPhotos = intent.getBooleanExtra(ViewLocalTask.REQPHOTO, false);
        boolean requestAudio = intent.getBooleanExtra(ViewLocalTask.REQAUDIO, false);

        Button photoButton = (Button) findViewById(R.id.get_image);
        Button audioButton = (Button) findViewById(R.id.get_audio);
        photoButton.setClickable(false);
        audioButton.setClickable(false);
        
        if (requestPhotos){
        	photoButton.setOnClickListener(this);
        } else {
        	photoButton.setTextColor(0xF7F7F7);
        }
        if (requestAudio){
        	audioButton.setOnClickListener(this);
        } else {
        	audioButton.setTextColor(0xF7F7F7);
        }
    }
    
    public void onClick(View v){
    	Intent intent;
    	switch (v.getId()){
    		case R.id.get_audio:
    			intent = new Intent();
    			break;
    		case R.id.get_image:
    			intent = new Intent(this, TakePhoto.class);
    			break;
    		case R.id.taskdone:
    			//save changes
    			//return to list
    			intent = new Intent();
    			break;
    		case R.id.save_draft:
    			//save changes
    			//return to list
    			intent = new Intent();
    			break;
    		default:
    			intent = new Intent();
    			break;
    	}
    	startActivity(intent);
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
