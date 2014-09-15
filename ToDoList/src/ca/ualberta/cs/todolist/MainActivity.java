package ca.ualberta.cs.todolist;


import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    	updateList();
    	itemClicked();
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
    	ArrayAdapter<ToDoItem> adapter = new ToDoAdapter();
    	ListView list = (ListView) findViewById( R.id.ToDoList_ListView);
    	list.setAdapter(adapter);
    }
    
    private class ToDoAdapter extends ArrayAdapter<ToDoItem> {
    	
    	List<ToDoItem> toDoList = ToDoListController.getToDoList().getToDoList();
    	
    	public ToDoAdapter() {
    		super(MainActivity.this, R.layout.list_item, ToDoListController.getToDoList().getToDoList());
    	}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View itemView = convertView;
			if (itemView == null) {
				itemView = getLayoutInflater().inflate(R.layout.list_item, parent, false);
			}
			
			ToDoItem currentItem = toDoList.get(position);
			
			TextView textView = (TextView) itemView.findViewById(R.id.itemName_TextView);
			textView.setText(currentItem.getName());
			
			final CheckBox checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
			checkBox.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					List<ToDoItem> toDoList = ToDoListController.getToDoList().getToDoList();
					ToDoItem currentItem = toDoList.get(position);
					currentItem.changeChecked(checkBox.isChecked()); 
			    	Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_SHORT).show(); 
				}
			});
			checkBox.setChecked(currentItem.isChecked());
			
			return itemView; 
		}
    	
    	
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
    
    private void itemClicked() {

    	ListView list = (ListView) findViewById(R.id.ToDoList_ListView);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {
				// TODO Auto-generated method stub
				CheckBox checkBox = (CheckBox) viewClicked.findViewById(R.id.checkBox);	
				
		    	List<ToDoItem> toDoList = ToDoListController.getToDoList().getToDoList();
				ToDoItem currentItem = toDoList.get(position);
				currentItem.changeChecked(checkBox.isChecked()); 
		    	Toast.makeText(MainActivity.this, "hello"+id, Toast.LENGTH_SHORT).show();  	
			}
			
		});
	}
}
