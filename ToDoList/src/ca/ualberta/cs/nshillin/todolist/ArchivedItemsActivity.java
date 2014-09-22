package ca.ualberta.cs.nshillin.todolist;


import ca.ualberta.cs.todolist.R;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class ArchivedItemsActivity extends MainActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_archived_items);
		
		optionsArray[0] = "UnArchive";
		listViewId  = R.id.ArchivedItemList_ListView;
        itemCountViewId = R.id.itemCount_textView;
    	
    	updateList();
    	itemLongClicked();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.archived_items, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.todoList_item) {
			this.finish();
		} 
		return super.onOptionsItemSelected(item);
	}
	
}
