//     Copyright (C) 2014 Noah Shillington
//	   Full notice in MainActivity.java

/*
 * This is the class that organizes all of the list data, it initiates all of the items and check boxes.
 * If a checkbox is clicked, this updates the list with the information and calls the updateCount method of the MainActivity.
 */

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
	
	List<ToDoItem> toDoList;
	int listNumber;
	
	Context context;
	int textViewResourceId;
	
	public ToDoAdapter(Context context, int textViewResourceId, List<ToDoItem> list, int listNumber) {
		super(context, textViewResourceId, list);
		this.toDoList = ToDoListController.getToDoList(listNumber);
		this.context = context;
		this.listNumber = listNumber;
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
				List<ToDoItem> toDoList = ToDoListController.getToDoList(listNumber);
				ToDoItem currentItem = toDoList.get(position);
				currentItem.changeChecked(checkBox.isChecked()); 
				try {
					MainActivity mainActivity = ((MainActivity) context);
					mainActivity.updateCount();
				} catch (Exception e) {}
			}
		});
		checkBox.setChecked(currentItem.isChecked());
		
		if (currentItem.isSelected()) {
			itemView.setBackgroundColor(Color.parseColor("#33B5E5"));
		}
		
		return itemView;
	}
	
	
}