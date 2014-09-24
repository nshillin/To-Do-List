package ca.ualberta.cs.nshillin.todolist;

import java.util.List;

import ca.ualberta.cs.todolist.R;
import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

@SuppressWarnings("static-access")
public class EmailSelectionActivity extends Activity {
	
	public int listNumber = 1;
	public ToDoListController listController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_email_selection);
		
		updateList();
	}

	  protected void itemLongClicked() {

	    	ListView list = (ListView) findViewById(R.id.selection_listView);
			list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					updateList();
				}
			});
	  }
	
	public void updateCount() {
    	TextView itemCount = (TextView) findViewById( R.id.emailcount_textView);
    	itemCount.setText("Items Selected: ");
    }
    
    public void updateList() {
    	updateCount();
    	List<ToDoItem> toDoList;
		toDoList = listController.getToDoList(listNumber);
    	ArrayAdapter<ToDoItem> adapter = new ToDoAdapter( this, R.layout.list_item,toDoList, listNumber);
    	ListView list = (ListView) findViewById( R.id.selection_listView);
    	list.setAdapter(adapter);
    }
}
