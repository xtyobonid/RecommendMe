package testing;


import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;

import database.dao.SongDAO;
import database.dao.TagDAO;
import database.dao.impl.SongDaoImpl;
import database.dao.impl.TagDaoImpl;
import database.entity.Song;
import database.entity.Tag;
import database.services.SongService;
import database.services.impl.SongServiceImpl;
import database.util.DAOException;

public class serviceSongTest {

	@Test
	public void testCreate() throws SQLException, DAOException, IOException {
		DataSource dataSource = datasourceManager.getDataSource();
		Connection connection = dataSource.getConnection();

		SongService sgService = new SongServiceImpl(dataSource);
		Song song = buildSong();

		Song song2 = sgService.create(song);
		assertNotNull(song2);
		int songID = song2.getId();
		assertNotNull(songID);
		System.out.println("Generated Key: " + song2.getId());

		SongDAO songDAO = new SongDaoImpl();
		Song song3 = songDAO.retrieve(connection, songID);
		assertNotNull(song3);
	}

	@Test
	public void testRetrieve() throws Exception{
		DataSource dataSource = datasourceManager.getDataSource();
		Song song = buildSong();
		SongService songService = new SongServiceImpl(dataSource);

		Song song2 = songService.create(song);
		int id = song2.getId();

		Song song3 = songService.retrieve(id);
		assertNotNull(song3);
		assertEquals(song2.getId(), song3.getId());
		assertEquals(song2.getTitle(), song3.getTitle());
		assertEquals(song2.getSinger(), song3.getSinger());
		assertEquals(song2.getDescription(), song3.getDescription());
	}

	@Test
	public void testUpdate() throws Exception{
		DataSource dataSource = datasourceManager.getDataSource();
		Song song = buildSong();
		SongService songService = new SongServiceImpl(dataSource);

		Song song2 = songService.create(song);

		String newTitle = "TryMeFool" + System.currentTimeMillis();
		song2.setTitle(newTitle);
		int rows = songService.update(song2);
		assertEquals(1, rows);
	}

	@Test
	public void testDelete() throws Exception{
		DataSource dataSource = datasourceManager.getDataSource();
		Connection connection = dataSource.getConnection();
		Song song = buildSong();
		SongService songService = new SongServiceImpl(dataSource);

		Song song2 = songService.create(song);
		int songID = song2.getId();

		int rows = songService.delete(songID);
		assertEquals(1, rows);

		// This code assumes that all associated tags will be deleted too
		TagDAO tagDAO = new TagDaoImpl();
		List<Tag> tag = tagDAO.retrieveForSongId(connection, songID);
		assertTrue(tag.size()==0);
	}

	@Test
	public void testRetrieveBySinger() throws Exception {
		DataSource dataSource = datasourceManager.getDataSource();
		SongService sgService = new SongServiceImpl(dataSource);

		Song song = buildSong();
		song = sgService.create(song);

		List<Song> songs = sgService.retrieveBySinger(song.getSinger()); // Need a song with this singer
		assertTrue(songs.size() > 0);
	}

	@Test
	public void testRetrieveByTitle() throws Exception{
		DataSource dataSource = datasourceManager.getDataSource();
		SongService sgService = new SongServiceImpl(dataSource);

		Song song = buildSong();
		song = sgService.create(song);

		List<Song> songs = sgService.retrieveByTitle(song.getTitle()); // Need a song with this title
		assertTrue(songs.size() > 0);
	}

	@Test
	public void testRetrieveByTagName() throws Exception{
		DataSource dataSource = datasourceManager.getDataSource();
		SongService sgService = new SongServiceImpl(dataSource);

		Song song = buildSong();
		Song song2 = sgService.create(song);
		int songId = song2.getId();
		assertTrue(song2!=null);	//proves song was created

		TagDAO tagDAO = new TagDaoImpl();
		Tag tag = new Tag();
		tag.setCategoryName("Song");
		tag.setCategoryId(songId);
		tag.setDescription("Pop");

		Connection connection = dataSource.getConnection();
		Tag tag2 = tagDAO.create(connection, tag);
		assertTrue(tag2!=null);	//proves tag was created

		List<Song> songs = sgService.retrieveByTagName(tag2.getDescription()); // Need a song with this tagname
		assertTrue(songs.size() > 0);
	}


	private Song buildSong() {
		Song result = new Song();
		result.setTitle("Falling");
		result.setSinger("Trevor Daniel");
		result.setDescription("The singer is expressing his strong affection for her and trying to make sure if she feels the same.");
		return result;
	}

}