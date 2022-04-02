package model;

import java.io.Serializable;

public class Todo implements Serializable {
	private int id;
	private String text;
	
	public Todo() {}
	public Todo(String text) {
		this.text = text;
	}
	public Todo(int id) {
		this.id = id;
	}
	public Todo(int id, String text) {
		this.id = id;
		this.text = text;
	}
	
	public int getId() { return id; }
	public String getText() { return text;}
}
