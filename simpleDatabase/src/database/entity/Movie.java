package database.entity;

public class Movie {
	private String description;
	private int id;
	private String title;
	private String director;
	
	public Movie(int id, String title, String director, String description) {
		this.id = id;
		this.title = title;
		this.director = director;
		this.description = description;
	}
	
	public Movie() {
		
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
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
