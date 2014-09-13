package ca.ualberta.cs.todolist;

public class ToDoItem {
	protected String todoitemName;
	public ToDoItem(String todoitemName) {
		this.todoitemName = todoitemName;
	}

	public String getName() {
		return todoitemName;
	}

}
