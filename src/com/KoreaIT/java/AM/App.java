package com.KoreaIT.java.AM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.AM.dto.Article;
import com.KoreaIT.java.AM.dto.Member;
import com.KoreaIT.java.AM.util.Util;

public class App {

	List<Article> articles;
	List<Member> members;

	public App() {
		articles = new ArrayList<>();
		members = new ArrayList<>();
	}

	public void run() {
		System.out.println("== 프로그램 시작 == ");

		makeTestData();

		Scanner sc = new Scanner(System.in);

		int lastArticleId = 3;
		int lastMemberId = 0;

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
				System.out.println("==회원 가입==");
				int id = lastMemberId + 1;
				String regDate = Util.getNowDate_TimeStr();
				String loginId = null;
				while (true) {
					System.out.print("로그인 아이디 : ");
					loginId = sc.nextLine();
					if (isJoinableLoginId(loginId) == false) {
						System.out.println("이미 사용중이야");
						continue;
					}
					break;
				}
				String loginPw = null;

				while (true) {
					System.out.print("로그인 비밀번호 : ");
					loginPw = sc.nextLine();
					System.out.print("로그인 비밀번호 확인: ");
					String loginPwConfirm = sc.nextLine();

					if (loginPw.equals(loginPwConfirm) == false) {
						System.out.println("비밀번호 다시 확인해");
						continue;
					}
					break;
				}

				System.out.print("이름 : ");
				String name = sc.nextLine();

				Member member = new Member(id, regDate, loginId, loginPw, name);
				members.add(member);

				System.out.printf("%d번 회원이 가입 되었습니다. %s님 환영합니다.\n", id, name);
				lastMemberId++;
			} else if (cmd.equals("article write")) {
				System.out.println("==게시글 작성==");
				int id = lastArticleId + 1;
				String regDate = Util.getNowDate_TimeStr();
				String updateDate = regDate;
				System.out.print("제목 : ");
				String title = sc.nextLine();
				System.out.print("내용 : ");
				String body = sc.nextLine();

				Article article = new Article(id, regDate, updateDate, title, body);
				articles.add(article);

				System.out.printf("%d번 글이 생성 되었습니다.\n", id);
				lastArticleId++;
			} else if (cmd.startsWith("article list")) {
				System.out.println("==게시글 목록==");
				if (articles.size() == 0) {
					System.out.println("아무것도 없어");
					continue;
				}

				String searchKeyword = cmd.substring("article list".length()).trim();

				List<Article> forPrintArticles = articles;

				if (searchKeyword.length() > 0) {
					System.out.println("검색어 : " + searchKeyword);
					forPrintArticles = new ArrayList<>();

					for (Article article : articles) {
						if (article.getTitle().contains(searchKeyword)) {
							forPrintArticles.add(article);
						}
					}
					if (forPrintArticles.size() == 0) {
						System.out.println("  번호  /  제목    /   작성일     /   조회");
						System.out.println("검색 결과 없음");
						continue;
					}
				}

				System.out.println("  번호  /  제목    /   작성일     /   조회");
				for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
					Article article = forPrintArticles.get(i);
					if (Util.getNowDate_TimeStr().split(" ")[0].equals(article.getRegDate().split(" ")[0])) {
						System.out.printf("  %4d  /   %s    /     %s   /   %d\n", article.getId(), article.getTitle(),
								article.getRegDate().split(" ")[1], article.getHit());
					} else {
						System.out.printf("  %4d  /   %s    /     %s   /   %d\n", article.getId(), article.getTitle(),
								article.getRegDate().split(" ")[0], article.getHit());
					}

				}

			} else if (cmd.startsWith("article detail")) {

				String[] cmdDiv = cmd.split(" ");

				int id = 0;

				try {
					id = Integer.parseInt(cmdDiv[2]);
				} catch (Exception e) {
					System.out.println("번호는 정수로 입력해");
					continue;
				}

				Article foundArticle = getArticleById(id);

				if (foundArticle == null) {
					System.out.printf("%d번 게시글은 없습니다\n", id);
					continue;
				}
				System.out.println("번호 : " + foundArticle.getId());
				System.out.println("작성 날짜 : " + foundArticle.getRegDate());
				System.out.println("수정 날짜 : " + foundArticle.getUpdateDate());
				System.out.println("제목 : " + foundArticle.getTitle());
				System.out.println("내용 : " + foundArticle.getBody());
				System.out.println("조회 : " + foundArticle.getHit());

				foundArticle.setHit(foundArticle.getHit() + 1);

			} else if (cmd.startsWith("article delete")) {

				String[] cmdDiv = cmd.split(" ");

				int id = 0;

				try {
					id = Integer.parseInt(cmdDiv[2]);
				} catch (Exception e) {
					System.out.println("번호는 정수로 입력해");
					continue;
				}

				Article foundArticle = getArticleById(id);

				if (foundArticle == null) {
					System.out.printf("%d번 게시글은 없습니다\n", id);
					continue;
				}
				articles.remove(foundArticle);
				System.out.println(id + "번 글이 삭제되었습니다.");

			} else if (cmd.startsWith("article modify")) {

				String[] cmdDiv = cmd.split(" ");

				int id = 0;

				try {
					id = Integer.parseInt(cmdDiv[2]);
				} catch (Exception e) {
					System.out.println("번호는 정수로 입력해");
					continue;
				}

				Article foundArticle = getArticleById(id);

				if (foundArticle == null) {
					System.out.printf("%d번 게시글은 없습니다\n", id);
					continue;
				}

				System.out.println("기존 제목 : " + foundArticle.getTitle());
				System.out.println("기존 내용 : " + foundArticle.getBody());
				System.out.print("새 제목 : ");
				String newTitle = sc.nextLine();
				System.out.print("새 내용 : ");
				String newBody = sc.nextLine();

				foundArticle.setUpdateDate(Util.getNowDate_TimeStr());
				foundArticle.setTitle(newTitle);
				foundArticle.setBody(newBody);
				System.out.println(id + "번 글이 수정되었습니다.");
			} else {
				System.out.println("사용할 수 없는 명령어입니다");
			}
		}

		System.out.println("== 프로그램 끝 == ");

		sc.close();

	}

	private boolean isJoinableLoginId(String loginId) {
		for (Member member : members) {
			if (member.getLoginId().equals(loginId)) {
				return false;
			}
		}

		return true;
	}

	private Article getArticleById(int id) {
		for (Article article : articles) {
			if (article.getId() == id) {
				return article;
			}
		}
		return null;
	}

	private void makeTestData() {
		System.out.println("테스트를 위한 데이터를 생성합니다.");
		articles.add(new Article(1, "2023-12-12 12:12:12", Util.getNowDate_TimeStr(), "제목123", "내용1", 11));
		articles.add(new Article(2, "2024-01-01 12:12:12", Util.getNowDate_TimeStr(), "제목2", "내용2", 22));
		articles.add(new Article(3, Util.getNowDate_TimeStr(), Util.getNowDate_TimeStr(), "제목1233", "내용3", 33));
	}

}