package com.todo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {
	private static Connection connect = null;
	
	public static void closeConnection() {
		if(connect!=null) {
			try {
				connect.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static Connection getConnection() {
		if(connect==null) {
			try {
				String dbFile="todolist.db";
				Class.forName("org.sqlite.JDBC");
				connect=DriverManager.getConnection("jdbc:sqlite:"+dbFile);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return connect;
	}

}
