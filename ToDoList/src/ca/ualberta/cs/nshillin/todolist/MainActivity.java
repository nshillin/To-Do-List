/*

	To Do List: Makes a list of added items. Items can be checked, unchecked, archived, emailed, or deleted.
    Copyright (C) 2014  Noah Shillington nshillin@ualberta.ca

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    
*/
package ca.ualberta.cs.nshillin.todolist;


import java.util.List;

import ca.ualberta.cs.todolist.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;
import android.content.DialogInterface;
import android.content.Intent;

public class MainActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    	updateList();
    	itemLongClicked();
    }


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
  /*      if (id == R.id.action_settings) {
            return true;
        } */
        return super.onOptionsItemSelected(item);
    }


	public void archivedItems(MenuItem menu) {
    	Toast.makeText(this, "Archived Items", Toast.LENGTH_SHORT).show();
    	
    	Intent archivedItemsScreen = new Intent(MainActivity.this, ArchivedItemsActivity.class);
    	startActivity(archivedItemsScreen);
    	
    }
    
    public void addItem(View view) {
    	Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();    	
    	ToDoListController todolistcontroller = new ToDoListController();
    	AutoCompleteTextView textView = (AutoCompleteTextView) findViewById( R.id.addItem_TextView);
    	ToDoItem todoitem = new ToDoItem(textView.getText().toString());
    	textView.setText("");
    	todolistcontroller.addItem(todoitem);
    	updateList();
    }
    
    private void itemLongClicked() {

    	ListView list = (ListView) findViewById(R.id.ToDoList_ListView);
		list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public  boolean onItemLongClick(AdapterView<?> parent, View viewClicked,
					final int position, long id) {
				
				if (position != ToDoListController.getToDoList().size()-1) {
					
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				String[] optionsArray = {"Archive", "Delete", "Email"};
				builder.setTitle("");
				builder.setItems(optionsArray, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						List<ToDoItem> toDoList = ToDoListController.getToDoList();
					//	List<ToDoItem> archivedToDoList = ToDoListController.getArchivedToDoList().getToDoList();
						if (which == 0) { // Archive
							ToDoItem currentItem = toDoList.get(position);
					//		archivedToDoList.add(currentItem);
							toDoList.remove(currentItem);
							ToDoItem finalItem = ToDoListController.getToDoList().get(ToDoListController.getToDoList().size()-1);
							ToDoListController.removeItem(finalItem);
							ToDoItem todocount = new ToDoItem(ToDoListController.getToDoList().size() + " items");
							ToDoListController.addItem(todocount);
						}
						else if (which == 1) { // Delete
							toDoList.remove(position);
							ToDoItem currentItem = ToDoListController.getToDoList().get(ToDoListController.getToDoList().size()-1);
							ToDoListController.removeItem(currentItem);
							ToDoItem todocount = new ToDoItem("Number of items: " + ToDoListController.getToDoList().size());
							ToDoListController.addItem(todocount);
						}
						else if (which == 2) { //Email one
							Intent emailIntent = new Intent(Intent.ACTION_SEND);
							emailIntent.putExtra(Intent.EXTRA_SUBJECT, "ToDo Item");
							ToDoItem currentItem = toDoList.get(position);
							emailIntent.putExtra(Intent.EXTRA_TEXT, currentItem.getName());
							startActivity(Intent.createChooser(emailIntent, ""));
						}
						updateList();
					}
				});
				builder.create();
				builder.show();
				}
				return false;
			}
		});
	}
    
    public void updateList() {
    	List<ToDoItem> toDoList;
		toDoList = ToDoListController.getToDoList();
    	ArrayAdapter<ToDoItem> adapter = new ToDoAdapter( this, R.layout.list_item, toDoList);
    	ListView list = (ListView) findViewById( R.id.ToDoList_ListView);
    	list.setAdapter(adapter);
    }
}
