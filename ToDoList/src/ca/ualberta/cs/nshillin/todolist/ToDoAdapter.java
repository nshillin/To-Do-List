package ca.ualberta.cs.nshillin.todolist;

import java.util.List;

import ca.ualberta.cs.todolist.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

class ToDoAdapter extends ArrayAdapter<ToDoItem> {
	
	List<ToDoItem> toDoList = ToDoListController.getToDoList();
	
	Context context;
	int textViewResourceId;
	
	public ToDoAdapter(Context context, int textViewResourceId, List<ToDoItem> list) {
		super(context, textViewResourceId, list);
		this.toDoList = list;
		this.context = context;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		
		View itemView = convertView;
		if (itemView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			itemView = inflater.inflate(R.layout.list_item, parent, false);
		}
		
		ToDoItem currentItem = toDoList.get(position);
		
		TextView textView = (TextView) itemView.findViewById(R.id.item_MediumTextView);
		textView.setText(currentItem.getName());
		
		final CheckBox checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
		checkBox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				List<ToDoItem> toDoList = ToDoListController.getToDoList();
				ToDoItem currentItem = toDoList.get(position);
				currentItem.changeChecked(checkBox.isChecked()); 
			}
		});
		checkBox.setChecked(currentItem.isChecked());
	/*	
		if (position == ToDoListController.getToDoList().size() -1) {
			checkBox.setVisibility(View.INVISIBLE);
		}
		*/
	//	itemView.setBackgroundColor(Color.CYAN);
		
		return itemView;
	}
	
	
}