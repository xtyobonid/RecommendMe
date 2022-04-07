package database.entity;

public class Song {
	private String description;
	private int id;
	private String title;
	private String singer;
	
	public Song(int id, String title, String singer, String description) {
		this.id = id;
		this.title = title;
		this.singer = singer;
		this.description = description;
	}
	
	public Song() {
		
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
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
