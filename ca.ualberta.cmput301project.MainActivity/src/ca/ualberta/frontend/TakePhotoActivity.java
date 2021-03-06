package ca.ualberta.frontend;



import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import ca.ualberta.backend.LocalTaskManager;
import ca.ualberta.backend.Task;

/**TakePhotoActivity is responsible for allowing the user to take
 * and save photos
 * @author fessehay
 *
 */
public class TakePhotoActivity extends Activity implements OnClickListener {

	Uri imageFileUri;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_takephoto);

		ImageButton ibutton = (ImageButton) findViewById(R.id.TakeAPhoto);
		Button fButton = (Button) findViewById(R.id.finishButton);
		OnClickListener listen = new OnClickListener(){
			public void onClick(View v) {
				takePhoto();
			}
		};
		
		fButton.setOnClickListener(this);
		ibutton.setOnClickListener(listen);
	}
	/**takePhoto will capture a photo and start the activity
	 * meant to save the photo
	 */
	public void takePhoto(){   	
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, 1888);
	}
	/**getImageUri is meant to convert a Bitmap image
	 * into Uri format, which can be converted to a string
	 * to make it serializable
	 * @param inImage
	 * @param inContext
	 * @return
	 */
	public Uri getImageUri(Bitmap inImage, Context inContext) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
            return Uri.parse(path);
        }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		Task oldTask = (Task) getIntent().getSerializableExtra("task");
		Task newTask = oldTask.cloneTask();
		TextView tv = (TextView) findViewById(R.id.camera_status);
		switch (resultCode){
		case RESULT_OK:
			ImageButton button = (ImageButton) findViewById(R.id.TakeAPhoto);
			Bitmap photo = (Bitmap) data.getExtras().get("data");
			imageFileUri = getImageUri(photo, this);
			newTask.addUri(imageFileUri);
			LocalTaskManager.replaceDraft(oldTask, newTask, this);
			LocalTaskManager.replaceLocalTask(oldTask, newTask, this);
			LocalTaskManager.replaceFavourite(oldTask, newTask, this);
			
			button.setImageBitmap(photo);
			tv.setText("Took photo.");

			break;
		case RESULT_CANCELED:
			tv.setText("Cancelled photo.");
			break;
		default:
			tv.setText("Not sure what happened: " + resultCode);
			break;

		}

	}

	public void onClick(View v) {
	switch (v.getId()){
		case R.id.finishButton:
			finish();
			break;
		
	}
}

	/** 
	 * @uml.property name="fulfillTaskActivity"
	 * @uml.associationEnd inverse="takePhotoActivity:ca.ualberta.frontend.FulfillTaskActivity"
	 */
	private FulfillTaskActivity fulfillTaskActivity;

	/** 
	 * Getter of the property <tt>fulfillTaskActivity</tt>
	 * @return  Returns the fulfillTaskActivity.
	 * @uml.property  name="fulfillTaskActivity"
	 */
	public FulfillTaskActivity getFulfillTaskActivity() {
		return fulfillTaskActivity;
	}
	/** 
	 * Setter of the property <tt>fulfillTaskActivity</tt>
	 * @param fulfillTaskActivity  The fulfillTaskActivity to set.
	 * @uml.property  name="fulfillTaskActivity"
	 */
	public void setFulfillTaskActivity(FulfillTaskActivity fulfillTaskActivity) {
		this.fulfillTaskActivity = fulfillTaskActivity;
	}

	/**
	 * @uml.property  name="localTaskManager"
	 * @uml.associationEnd  inverse="takePhotoActivity:ca.ualberta.backend.LocalTaskManager"
	 */
	private LocalTaskManager localTaskManager;

	/**
	 * Getter of the property <tt>localTaskManager</tt>
	 * @return  Returns the localTaskManager.
	 * @uml.property  name="localTaskManager"
	 */
	public LocalTaskManager getLocalTaskManager() {
		return localTaskManager;
	}
	/**
	 * Setter of the property <tt>localTaskManager</tt>
	 * @param localTaskManager  The localTaskManager to set.
	 * @uml.property  name="localTaskManager"
	 */
	public void setLocalTaskManager(LocalTaskManager localTaskManager) {
		this.localTaskManager = localTaskManager;
	}
}