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
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;
import android.content.Intent;

public class MainActivity extends Activity {
	
	public String[] optionsArray = {"Archive", "Delete", "Email"};
	public ToDoListController listController;
	public ArchivedListController oppositeListController; //in this case archived
	
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
    	AutoCompleteTextView textView = (AutoCompleteTextView) findViewById( R.id.addItem_TextView);
    	ToDoItem todoitem = new ToDoItem(textView.getText().toString());
    	textView.setText("");
    	listController.addItem(todoitem);
    	updateList();
    }
    
    private void itemLongClicked() {

    	ListView list = (ListView) findViewById(R.id.ToDoList_ListView);
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
    	List<ToDoItem> toDoList = listController.getToDoList();
		List<ToDoItem> archivedToDoList = ArchivedListController.getToDoList();
		
    	ToDoItem currentItem = toDoList.get(position);
		archivedToDoList.add(currentItem);
		toDoList.remove(currentItem);
    }
    
    public void delete(int position) {
    	List<ToDoItem> toDoList = listController.getToDoList();
    	
		toDoList.remove(position);
		ToDoItem currentItem = listController.getToDoList().get(listController.getToDoList().size()-1);
		listController.removeItem(currentItem);
		
    }
    
    public void emailOne(int position) {
    	List<ToDoItem> toDoList = listController.getToDoList();
    	
    	Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.setType("memessage/rfc822");
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "ToDo Item");
		ToDoItem currentItem = toDoList.get(position);
		emailIntent.putExtra(Intent.EXTRA_TEXT, currentItem.getName());
//		startActivity(Intent.createChooser(emailIntent, ""));
    }
    
    public void updateCount() {
    	TextView itemCount = (TextView) findViewById( R.id.itemCount_TextView);
    	int checked = 0;
    	for (int x=0; x < listController.getToDoList().size(); x++) {
    		if (listController.getToDoList().get(x).isChecked()) {
    			checked++;
    		}
    	}
    	itemCount.setText("Items: "+ listController.getToDoList().size() + "     Checked: " + checked + "     Unchecked: " + (listController.getToDoList().size() - checked));
    }
    
    
    public void updateList() {
    	updateCount();
    	List<ToDoItem> toDoList;
		toDoList = listController.getToDoList();
    	ArrayAdapter<ToDoItem> adapter = new ToDoAdapter( this, R.layout.list_item, toDoList);
    	ListView list = (ListView) findViewById( R.id.ToDoList_ListView);
    	list.setAdapter(adapter);
    }
}
