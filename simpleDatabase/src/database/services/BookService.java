package database.services;

import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
import javax.sql.DataSource;
import java.util.ArrayList;
import database.util.DAOException;
import database.entity.Book;

public interface BookService {
    Book create(Book book) throws SQLException, DAOException;

	Book retrieve(int id) throws SQLException, DAOException;
	
	int update(Book book) throws SQLException, DAOException;
	
	int delete(int id) throws SQLException, DAOException;
	
	//Methods for searching needed below
	
	List<Book> retrieveByAuthor(String author) throws SQLException, DAOException;
	
	List<Book> retrieveByTitle(String title) throws SQLException, DAOException;
	
	//retrieve by tag desc is probably the most complicated query but not too hard.
	
	List<Book> retrieveByTagName(String tag) throws SQLException, DAOException;
}
