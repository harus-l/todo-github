package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.GetTodoListLogic;
import model.PostTodoLogic;
import model.Todo;

@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Todoリストを取得してリクエストスコープに保存
		GetTodoListLogic getTodoListLogic = new GetTodoListLogic();
		List<Todo> todoList = getTodoListLogic.execute();
		request.setAttribute("todoList", todoList);
		
		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		String text = request.getParameter("text");
		String[] strIdArray = request.getParameterValues("check");
		
		//追加ボタンが押された場合
		if(action.equals("create")) {
			//入力テキストの値チェック
			if(text == null || text.length() == 0) {
				request.setAttribute("errorMsg", "テキストが入力されていません");
			}
			//テキストの文字数チェック
			if(text.length() > 30) {
				request.setAttribute("errorMsg", "30字以内で入力してください");
			}
			//登録件数チェック
			PostTodoLogic postTodoLogic = new PostTodoLogic();
			int count = postTodoLogic.countAll();
			if(count == 30) {
				request.setAttribute("errorMsg", "登録可能件数を超えました");
			}
			//エラーではない場合データベースに追加
			if(request.getAttribute("errorMsg") == null) {
				Todo todo = new Todo(text);
				postTodoLogic.create(todo);
			}
			
		}
		
		//消去ボタンが押された場合
		if(action.equals("delete")) {
			if(strIdArray != null && strIdArray.length != 0) {
				//データベースから削除
				PostTodoLogic postTodoLogic = new PostTodoLogic();
				int[] id = new int[strIdArray.length];
				for(int i = 0; i < strIdArray.length; i++) {
					id[i] = Integer.parseInt(strIdArray[i]);
					Todo todo = new Todo(id[i]);
					postTodoLogic.delete(todo);
				}
			} else {
				//エラーメッセージをリクエストスコープに保存
				request.setAttribute("errorMsg", "チェック項目がありません");
			}
		}
		
		//Todoリストを取得してリクエストスコープに保存
		GetTodoListLogic getTodoListLogic = new GetTodoListLogic();
		List<Todo> toodList = getTodoListLogic.execute();
		request.setAttribute("todoList", toodList);
		
		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
		dispatcher.forward(request, response);
	}
}
