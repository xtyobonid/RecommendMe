package database.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;
import java.util.ArrayList;

import database.dao.TagDAO;
import database.entity.Book;
import database.entity.Tag;
import database.util.DAOException;

public class TagDaoImpl implements TagDAO{
	private static final String insertSQL = "INSERT INTO Tag (description, category_id, category_name) VALUES (?, ?, ?);";

	@Override
	public Tag create(Connection connection, Tag tag) throws SQLException, DAOException {
		if (tag.getId() != 0) {
			throw new DAOException("Trying to insert Tag with NON-NULL ID");
		}

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, tag.getDescription());
			ps.setInt(2, tag.getCategoryId());
			ps.setString(3, tag.getCategoryName());
			ps.executeUpdate();

			// Copy the assigned ID to the book instance.
			ResultSet keyRS = ps.getGeneratedKeys();
			keyRS.next();
			int lastKey = keyRS.getInt(1);
			tag.setId(lastKey);
			return tag;
		} finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}
	
	private static final String selectSQL = "SELECT id, category_name, category_id, description FROM Tag where id = ?";

	@Override
	public Tag retrieve(Connection connection, int id) throws SQLException, DAOException {
		if (id == 0)
			throw new DAOException("Tried to retrieve tag with null id");

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(selectSQL);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next() == false) {
				return null;
			}

			Tag tag = new Tag();
			tag.setId(rs.getInt("id"));
			tag.setCategoryName(rs.getString("category_name"));
			tag.setCategoryId(rs.getInt("category_id"));
			tag.setDescription(rs.getString("description"));
			return tag;
		} finally {
			// if ps is not null and is open... close it
			if ((ps != null) && (ps.isClosed() == false))
				ps.close();
		}

	}

	private static final String updateSQL = "UPDATE Tag SET category_name =?, category_id =?, description =? WHERE id=?";

	@Override
	public int update(Connection connection, Tag tag) throws SQLException, DAOException {
		int id = tag.getId();

		if (id == 0)
			throw new DAOException("Trying to update tag with null id");
		
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(updateSQL);
			ps.setString(1, tag.getCategoryName());
			ps.setInt(2, tag.getCategoryId());
			ps.setString(3, tag.getDescription());
			ps.setInt(4, id);
			
			int rowCount = ps.executeUpdate();
			return rowCount;
		}
		finally {
			if ((ps!=null) && (ps.isClosed()==false))
				ps.close();
		}
	}

	private static final String deleteSQL = "DELETE FROM Tag WHERE id=?";
	
	@Override
	public int delete(Connection connection, int id) throws SQLException, DAOException {
		if (id==0)
			throw new DAOException("Trying to delete tag with null id");
		
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(deleteSQL);
			ps.setLong(1, id);
			//System.out.println("Delete tagid is:"+id);
			
			int rowCount = ps.executeUpdate();
			//System.out.println("Delete rowcount is:"+rowCount);
			if (rowCount!=1)
				throw new DAOException("Delete produced unexpected result");
			else
			return rowCount;
		}
		finally {
			if ((ps!=null) && (ps.isClosed()==false))
				ps.close();
		}
	}
	
	private static final String retrieveForBook = "SELECT * FROM Tag WHERE category_name= 'Book' AND category_id =?";

	public List<Tag> retrieveForBookId(Connection connection, int bookid) throws SQLException, DAOException {
		PreparedStatement ps = null;
		try {
			List<Tag> list = new ArrayList<Tag>();
			ps = connection.prepareStatement(retrieveForBook);
			ps.setInt(1, bookid);
			//System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String description = rs.getString("description");
				String categoryName = "Book";
				int categoryId = bookid;
				// set the retrieved values to the temp object
				Tag temp = new Tag();
				temp.setId(id);
				temp.setDescription(description);
				temp.setCategoryId(categoryId);
				temp.setCategoryName(categoryName);
				list.add(temp);
			}
			return list;
		} finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}
	

	private static final String retrieveForMovie = "SELECT * FROM Tag WHERE category_name= 'Movie' AND category_id =?";

	public List<Tag> retrieveForMovieId(Connection connection, int movieid) throws SQLException, DAOException {
		PreparedStatement ps = null;
		try {
			List<Tag> list = new ArrayList<Tag>();
			ps = connection.prepareStatement(retrieveForMovie);
			ps.setInt(1, movieid);
			//System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String description = rs.getString("description");
				String categoryName = "Movie";
				int categoryId = movieid;
				// set the retrieved values to the temp object
				Tag temp = new Tag();
				temp.setId(id);
				temp.setDescription(description);
				temp.setCategoryId(categoryId);
				temp.setCategoryName(categoryName);
				list.add(temp);
			}
			return list;
		} finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	private static final String retrieveForSong = "SELECT * FROM Tag WHERE category_name='Song' AND category_id =?";
	
	public List<Tag> retrieveForSongId(Connection connection, int songid) throws SQLException, DAOException {
		PreparedStatement ps = null;
		try {
			List<Tag> list = new ArrayList<Tag>();
			ps = connection.prepareStatement(retrieveForSong);
			ps.setInt(1, songid);
			//System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String description = rs.getString("description");
				String categoryName = "Song";
				int categoryId = songid;
				// set the retrieved values to the temp object
				Tag temp = new Tag();
				temp.setId(id);
				temp.setDescription(description);
				temp.setCategoryId(categoryId);
				temp.setCategoryName(categoryName);
				//System.out.println("retrieveForSongId "+id+" "+description+" "+categoryName+" "+categoryId);
				list.add(temp);
			}
			return list;
		} finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}
	
}
