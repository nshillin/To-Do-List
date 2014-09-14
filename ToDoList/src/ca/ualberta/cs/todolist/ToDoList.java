package ca.ualberta.cs.todolist;

import java.util.ArrayList;
import java.util.Collection;

public class ToDoList {
	
	protected ArrayList<ToDoItem> toDoList;
	
	public ToDoList() {
		toDoList = new ArrayList<ToDoItem>();
	}
	
	public Collection<ToDoItem> getToDoList() {
		return toDoList;
	}
	
	public void addItem(ToDoItem todoitem) {
		toDoList.add(todoitem);
	}
	
	public void removeItem(ToDoItem todoitem) {
		toDoList.remove(todoitem);
	}
}
