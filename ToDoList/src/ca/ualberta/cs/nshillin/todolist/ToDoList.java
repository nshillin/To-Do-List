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
	
	public void removeItem(ToDoItem todoitem) {
		toDoList.remove(todoitem);
	}
}