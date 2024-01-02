
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		System.out.println("== 프로그램 시작 == ");

		Scanner sc = new Scanner(System.in);

		int lastArticleId = 0;
		List<Article> articles = new ArrayList<>();

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
			if (cmd.equals("article write")) {
				System.out.println("==게시글 작성==");
				int id = lastArticleId + 1;
				String regDate = Util.getNowDate_TimeStr();
				System.out.print("제목 : ");
				String title = sc.nextLine();
				System.out.print("내용 : ");
				String body = sc.nextLine();

				Article article = new Article(id, regDate, title, body);
				articles.add(article);

				System.out.printf("%d번 글이 생성 되었습니다.\n", id);
				lastArticleId++;
			} else if (cmd.equals("article list")) {
				System.out.println("==게시글 목록==");
				if (articles.size() == 0) {
					System.out.println("아무것도 없어");
				} else {
					System.out.println("  번호  /  제목  ");
					for (int i = articles.size() - 1; i >= 0; i--) {
						Article article = articles.get(i);
						System.out.printf("  %4d  /   %s  \n", article.getId(), article.getTitle());
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

				Article foundArticle = null;

				for (int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					if (article.getId() == id) {
						foundArticle = article;
						break;
					}
				}

				if (foundArticle == null) {
					System.out.printf("%d번 게시글은 없습니다\n", id);
				} else {
					System.out.println("번호 : " + foundArticle.getId());
					System.out.println("날짜 : " + foundArticle.getRegDate());
					System.out.println("제목 : " + foundArticle.getTitle());
					System.out.println("내용 : " + foundArticle.getBody());
				}

			} else if (cmd.startsWith("article delete")) {

				String[] cmdDiv = cmd.split(" ");

				int id = 0;

				try {
					id = Integer.parseInt(cmdDiv[2]);
				} catch (Exception e) {
					System.out.println("번호는 정수로 입력해");
					continue;
				}

				int foundIndex = -1;
//				Article foundArticle = null;

				for (int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					if (article.getId() == id) {
						foundIndex = i;
//						foundArticle = article;
						break;
					}
				}

//				if(foundArticle == null)
				if (foundIndex == -1) {
					System.out.printf("%d번 게시글은 없습니다\n", id);
				} else {
//					articles.remove(id-1);
					articles.remove(foundIndex);
					System.out.println(id + "번 글이 삭제되었습니다.");
				}

			} else {
				System.out.println("사용할 수 없는 명령어입니다");
			}
		}

		System.out.println("== 프로그램 끝 == ");

		sc.close();
	}
}

class Article {
	private int id;
	private String regDate;

	private String title;
	private String body;

	public Article(int id, String regDate, String title, String body) {
		this.id = id;
		this.regDate = regDate;
		this.title = title;
		this.body = body;
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
}