package testing;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;
import org.junit.Test;

import database.util.DAOException;
import testing.datasourceManager;
import database.dao.BookDAO;
import database.dao.impl.BookDaoImpl;
import database.entity.Book;

public class daoBookTest {

	private Book buildBook()
	{
		Book result = new Book();
		result.setTitle("SAT For Dummies");
		result.setAuthor("Biggest Dummy");
		result.setDescription("Prepare for the SAT test with ease using these simple tricks!");
		return result;
	}

	@Test
	public void testCreate() throws Exception{
		DataSource ds = datasourceManager.getDataSource();
		Connection connection = ds.getConnection();
		// Do not commit any changes made by this test.
		connection.setAutoCommit(false);
		try {
			Book book = buildBook();
			BookDAO dao = new BookDaoImpl();

			Book book2 = dao.create(connection, book);
			assertNotNull(book2);
			assertNotNull(book2.getId());
			System.out.println("Generated Key: " + book2.getId());
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
			BookDAO dao = new BookDaoImpl();

			Book book = buildBook();
			book.setId(123); 						// This will cause the
			                                        // create to fail, as create() expects
			dao.create(connection, book);			// a null id.
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
			Book book = buildBook();
			BookDAO dao = new BookDaoImpl();
			// Do not commit any changes made by this test.
			connection.setAutoCommit(false);

			Book book2 = dao.create(connection, book);
			int id = book2.getId();

			Book book3 = dao.retrieve(connection, id);
			assertNotNull(book3);
			assertEquals(book2.getId(), book3.getId());
			assertEquals(book2.getTitle(), book3.getTitle());
			assertEquals(book2.getAuthor(), book3.getAuthor());
			assertEquals(book2.getDescription(), book3.getDescription());
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
			BookDAO dao = new BookDaoImpl();

			int id = (int)System.currentTimeMillis();		//There should be no book with
			Book book3 = dao.retrieve(connection, id);		//an id equal to System.currentTimeMillis()
			assertNull(book3);
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
			Book book = buildBook();
			BookDAO dao = new BookDaoImpl();

			Book book2 = dao.create(connection, book);
			int id = book2.getId();

			String newTitle = "SATForIdiots" + System.currentTimeMillis();
			book2.setTitle(newTitle);
			int rows = dao.update(connection, book2);
			assertEquals(1, rows);

			Book book3 = dao.retrieve(connection, id);
			assertEquals(newTitle, book3.getTitle());
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
			Book book = buildBook();
			BookDAO dao = new BookDaoImpl();

			Book book2 = dao.create(connection, book);
			int id = book2.getId();

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
			BookDAO bookDao = new BookDaoImpl();

			// WILL ONLY WORK IF DB HAS BOOK WITH THIS AUTHOR
			List<Book> books = bookDao.retrieveByAuthor(connection, "Jane Austen");
			assertTrue(books.size() > 0);
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
			BookDAO bookDao = new BookDaoImpl();

			// WILL ONLY WORK IF DB HAS BOOK WITH THIS TITLE
			List<Book> books = bookDao.retrieveByTitle(connection, "Pride and Prejudice");
			assertTrue(books.size() > 0);
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
			BookDAO bookDao = new BookDaoImpl();

			// WILL ONLY WORK IF DB HAS BOOK WITH THIS TAG
			List<Book> books = bookDao.retrieveByTagName(connection, "Classical");
			assertTrue(books.size() > 0);
		}
		finally {
			connection.close();
		}
	}

}
