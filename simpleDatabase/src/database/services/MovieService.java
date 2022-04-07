package database.services;

import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
import javax.sql.DataSource;
import java.util.ArrayList;
import database.util.DAOException;
import database.entity.Movie;

public interface MovieService {
    Movie create(Movie movie) throws SQLException, DAOException;

	Movie retrieve(int id) throws SQLException, DAOException;
	
	int update(Movie movie) throws SQLException, DAOException;
	
	int delete(int id) throws SQLException, DAOException;
	
	//Methods for searching needed below
	
	List<Movie> retrieveByDirector(String director) throws SQLException, DAOException;
	
	List<Movie> retrieveByTitle(String title) throws SQLException, DAOException;
	
	List<Movie> retrieveByTagName(String tag) throws SQLException, DAOException;
}
