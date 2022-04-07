package database.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

import database.dao.MovieDAO;
import database.entity.Movie;
import database.util.DAOException;

public class MovieDaoImpl implements MovieDAO {
	private static final String insertSQL = "INSERT INTO Movie (title, director, description) VALUES (?, ?, ?);";

	@Override
	public Movie create(Connection connection, Movie movie) throws SQLException, DAOException {
		if (movie.getId() != 0) {
			throw new DAOException("Trying to insert Movie with NON-NULL ID");
		}

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, movie.getTitle());
			ps.setString(2, movie.getDirector());
			ps.setString(3, movie.getDescription());
			ps.executeUpdate();

			// Copy the assigned ID to the book instance.
			ResultSet keyRS = ps.getGeneratedKeys();
			keyRS.next();
			int lastKey = keyRS.getInt(1);
			movie.setId(lastKey);
			return movie;
		} finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	private static final String selectSQL = "SELECT id, title, director, description FROM Movie where id = ?";

	@Override
	public Movie retrieve(Connection connection, int id) throws SQLException, DAOException {
		if (id == 0)
			throw new DAOException("Tried to retrieve movie with null id");

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(selectSQL);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next() == false) {
				return null;
			}

			Movie movie = new Movie();
			movie.setId(rs.getInt("id"));
			movie.setTitle(rs.getString("title"));
			movie.setDirector(rs.getString("director"));
			movie.setDescription(rs.getString("description"));
			return movie;
		} finally {
			// if ps is not null and is open... close it
			if ((ps != null) && (ps.isClosed() == false))
				ps.close();
		}

	}

	private static final String updateSQL = "UPDATE Movie SET title =?, director =?, description =? WHERE id=?";

	@Override
	public int update(Connection connection, Movie movie) throws SQLException, DAOException {
		int id = movie.getId();

		if (id == 0)
			throw new DAOException("Trying to update movie with null id");

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(updateSQL);
			ps.setString(1, movie.getTitle());
			ps.setString(2, movie.getDirector());
			ps.setString(3, movie.getDescription());
			ps.setInt(4, id);

			int rowCount = ps.executeUpdate();
			return rowCount;
		} finally {
			if ((ps != null) && (ps.isClosed() == false))
				ps.close();
		}
	}

	private static final String deleteSQL = "DELETE FROM Movie WHERE id=?";

	@Override
	public int delete(Connection connection, int id) throws SQLException, DAOException {
		if (id == 0)
			throw new DAOException("Trying to delete movie with null id");

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

	private static final String retrieveDirectorSQL = "SELECT * FROM Movie WHERE director=?";

	public List<Movie> retrieveByDirector(Connection connection, String director) throws SQLException, DAOException {
		PreparedStatement ps = null;
		try {
			List<Movie> list = new ArrayList<Movie>();
			ps = connection.prepareStatement(retrieveDirectorSQL);
			ps.setString(1, director);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				/*
				int id = rs.getInt("id");
				String description = rs.getString("description");
				String titleName = rs.getString("title");
				String directorName = rs.getString("director"); */
				int id = rs.getInt(1);							//Column 1 (id)
				String description = rs.getString(2);			//Column 2 (desc)
				String titleName = rs.getString(3);				//Column 3 (title)
				String directorName = rs.getString(4);			//Column 4 (director)
				// set the retrieved values to the temp object
				Movie temp = new Movie();
				temp.setId(id);
				temp.setDescription(description);
				temp.setTitle(titleName);
				temp.setDirector(directorName);
				list.add(temp);
			}
			return list;
		} finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	private static final String retrieveTitleSQL = "SELECT * FROM Movie WHERE title=?";

	public List<Movie> retrieveByTitle(Connection connection, String title) throws SQLException, DAOException {
		PreparedStatement ps = null;
		try {
			List<Movie> list = new ArrayList<Movie>();
			ps = connection.prepareStatement(retrieveTitleSQL);
			ps.setString(1, title);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				/*
				int id = rs.getInt("id");
				String description = rs.getString("description");
				String titleName = rs.getString("title");
				String directorName = rs.getString("director"); */
				int id = rs.getInt(1);							//Column 1 (id)
				String description = rs.getString(2);			//Column 2 (desc)
				String titleName = rs.getString(3);				//Column 3 (title)
				String directorName = rs.getString(4);			//Column 4 (director)
				// set the retrieved values to the temp object
				Movie temp = new Movie();
				temp.setId(id);
				temp.setDescription(description);
				temp.setTitle(titleName);
				temp.setDirector(directorName);
				list.add(temp);
			}
			return list;
		} finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}
	
	private static final String retrieveTagSQL = "Select * FROM Movie Join Tag on Movie.id = Tag.category_id WHERE Tag.category_name='Movie' AND Tag.description=?";
	
	public List<Movie> retrieveByTagName(Connection connection, String tag) throws SQLException, DAOException {
		PreparedStatement ps = null;
		try {
			List<Movie> list = new ArrayList<Movie>();
			ps = connection.prepareStatement(retrieveTagSQL);
			ps.setString(1, tag);
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				/*

				int id = rs.getInt("id");
				String description = rs.getString("description");
				String titleName = rs.getString("title");
				String directorName = rs.getString("director"); */
				int id = rs.getInt(1);							//Column 1 (id)
				String description = rs.getString(2);			//Column 2 (desc)
				String titleName = rs.getString(3);				//Column 3 (title)
				String directorName = rs.getString(4);			//Column 4 (director)
				// set the retrieved values to the temp object
				Movie temp = new Movie();
				temp.setId(id);
				temp.setDescription(description);
				temp.setTitle(titleName);
				temp.setDirector(directorName);
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
