package database.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import database.entity.Song;
import database.util.DAOException;

public interface SongDAO {

	Song create(Connection connection, Song song) throws SQLException, DAOException;
	
	Song retrieve(Connection connection, int id) throws SQLException, DAOException;
	
	int update(Connection connection, Song song) throws SQLException, DAOException;
	
	int delete(Connection connection, int id) throws SQLException, DAOException;
	
	//Methods for searching needed below
	
	List<Song> retrieveBySinger(Connection connection, String singer) throws SQLException, DAOException;
	
	List<Song> retrieveByTitle(Connection connection, String title) throws SQLException, DAOException;
	
	//retrieve by tag desc is probably the most complicated query but not too hard.
	
	List<Song> retrieveByTagName(Connection connection, String tag) throws SQLException, DAOException;
}
