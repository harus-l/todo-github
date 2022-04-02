package model;

import dao.TodoDAO;

public class PostTodoLogic {
	public void create(Todo todo) {
		TodoDAO dao = new TodoDAO();
		dao.create(todo);
	}
	public void delete(Todo todo) {
		TodoDAO dao = new TodoDAO();
		dao.delete(todo);
	}
	public int countAll() {
		TodoDAO dao = new TodoDAO();
		int count = dao.countAll();
		return count;
	}

}
