package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.service.DbConnect;
import com.todo.service.TodoUtil;

public class TodoList {
	Connection connect;

	public TodoList() {
		this.connect = DbConnect.getConnection();
	}

	public void importData(String file) {
		try {
			BufferedReader br=new BufferedReader(new FileReader(file));
			String line;
			String sql="insert into list (title, dsc, category, due_date, current_date) "
					+"values (?, ?, ?, ?, ?);";
			int records=0;
			while((line = br.readLine())!=null) {
				StringTokenizer st=new StringTokenizer(line,"##");
				String category=st.nextToken();
				String title=st.nextToken();
				String dsc =st.nextToken();
				String currD=st.nextToken();
				String dueD=st.nextToken();
				
				
				PreparedStatement pstmt=connect.prepareStatement(sql);
				pstmt.setString(1,title);
				pstmt.setString(2,dsc);
				pstmt.setString(3,category);
				pstmt.setString(5,currD);
				pstmt.setString(4,dueD);
				
				int cnt=pstmt.executeUpdate();
				if(cnt>0)records++;
				pstmt.close();
			}
			System.out.println(records+"개의 할 일이 기존에 남아있음");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public int addItem(TodoItem t) {
		String sql="insert into list (title, dsc, category, due_date) "
				+"values (?, ?, ?, ?);";
		
		PreparedStatement pstmt;
		int cnt=0;
		
		try {
			pstmt=connect.prepareStatement(sql);
			pstmt.setString(1,t.getTitle());
			pstmt.setString(2,t.getDesc());
			pstmt.setString(3,t.getCategory());
			pstmt.setString(4,t.getDue_date());
			cnt=pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cnt;
	}

	public int updateItem(TodoItem t) {
		int cnt=0;
		String sql="update list set title=?, dsc=?,category=?,current_date=?,due_date=? where id = ?;";
		
		PreparedStatement pstmt;
		try {
			pstmt = connect.prepareStatement(sql);
			pstmt.setString(1,t.getTitle());
			pstmt.setString(2,t.getDesc());
			pstmt.setString(3,t.getCategory());
			pstmt.setString(4,t.getCurrent_date());
			pstmt.setString(5,t.getDue_date());
			String id=Integer.toString(t.getId());
			pstmt.setString(6,id);
			cnt=pstmt.executeUpdate();
			
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cnt;
	}
	
	public int deleteItem(int idx) {
		String sql="delete from list where id = ?;";
		PreparedStatement pstmt;
		int cnt=0;
		
		try {
			pstmt=connect.prepareStatement(sql);
			pstmt.setInt(1,idx);
			cnt=pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cnt;
	}
	
	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt=connect.createStatement();
			String spl="SELECT * FROM list";
			ResultSet rs=stmt.executeQuery(spl);
			while(rs.next()) {
				int id=rs.getInt("id");
				String cate=rs.getString("category");
				String title=rs.getString("title");
				String dsc=rs.getString("dsc");
				String dueD=rs.getString("due_date");
				String currentD=rs.getString("current_date");
				TodoItem t= new TodoItem(title,dsc,cate,dueD);
				t.setId(id);
				t.setCurrent_date(currentD);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<TodoItem> getList(String key){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		key="%"+key+"%";
		String sql="SELECT * FROM list WHERE title like ? or dsc like ?";
		try {
			pstmt = connect.prepareStatement(sql);
			pstmt.setString(1, key);
			pstmt.setString(2, key);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				int id=rs.getInt("id");
				String cate=rs.getString("category");
				String title=rs.getString("title");
				String dsc=rs.getString("dsc");
				String dueD=rs.getString("due_date");
				String currentD=rs.getString("current_date");
				TodoItem t= new TodoItem(title,dsc,cate,dueD, currentD);
				t.setId(id);
				list.add(t);
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return list;
	}
	
	public ArrayList<String> getCategories(){
		ArrayList<String> list=new ArrayList<String>();
		Statement stmt;
		try {
			stmt=connect.createStatement();
			String sql="SELECT DISTINCT category FROM list";
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				String cate=rs.getString("category");
				list.add(cate);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getListCategory(String keyW){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		String sql="SELECT *FROM list WHERE category = ?;";//?
		try {
			pstmt = connect.prepareStatement(sql);
			pstmt.setString(1, keyW);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				int id=rs.getInt("id");
				String cate=rs.getString("category");
				String title=rs.getString("title");
				String dsc=rs.getString("dsc");
				String dueD=rs.getString("due_date");
				String currentD=rs.getString("current_date");
				TodoItem t= new TodoItem(title,dsc,cate,dueD);
				t.setId(id);
				t.setCurrent_date(currentD);
				list.add(t);
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return list;
	}
	
	public ArrayList<TodoItem> getOderedList(String kind,int ordering){
		ArrayList<TodoItem> list=new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt=connect.createStatement();
			String sql="SELECT * FROM list ORDER BY "+kind;
			if(ordering==0) {
				sql+=" desc";
			}
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				int id=rs.getInt("id");
				String cate=rs.getString("category");
				String title=rs.getString("title");
				String dsc=rs.getString("dsc");
				String dueD=rs.getString("due_date");
				String currentD=rs.getString("current_date");
				TodoItem t= new TodoItem(title,dsc,cate,dueD);
				t.setId(id);
				t.setCurrent_date(currentD);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public int getCnt() {
		Statement stmt;
		int cnt=0;
		
		try {
			stmt=connect.createStatement();
			String sql="SELECT count(id) FROM list";
			ResultSet rs=stmt.executeQuery(sql);
			rs.next();
			cnt=rs.getInt("count(id)");
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cnt;
	}

	public int indexOf(TodoItem t) {
		return t.getId();
	}

	public Boolean isDuplicate(String cate, String title) {
		for (TodoItem item :getListCategory(cate) ) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}
}
