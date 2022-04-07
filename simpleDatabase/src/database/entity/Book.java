package database.entity;

public class Book {

	private String description;
	private int id;
	private String title;
	private String author;

	public Book(int id, String title, String author, String description) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.description = description;
	}
	
	public Book() {
		
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
