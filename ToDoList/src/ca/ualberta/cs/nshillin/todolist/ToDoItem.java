package ca.ualberta.cs.nshillin.todolist;

public class ToDoItem {
	private String name;
	private Boolean checked;
	private Boolean selected;
	
	public ToDoItem(String name) {
		this.name = name;
		this.checked = false;
		this.selected = false;
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
	
	public Boolean isSelected() {
		return selected;
	}
	
	public void changeSelected(Boolean selected) {
		this.selected = selected;
	}
}
