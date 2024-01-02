
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	static List<Article> articles = new ArrayList<>();

	public static void main(String[] args) {
		System.out.println("== 프로그램 시작 == ");

		makeTestData();

		Scanner sc = new Scanner(System.in);

		int lastArticleId = 3;

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
				String updateDate = regDate;
				System.out.print("제목 : ");
				String title = sc.nextLine();
				System.out.print("내용 : ");
				String body = sc.nextLine();

				Article article = new Article(id, regDate, updateDate, title, body);
				articles.add(article);

				System.out.printf("%d번 글이 생성 되었습니다.\n", id);
				lastArticleId++;
			} else if (cmd.equals("article list")) {
				System.out.println("==게시글 목록==");
				if (articles.size() == 0) {
					System.out.println("아무것도 없어");
				} else {
					System.out.println("  번호  /  제목    /   작성일     /   조회");
					for (int i = articles.size() - 1; i >= 0; i--) {
						Article article = articles.get(i);
						if (Util.getNowDate_TimeStr().split(" ")[0].equals(article.getRegDate().split(" ")[0])) {
							System.out.printf("  %4d  /   %s    /     %s   /   %d\n", article.getId(),
									article.getTitle(), article.getRegDate().split(" ")[1], article.getHit());
						} else {
							System.out.printf("  %4d  /   %s    /     %s   /   %d\n", article.getId(),
									article.getTitle(), article.getRegDate().split(" ")[0], article.getHit());
						}

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

	private static Article getArticleById(int id) {
		for (int i = 0; i < articles.size(); i++) {
			Article article = articles.get(i);
			if (article.getId() == id) {
				return article;
			}
		}
		return null;
	}

	private static void makeTestData() {
		System.out.println("테스트를 위한 데이터를 생성합니다.");
		articles.add(new Article(1, "2023-12-12 12:12:12", Util.getNowDate_TimeStr(), "제목1", "내용1", 11));
		articles.add(new Article(2, "2024-01-01 12:12:12", Util.getNowDate_TimeStr(), "제목2", "내용2", 22));
		articles.add(new Article(3, Util.getNowDate_TimeStr(), Util.getNowDate_TimeStr(), "제목3", "내용3", 33));
	}
}

class Article {
	private int id;
	private String regDate;
	private String updateDate;

	private String title;
	private String body;

	private int hit;

	public Article(int id, String regDate, String updateDate, String title, String body) {
		this(id, regDate, updateDate, title, body, 0);
	}

	public Article(int id, String regDate, String updateDate, String title, String body, int hit) {
		this.id = id;
		this.regDate = regDate;
		this.updateDate = updateDate;
		this.title = title;
		this.body = body;
		this.hit = hit;
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