package model;

import java.util.List;

import dao.TodoDAO;

public class GetTodoListLogic {
	public List<Todo> execute() {
		TodoDAO dao = new TodoDAO();
		List<Todo> todoList = dao.findAll();
		return todoList;
	}
}
