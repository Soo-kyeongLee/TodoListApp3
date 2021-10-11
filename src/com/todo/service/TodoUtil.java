package com.todo.service;

import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String cate, title, desc, end;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "#할 일 추가하기\n"
				+ "##할 일 카테고리:\n");
		
		cate = sc.nextLine();
		
		System.out.println("\n"
				+ "##할 일 제목:\n");
		
		title = sc.nextLine();
		if(list.isDuplicate(cate,title)) {
			System.out.println(cate+"내에서 제목 중복");
			return;
		}
		System.out.println("##할 일 구체적인 내용:");
		desc = sc.nextLine();
		
		System.out.println("##마감일_yyy/mm/dd:");
		end = sc.nextLine();
		
		TodoItem t = new TodoItem(title, desc, cate, end);
		if(list.addItem(t)>0) {
			System.out.println("추가됨");
		}
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		listAll(l,"id",1);
		System.out.println("\n"
				+ "#목록에서 할 일 삭제하기\n"
				+ "##삭제할 할 항목 번호:");
		int idx = sc.nextInt();
		 sc.nextLine();
		 if(l.deleteItem(idx)>0) {
			 System.out.println("삭제됨");
		 }
	}

	public static void updateItem(TodoList l) {	
		Scanner sc=new Scanner(System.in);
		
		System.out.println("\n"
				+ "#할 일 내용 수정하기\n"
				+ "##업데이트할 항목 번호");
		int num = sc.nextInt();
		sc.nextLine(); 
					
		System.out.println("##새로운 제목:");
		String new_title = sc.nextLine().trim();
		
		System.out.println("##"+new_title+"의 새로운 내용:");
		String new_description = sc.nextLine().trim();
		System.out.println("##"+new_title+"의 새로운 카테고리:");
		String cate = sc.nextLine().trim();
		System.out.println("##끝나는 날짜:");
		String end = sc.nextLine().trim();
		
		TodoItem t= new TodoItem(new_title,new_description,cate,end);
		t.setId(num);
		if(l.updateItem(t)>0)
			System.out.println("업데이트 완료");
		else System.out.println("업데이트 실패");
	}

	public static void listAll(TodoList l, String orderby, int ordering) {
		
		System.out.println("#전체 할 일 목록 "+l.getCnt()+"개\n"
				+"#번호, 분야, 제목 , 내용, 시작 날짜, 끝나는 날짜\n");
		for (TodoItem item : l.getOderedList(orderby, ordering)) {
			System.out.println(item.toString()); 
		}
	}
	
	public static void findList(TodoList l, String key) {
		int cnt=0;
		for (TodoItem item : l.getList(key)) {
			System.out.println(item.toString());
			cnt++;
		}
		System.out.println(cnt+"개의 할 일을 발견");
	}
	
	public static boolean find_(String s,String key) {
		StringTokenizer st=new StringTokenizer(s);
		while(st.hasMoreTokens()) {
			if(st.nextToken().equals(key)) {
				return true;	
			}
		}
		return false;
	}
	
	public static void findCateList(TodoList l, String cate) {
		int cnt=0;
		for(TodoItem item:l.getListCategory(cate)) {
			System.out.println(item.toString());
			cnt++;
		}
		System.out.printf("\n총 %d개의 항목을 찾음\n",cnt);
	}
	
	public static void listCateAll(TodoList l) {
		int cnt=0;
		for(String item: l.getCategories()) {
			System.out.print(item+" ");
			cnt++;
		}
		System.out.printf("\n총 %d개의 카테고리가 등록됨\n",cnt);
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
				newItem.setCategory(st.nextToken("##"));
				newItem.setTitle(st.nextToken("##"));
				newItem.setDesc(st.nextToken("##"));
				newItem.setCurrent_date(st.nextToken("##"));
				newItem.setDue_date(st.nextToken());
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
			System.out.println(num+"개의 해야할 일이 있음\n");
		else 
			System.out.println("해야 할 일이 존재하지 않음\n");
	
	}
}
