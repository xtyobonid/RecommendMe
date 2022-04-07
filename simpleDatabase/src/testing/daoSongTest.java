package testing;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;
import org.junit.Test;

import database.util.DAOException;
import testing.datasourceManager;
import database.dao.SongDAO;
import database.dao.impl.SongDaoImpl;
import database.entity.Song;

public class daoSongTest {
	
private Song buildSong()
	{
		Song result = new Song();
		result.setTitle("Falling");
		result.setSinger("Trevor Daniel");
		result.setDescription("Singer is expressing his strong affection for her and trying to make sure if she feels the same.");
		return result;
	}

	@Test
	public void testCreate() throws Exception{
		DataSource ds = datasourceManager.getDataSource();
		Connection connection = ds.getConnection();
		// Do not commit any changes made by this test.
		connection.setAutoCommit(false);
		try {
			Song song = buildSong();
			SongDAO dao = new SongDaoImpl();

			Song song2 = dao.create(connection, song);
			assertNotNull(song2);
			assertNotNull(song2.getId());
			System.out.println("Generated Key: " + song2.getId());
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
			SongDAO dao = new SongDaoImpl();

			Song song = buildSong();
			song.setId(123); 						// This will cause the
			                                        // create to fail, as create() expects
			dao.create(connection, song);			// a null id.
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
			Song song = buildSong();
			SongDAO dao = new SongDaoImpl();
			// Do not commit any changes made by this test.
			connection.setAutoCommit(false);


			Song song2 = dao.create(connection, song);

			int id = song2.getId();

			Song song3 = dao.retrieve(connection, id);
			assertNotNull(song3);
			assertEquals(song2.getId(), song3.getId());
			assertEquals(song2.getTitle(), song3.getTitle());
			assertEquals(song2.getSinger(), song3.getSinger());
			assertEquals(song2.getDescription(), song3.getDescription());
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
			SongDAO dao = new SongDaoImpl();

			int id = (int)System.currentTimeMillis();		//There should be no song with
			Song song3 = dao.retrieve(connection, id);		//an id equal to System.currentTimeMillis()
			assertNull(song3);
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
			Song song = buildSong();

			SongDAO dao = new SongDaoImpl();


			Song song2 = dao.create(connection, song);
			int id = song2.getId();

			String newTitle = "SATForIdiots" + System.currentTimeMillis();
			song2.setTitle(newTitle);
			int rows = dao.update(connection, song2);
			assertEquals(1, rows);

			Song song3 = dao.retrieve(connection, id);
			assertEquals(newTitle, song3.getTitle());
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
			Song song = buildSong();
			SongDAO dao = new SongDaoImpl();

			Song song2 = dao.create(connection, song);
			int id = song2.getId();

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
	public void testRetrieveByAuthor() throws Exception {
		DataSource ds = datasourceManager.getDataSource();
		Connection connection = ds.getConnection();
		try {
			SongDAO songDao = new SongDaoImpl();

			// WILL ONLY WORK IF DB HAS SONG WITH THIS SINGER
			List<Song> songs = songDao.retrieveBySinger(connection, "2 Chainz");
			assertTrue(songs.size() > 0);
		}
		finally {
			connection.close();
		}
	}

	@Test
	public void testRetrieveByTitle() throws Exception{
		DataSource ds = datasourceManager.getDataSource();
		Connection connection = ds.getConnection();
		try {
			SongDAO songDao = new SongDaoImpl();

			// WILL ONLY WORK IF DB HAS SONG WITH THIS TITLE
			List<Song> songs = songDao.retrieveByTitle(connection, "It's A Vibe");
			assertTrue(songs.size() > 0);
		}
		finally {
			connection.close();
		}
	}

	@Test
	public void testRetrieveByTagName() throws Exception{
		DataSource ds = datasourceManager.getDataSource();
		Connection connection = ds.getConnection();
		try {
			SongDAO songDao = new SongDaoImpl();

			// WILL ONLY WORK IF DB HAS SONG WITH THIS TAG
			List<Song> songs = songDao.retrieveByTagName(connection, "Pop");
			assertTrue(songs.size() > 0);
		}
		finally {
			connection.close();
		}
	}

}