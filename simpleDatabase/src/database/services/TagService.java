package database.services;

import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
import javax.sql.DataSource;
import java.util.ArrayList;
import database.util.DAOException;
import database.entity.Tag;

public interface TagService {
    Tag create(Tag tag) throws SQLException, DAOException;
	
	Tag retrieve(int id) throws SQLException, DAOException;
	
	int update(Tag tag) throws SQLException, DAOException;
	
	int delete(int id) throws SQLException, DAOException;
	
	//Methods for searching needed below
	
	List<Tag> retrieveForBookId(int bookid) throws SQLException, DAOException;
	
	List<Tag> retrieveForMovieId(int movieid) throws SQLException, DAOException;
	
	List<Tag> retrieveForSongId(int songid) throws SQLException, DAOException;

    int deleteByBookId(int bookid) throws SQLException, DAOException;

	int deleteByMovieId(int movieid) throws SQLException, DAOException;

	int deleteBySongId(int songid) throws SQLException, DAOException;
    
}
