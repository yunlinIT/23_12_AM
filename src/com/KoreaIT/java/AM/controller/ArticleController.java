package com.KoreaIT.java.AM.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.AM.dto.Article;
import com.KoreaIT.java.AM.util.Util;

public class ArticleController extends Controller {

	private List<Article> articles;
	private Scanner sc;
	private String cmd;

	public ArticleController(Scanner sc) {
		this.articles = new ArrayList<>();
		this.sc = sc;
	}

	public void doAction(String actionMethodName, String cmd) {
		this.cmd = cmd;

		switch (actionMethodName) {
		case "write":
			doWrite();
			break;
		case "list":
			showList();
			break;
		case "detail":
			showDetail();
			break;
		case "modify":
			doModify();
			break;
		case "delete":
			doDelete();
			break;
		default:
			System.out.println("명령어 확인해 (actionMethodName 오류)4");
			break;
		}
	}

	int lastArticleId = 3;

	private void doWrite() {
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

	}

	private void showList() {
		System.out.println("==게시글 목록==");
		if (articles.size() == 0) {
			System.out.println("아무것도 없어");
			return;
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
				return;
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

	}

	private void showDetail() {
		String[] cmdDiv = cmd.split(" ");

		int id = 0;

		try {
			id = Integer.parseInt(cmdDiv[2]);
		} catch (Exception e) {
			System.out.println("번호는 정수로 입력해");
			return;
		}

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시글은 없습니다\n", id);
			return;
		}
		System.out.println("번호 : " + foundArticle.getId());
		System.out.println("작성 날짜 : " + foundArticle.getRegDate());
		System.out.println("수정 날짜 : " + foundArticle.getUpdateDate());
		System.out.println("제목 : " + foundArticle.getTitle());
		System.out.println("내용 : " + foundArticle.getBody());
		System.out.println("조회 : " + foundArticle.getHit());

		foundArticle.setHit(foundArticle.getHit() + 1);

	}

	private void doDelete() {
		String[] cmdDiv = cmd.split(" ");

		int id = 0;

		try {
			id = Integer.parseInt(cmdDiv[2]);
		} catch (Exception e) {
			System.out.println("번호는 정수로 입력해");
			return;
		}

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시글은 없습니다\n", id);
			return;
		}
		articles.remove(foundArticle);
		System.out.println(id + "번 글이 삭제되었습니다.");

	}

	private void doModify() {
		String[] cmdDiv = cmd.split(" ");

		int id = 0;

		try {
			id = Integer.parseInt(cmdDiv[2]);
		} catch (Exception e) {
			System.out.println("번호는 정수로 입력해");
			return;
		}

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시글은 없습니다\n", id);
			return;
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

	}

	private Article getArticleById(int id) {
		for (Article article : articles) {
			if (article.getId() == id) {
				return article;
			}
		}
		return null;
	}

	public void makeTestData() {
		System.out.println("테스트를 위한 데이터를 생성합니다.");
		articles.add(new Article(1, "2023-12-12 12:12:12", Util.getNowDate_TimeStr(), "제목123", "내용1", 11));
		articles.add(new Article(2, "2024-01-01 12:12:12", Util.getNowDate_TimeStr(), "제목2", "내용2", 22));
		articles.add(new Article(3, Util.getNowDate_TimeStr(), Util.getNowDate_TimeStr(), "제목1233", "내용3", 33));
	}

}