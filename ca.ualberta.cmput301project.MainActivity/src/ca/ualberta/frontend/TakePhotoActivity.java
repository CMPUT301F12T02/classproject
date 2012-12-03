package ca.ualberta.frontend;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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

	public void takePhoto(){   	
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		startActivityForResult(intent, 1888);
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){


		TextView tv = (TextView) findViewById(R.id.camera_status);
		switch (resultCode){
		case RESULT_OK:
			ImageButton button = (ImageButton) findViewById(R.id.TakeAPhoto);
			Bitmap photo = (Bitmap) data.getExtras().get("data");
			button.setImageBitmap(photo);
			tv.setText("Took photo.");
			Intent returnIntent = new Intent();
			returnIntent.putExtra("newphoto", photo);
			setResult(Activity.RESULT_OK,returnIntent);

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
		// TODO Auto-generated method stub
	switch (v.getId()){
		case R.id.finishButton:
			finish();
			break;
		
	}
}
}