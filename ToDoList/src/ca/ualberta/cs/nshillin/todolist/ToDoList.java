//     Copyright (C) 2014 Noah Shillington
//	   Full notice in MainActivity.java

/*
 * this is the actual todo list class, only two instances of this class are actually used in the application and they are both
 * called from the ToDoListController. The lists are filled with ToDoItem's.
 */

package ca.ualberta.cs.nshillin.todolist;

import java.util.ArrayList;
//import java.util.Collection;
import java.util.List;

public class ToDoList {
	
	protected List<ToDoItem> toDoList= new ArrayList<ToDoItem>();
	
	public ToDoList() {
		toDoList = new ArrayList<ToDoItem>();
	}
	
	public List<ToDoItem> getToDoList() {
		return toDoList;
	}
	
	public void addItem(ToDoItem todoitem) {
		toDoList.add(todoitem);	
	}
}
