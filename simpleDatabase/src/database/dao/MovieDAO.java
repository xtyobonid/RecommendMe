package database.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import database.entity.Movie;
import database.util.DAOException;

public interface MovieDAO {
	
	Movie create(Connection connection, Movie movie) throws SQLException, DAOException;

	Movie retrieve(Connection connection, int id) throws SQLException, DAOException;
	
	int update(Connection connection, Movie movie) throws SQLException, DAOException;
	
	int delete(Connection connection, int id) throws SQLException, DAOException;
	
	//Methods for searching needed below
	
	List<Movie> retrieveByDirector(Connection connection, String director) throws SQLException, DAOException;

	List<Movie> retrieveByTitle(Connection connection, String title) throws SQLException, DAOException;
	
	//retrieve by tag desc is probably the most complicated query but not too hard.
	
	List<Movie> retrieveByTagName(Connection connection, String tag) throws SQLException, DAOException;
}
