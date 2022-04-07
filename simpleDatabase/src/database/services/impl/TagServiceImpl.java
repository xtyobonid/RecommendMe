package database.services.impl;

import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
import javax.sql.DataSource;
import java.util.ArrayList;
import database.util.DAOException;
import database.services.TagService;
import database.entity.Tag;
import database.dao.TagDAO;
import database.dao.impl.TagDaoImpl;

public class TagServiceImpl implements TagService {
    @Override
    public Tag create(Tag tag) throws SQLException, DAOException {
        TagDAO tagDAO = new TagDaoImpl();
        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            Tag tempTag = tagDAO.create(connection, tag);
            connection.commit();
            return tempTag;
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
    public Tag retrieve(int id) throws SQLException, DAOException {
        TagDAO tagDAO = new TagDaoImpl();
        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            Tag tempTag = tagDAO.retrieve(connection, id);
            connection.commit();
            return tempTag;
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
    public int update(Tag tag) throws SQLException, DAOException {
        TagDAO tagDAO = new TagDaoImpl();
        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            int tempTag = tagDAO.update(connection, tag);
            connection.commit();
            return tempTag;
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
        TagDAO tagDAO = new TagDaoImpl();
        Connection connection = dataSource.getConnection();
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            int tag = tagDAO.delete(connection, id);
            connection.commit();
            return tag;
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
    public List<Tag> retrieveForBookId(int bookid) throws SQLException, DAOException {
        TagDAO tagDAO = new TagDaoImpl();
        Connection connection = dataSource.getConnection();
        List<Tag> tagList = new ArrayList<Tag>();
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            tagList = tagDAO.retrieveForBookId(connection, bookid);
            connection.commit();
            return tagList;
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
    public List<Tag> retrieveForMovieId(int movieid) throws SQLException, DAOException {
        TagDAO tagDAO = new TagDaoImpl();
        Connection connection = dataSource.getConnection();
        List<Tag> tagList = new ArrayList<Tag>();
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            tagList = tagDAO.retrieveForMovieId(connection, movieid);
            connection.commit();
            return tagList;
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
    public List<Tag> retrieveForSongId(int songid) throws SQLException, DAOException {
        TagDAO tagDAO = new TagDaoImpl();
        Connection connection = dataSource.getConnection();
        List<Tag> tagList = new ArrayList<Tag>();
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            tagList = tagDAO.retrieveForSongId(connection, songid);
            connection.commit();
            return tagList;
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
    public int deleteByBookId(int bookid) throws SQLException, DAOException{
        TagDAO tagDAO = new TagDaoImpl();
        Connection connection = dataSource.getConnection();
        List<Tag> tagList = new ArrayList<Tag>();
        int toReturn = 0;
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            tagList = tagDAO.retrieveForBookId(connection, bookid);
            //System.out.println("tagList of Books contains: "+tagList.size());
            for(Tag temp : tagList){
                toReturn += tagDAO.delete(connection, temp.getId());
            }
            connection.commit();
            return toReturn;
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
    public int deleteByMovieId(int movieid) throws SQLException, DAOException{
        TagDAO tagDAO = new TagDaoImpl();
        Connection connection = dataSource.getConnection();
        List<Tag> tagList = new ArrayList<Tag>();
        int toReturn = 0;
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            tagList = tagDAO.retrieveForMovieId(connection, movieid);
            //System.out.println("tagList of Movies contains: "+tagList.size());
            for(Tag temp : tagList){
                toReturn += tagDAO.delete(connection, temp.getId());
            }
            connection.commit();
            return toReturn;
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
    public int deleteBySongId(int songid) throws SQLException, DAOException{
        TagDAO tagDAO = new TagDaoImpl();
        Connection connection = dataSource.getConnection();
        List<Tag> tagList = new ArrayList<Tag>();
        int toReturn = 0;
        try {
            connection.setAutoCommit(false); // Starts new Transaction on Connection
            tagList = tagDAO.retrieveForSongId(connection, songid);
            //System.out.println("tagList of Songs contains: "+tagList.size());
            for(Tag temp : tagList){
            	//System.out.println(temp.getId()+" "+temp.getDescription()+" "+temp.getCategoryName()+" "+temp.getCategoryId());
                toReturn += tagDAO.delete(connection, temp.getId());
            }
            connection.commit();
            return toReturn;
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

    public TagServiceImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
