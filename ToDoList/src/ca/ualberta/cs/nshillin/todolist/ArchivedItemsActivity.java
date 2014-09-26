//     Copyright (C) 2014 Noah Shillington
//	   Full notice in MainActivity.java

/*
 * This Activity is a slightly modified version of MainActivity.
 * It displays all of the archived items, and allows the user to check, delete, UnArchive, and email items.
 * All of the differences between this and the main class are implemented in the three methods below.
 * onCreate does most of the implementing; it replaces the regular todolist with the archived todolist and also
 * changes anything that needs to be reversed, such as archive becomes unarchive and so on.
 * The two other methods switch the menu to fit with the archived items. The menu item for opening up the archived to do list
 * is now to open up the regular to do list and so on. 
 * 
 */


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
		
		mainListNumber = 2;
        oppositeListNumber = 1;
       
        oppositespItemName = "ToDoItem_";
        oppositespSize = "ToDoSize";
        oppositespCheckItemName = "ToDoCheck_";
        spItemName = "ArchivedToDoItem_";
        spSize = "ArchivedSize";
        spCheckItemName = "ArchivedCheck_";
        
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
		
		if (id == R.id.emailArchived_item) {
        	emailAll(mainListNumber);
        }
		
		return super.onOptionsItemSelected(item);
	}
	
}
