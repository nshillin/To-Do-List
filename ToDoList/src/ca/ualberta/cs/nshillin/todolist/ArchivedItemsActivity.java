package ca.ualberta.cs.nshillin.todolist;

import java.util.List;

import ca.ualberta.cs.todolist.R;
import ca.ualberta.cs.todolist.R.id;
import ca.ualberta.cs.todolist.R.layout;
import ca.ualberta.cs.todolist.R.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ArchivedItemsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_archived_items);
		
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
	
	private void itemLongClicked() {

    	ListView list = (ListView) findViewById(R.id.ArchivedItemList_ListView);
		list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public  boolean onItemLongClick(AdapterView<?> parent, View viewClicked,
					final int position, long id) {
									
				AlertDialog.Builder builder = new AlertDialog.Builder(ArchivedItemsActivity.this);
				String[] optionsArray = {"Unarchive", "Delete", "Email"};
				builder.setTitle("");
				builder.setItems(optionsArray, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						List<ToDoItem> archivedToDoList = ToDoListController.getToDoList();
						if (which == 0) { // Unarchive
							ToDoItem currentItem = archivedToDoList.get(position);
							ToDoListController.addItem(currentItem);
							archivedToDoList.remove(currentItem);
						}
						else if (which == 1) { // Delete
							archivedToDoList.remove(position);
						}
						else if (which == 2) { //Email one
							Intent emailIntent = new Intent(Intent.ACTION_SEND);
							emailIntent.putExtra(Intent.EXTRA_SUBJECT, "ToDo Item");
							ToDoItem currentItem = archivedToDoList.get(position);
							emailIntent.putExtra(Intent.EXTRA_TEXT, currentItem.getName());
							startActivity(Intent.createChooser(emailIntent, ""));
						}
						updateList();
					}
				});
				builder.create();
				builder.show();
				return false;
			}
		});
	}
	
	public void updateList() {
		List<ToDoItem> toDoList;
		toDoList = ToDoListController.getToDoList();
    	ArrayAdapter<ToDoItem> adapter = new ToDoAdapter( this, R.layout.list_item, toDoList);
    	ListView list = (ListView) findViewById( R.id.ArchivedItemList_ListView);
    	list.setAdapter(adapter);
    }
}
