//     Copyright (C) 2014 Noah Shillington
//	   Full notice in MainActivity.java

/*
 * This is the class where the ToDoList's are stored. Both are static so that they can be accessed anywhere. Each list also has
 * a designated number, todolist is 1 and archivedtodolist is 2. In some instances, 3 is used to access both of these classes, 
 * although that is not the case here.
 */

package ca.ualberta.cs.nshillin.todolist;

import java.util.List;

public class ToDoListController {

	private static ToDoList todolist = null;
	private static ToDoList archivedtodolist = null;
	
	public static List<ToDoItem> getToDoList(int listNumber) {
		if (listNumber == 1) {
			if (todolist == null) {
				todolist = new ToDoList();
			}
			return todolist.getToDoList();
		}
		else if (listNumber == 2) {
			if (archivedtodolist == null) {
				archivedtodolist = new ToDoList();
			}
			return archivedtodolist.getToDoList();
		}
		return null;
	}
	
	public static void addItem(ToDoItem todoitem, int listNumber) {
		
		if (listNumber == 1) {
			todolist.addItem(todoitem);
		}
		else if (listNumber == 2) {
			archivedtodolist.addItem(todoitem);
		}
	}
}
