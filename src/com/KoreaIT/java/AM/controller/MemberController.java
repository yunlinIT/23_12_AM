package com.KoreaIT.java.AM.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.AM.dto.Member;
import com.KoreaIT.java.AM.util.Util;

public class MemberController extends Controller {
	private List<Member> members;
	private Scanner sc;
	private String cmd;
	private Member loginedMember = null;

	int lastMemberId = 3;

	public MemberController(Scanner sc) {
		this.members = new ArrayList<>();
		this.sc = sc;
	}

	public void doAction(String actionMethodName, String cmd) {
		this.cmd = cmd;

		switch (actionMethodName) {
		case "join":
			doJoin();
			break;
		case "login":
			doLogin();
			break;
		case "logout":
			doLogout();
			break;
		default:
			System.out.println("명령어 확인해 (actionMethodName 오류)5");
			break;
		}
	}

	private boolean isLogined() {
		return loginedMember != null;
	}

	private void doLogin() {
		if (isLogined()) {
			System.out.println("이미 로그인 상태야");
			return;
		}
		System.out.println("==로그인==");
		System.out.print("로그인 아이디 : ");
		String loginId = sc.nextLine();
		System.out.print("로그인 비밀번호 : ");
		String loginPw = sc.nextLine();

		// 얘 있나? -> 사용자가 입력한 로그인 아이디랑 일치하는 회원이 나한테 있나?

		Member member = getMemberByLoginId(loginId);

		if (member == null) {
			System.out.println("일치하는 회원이 없어");
			return;
		}

		if (member.getLoginPw().equals(loginPw) == false) {
			System.out.println("비밀번호가 일치하지 않습니다");
			return;
		}

		loginedMember = member;

		System.out.printf("로그인 성공! %s님 반갑습니다.\n", member.getName());

	}

	private void doLogout() {
		if (!isLogined()) {
			System.out.println("이미 로그아웃 상태야");
			return;
		}

		loginedMember = null;

		System.out.printf("로그아웃 성공!\n");

	}

	private void doJoin() {
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

	}

	private Member getMemberByLoginId(String loginId) {
		for (Member member : members) {
			if (member.getLoginId().equals(loginId)) {
				return member;
			}
		}
		return null;
	}

	private boolean isJoinableLoginId(String loginId) {
		for (Member member : members) {
			if (member.getLoginId().equals(loginId)) {
				return false;
			}
		}

		return true;
	}

	public void makeTestData() {
		System.out.println("테스트를 위한 회원 데이터를 생성합니다.");
		members.add(new Member(1, Util.getNowDate_TimeStr(), "admin", "admin", "관리자"));
		members.add(new Member(2, Util.getNowDate_TimeStr(), "test1", "test1", "회원1"));
		members.add(new Member(3, Util.getNowDate_TimeStr(), "test2", "test2", "회원2"));
	}

}