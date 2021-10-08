package com.todo;

import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() throws IOException {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		String fileLo="C:\\Users\\user\\git\\TodoListApp\\src\\com\\todo\\service\\todolist.txt";
		TodoUtil.loadList(l,fileLo);
		Menu.displaymenu();
		do {
			Menu.prompt();
			isList = false;
			String choice = sc.nextLine();

			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit2222":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name_asc":
				l.sortByName();
				isList = true;
				break;

			case "ls_name_desc":
				l.sortByName();
				l.reverseList();
				isList = true;
				break;
				
			case "ls_date":
				l.sortByDate();
				isList = true;
				break;
				
			case "help":
				Menu.displaymenu();
				break;
				
			case "exit":
				quit = true;
				break;

			default:
				if(choice.length()>5) {
					StringTokenizer st =new StringTokenizer(choice);
					String x=st.nextToken();
					//System.out.println(x);
					if(x.equals("find")){
						String x2=st.nextToken();
						//System.out.println(x2);
						TodoUtil.find(l,x2);
					}
				}else {
					System.out.println("존재하지 않는 선택지 (메뉴 다시 보기_help)");
				}
				break;
			}
			
			if(isList) l.listAll();
		} while (!quit);
		TodoUtil.saveList(l,fileLo);
	}
}
