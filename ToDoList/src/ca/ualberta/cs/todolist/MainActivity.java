package ca.ualberta.cs.todolist;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends Activity {
//	private List<ToDoItem> toDoList= new ArrayList<ToDoItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
  //      toDoList.add(new ToDoItem("test"));
    	updateList();
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
    
    public void updateList() {
   /* 	ArrayList<ToDoItem> toDoList = ToDoListController.getToDoList().toDoList;
    	String[] toDoArray = new String[toDoList.size()];
		for (int i = 0; i < toDoList.size(); i++) {
			ToDoItem x = toDoList.get(i);
			toDoArray[i] = x.getName();
		} 
		ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(this, android.R.layout.select_dialog_singlechoice, toDoArray);
    	ListView list = (ListView) findViewById( R.id.ToDoList_ListView);
    	list.setAdapter(adapter); */
    	
    	ArrayAdapter<ToDoItem> adapter = new ToDoAdapter();
    	ListView list = (ListView) findViewById( R.id.ToDoList_ListView);
    	list.setAdapter(adapter);
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
    	updateList();
    }
  /*  
    private void itemClicked() {
    	ListView list = (ListView) findViewById( R.id.ToDoList_ListView);
		list.setOnItemClickListener(listener)
	}
*/

    
    private class ToDoAdapter extends ArrayAdapter<ToDoItem> {
    	
    	public ToDoAdapter() {
    		super(MainActivity.this, R.layout.list_item, ToDoListController.getToDoList().getToDoList());
    	}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView = convertView;
			if (itemView == null) {
				itemView = getLayoutInflater().inflate(R.layout.list_item, parent, false);
			}
			
		//	ToDoItem currentItem = toDoList.get(position);
			
			return itemView; 
		}
    	
    	

    }
}
