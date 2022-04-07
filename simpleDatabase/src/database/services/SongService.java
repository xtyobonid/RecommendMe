package database.services;

import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
import javax.sql.DataSource;
import java.util.ArrayList;
import database.util.DAOException;
import database.entity.Song;

public interface SongService {
    Song create(Song song) throws SQLException, DAOException;

	Song retrieve(int id) throws SQLException, DAOException;
	
	int update(Song song) throws SQLException, DAOException;
	
	int delete(int id) throws SQLException, DAOException;
	
	//Methods for searching needed below
	
	List<Song> retrieveBySinger(String singer) throws SQLException, DAOException;
	
	List<Song> retrieveByTitle(String title) throws SQLException, DAOException;
	
	List<Song> retrieveByTagName(String tag) throws SQLException, DAOException;
}
