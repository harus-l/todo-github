package dao;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Todo;

public class TodoDAO {
	//データベース接続に使用する情報
	private static Connection getConnection() throws URISyntaxException, SQLException {
	    String dbUrl = System.getenv("JDBC_DATABASE_URL");
	    return DriverManager.getConnection(dbUrl);
	}
	
	public List<Todo> findAll() {
		List<Todo> todoList = new ArrayList<>();
		
		//データベース接続
		try(Connection conn = getConnection()) {
			//SELCT文の準備
			String sql = "SELECT * FROM TODOLIST";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			//SELECTを実行
			ResultSet rs = pStmt.executeQuery();
			
			//SELECT文の結果をArrayListに格納
			while(rs.next()) {
				int id = rs.getInt("ID");
				String text = rs.getString("TEXT");
				Todo todo = new Todo(id, text);
				todoList.add(todo);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		} catch(URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
		return todoList;
	}
	
	public boolean create(Todo todo) {
		//データベース接続
		try(Connection conn = getConnection()) {
			
			//INSERT文の準備
			String sql = "INSERT INTO TODOLIST(TEXT) VALUES(?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			//INSERT文中の？に使用する値を設定
			pStmt.setString(1, todo.getText());
			
			//INSERT文を実行
			int result = pStmt.executeUpdate();
			if(result != 1) {
				return false;
			}
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		} catch(URISyntaxException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean delete(Todo todo) {
		//データベース接続
		try(Connection conn = getConnection()) {
			
			//DELETE文の準備
			String sql = "DELETE FROM TODOLIST WHERE(ID=?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			
			//DELETE文中の？に使用する値を設定
			pStmt.setInt(1, todo.getId());
			
			//DELETE文を実行
			int result = pStmt.executeUpdate();
			if(result != 1) {
				return false;
			}
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		} catch(URISyntaxException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public int countAll() {
		int count = 0;
		
		//データベース接続
		try(Connection conn = getConnection()) {
			//SELCT COUNT文の準備
			String sql = "SELECT COUNT(*) FROM TODOLIST";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			//SELECT COUNTを実行
			ResultSet rs = pStmt.executeQuery();
			
			//SELECT COUNT文の結果をcountに代入
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return 0;
		} catch(URISyntaxException e) {
			e.printStackTrace();
			return 0;
		}
		return count;
	}
}
