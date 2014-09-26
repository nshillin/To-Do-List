//     Copyright (C) 2014 Noah Shillington
//	   Full notice in MainActivity.java

/*
 * This is the actual ToDoItem class. Each item that is added in the list is an instance of this. The two main components of this
 * class are its name and whether it is checked or not. Both of these can be called using getName and isChecked, and the state of
 * the item can be changed with changeChecked. The selected part of this class is almost always false and only changes to true
 * when the item is selected in the EmailSelection class. Upon exit of this class, all items return to false for selected.
 */

package ca.ualberta.cs.nshillin.todolist;

public class ToDoItem {
	private String name;
	private Boolean checked;
	private Boolean selected;
	
	public ToDoItem(String name) {
		this.name = name;
		this.checked = false;
		this.selected = false;
	}

	public String getName() {
		return name;
	}
	
	public Boolean isChecked() {
		return checked;
	}
	
	public void changeChecked(Boolean checked) {
		this.checked = checked;
	}
	
	public Boolean isSelected() {
		return selected;
	}
	
	public void changeSelected(Boolean selected) {
		this.selected = selected;
	}
}
