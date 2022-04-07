package database.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import database.entity.Tag;
import database.util.DAOException;

public interface TagDAO {

	Tag create(Connection connection, Tag tag) throws SQLException, DAOException;
	
	Tag retrieve(Connection connection, int id) throws SQLException, DAOException;
	
	int update(Connection connection, Tag tag) throws SQLException, DAOException;
	
	int delete(Connection connection, int id) throws SQLException, DAOException;
	
	//Methods for searching needed below
	
	List<Tag> retrieveForBookId(Connection connection, int bookid) throws SQLException, DAOException;
	
	List<Tag> retrieveForMovieId(Connection connection, int movieid) throws SQLException, DAOException;
	
	List<Tag> retrieveForSongId(Connection connection, int songid) throws SQLException, DAOException;

	//List<Tag> retrieveForBookId(Connection connection, int bookid);
	
	//List<Tag> retrieveByTagDesc(Connection connection, String desc) throws SQLException, DAOException;
	//and more...
	
	
	//Tag class needs a way to interact with different classes, such as trying to delete the
	//tags associated with a movie when deleting a movie. (Mostly service tier impl, not sure what methods
	//are needed in the DAO tier for sure)
	//Could be methods like: deleteByBookId, deleteByMovieId, deleteBySongId.
	//Alternatively, to delete tag we could retrieve tagId from BookId, MovieId, songId for all tags
	//with matching category+categoryId and then call delete(tag_id).
	
	//Thinking about it, I think that having specific retrieve by category+category_id (returning tag_id)followed 
	//by using the default CRUD operations in the service tier might be the best way to implement tag
}
