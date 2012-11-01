package ca.ualberta.cmput301project;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.app.Activity;
//import android.content.Intent;

public class StoredTasks extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        LinearLayout llayout = new LinearLayout(this);
        llayout.setOrientation(LinearLayout.VERTICAL);
        setContentView(llayout);
        
        //Intent intent = getIntent();
    }

}
