package com.todo.service;

import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "#할 일 추가하기\n"
				+ "##할 일 제목:\n");
		
		title = sc.nextLine();
		if (list.isDuplicate(title)) {
			System.out.printf("겹치는 제목 존재\n");
			return;
		}
		
		System.out.println("##할 일 구체적인 내용:");
		desc = sc.nextLine();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "#목록에서 할 일 삭제하기\n"
				+ "##삭제할 할 일 제목:");
		String title = sc.nextLine();
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "#할 일 내용 수정하기\n"
				+ "##업데이트할 할 일의 제목:");
		String title = sc.nextLine().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("존재하지 않는 제목\n");
			return;
		}

		System.out.println("##아이템의 새로운 제목:");
		String new_title = sc.nextLine().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("겹치는 제목 존재\n");
			return;
		}
		
		System.out.println("##"+new_title+"의 새로운 내용:");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("업데이트 완료");
			}
		}

	}

	public static void listAll(TodoList l) {
		
		System.out.println("전체 할 일 목록\n제목 , 내용\n");
		for (TodoItem item : l.getList()) {
			System.out.println( item.getTitle() + " , " + item.getDesc()+" , "+item.getCurrent_date());
		}
	}
	
	public static void saveList(TodoList l,String file) throws IOException {
		//file open
		FileWriter f = new FileWriter(file,false);
		for (TodoItem item : l.getList()) {
			f.write(item.toSaveSTring());
			//f.flush();
		}
		//file close
		f.close();
	}
	
	public static void loadList(TodoList l,String file) {
		int num=0;
		BufferedReader bf;
		try {
			bf = new BufferedReader(new FileReader(file));
			String s;
			while((s=bf.readLine())!=null) {
				StringTokenizer st=new StringTokenizer(s);
				TodoItem newItem=new TodoItem();
				newItem.setTitle(st.nextToken("##"));
				newItem.setDesc(st.nextToken("##"));
				newItem.setCurrent_date(st.nextToken());
				l.addItem(newItem);
				num++;
			}
			
			bf.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("todoutil::loadList");
			e.printStackTrace();
		}
		
	
		if(num!=0)
			System.out.println(num+"개를 파일로부터 읽어옴\n");
		else 
			System.out.println("해야 할 일이 존재하지 않음\n");
	
	}
}
