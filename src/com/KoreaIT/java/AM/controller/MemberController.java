package com.KoreaIT.java.AM.controller;

import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.AM.dto.Member;
import com.KoreaIT.java.AM.util.Util;

public class MemberController {
	List<Member> members;
	private Scanner sc;

	public MemberController(Scanner sc, List<Member> members) {
		this.members = members;
		this.sc = sc;
	}

	int lastMemberId = 0;

	public void doJoin() {
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

	private boolean isJoinableLoginId(String loginId) {
		for (Member member : members) {
			if (member.getLoginId().equals(loginId)) {
				return false;
			}
		}

		return true;
	}

}