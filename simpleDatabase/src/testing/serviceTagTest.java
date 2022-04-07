package testing;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;

import database.dao.TagDAO;
import database.dao.impl.TagDaoImpl;
import database.entity.Tag;
import database.services.TagService;
import database.services.impl.TagServiceImpl;
import database.util.DAOException;
public class serviceTagTest {

	@Test
	public void testCreate() throws SQLException, DAOException, IOException {
		DataSource dataSource = datasourceManager.getDataSource();
		Connection connection = dataSource.getConnection();

		TagService mvService = new TagServiceImpl(dataSource);
		Tag tag = buildTag();

		Tag tag2 = mvService.create(tag);
		assertNotNull(tag2);
		int tagID = tag2.getId();
		assertNotNull(tagID);
		System.out.println("Generated Key: " + tag2.getId());

		TagDAO tagDAO = new TagDaoImpl();
		Tag tag3 = tagDAO.retrieve(connection, tagID);
		assertNotNull(tag3);
	}

	@Test
	public void testRetrieve() throws Exception{
		DataSource dataSource = datasourceManager.getDataSource();
		Tag tag = buildTag();
		TagService tagService = new TagServiceImpl(dataSource);

		Tag tag2 = tagService.create(tag);
		int id = tag2.getId();

		Tag tag3 = tagService.retrieve(id);
		assertNotNull(tag3);
		assertEquals(tag2.getId(), tag3.getId());
		assertEquals(tag2.getCategoryName(), tag3.getCategoryName());
		assertEquals(tag2.getCategoryId(), tag3.getCategoryId());
		assertEquals(tag2.getDescription(), tag3.getDescription());
	}

	@Test
	public void testUpdate() throws Exception{
		DataSource dataSource = datasourceManager.getDataSource();
		Tag tag = buildTag();
		TagService tagService = new TagServiceImpl(dataSource);

		Tag tag2 = tagService.create(tag);

		String newCategoryName = "TryMeFool" + System.currentTimeMillis();
		tag2.setCategoryName(newCategoryName);
		int rows = tagService.update(tag2);
		assertEquals(1, rows);
	}

	@Test
	public void testDelete() throws Exception{
		DataSource dataSource = datasourceManager.getDataSource();
		Tag tag = buildTag();
		TagService tagService = new TagServiceImpl(dataSource);

		Tag tag2 = tagService.create(tag);
		int tagID = tag2.getId();

		int rows = tagService.delete(tagID);
		assertEquals(1, rows);
	}

	@Test
	public void testRetrieveForBookId() throws Exception {
		DataSource dataSource = datasourceManager.getDataSource();
		TagService tgService = new TagServiceImpl(dataSource);

		List<Tag> tags = tgService.retrieveForBookId(2); // Need a book with this id
		assertTrue(tags.size() > 0);
	}

	@Test
	public void testRetrieveForMovieId() throws Exception {
		DataSource dataSource = datasourceManager.getDataSource();
		TagService tgService = new TagServiceImpl(dataSource);

		List<Tag> tags = tgService.retrieveForMovieId(2); // Need a movie with this id
		assertTrue(tags.size() > 0);
	}

	@Test
	public void testRetrieveForSongId() throws Exception {
		DataSource dataSource = datasourceManager.getDataSource();
		TagService tgService = new TagServiceImpl(dataSource);

		List<Tag> tags = tgService.retrieveForSongId(2); // Need a song with this id
		assertTrue(tags.size() > 0);
	}

	@Test
	public void testDeleteByBookId() throws Exception {
		DataSource dataSource = datasourceManager.getDataSource();
		TagService tgService = new TagServiceImpl(dataSource);

		int tags = tgService.deleteByBookId(1); // Need a book with this id
		assertTrue(tags > 0);
	}

	@Test
	public void testDeleteByMovieId() throws Exception {
		DataSource dataSource = datasourceManager.getDataSource();
		TagService tgService = new TagServiceImpl(dataSource);

		int tags = tgService.deleteByMovieId(1); // Need a movie with this id
		assertTrue(tags > 0);
	}

	@Test
	public void testDeleteBySongId() throws Exception {
		DataSource dataSource = datasourceManager.getDataSource();
		TagService tgService = new TagServiceImpl(dataSource);

		int tags = tgService.deleteBySongId(1); // Need a song with this id
		assertTrue(tags > 0);
	}

	private Tag buildTag() {
		Tag result = new Tag();
		result.setCategoryName("Gaming");
		result.setCategoryId(6);
		result.setDescription("Short film detailing the world of rat vices.");
		return result;
	}
}