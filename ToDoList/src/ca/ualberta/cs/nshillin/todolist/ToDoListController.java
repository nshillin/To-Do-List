package ca.ualberta.cs.nshillin.todolist;

import java.util.List;

public class ToDoListController {

	private static ToDoList todolist = null;
	
	public static List<ToDoItem> getToDoList() {
		if (todolist == null) {
			todolist = new ToDoList();
		}
		return todolist.getToDoList();
	}
	
	public static void addItem(ToDoItem todoitem) {
		todolist.addItem(todoitem);
	}
}
