import java.time.LocalDateTime;
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
			
			LocalDateTime nowDate = LocalDateTime.now();

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
				System.out.print("제목 : ");
				String title = sc.nextLine();
				System.out.print("내용 : ");
				String body = sc.nextLine();
				LocalDateTime regDate = nowDate;
				
				Article article = new Article(id, title, body, regDate);
				articles.add(article);
//				System.out.println(title + " / " + body);

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
			} else if (cmd.startsWith("article detail ")) { // parse int, split();
				
				String[] cmdDiv = cmd.split(" ");
				System.out.println(cmdDiv[0]);
				System.out.println(cmdDiv[1]);
				System.out.println(cmdDiv[2]);
				
				int id = Integer.parseInt(cmdDiv[2]);
						
				
				System.out.println("==게시글 내용==");
				if (articles.size() == 0) {
					System.out.printf("%d번 게시글은 없습니다. \n", id);
				} else { 
					int i = articles.size()-1;
					Article article = articles.get(i - 1);
					
					
						System.out.println("  번호 : " + article.getId());
						System.out.println("  날짜 : " + article.getRegDate());
						System.out.println("  내용 : " + article.getBody());
					
					
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
	private String title;
	private String body;
	private LocalDateTime regDate;


	public Article(int id, String title, String body, LocalDateTime regDate) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.regDate = regDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	public LocalDateTime getRegDate() {
		return regDate;
	}

	public void LocalDateTime(java.time.LocalDateTime regDate) {
		this.regDate = regDate;
	}
}