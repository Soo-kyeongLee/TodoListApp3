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
		l.importData(fileLo);
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
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l,"id",1);
				break;
				
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;
				
			case "ls_name_asc":
				System.out.println("제목순으로 정렬");
				TodoUtil.listAll(l,"title",1);
				isList = true;
				break;

			case "ls_name_desc":
				System.out.println("제목역순으로 정렬");
				TodoUtil.listAll(l,"title",0);
				break;
				
			case "ls_date":
				System.out.println("마감날짜순으로 정렬");
				TodoUtil.listAll(l,"due_date",1);
				break;
				
			case "ls_date_desc":
				System.out.println("날짜역순으로 정렬");
				TodoUtil.listAll(l,"due_date",0);
				break;
				
			case "find":
				String keyword=sc.nextLine().trim();
				TodoUtil.findList(l,keyword);
				break;
			
			case "find_cate":
				String cate=sc.nextLine().trim();
				TodoUtil.findCateList(l,cate);
				break;
				
			case "help":
				Menu.displaymenu();
				break;
				
			case "exit":
				quit = true;
				break;

			default:
				System.out.println("존재하지 않는 선택지 (메뉴 다시 보기_help)");
			}
		} while (!quit);
	}
}
