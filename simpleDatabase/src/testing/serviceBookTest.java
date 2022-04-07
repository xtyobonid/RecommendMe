package testing;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;

import database.dao.BookDAO;
import database.dao.TagDAO;
import database.dao.impl.BookDaoImpl;
import database.dao.impl.TagDaoImpl;
import database.entity.Book;
import database.entity.Tag;
import database.services.BookService;
import database.services.impl.BookServiceImpl;
import database.util.DAOException;

public class serviceBookTest {

	@Test
	public void testCreate() throws SQLException, DAOException, IOException {
		DataSource dataSource = datasourceManager.getDataSource();
		Connection connection = dataSource.getConnection();

		BookService bkService = new BookServiceImpl(dataSource);
		Book book = buildBook();

		Book book2 = bkService.create(book);
		assertNotNull(book2);
		int bookID = book2.getId();
		assertNotNull(bookID);
		System.out.println("Generated Key: " + book2.getId());

		BookDAO bookDAO = new BookDaoImpl();
		Book book3 = bookDAO.retrieve(connection, bookID);
		assertNotNull(book3);
	}

	@Test
	public void testRetrieve() throws Exception{
		DataSource dataSource = datasourceManager.getDataSource();
		Book book = buildBook();
		BookService bookService = new BookServiceImpl(dataSource);

		Book book2 = bookService.create(book);
		int id = book2.getId();

		Book book3 = bookService.retrieve(id);
		assertNotNull(book3);
		assertEquals(book2.getId(), book3.getId());
		assertEquals(book2.getTitle(), book3.getTitle());
		assertEquals(book2.getAuthor(), book3.getAuthor());
		assertEquals(book2.getDescription(), book3.getDescription());
	}

	@Test
	public void testUpdate() throws Exception{
		DataSource dataSource = datasourceManager.getDataSource();
		Book book = buildBook();
		BookService bookService = new BookServiceImpl(dataSource);
		
		Book book2 = bookService.create(book);
		
		String newTitle = "TryMeFool" + System.currentTimeMillis();
		book2.setTitle(newTitle);
		int rows = bookService.update(book2);
		assertEquals(1, rows);
	}

	@Test
	public void testDelete() throws Exception{
		DataSource dataSource = datasourceManager.getDataSource();
		Connection connection = dataSource.getConnection();
		Book book = buildBook();
		BookService bookService = new BookServiceImpl(dataSource);
		
		Book book2 = bookService.create(book);
		int bookID = book2.getId();
		
		int rows = bookService.delete(bookID);
		assertEquals(1, rows);

		// This code assumes that all associated tags will be deleted too
		TagDAO tagDAO = new TagDaoImpl();
		List<Tag> tag = tagDAO.retrieveForBookId(connection, bookID);
		assertTrue(tag.size()==0);
	}

	@Test
	public void testRetrieveByAuthor() throws Exception {
		DataSource dataSource = datasourceManager.getDataSource();
		BookService bkService = new BookServiceImpl(dataSource);
		
		Book book = buildBook();
		book = bkService.create(book);
		
		List<Book> books = bkService.retrieveByAuthor(book.getAuthor()); // Need a book with this author
		assertTrue(books.size() > 0);
	}

	@Test
	public void testRetrieveByTitle() throws Exception{
		DataSource dataSource = datasourceManager.getDataSource();
		BookService bkService = new BookServiceImpl(dataSource);
		
		Book book = buildBook();
		book = bkService.create(book);
		
		List<Book> books = bkService.retrieveByTitle(book.getTitle()); // Need a book with this title
		assertTrue(books.size() > 0);
	}

	@Test
	public void testRetrieveByTagName() throws Exception{
		DataSource dataSource = datasourceManager.getDataSource();
		BookService bkService = new BookServiceImpl(dataSource);
		
		Book book = buildBook();
		Book book2 = bkService.create(book);
		int bookId = book2.getId();
		assertTrue(book2!=null);	//proves book was created
		
		TagDAO tagDAO = new TagDaoImpl();
		Tag tag = new Tag();
		tag.setCategoryName("Book");
		tag.setCategoryId(bookId);
		tag.setDescription("Education");
		
		Connection connection = dataSource.getConnection();
		Tag tag2 = tagDAO.create(connection, tag);
		assertTrue(tag2!=null);	//proves tag was created
		
		List<Book> books = bkService.retrieveByTagName(tag2.getDescription()); // Need a book with this tagname
		assertTrue(books.size() > 0);
	}

	
	private Book buildBook() {
		Book result = new Book();
		result.setTitle("ACT For Dummies");
		result.setAuthor("Smallest Dummy");
		result.setDescription("Prepare for the ACT test with ease using these simple tricks!");
		return result;
	}

}
