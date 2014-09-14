package ca.ualberta.cs.todolist;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    	todolistcontroller.addItem(todoitem);
    	
 //   	ArrayAdapter<ToDoItem> adapter = new ArrayAdapter<ToDoItem>(this, R.layout.list_item, );
    //	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, todolistcontroller.getToDoList());
    	
    	ArrayList<ToDoItem> toDoList = ToDoListController.getToDoList().toDoList;
    //	String[] toDoArray = {"red", "blue"};
    	String[] toDoArray = new String[toDoList.size()];
		for (int i = 0; i < toDoList.size(); i++) {
			ToDoItem x = toDoList.get(i);
			toDoArray[i] = x.getName();
		} 
    //	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, todolistcontroller.getToDoList().toDoList);
    //	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, toDoArray);
		ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(this, android.R.layout.simple_list_item_1, toDoArray);
    	ListView list = (ListView) findViewById( R.id.ToDoList_ListView);
    	list.setAdapter(adapter);
    }
}
