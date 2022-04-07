package testing;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;

import database.util.DAOException;
import testing.datasourceManager;
import database.dao.TagDAO;
import database.dao.impl.TagDaoImpl;
import database.entity.Tag;

public class daoTagTest {

	private Tag buildTag()
	{
		Tag result = new Tag();
		result.setCategoryName("Gaming");
		result.setCategoryId(6);
		result.setDescription("Media based on gaming");
		return result;
	}

	@Test
	public void testCreate() throws Exception{
		DataSource ds = datasourceManager.getDataSource();
		Connection connection = ds.getConnection();
		// Do not commit any changes made by this test.
		connection.setAutoCommit(false);
		try {
			Tag tag = buildTag();
			TagDAO dao = new TagDaoImpl();

			Tag tag2 = dao.create(connection, tag);
			assertNotNull(tag2);
			assertNotNull(tag2.getId());
			System.out.println("Generated Key: " + tag2.getId());
		}
		finally {
			// Do not commit changes made by this test.
			connection.rollback();
			connection.setAutoCommit(true);
			connection.close();
		}
	}

	@Test(expected = DAOException.class)
	public void testCreateFailed() throws Exception
	{
		DataSource ds = datasourceManager.getDataSource();
		Connection connection = ds.getConnection();
		// Do not commit any changes made by this test.
		connection.setAutoCommit(false);
		try {
			TagDAO dao = new TagDaoImpl();

			Tag tag = buildTag();
			tag.setId(123); 						// This will cause the
			                                        // create to fail, as create() expects
			dao.create(connection, tag);			// a null id.
		}
		finally {
			// Do not commit changes made by this test.
			connection.rollback();
			connection.setAutoCommit(true);
			connection.close();
		}
	}

	@Test
	public void testRetrieve() throws Exception
	{
		DataSource ds = datasourceManager.getDataSource();
		Connection connection = ds.getConnection();
		try {
			Tag tag = buildTag();
			TagDAO dao = new TagDaoImpl();
			// Do not commit any changes made by this test.
			connection.setAutoCommit(false);

			Tag tag2 = dao.create(connection, tag);
			int id = tag2.getId();

			Tag tag3 = dao.retrieve(connection, id);
			assertNotNull(tag3);
			assertEquals(tag2.getId(), tag3.getId());
			assertEquals(tag2.getCategoryName(), tag3.getCategoryName());
			assertEquals(tag2.getCategoryId(), tag3.getCategoryId());
			assertEquals(tag2.getDescription(), tag3.getDescription());
		}
		finally {
			// Do not commit changes made by this test.
			connection.rollback();
			connection.setAutoCommit(true);
			connection.close();
		}
	}

	@Test
	public void testRetrieveFailed() throws Exception
	{
		DataSource ds = datasourceManager.getDataSource();
		Connection connection = ds.getConnection();
		try {
			TagDAO dao = new TagDaoImpl();

			int id = (int)System.currentTimeMillis();		//There should be no tag with
			Tag tag3 = dao.retrieve(connection, id);		//an id equal to System.currentTimeMillis()
			assertNull(tag3);
		}
		finally {
			connection.close();
		}
	}

	@Test
	public void testUpdate() throws Exception
	{
		DataSource ds = datasourceManager.getDataSource();
		Connection connection = ds.getConnection();
		// Do not commit any changes made by this test.
		connection.setAutoCommit(false);
		try {
			Tag tag = buildTag();
			TagDAO dao = new TagDaoImpl();

			Tag tag2 = dao.create(connection, tag);
			int id = tag2.getId();

			String newDescription = "Fools" + System.currentTimeMillis();
			tag2.setDescription(newDescription);
			int rows = dao.update(connection, tag2);
			assertEquals(1, rows);

			Tag tag3 = dao.retrieve(connection, id);
			assertEquals(newDescription, tag3.getDescription());
		}
		finally {
			// Do not commit any changes made by this test.
			connection.rollback();
			connection.setAutoCommit(true);
			connection.close();
		}
	}

	@Test
	public void testDelete() throws Exception
	{
		DataSource ds = datasourceManager.getDataSource();
		Connection connection = ds.getConnection();
		// Do not commit any changes made by this test.
		connection.setAutoCommit(false);
		try {
			Tag tag = buildTag();
			TagDAO dao = new TagDaoImpl();

			Tag tag2 = dao.create(connection, tag);
			int id = tag2.getId();

			int rows = dao.delete(connection, id);
			assertEquals(1, rows);
		}
		finally {
			// Do not commit any changes made by this test.
			connection.rollback();
			connection.setAutoCommit(true);
			connection.close();
		}
	}

	@Test
	public void testRetrieveForBookId() throws Exception {
		DataSource ds = datasourceManager.getDataSource();
		Connection connection = ds.getConnection();
		try {
			TagDAO tagDao = new TagDaoImpl();

			// WILL ONLY WORK IF DB HAS BOOK WITH THIS ID
			//List<Tag> tags  = tagDao.retrieveforBookId(connection, 1);
			List<Tag> tags2 = tagDao.retrieveForBookId(connection, 1);
			assertTrue(tags2.size() > 0);
		}
		finally {
			connection.close();
		}
	}

	@Test
	public void testRetrieveForMovieId() throws Exception {
		DataSource ds = datasourceManager.getDataSource();
		Connection connection = ds.getConnection();
		try {
			TagDAO tagDao = new TagDaoImpl();

			// WILL ONLY WORK IF DB HAS MOVIE WITH THIS ID
			List<Tag> tags = tagDao.retrieveForMovieId(connection, 1);
			assertTrue(tags.size() > 0);
		}
		finally {
			connection.close();
		}
	}

	@Test
	public void testRetrieveForSongId() throws Exception {
		DataSource ds = datasourceManager.getDataSource();
		Connection connection = ds.getConnection();
		try {
			TagDAO tagDao = new TagDaoImpl();

			// WILL ONLY WORK IF DB HAS MOVIE WITH THIS ID
			List<Tag> tags = tagDao.retrieveForSongId(connection, 1);
			assertTrue(tags.size() > 0);
		}
		finally {
			connection.close();
		}
	}

}