package ca.ualberta.cs.nshillin.todolist;

import java.util.List;

public class ArchivedListController {
	
	private static ToDoList archivedtodolist = null;
	
	public static List<ToDoItem> getToDoList() {
		if (archivedtodolist == null) {
			archivedtodolist = new ToDoList();
		}
		return archivedtodolist.getToDoList();
	}
	
	public static void addItem(ToDoItem todoitem) {
		archivedtodolist.addItem(todoitem);
	}

}
