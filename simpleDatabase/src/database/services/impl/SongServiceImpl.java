package database.services.impl;

import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
import javax.sql.DataSource;
import java.util.ArrayList;
import database.util.DAOException;
import database.services.SongService;
import database.entity.Song;
import database.dao.SongDAO;
import database.dao.impl.SongDaoImpl;
import database.entity.Tag;
import database.dao.TagDAO;
import database.dao.impl.TagDaoImpl;

public class SongServiceImpl implements SongService {
    @Override
    public Song create(Song song) throws SQLException, DAOException {
        SongDAO songDAO = new SongDaoImpl();
        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            Song tempSong = songDAO.create(connection, song);
            connection.commit();
            return tempSong;
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
    public Song retrieve(int id) throws SQLException, DAOException {
        SongDAO songDAO = new SongDaoImpl();
        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            Song tempSong = songDAO.retrieve(connection, id);
            connection.commit();
            return tempSong;
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
    public int update(Song song) throws SQLException, DAOException {
        SongDAO songDAO = new SongDaoImpl();
        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            int tempSong = songDAO.update(connection, song);
            connection.commit();
            return tempSong;
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
    public int delete(int id) throws SQLException, DAOException {
        SongDAO songDAO = new SongDaoImpl();
        TagDAO tagDAO = new TagDaoImpl();
        List<Tag> tagList = new ArrayList<Tag>();
        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            tagList = tagDAO.retrieveForSongId(connection, id);
            int prod = songDAO.delete(connection, id);
            for (Tag temp : tagList) {
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
    public List<Song> retrieveBySinger(String singer) throws SQLException, DAOException {
        SongDAO songDAO = new SongDaoImpl();
        Connection connection = dataSource.getConnection();
        List<Song> songList = new ArrayList<Song>();
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            songList = songDAO.retrieveBySinger(connection, singer);
            connection.commit();
            return songList;
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
    public List<Song> retrieveByTitle(String title) throws SQLException, DAOException {
        SongDAO songDAO = new SongDaoImpl();
        Connection connection = dataSource.getConnection();
        List<Song> songList = new ArrayList<Song>();
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            songList = songDAO.retrieveByTitle(connection, title);
            connection.commit();
            return songList;
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
    public List<Song> retrieveByTagName(String tag) throws SQLException, DAOException {
        SongDAO songDAO = new SongDaoImpl();
        Connection connection = dataSource.getConnection();
        List<Song> songList = new ArrayList<Song>();
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            songList = songDAO.retrieveByTagName(connection, tag);
            connection.commit();
            return songList;
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

    public SongServiceImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
