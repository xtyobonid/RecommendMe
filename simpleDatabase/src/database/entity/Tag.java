package database.entity;

public class Tag {
	private String description;
	private int category_id;
	private String category_name;
	private int id;

	public Tag(int id, String cat_name, int cat_id, String description) {
		this.id = id;
		this.category_name = cat_name;
		this.category_id = cat_id;
		this.description = description;
	}

	public Tag() {

	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategoryName() {
		return category_name;
	}

	public void setCategoryName(String category_name) {
		this.category_name = category_name;
	}

	public int getCategoryId() {
		return category_id;
	}

	public void setCategoryId(int category_id) {
		this.category_id = category_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
