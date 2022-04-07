package database.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

import database.dao.BookDAO;
import database.entity.Book;
import database.util.DAOException;

public class BookDaoImpl implements BookDAO {
	private static final String insertSQL = "INSERT INTO Book (title, author, description) VALUES (?, ?, ?);";

	@Override
	public Book create(Connection connection, Book book) throws SQLException, DAOException {
		if (book.getId() != 0) {
			throw new DAOException("Trying to insert Customer with NON-NULL ID");
		}

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setString(3, book.getDescription());
			ps.executeUpdate();

			// Copy the assigned ID to the book instance.
			ResultSet keyRS = ps.getGeneratedKeys();
			keyRS.next();
			int lastKey = keyRS.getInt(1);
			book.setId(lastKey);
			return book;
		} finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	private static final String selectSQL = "SELECT id, title, author, description FROM Book where id = ?";

	@Override
	public Book retrieve(Connection connection, int id) throws SQLException, DAOException {
		if (id == 0)
			throw new DAOException("Tried to retrieve book with null id");

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(selectSQL);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next() == false) {
				return null;
			}
			
			Book book = new Book();
			book.setId(rs.getInt("id"));
			book.setTitle(rs.getString("title"));
			book.setAuthor(rs.getString("author"));
			book.setDescription(rs.getString("description"));
			return book;
		} finally {
			// if ps is not null and is open... close it
			if ((ps != null) && (ps.isClosed() == false))
				ps.close();
		}

	}

	private static final String updateSQL = "UPDATE Book SET title =?, author =?, description =? WHERE id=?";

	@Override
	public int update(Connection connection, Book book) throws SQLException, DAOException {
		int id = book.getId();

		if (id == 0)
			throw new DAOException("Trying to update book with null id");

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(updateSQL);
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setString(3, book.getDescription());
			ps.setInt(4, id);

			int rowCount = ps.executeUpdate();
			return rowCount;
		} finally {
			if ((ps != null) && (ps.isClosed() == false))
				ps.close();
		}
	}

	private static final String deleteSQL = "DELETE FROM Book WHERE id=?";

	@Override
	public int delete(Connection connection, int id) throws SQLException, DAOException {
		if (id == 0)
			throw new DAOException("Trying to delete book with null id");

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(deleteSQL);
			ps.setLong(1, id);

			int rowCount = ps.executeUpdate();
			if (rowCount != 1)
				throw new DAOException("Delete produced unexpected result");
			else
				return rowCount;
		} finally {
			if ((ps != null) && (ps.isClosed() == false))
				ps.close();
		}
	}

	private static final String retrieveAuthorSQL = "SELECT * FROM Book WHERE author=?";

	@Override
	public List<Book> retrieveByAuthor(Connection connection, String author) throws SQLException, DAOException {
		PreparedStatement ps = null;
		try {
			List<Book> list = new ArrayList<Book>();
			ps = connection.prepareStatement(retrieveAuthorSQL);
			ps.setString(1, author);
			//System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Book temp = new Book();
				/*
				int id = rs.getInt("id");
				String description = rs.getString("description");
				String title = rs.getString("title");
				String authorName = rs.getString("author");*/
				int id = rs.getInt(1);							//refers to column 1 (id)
				String description = rs.getString(2);			//column 2 (desc)
				String title = rs.getString(3);					//column 3 (title)
				String authorName = rs.getString(4);			//column 4 (author)
				
				// set the retrieved values to the temp object
				temp.setId(id);
				temp.setDescription(description);
				temp.setTitle(title);
				temp.setAuthor(authorName);
				list.add(temp);
			}
			return list;
		} finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	private static final String retrieveTitleSQL = "SELECT * FROM Book where title= ? ";

	public List<Book> retrieveByTitle(Connection connection, String title) throws SQLException, DAOException {
		PreparedStatement ps = null;
		try {
			List<Book> list = new ArrayList<Book>();
			ps = connection.prepareStatement(retrieveTitleSQL);
			ps.setString(1, title);
			//System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Book temp = new Book();
				/*
				int id = rs.getInt("id");
				String description = rs.getString("description");
				String title = rs.getString("title");
				String authorName = rs.getString("author");*/
				int id = rs.getInt(1);							//refers to column 1 (id)
				String description = rs.getString(2);			//column 2 (desc)
				String titleName = rs.getString(3);				//column 3 (title)
				String authorName = rs.getString(4);			//column 4 (author)
				// set the retrieved values to the temp object
				temp.setId(id);
				temp.setDescription(description);
				temp.setTitle(titleName);
				temp.setAuthor(authorName);
				list.add(temp);
			}
			return list;
		} finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}
	
	private static final String retrieveTagSQL = "Select * FROM Book Join Tag on Book.id = Tag.category_id WHERE Tag.category_name='Book' AND Tag.description=?";

	public List<Book> retrieveByTagName(Connection connection, String tag) throws SQLException, DAOException {
		PreparedStatement ps = null;
		try {
			List<Book> list = new ArrayList<Book>();
			ps = connection.prepareStatement(retrieveTagSQL);
			ps.setString(1, tag);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Book temp = new Book();
				/*
				int id = rs.getInt("id");
				String description = rs.getString("description");
				String title = rs.getString("title");
				String authorName = rs.getString("author");*/
				int id = rs.getInt(1);							//refers to column 1 (id)
				String description = rs.getString(2);			//column 2 (desc)
				String title = rs.getString(3);					//column 3 (title)
				String authorName = rs.getString(4);			//column 4 (author)
				// set the retrieved values to the temp object
				temp.setId(id);
				temp.setDescription(description);
				temp.setTitle(title);
				temp.setAuthor(authorName);
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
