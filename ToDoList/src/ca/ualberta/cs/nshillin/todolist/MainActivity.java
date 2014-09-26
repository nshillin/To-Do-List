/*
	An android application to keep todo items in, items can be added, deleted, checked, archived, and emailed.
    Copyright (C) 2014 Noah Shillington

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/


/*
 * This is the activity that does most of the work in the application.
 * The user can add, delete, email, and archive items from this page. 
 * The page also displays the current number of items, checked and unchecked.
 * 
 * addItem is called when the user presses the add button. The text from the textbox is then added as a new item.
 * 
 * itemLongClicked is used to open a dialog with three options, when an item is long clicked.
 * archive, delete, and emailOne are called when the user selects the appropriate button after long clicking on an object.
 * archive deletes the item from the list and adds the same item to the archived list.
 * delete removes the item from the list.
 * emailOne creates an email intent with the selected item.
 * 
 * emailAll is used when Email>Email All or Email>Email To Do is selected from the menu. Depending on what's selected, it either
 * creates an email intent with just the items from the todo list, or creates an intent with the items from the todo list as
 * well as the items from the archived todo list
 * 
 * storeInformation, removeInformation, retrieveInformation, storeCheckInformation, and retrieveCheckInformation are all in charge
 * of storing the user's todo list data. 
 * storeInformation is called when an item is added, and adds the item to SharedPrefs
 * removeInformation is called when archive or delete are called and updates everything in SharedPrefs, accounting for the removed item
 * retrieveInformation is called when the app starts and grabs all of the item names from SharedPrefs
 * storeCheckInformation is called when an item is checked off and updates SharedPrefs with this information
 * retrieveCheckInformation is called when the app starts and adds checkmark information to the todo list
 * 
 * updateCount updates the textbox that displays the number of items, checked, and unchecked and is called when the list updates
 * or when an item is checked.
 * updateList updates the list by sending the todo list into ToDoAdapter.
 * 
 * onResume updates the list with any unarchived objects that may have since been added to the ToDoListController.
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
import android.widget.TextView;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

@SuppressWarnings("static-access")
public class MainActivity extends Activity {
	
	public String[] optionsArray = {"Archive", "Delete", "Email"};
	public int mainListNumber;
	public int oppositeListNumber;	
	public int listViewId;
	public int itemCountViewId;
	public static ToDoListController listController;
	public String spItemName;
	public String spSize;
	public String spCheckItemName;
	public String oppositespItemName;
	public String oppositespSize;
	public String oppositespCheckItemName;

	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mainListNumber = 1;
        oppositeListNumber = 2;
        spItemName = "ToDoItem_";
        spSize = "ToDoSize";
        spCheckItemName = "ToDoCheck_";
        oppositespItemName = "ArchivedToDoItem_";
        oppositespSize = "ArchivedSize";
        oppositespCheckItemName = "ArchivedCheck_";
        if (listController.getToDoList(mainListNumber).size() == 0) {
        	retrieveInformation(mainListNumber, spItemName, spSize);
        	retrieveCheckInformation(mainListNumber, spCheckItemName);
        }
        if (listController.getToDoList(oppositeListNumber).size() == 0) {
        	retrieveInformation(oppositeListNumber, oppositespItemName, oppositespSize);
        	retrieveCheckInformation(oppositeListNumber, oppositespCheckItemName);

        } 
    	
        listViewId  = R.id.ToDoList_ListView;
        itemCountViewId = R.id.itemCount_TextView;
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
        if (id == R.id.archivedItems_Item) {
        	Intent archivedItemsScreen = new Intent(MainActivity.this, ArchivedItemsActivity.class);
        	startActivity(archivedItemsScreen);
        }
        if (id == R.id.help_item) {
        	Intent settingsScreen = new Intent(MainActivity.this, SettingsActivity.class);
        	startActivity(settingsScreen);
        }
        if (id == R.id.emailAll_item) {
        	emailAll(3);
        }
        if (id == R.id.emailTodo_item) {
        	emailAll(mainListNumber);
        }
        if (id == R.id.emailSelection_item) {
        	Intent emailSelectionScreen = new Intent(MainActivity.this, EmailSelectionActivity.class);
        	emailSelectionScreen.putExtra("test", mainListNumber);
        	startActivity(emailSelectionScreen);
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		for (int x=0; x < listController.getToDoList(mainListNumber).size(); x++) {
			listController.getToDoList(mainListNumber).get(x).changeSelected(false);
    	}
		for (int x=0; x < listController.getToDoList(oppositeListNumber).size(); x++) {
			listController.getToDoList(oppositeListNumber).get(x).changeSelected(false);
    	}
    	updateList();
	}
	

	public void addItem(View view) {

    	AutoCompleteTextView textView = (AutoCompleteTextView) findViewById( R.id.addItem_TextView);
    	ToDoItem todoitem = new ToDoItem(textView.getText().toString());
    	textView.setText("");
    	listController.addItem(todoitem, mainListNumber);
    	storeInformation(mainListNumber, spItemName, spSize);
    	updateList();
    }
	
	public void archive(int position) {
    	List<ToDoItem> toDoList = listController.getToDoList(mainListNumber);
		List<ToDoItem> archivedToDoList = listController.getToDoList(oppositeListNumber);
		
    	ToDoItem currentItem = toDoList.get(position);
		archivedToDoList.add(currentItem);
		storeCheckInformation(oppositeListNumber, oppositespCheckItemName);
		storeInformation(oppositeListNumber, oppositespItemName, oppositespSize);
		toDoList.remove(currentItem);
		removeInformation(position, mainListNumber);
    }
    
	public void delete(int position) {
    	List<ToDoItem> toDoList = listController.getToDoList(mainListNumber);
    	
		toDoList.remove(position);
		removeInformation(position, mainListNumber);		
    }
    
    public void emailOne(int position) {

		List<ToDoItem> toDoList = listController.getToDoList(mainListNumber);
    	
    	Intent email = new Intent(Intent.ACTION_SEND);
		email.setType("memessage/rfc822");
		email.putExtra(Intent.EXTRA_SUBJECT, "To Do Item");
		ToDoItem currentItem = toDoList.get(position);
		email.putExtra(Intent.EXTRA_TEXT, currentItem.getName());
		startActivity(Intent.createChooser(email, ""));
    }
    
    public void emailAll(int listNumber) {
    	Intent email = new Intent(Intent.ACTION_SEND);
		email.setType("memessage/rfc822");
		email.putExtra(Intent.EXTRA_SUBJECT, "My To Do List");
		String stringListOfItems = "";
    	if ((listNumber == 1) || (listNumber == 3)) {
    		stringListOfItems += "To Do List:";
    		for (int x = 0; x < listController.getToDoList(1).size(); x++) {
    			stringListOfItems += ("\n" + listController.getToDoList(1).get(x).getName());
    		}
    	}
    	if ((listNumber == 2) || (listNumber == 3)) {
    		if (listNumber == 3) {
    			stringListOfItems += "\n\n";
    		}
    		stringListOfItems += "Archived To Do List:";
    		for (int x = 0; x < listController.getToDoList(2).size(); x++) {
    			stringListOfItems += ("\n" + listController.getToDoList(2).get(x).getName());
    		}
    	}
		email.putExtra(Intent.EXTRA_TEXT, stringListOfItems);
		startActivity(Intent.createChooser(email, ""));
    }
    
    
    public void storeInformation(int listNumber, String itemName, String sizeName) {
    	SharedPreferences settings = this.getSharedPreferences("ToDoList", 0);
    	SharedPreferences.Editor editor = settings.edit();
    	editor.putInt(sizeName, listController.getToDoList(listNumber).size());
    	editor.putString(itemName+(listController.getToDoList(listNumber).size()-1), listController.getToDoList(listNumber).get(listController.getToDoList(listNumber).size()-1).getName());
    	editor.commit();

	}
    
    public void removeInformation(int position, int listNumber) {
		SharedPreferences settings = this.getSharedPreferences("ToDoList", 0);
    	SharedPreferences.Editor editor = settings.edit();
		int size = listController.getToDoList(listNumber).size();
    	editor.putInt(spSize, size);
    	for (int x=0; x<size; x++) {
        	editor.putString(spItemName+x, listController.getToDoList(listNumber).get(x).getName());
    	}
    	editor.commit();
    }
	
	public void retrieveInformation(int listNumber, String itemName, String sizeName) {
		SharedPreferences settings = this.getSharedPreferences("ToDoList", 0);
		int size = settings.getInt(sizeName, 0);
    	for (int x=0; x<size; x++) {
    		listController.addItem(new ToDoItem(settings.getString(itemName+x,"")), listNumber);
    	}
	}
	
	public void storeCheckInformation(int listNumber, String itemName) {
		SharedPreferences settings = this.getSharedPreferences("ToDoList", 0);
    	SharedPreferences.Editor editor = settings.edit();
    	for (int x=0; x<listController.getToDoList(listNumber).size(); x++) {
    		editor.putBoolean(itemName+x, listController.getToDoList(listNumber).get(x).isChecked());
    		editor.commit();
    	}
	}

	public void retrieveCheckInformation(int listNumber, String itemName) {
		
		SharedPreferences settings = this.getSharedPreferences("ToDoList", 0);
    	for (int x=0; x<listController.getToDoList(listNumber).size(); x++) {
    		listController.getToDoList(listNumber).get(x).changeChecked(settings.getBoolean(itemName+x, true));
    	}
	}
    
	
    protected void itemLongClicked() {

    	ListView list = (ListView) findViewById(listViewId);
		list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public  boolean onItemLongClick(AdapterView<?> parent, View viewClicked,
					final int position, long id) {
									
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle("");
				builder.setItems(optionsArray, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (which == 0) { // Archive
							archive(position);
						}
						else if (which == 1) { // Delete
							delete(position);
						}
						else if (which == 2) { //Email one
							emailOne(position);
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
    
    public void updateCount() {
    	storeCheckInformation(mainListNumber, spCheckItemName);
    	TextView itemCount = (TextView) findViewById( itemCountViewId);
    	int checked = 0;
    	for (int x=0; x < listController.getToDoList(mainListNumber).size(); x++) {
    		if (listController.getToDoList(mainListNumber).get(x).isChecked()) {
    			checked++;
    		}
    	}
    	itemCount.setText("Items: "+ listController.getToDoList(mainListNumber).size() + "     Checked: " + checked + "     Unchecked: " + (listController.getToDoList(mainListNumber).size() - checked));
    }
    
    public void updateList() {
    	updateCount();
    	List<ToDoItem> toDoList;
		toDoList = listController.getToDoList(mainListNumber);
    	ArrayAdapter<ToDoItem> adapter = new ToDoAdapter( this, R.layout.list_item,toDoList, mainListNumber);
    	ListView list = (ListView) findViewById( listViewId);
    	list.setAdapter(adapter);
    }
}
