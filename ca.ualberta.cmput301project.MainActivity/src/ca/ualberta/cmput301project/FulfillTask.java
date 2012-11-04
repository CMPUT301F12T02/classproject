package ca.ualberta.cmput301project;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

public class FulfillTask extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulfilltask);
        printinfo();
        
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
