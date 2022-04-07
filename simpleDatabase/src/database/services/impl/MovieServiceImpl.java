package database.services.impl;

import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
import javax.sql.DataSource;
import java.util.ArrayList;
import database.util.DAOException;
import database.services.MovieService;
import database.entity.Movie;
import database.dao.MovieDAO;
import database.dao.impl.MovieDaoImpl;
import database.entity.Tag;
import database.dao.TagDAO;
import database.dao.impl.TagDaoImpl;

public class MovieServiceImpl implements MovieService{
    @Override
    public Movie create(Movie movie) throws SQLException, DAOException{
        MovieDAO movieDAO = new MovieDaoImpl();
        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            Movie tempMovie = movieDAO.create(connection, movie);
            connection.commit();
            return tempMovie;
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
	public Movie retrieve(int id) throws SQLException, DAOException{
        MovieDAO movieDAO = new MovieDaoImpl();
        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            Movie tempMovie = movieDAO.retrieve(connection, id);
            connection.commit();
            return tempMovie;
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
	public int update(Movie movie) throws SQLException, DAOException{
        MovieDAO movieDAO = new MovieDaoImpl();
        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            int tempMovie = movieDAO.update(connection, movie);
            connection.commit();
            return tempMovie;
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
        MovieDAO movieDAO = new MovieDaoImpl();
        TagDAO tagDAO = new TagDaoImpl();
        List<Tag> tagList = new ArrayList<Tag>();
        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            tagList = tagDAO.retrieveForMovieId(connection, id);
            int prod = movieDAO.delete(connection, id);
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
	public List<Movie> retrieveByDirector(String director) throws SQLException, DAOException{
        MovieDAO movieDAO = new MovieDaoImpl();
        Connection connection = dataSource.getConnection();
        List<Movie> movieList = new ArrayList<Movie>();
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            movieList = movieDAO.retrieveByDirector(connection, director);
            connection.commit();
            return movieList;
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
	public List<Movie> retrieveByTitle(String title) throws SQLException, DAOException{
        MovieDAO movieDAO = new MovieDaoImpl();
        Connection connection = dataSource.getConnection();
        List<Movie> movieList = new ArrayList<Movie>();
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            movieList = movieDAO.retrieveByTitle(connection, title);
            connection.commit();
            return movieList;
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
	public List<Movie> retrieveByTagName(String tag) throws SQLException, DAOException{
        MovieDAO movieDAO = new MovieDaoImpl();
        Connection connection = dataSource.getConnection();
        List<Movie> movieList = new ArrayList<Movie>();
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            movieList = movieDAO.retrieveByTagName(connection, tag);
            connection.commit();
            return movieList;
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

    public MovieServiceImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
