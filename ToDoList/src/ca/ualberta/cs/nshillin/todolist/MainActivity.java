package ca.ualberta.cs.nshillin.todolist;



import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.ualberta.cs.todolist.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
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
	public ToDoListController listController;
	public String spName;
	public String oppositespName;
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mainListNumber = 1;
        oppositeListNumber = 2;
        spName = "ToDoItems";
        oppositespName = "ArchivedToDoItems";
        if (listController.getToDoList(mainListNumber).size() == 0) {
        	retrieveInformation();
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
        return super.onOptionsItemSelected(item);
    }

    
    

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
    	updateList();
	}
	
	
	
	public void addItem(View view) {
    	AutoCompleteTextView textView = (AutoCompleteTextView) findViewById( R.id.addItem_TextView);
    	ToDoItem todoitem = new ToDoItem(textView.getText().toString());
    	textView.setText("");
    	listController.addItem(todoitem, mainListNumber);
    	storeInformation(mainListNumber, spName);
    	updateList();
    }
    
    public void storeInformation(int listNumber, String spName) {
		SharedPreferences settings = this.getSharedPreferences(spName, 0);
    	SharedPreferences.Editor editor = settings.edit();
    	Set<String> itemNames = new HashSet<String>(settings.getStringSet("itemNames", new HashSet<String>()));
    	ToDoItem todoitem = listController.getToDoList(listNumber).get(listController.getToDoList(listNumber).size()-1);
    	itemNames.add(todoitem.getName());
    	editor.putStringSet("itemNames", itemNames);
    	editor.commit();
    }
    
    public void removeInformation(int position) {
		SharedPreferences settings = this.getSharedPreferences(spName, 0);
    	SharedPreferences.Editor editor = settings.edit();
    	Set<String> itemNames = new HashSet<String>(settings.getStringSet("itemNames", new HashSet<String>()));
    	ToDoItem todoitem = listController.getToDoList(mainListNumber).get(position);
    	itemNames.remove(todoitem.getName());
    	editor.putStringSet("itemNames", itemNames);
    	editor.commit();
    }
	
	public void retrieveInformation() {
		SharedPreferences settings = this.getSharedPreferences(spName, 0);
    	Set<String> itemNames = settings.getStringSet("itemNames", new HashSet<String>());
    	String[] itemNamesArray = itemNames.toArray(new String[itemNames.size()]);
    	for (int x=itemNames.size()-1; x>-1; x--) {
        	ToDoItem todoitem = new ToDoItem(itemNamesArray[x].toString());
        	listController.addItem(todoitem, mainListNumber);
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
    
    public void archive(int position) {
    	List<ToDoItem> toDoList = listController.getToDoList(mainListNumber);
		List<ToDoItem> archivedToDoList = listController.getToDoList(oppositeListNumber);
		
    	ToDoItem currentItem = toDoList.get(position);
		archivedToDoList.add(currentItem);
		storeInformation(oppositeListNumber, oppositespName);
		removeInformation(position);
		toDoList.remove(currentItem);
    }
    
	public void delete(int position) {
    	List<ToDoItem> toDoList = listController.getToDoList(mainListNumber);
    	
		removeInformation(position);
		toDoList.remove(position);
		
    }
    
    public void emailOne(int position) {
		List<ToDoItem> toDoList = listController.getToDoList(mainListNumber);
    	
    	Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.setType("memessage/rfc822");
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "To Do Item");
		ToDoItem currentItem = toDoList.get(position);
		emailIntent.putExtra(Intent.EXTRA_TEXT, currentItem.getName());
		startActivity(Intent.createChooser(emailIntent, ""));
    }
    
    public void updateCount() {
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
