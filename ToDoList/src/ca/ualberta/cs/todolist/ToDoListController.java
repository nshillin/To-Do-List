package ca.ualberta.cs.todolist;

public class ToDoListController {

	private static ToDoList todolist = null;
	private static ToDoList archivedtodolist = null;
	
	static public ToDoList getToDoList() {
		if (todolist == null) {
			todolist = new ToDoList();
			ToDoItem todocount = new ToDoItem("Number of items: " + getToDoList().getToDoList().size());
			getToDoList().addItem(todocount);
		}
		return todolist;
	}
	
	public static void addItem(ToDoItem todoitem) {
		ToDoItem finalItem = getToDoList().getToDoList().get(getToDoList().getToDoList().size()-1);
		getToDoList().removeItem(finalItem);
		getToDoList().addItem(todoitem);
		ToDoItem todocount = new ToDoItem("Number of items: " + getToDoList().getToDoList().size());
		getToDoList().addItem(todocount);
	}
	
	static public ToDoList getArchivedToDoList() {
		if (archivedtodolist == null) {
			archivedtodolist = new ToDoList();
		}
		return archivedtodolist;
	}
	
	public void addItemToArchive(ToDoItem todoitem) {
		getArchivedToDoList().addItem(todoitem);
	}
}
