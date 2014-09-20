package ca.ualberta.cs.nshillin.todolist;

import java.util.List;

public class ToDoListController {

	private static ToDoList todolist = null;
//	private static ToDoList archivedtodolist = null;
	
	// To Do
	
	static public List<ToDoItem> getToDoList() {
		if (todolist == null) {
			todolist = new ToDoList();
			ToDoItem todocount = new ToDoItem(getToDoList().size() + " items");
			addItem(todocount);
		}
		return todolist.getToDoList();
	}
	
	public static void addItem(ToDoItem todoitem) {
		ToDoItem finalItem = getToDoList().get(getToDoList().size()-1);
		todolist.removeItem(finalItem);
		todolist.addItem(todoitem);
		ToDoItem todocount = new ToDoItem(getToDoList().size() + " items");
		todolist.addItem(todocount);
	}
	
	public static void removeItem(ToDoItem todoitem) {
		ToDoItem finalItem = getToDoList().get(getToDoList().size()-1);
		todolist.removeItem(finalItem);
		todolist.removeItem(todoitem);
		ToDoItem todocount = new ToDoItem(getToDoList().size() + " items");
		todolist.addItem(todocount);
	}
	
	// Archived
	/*
	static public List<ToDoItem> getArchivedToDoList() {
		if (todolist == null) {
			todolist = new ToDoList();
			ToDoItem todocount = new ToDoItem(getToDoList().size() + " items");
			addItem(todocount);
		}
		return archivedtodolist.getToDoList();
	}
	
	public void addArchivedItem(ToDoItem todoitem) {
		ToDoItem finalItem = getToDoList().get(getToDoList().size()-1);
		todolist.removeItem(finalItem);
		todolist.addItem(todoitem);
		ToDoItem todocount = new ToDoItem(getToDoList().size() + " items");
		todolist.addItem(todocount);
	}
	
	public static void removeArchivedItem(ToDoItem todoitem) {
		ToDoItem finalItem = getToDoList().get(getToDoList().size()-1);
		todolist.removeItem(finalItem);
		todolist.removeItem(todoitem);
		ToDoItem todocount = new ToDoItem(getToDoList().size() + " items");
		todolist.addItem(todocount);
	}
	*/
}
