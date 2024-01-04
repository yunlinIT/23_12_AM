package com.KoreaIT.java.AM.dto;

import com.KoreaIT.java.AM.util.Util;

public class Article extends Dto {

	private String updateDate;

	private String title;
	private String body;

	private int memberId;

	private int hit;

	public Article(int id, String regDate, String updateDate, int memberId, String title, String body) {
		this(id, regDate, updateDate, memberId, title, body, 0);
	}

	public Article(int id, String regDate, String updateDate, int memberId, String title, String body, int hit) {
		this.id = id;
		this.regDate = regDate;
		this.updateDate = updateDate;
		this.memberId = memberId;
		this.title = title;
		this.body = body;
		this.hit = hit;
	}

	public void update(String newTitle, String newBody) {
		this.setUpdateDate(Util.getNowDate_TimeStr());
		this.setTitle(newTitle);
		this.setBody(newBody);
	}

	public void increaseHit() {
		this.hit++;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

}