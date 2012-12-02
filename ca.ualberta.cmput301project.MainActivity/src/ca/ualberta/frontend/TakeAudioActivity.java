package ca.ualberta.frontend;

import java.io.File;

import ca.ualberta.frontend.R;

import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;


public class TakeAudioActivity extends Activity {

	Uri imageFileUri;
	private static final int CAPTURE_ACTIVITY_REQUEST_CODE = 100;
	private static MediaRecorder recorder;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takeaudio);
        
        ImageButton button = (ImageButton) findViewById(R.id.TakeAAudio);
        OnClickListener listener = new OnClickListener(){
        	public void onClick(View v){
        		takeAudio();
        	}
        };
        button.setOnClickListener(listener);
    }
    
    public void takeAudio(){   	
    	String folder2 = Environment.getExternalStorageDirectory().getAbsolutePath() ;
    	File folderF = new File(folder2);
    	if (!folderF.exists()){
    		folderF.mkdir();
    	}
    	String imageFilePath = folder2 + "/" + String.valueOf(System.currentTimeMillis()) + ".3gpp";
    	File imageFile = new File(imageFilePath);
    	
    	imageFileUri = Uri.fromFile(imageFile);
    	
    	
    	
    	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    	intent.putExtra(MediaStore.EXTRA_OUTPUT,(Uri) imageFileUri);
    	startActivityForResult(intent, CAPTURE_ACTIVITY_REQUEST_CODE);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	
    		TextView tv = (TextView) findViewById(R.id.camera_status);
    		switch (resultCode){
    			case RESULT_OK:
    				ImageButton button = (ImageButton) findViewById(R.id.TakeAPhoto);
    				button.setImageDrawable(Drawable.createFromPath(imageFileUri.getPath()));
    				tv.setText("Took Audio.");
    				
    				break;
    			case RESULT_CANCELED:
    				tv.setText("Cancelled Audio.");
    				break;
    			default:
    				tv.setText("Not sure what happened: " + resultCode);
    				break;
    		
    	}
    }
}
