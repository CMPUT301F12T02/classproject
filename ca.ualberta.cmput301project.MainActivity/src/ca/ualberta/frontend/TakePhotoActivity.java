package ca.ualberta.frontend;

import java.io.File;

import ca.ualberta.frontend.R;

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

public class TakePhotoActivity extends Activity {

	Uri imageFileUri;
	private static final int CAPTURE_ACTIVITY_REQUEST_CODE = 100;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takephoto);
        
        ImageButton button = (ImageButton) findViewById(R.id.TakeAPhoto);
        OnClickListener listener = new OnClickListener(){
        	public void onClick(View v){
        		takePhoto();
        	}
        };
        button.setOnClickListener(listener);
    }
    
    public void takePhoto(){   	
    	String folder = Environment.getExternalStorageDirectory().getAbsolutePath() ;
    	File folderF = new File(folder);
    	if (!folderF.exists()){
    		folderF.mkdir();
    	}
    	String imageFilePath = folder + "/" + String.valueOf(System.currentTimeMillis()) + ".jpg";
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
				tv.setText("Took photo.");
				break;
			case RESULT_CANCELED:
				tv.setText("Cancelled photo.");
				break;
			default:
				tv.setText("Not sure what happened: " + resultCode);
				break;
<<<<<<< HEAD
=======
		}

    	//if (requestCode == CAPTURE_ACTIVITY_REQUEST_CODE){
    	//	TextView tv = (TextView) findViewById(R.id.camera_status);
    		switch (resultCode){
    			case RESULT_OK:
    				ImageButton button = (ImageButton) findViewById(R.id.TakeAPhoto);
    				button.setImageDrawable(Drawable.createFromPath(imageFileUri.getPath()));
    				tv.setText("Took photo.");
    				break;
    			case RESULT_CANCELED:
    				tv.setText("Cancelled photo.");
    				break;
    			default:
    				tv.setText("Not sure what happened: " + resultCode);
    				break;
    		//}
>>>>>>> 763037e6ea5009597b075bda454782345a47234f
    	}

    }
}
