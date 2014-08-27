package com.theexceptionulls.projectskullnbones;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;


public class StoreListActivity extends ListActivity {

    private String[] storeList;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);


        storeList = new String[]{"one", "two", "three"};
        arrayAdapter = new ArrayAdapter(this, R.layout.store_list_row, R.id.text1, storeList);

        // Bind to our new adapter.
        setListAdapter(arrayAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.store_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}



/*
 public class MyListAdapter extends ListActivity {

     @Override
     protected void onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);

         // We'll define a custom screen layout here (the one shown above), but
         // typically, you could just use the standard ListActivity layout.
         setContentView(R.layout.custom_list_activity_view);

         // Query for all people contacts using the Contacts.People convenience class.
         // Put a managed wrapper around the retrieved cursor so we don't have to worry about
         // requerying or closing it as the activity changes state.
         mCursor = this.getContentResolver().query(People.CONTENT_URI, null, null, null, null);
         startManagingCursor(mCursor);

         // Now create a new list adapter bound to the cursor.
         // SimpleListAdapter is designed for binding to a Cursor.
         ListAdapter adapter = new SimpleCursorAdapter(
                 this, // Context.
                 android.R.layout.two_line_list_item,  // Specify the row template to use (here, two columns bound to the two retrieved cursor
 rows).
                 mCursor,                                              // Pass in the cursor to bind to.
                 new String[] {People.NAME, People.COMPANY},           // Array of cursor columns to bind to.
                 new int[] {android.R.id.text1, android.R.id.text2});  // Parallel array of which template objects to bind to those columns.

         // Bind to our new adapter.
         setListAdapter(adapter);
     }
 }
 */
