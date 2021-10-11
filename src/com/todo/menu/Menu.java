package com.todo.menu;

import com.todo.service.TodoUtil;

public class Menu {

    public static void displaymenu()
    {
        System.out.println("\n[메뉴]");
        System.out.println("#할 일 추가하기_add");
        System.out.println("#목록에서 삭제하기_del");
        System.out.println("#할 일 내용 수정하기_edit");
        System.out.println("#목록 보여주기_ls");
        System.out.println("#카테고리 목록 보여주기_ls_cate");
        System.out.println("#이름으로 오름차순 정렬_ls_name_asc");
        System.out.println("#이름으로 내림차순 정렬_ls_name_desc");
        System.out.println("#마감일순으로 정렬_ls_date");
        System.out.println("#마감일역순으로 정렬_ls_date_desc");
        System.out.println("#키워드로 찾기_find 키워드");
        System.out.println("#카테고리 찾기_find_cate 카테고리");
        System.out.println("#나가기_exit");
    }
    
    public static void prompt()
    {
        System.out.println();
        System.out.println("실행할 메뉴의 영단어 작성>");
    }
}
