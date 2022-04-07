package database.services.impl;

import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
import javax.sql.DataSource;
import java.util.ArrayList;
import database.util.DAOException;
import database.services.BookService;
import database.entity.Book;
import database.dao.BookDAO;
import database.dao.impl.BookDaoImpl;
import database.entity.Tag;
import database.dao.TagDAO;
import database.dao.impl.TagDaoImpl;

public class BookServiceImpl implements BookService{
    @Override
    public Book create(Book book) throws SQLException, DAOException{
        BookDAO bookDAO = new BookDaoImpl();
        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            Book tempBook = bookDAO.create(connection, book);
            connection.commit();
            return tempBook;
        } catch (Exception ex) {
            connection.rollback();
            throw ex;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }

    @Override
	public Book retrieve(int id) throws SQLException, DAOException{
        BookDAO bookDAO = new BookDaoImpl();
        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            Book tempBook = bookDAO.retrieve(connection, id);
            connection.commit();
            return tempBook;
        } catch (Exception ex) {
            connection.rollback();
            throw ex;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }
	
    @Override
	public int update(Book book) throws SQLException, DAOException{
        BookDAO bookDAO = new BookDaoImpl();
        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            int tempBook = bookDAO.update(connection, book);
            connection.commit();
            return tempBook;
        } catch (Exception ex) {
            connection.rollback();
            throw ex;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }
	
    @Override
	public int delete(int id) throws SQLException, DAOException{
        BookDAO bookDAO = new BookDaoImpl();
        TagDAO tagDAO = new TagDaoImpl();
        List<Tag> tagList = new ArrayList<Tag>();
        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            tagList = tagDAO.retrieveForBookId(connection, id);
            int prod = bookDAO.delete(connection, id);
            for(Tag temp : tagList){
                prod += tagDAO.delete(connection, temp.getId());
            }
            connection.commit();
            return prod;
        } catch (Exception ex) {
            connection.rollback();
            throw ex;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }
	
    @Override
	public List<Book> retrieveByAuthor(String author) throws SQLException, DAOException{
        BookDAO bookDAO = new BookDaoImpl();
        Connection connection = dataSource.getConnection();
        List<Book> bookList = new ArrayList<Book>();
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            bookList = bookDAO.retrieveByAuthor(connection, author);
            connection.commit();
            return bookList;
        } catch (Exception ex) {
            connection.rollback();
            throw ex;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }
	
    @Override
	public List<Book> retrieveByTitle(String title) throws SQLException, DAOException{
        BookDAO bookDAO = new BookDaoImpl();
        Connection connection = dataSource.getConnection();
        List<Book> bookList = new ArrayList<Book>();
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            bookList = bookDAO.retrieveByTitle(connection, title);
            connection.commit();
            return bookList;
        } catch (Exception ex) {
            connection.rollback();
            throw ex;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }
	
    @Override
	public List<Book> retrieveByTagName(String tag) throws SQLException, DAOException{
        BookDAO bookDAO = new BookDaoImpl();
        Connection connection = dataSource.getConnection();
        List<Book> bookList = new ArrayList<Book>();
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            bookList = bookDAO.retrieveByTagName(connection, tag);
            connection.commit();
            return bookList;
        } catch (Exception ex) {
            connection.rollback();
            throw ex;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
    }

    private DataSource dataSource;

    public BookServiceImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
