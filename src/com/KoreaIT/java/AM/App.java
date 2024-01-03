package com.KoreaIT.java.AM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.AM.controller.ArticleController;
import com.KoreaIT.java.AM.controller.MemberController;
import com.KoreaIT.java.AM.dto.Article;
import com.KoreaIT.java.AM.dto.Member;

public class App {

	List<Article> articles;
	List<Member> members;

	public App() {
		articles = new ArrayList<>();
		members = new ArrayList<>();
	}

	public void run() {
		System.out.println("== 프로그램 시작 == ");

		Scanner sc = new Scanner(System.in);

		MemberController memberController = new MemberController(sc, members);
		ArticleController articleController = new ArticleController(sc, articles);

		articleController.makeTestData();

		while (true) {
			System.out.print("명령어 > ");
			String cmd = sc.nextLine().trim();

			if (cmd.length() == 0) {
				System.out.println("명령어를 입력하세요");
				continue;
			}

			if (cmd.equals("exit")) {
				break;
			}
			if (cmd.equals("member join")) {
				memberController.doJoin();
			} else if (cmd.equals("article write")) {
				articleController.doWrite();
			} else if (cmd.startsWith("article list")) {
				articleController.showList(cmd);
			} else if (cmd.startsWith("article detail")) {
				articleController.showDetail(cmd);
			} else if (cmd.startsWith("article delete")) {
				articleController.doDelete(cmd);
			} else if (cmd.startsWith("article modify")) {
				articleController.doModify(cmd);
			} else {
				System.out.println("사용할 수 없는 명령어입니다");
			}
		}

		System.out.println("== 프로그램 끝 == ");

		sc.close();

	}

}