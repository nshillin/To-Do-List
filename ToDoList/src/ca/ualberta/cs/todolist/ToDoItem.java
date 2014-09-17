package ca.ualberta.cs.todolist;

public class ToDoItem {
	private String name;
	private Boolean checked;
	public ToDoItem(String name) {
		this.name = name;
		this.checked = false;
	}

	public String getName() {
		return name;
	}
	
	public Boolean isChecked() {
		return checked;
	}
	
	public void changeChecked(Boolean checked) {
		this.checked = checked;
	}
}
