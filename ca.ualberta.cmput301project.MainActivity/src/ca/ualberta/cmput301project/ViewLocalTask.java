package ca.ualberta.cmput301project;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewLocalTask extends ListActivity{
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<Task>(this,android.R.layout.simple_list_item_1,MainActivity.locallist));

    }
    public void onListItemClick(ListView parent,View v, int position,long id){
        MainActivity.list_index = position;
        Intent intent = new Intent (this, FulfillTask.class);
        startActivity(intent);
        finish();
    }
}