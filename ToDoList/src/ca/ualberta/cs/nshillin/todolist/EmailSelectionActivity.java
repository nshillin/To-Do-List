package ca.ualberta.cs.nshillin.todolist;

import java.util.List;

import ca.ualberta.cs.todolist.R;
import android.app.Activity;
import android.content.Intent;
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
		
		Bundle extras = getIntent().getExtras();
		int listNumberString = extras.getInt("test");
    	TextView titleView = (TextView) findViewById( R.id.emailSelectionTitle_textView);
	//	titleView.setText("Select To Do Items");
		if (listNumberString == 1) {
			listNumber = 1;
			titleView.setText("Select To Do Items");
		}
		else if (listNumberString == 2) {
			listNumber = 2;
			titleView.setText("Select Archived Items");
		} 
		updateList();
		itemClicked();
	}

	protected void itemClicked() {

	    	ListView list = (ListView) findViewById(R.id.selection_listView);
			list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					if (listController.getToDoList(listNumber).get(position).isSelected()) {
						listController.getToDoList(listNumber).get(position).changeSelected(false);
					}
					else {
						listController.getToDoList(listNumber).get(position).changeSelected(true);
					} 
					updateList();
				}
			});
	  }
	
	public void emailSelection(View view) {
		Intent email = new Intent(Intent.ACTION_SEND);
		email.setType("memessage/rfc822");
		email.putExtra(Intent.EXTRA_SUBJECT, "Items From My To Do List");
		String stringListOfItems = "";
    	if (listNumber == 1) {
    		stringListOfItems = "To Do List:";
    	}
    	else if (listNumber == 2) {
    		stringListOfItems = "Archived To Do List:";
    	}
    	
    	for (int x = 0; x < listController.getToDoList(listNumber).size(); x++) {
    		if (listController.getToDoList(listNumber).get(x).isSelected()) {
    			stringListOfItems += ("\n" + listController.getToDoList(listNumber).get(x).getName());
    		}
		}
    	
		email.putExtra(Intent.EXTRA_TEXT, stringListOfItems);
		startActivity(Intent.createChooser(email, ""));
		
		this.finish();
	}
	  
	public void updateCount() {
    	TextView itemCount = (TextView) findViewById( R.id.emailcount_textView);
    	int numberSelected = 0;
    	for (int x=0; x < listController.getToDoList(listNumber).size(); x++) {
			if (listController.getToDoList(listNumber).get(x).isSelected()) {
				numberSelected++;
			}
    	}
    	itemCount.setText("Items Selected: " + numberSelected);
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
