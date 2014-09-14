package ca.ualberta.cs.todolist;

public class ToDoListController {

	private static ToDoList todolist = null;
	
	static public ToDoList getToDoList() {
		if (todolist == null) {
			todolist = new ToDoList();
		}
		return todolist;
	}
	
	public void addItem(ToDoItem todoitem) {
		getToDoList().addItem(todoitem);
	}
}
