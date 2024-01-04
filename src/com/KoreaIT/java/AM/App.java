package com.KoreaIT.java.AM;

import java.util.Scanner;

import com.KoreaIT.java.AM.controller.ArticleController;
import com.KoreaIT.java.AM.controller.Controller;
import com.KoreaIT.java.AM.controller.MemberController;

public class App {

	public void run() {
		System.out.println("== 프로그램 시작 == ");

		Scanner sc = new Scanner(System.in);

		MemberController memberController = new MemberController(sc);
		ArticleController articleController = new ArticleController(sc);

		Controller controller = null;

		articleController.makeTestData();
		memberController.makeTestData();

		while (true) {
			System.out.print("명령어 > ");
			String cmd = sc.nextLine().trim();

			if (cmd.length() == 0) {
				System.out.println("명령어를 입력하세요1");
				continue;
			}

			if (cmd.equals("exit")) {
				break;
			}

			String[] cmdBits = cmd.split(" ");

			String controllerName = cmdBits[0];

			if (cmdBits.length == 1) {
				System.out.println("명령어를 확인해줘2");
				continue;
			}

			String actionMethodName = cmdBits[1];

			if (controllerName.equals("article")) {
				controller = articleController;
			} else if (controllerName.equals("member")) {
				controller = memberController;
			} else {
				System.out.println("사용할 수 없는 명령어입니다3");
				continue;
			}

			String forLoginCheck = controllerName + "/" + actionMethodName;

			switch (forLoginCheck) {
			case "article/write":
			case "article/modify":
			case "article/delete":
			case "member/logout":
				if (Controller.isLogined() == false) {
					System.out.println("로그인 하고 이용해");
					continue;
				}
				break;
			}
			
			switch (forLoginCheck) {
			case "member/login":
			case "member/join":
				if (Controller.isLogined()) {
					System.out.println("로그아웃 하고 이용해");
					continue;
				}
				break;
			}

			controller.doAction(actionMethodName, cmd);
		}

		System.out.println("== 프로그램 끝 == ");

		sc.close();

	}

}