package database.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import database.entity.Book;
import database.util.DAOException;

public interface BookDAO {

	Book create(Connection connection, Book book) throws SQLException, DAOException;

	Book retrieve(Connection connection, int id) throws SQLException, DAOException;
	
	int update(Connection connection, Book book) throws SQLException, DAOException;
	
	int delete(Connection connection, int id) throws SQLException, DAOException;
	
	//Methods for searching needed below
	
	List<Book> retrieveByAuthor(Connection connection, String author) throws SQLException, DAOException;
	
	List<Book> retrieveByTitle(Connection connection, String title) throws SQLException, DAOException;
	
	//retrieve by tag desc is probably the most complicated query but not too hard.
	
	List<Book> retrieveByTagName(Connection connection, String tag) throws SQLException, DAOException;
}
