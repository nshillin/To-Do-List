package ca.ualberta.cs.todolist;



import java.util.List;

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
        if (id == R.id.action_settings) {
            return true;
        }
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
				
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				String[] optionsArray = {"Archive", "Delete"};
				builder.setTitle("");
				builder.setItems(optionsArray, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (which == 0) { // Archive
							
						}
						else if (which == 1) { // Delete
							
							List<ToDoItem> toDoList = ToDoListController.getToDoList().getToDoList();
							toDoList.remove(position);
							updateList();
						}
					}
				});
				builder.create();
				builder.show();
				return false;
			}
			
		});
	}
    
    public void updateList() {
    	ArrayAdapter<ToDoItem> adapter = new ToDoAdapter( this, R.layout.list_item, ToDoListController.getToDoList().getToDoList(), 0);
    	ListView list = (ListView) findViewById( R.id.ToDoList_ListView);
    	list.setAdapter(adapter);
    }
}
