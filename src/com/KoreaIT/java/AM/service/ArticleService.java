package com.KoreaIT.java.AM.service;

import java.util.ArrayList;
import java.util.List;

import com.KoreaIT.java.AM.Container;
import com.KoreaIT.java.AM.dao.ArticleDao;
import com.KoreaIT.java.AM.dto.Article;

public class ArticleService {

	private ArticleDao articleDao;

	public ArticleService() {
		articleDao = Container.articleDao;
	}

	public void add(Article article) {
		articleDao.add(article);
	}

	public int getSize() {
		return articleDao.getSize();
	}

	public List<Article> getArticles() {
		return articleDao.getArticles();
	}

	public List<Article> getForPrintArticles(String searchKeyword) {
		List<Article> forPrintArticles = new ArrayList<>();
		if (searchKeyword.length() > 0 || searchKeyword != null) {
			System.out.println("검색어 : " + searchKeyword);

			for (Article article : articleDao.getArticles()) {
				if (article.getTitle().contains(searchKeyword)) {
					forPrintArticles.add(article);
				}
			}
			if (forPrintArticles.size() == 0) {
				System.out.println("  번호     /   작성일  /   작성자   /  제목   /   조회");
				System.out.println("검색 결과 없음");
				return forPrintArticles;
			}
		}
		return forPrintArticles;
	}

	public Article getArticleById(int id) {
		return articleDao.getArticleById(id);
	}

	public void remove(Article article) {
		articleDao.remove(article);
	}

}