package ca.ualberta.cs.todolist;

public class ToDoItem {
	private String name;
	private Boolean checked;
	private Boolean archived;
	public ToDoItem(String name) {
		this.name = name;
		this.checked = false;
		this.archived = false;
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

	public Boolean isArchived() {
		return archived;
	}
	
	public void changeArchived(Boolean archived) {
		this.archived = archived;
	}
}
