package ca.ualberta.cs.nshillin.todolist;



import java.util.List;

import ca.ualberta.cs.todolist.R;
import ca.ualberta.cs.todolist.R.id;
import ca.ualberta.cs.todolist.R.layout;
import ca.ualberta.cs.todolist.R.menu;

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
	
	public String[] optionsArray = {"Archive", "Delete", "Email"};
	
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
        if (id == R.id.archivedItems_Item) {
        	Intent archivedItemsScreen = new Intent(MainActivity.this, ArchivedItemsActivity.class);
        	startActivity(archivedItemsScreen);
        } 
        return super.onOptionsItemSelected(item);
    }
    
    
    public void addItem(View view) {
    //	Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();    	
    	AutoCompleteTextView textView = (AutoCompleteTextView) findViewById( R.id.addItem_TextView);
    	ToDoItem todoitem = new ToDoItem(textView.getText().toString());
    	textView.setText("");
    	ToDoListController.addItem(todoitem);
    	updateList();
    }
    
    private void itemLongClicked() {

    	ListView list = (ListView) findViewById(R.id.ToDoList_ListView);
		list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public  boolean onItemLongClick(AdapterView<?> parent, View viewClicked,
					final int position, long id) {
				
				if (position != ToDoListController.getToDoList().getToDoList().size()-1) {
					
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle("");
				builder.setItems(optionsArray, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (which == 0) { // Archive
							archive();
						}
						else if (which == 1) { // Delete
							delete();
						}
						else if (which == 2) { //Email one
							emailOne();
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
    
    public static void archive() {
    	List<ToDoItem> toDoList = ToDoListController.getToDoList().getToDoList();
		List<ToDoItem> archivedToDoList = ArchivedListController.getToDoList().getToDoList();
		
    	ToDoItem currentItem = toDoList.get(1);
		archivedToDoList.add(currentItem);
		toDoList.remove(currentItem);
		ToDoItem finalItem = ToDoListController.getToDoList().getToDoList().get(ToDoListController.getToDoList().getToDoList().size()-1);
		ToDoListController.getToDoList().removeItem(finalItem);
		ToDoItem todocount = new ToDoItem("Number of items: " + ToDoListController.getToDoList().getToDoList().size());
		ToDoListController.getToDoList().addItem(todocount);
    }
    
    public static void delete() {
    	List<ToDoItem> toDoList = ToDoListController.getToDoList().getToDoList();
    	
		toDoList.remove(1);
		ToDoItem currentItem = ToDoListController.getToDoList().getToDoList().get(ToDoListController.getToDoList().getToDoList().size()-1);
		ToDoListController.getToDoList().removeItem(currentItem);
		ToDoItem todocount = new ToDoItem("Number of items: " + ToDoListController.getToDoList().getToDoList().size());
		ToDoListController.getToDoList().addItem(todocount);
    }
    
    public static void emailOne() {
    	List<ToDoItem> toDoList = ToDoListController.getToDoList().getToDoList();
    	
    	Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.setType("memessage/rfc822");
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "ToDo Item");
		ToDoItem currentItem = toDoList.get(1);
		emailIntent.putExtra(Intent.EXTRA_TEXT, currentItem.getName());
	//	startActivity(Intent.createChooser(emailIntent, ""));
    }
    
    
    public void updateList() {
    	ToDoList toDoList = new ToDoList();
		toDoList = ToDoListController.getToDoList();
    	ArrayAdapter<ToDoItem> adapter = new ToDoAdapter( this, R.layout.list_item, toDoList.getToDoList());
    	ListView list = (ListView) findViewById( R.id.ToDoList_ListView);
    	list.setAdapter(adapter);
    }
}
