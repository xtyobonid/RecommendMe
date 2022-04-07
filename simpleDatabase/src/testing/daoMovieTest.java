package testing;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;
import org.junit.Test;

import database.util.DAOException;
import testing.datasourceManager;
import database.dao.MovieDAO;
import database.dao.impl.MovieDaoImpl;
import database.entity.Movie;

public class daoMovieTest {

   private Movie buildMovie()
	{
		Movie result = new Movie();
		result.setTitle("Goodnight Bebop");
		result.setDirector("Jason Dupre");
		result.setDescription("Short film detailing the world of rat vices.");
		return result;
	}


	@Test
	public void testCreate() throws Exception {
   DataSource ds = datasourceManager.getDataSource();
		Connection connection = ds.getConnection();
		// Do not commit any changes made by this test.
		connection.setAutoCommit(false);
		try {
			Movie movie = buildMovie();
			MovieDAO dao = new MovieDaoImpl();

			Movie movie2 = dao.create(connection, movie);
			assertNotNull(movie2);
			assertNotNull(movie2.getId());
			System.out.println("Generated Key: " + movie2.getId());
		}
		finally {
			// Do not commit changes made by this test.
			connection.rollback();
			connection.setAutoCommit(true);
			connection.close();
		}
	}
	
	@Test(expected = DAOException.class)
	public void testCreateFailed() throws Exception {
		DataSource ds = datasourceManager.getDataSource();
		Connection connection = ds.getConnection();
		// Do not commit any changes made by this test.
		connection.setAutoCommit(false);
		try {
			MovieDAO dao = new MovieDaoImpl();

			Movie movie = buildMovie();
			movie.setId(123); 						// This will cause the
			                                        // create to fail, as create() expects
			dao.create(connection, movie);			// a null id.
		}
		finally {
			// Do not commit changes made by this test.
			connection.rollback();
			connection.setAutoCommit(true);
			connection.close();
		}
	}

	@Test
	public void testRetrieve() throws Exception {
		DataSource ds = datasourceManager.getDataSource();
		Connection connection = ds.getConnection();
		try {
			Movie movie = buildMovie();
			MovieDAO dao = new MovieDaoImpl();
			// Do not commit any changes made by this test.
			connection.setAutoCommit(false);

			Movie movie2 = dao.create(connection, movie);
			int id = movie2.getId();

			Movie movie3 = dao.retrieve(connection, id);
			assertNotNull(movie3);
			assertEquals(movie2.getId(), movie3.getId());
			assertEquals(movie2.getTitle(), movie3.getTitle());
			assertEquals(movie2.getDirector(), movie3.getDirector());
			assertEquals(movie2.getDescription(), movie3.getDescription());
		}
		finally {
			// Do not commit changes made by this test.
			connection.rollback();
			connection.setAutoCommit(true);
			connection.close();
		}
	}

	@Test
	public void testUpdate() throws Exception {
		DataSource ds = datasourceManager.getDataSource();
		Connection connection = ds.getConnection();
		// Do not commit any changes made by this test.
		connection.setAutoCommit(false);
		try {
			Movie movie = buildMovie();
			MovieDAO dao = new MovieDaoImpl();

			Movie movie2 = dao.create(connection, movie);
			int id = movie2.getId();

			String newTitle = "SATForIdiots" + System.currentTimeMillis();
			movie2.setTitle(newTitle);
			int rows = dao.update(connection, movie2);
			assertEquals(1, rows);

			Movie movie3 = dao.retrieve(connection, id);
			assertEquals(newTitle, movie3.getTitle());
		}
		finally {
			// Do not commit any changes made by this test.
			connection.rollback();
			connection.setAutoCommit(true);
			connection.close();
		}
	}

	@Test
	public void testDelete() throws Exception {
			DataSource ds = datasourceManager.getDataSource();
			Connection connection = ds.getConnection();
			// Do not commit any changes made by this test.
			connection.setAutoCommit(false);
			try {
				Movie movie = buildMovie();
				MovieDAO dao = new MovieDaoImpl();

				Movie movie2 = dao.create(connection, movie);
				int id = movie2.getId();

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
	public void testRetrieveByDirector() throws Exception {
		DataSource ds = datasourceManager.getDataSource();
		Connection connection = ds.getConnection();
		try {
			MovieDAO moDao = new MovieDaoImpl();

			// WILL ONLY WORK IF DB HAS MOVIE WITH THIS DIRECTOR
			List<Movie> movies = moDao.retrieveByDirector(connection, "David Fincher");
			assertTrue(movies.size() > 0);
		}
		finally {
			connection.close();
		}
	}

	@Test
	public void testRetrieveByTitle() throws Exception {
		DataSource ds = datasourceManager.getDataSource();
		Connection connection = ds.getConnection();
		try {
			MovieDAO movieDao = new MovieDaoImpl();

			// WILL ONLY WORK IF DB HAS MOVIE WITH THIS TITLE
			List<Movie> movies = movieDao.retrieveByTitle(connection, "Fight Club");
			assertTrue(movies.size() > 0);
		}
		finally {
			connection.close();
		}
	}

	@Test
	public void testRetrieveByTagName() throws Exception {
		DataSource ds = datasourceManager.getDataSource();
		Connection connection = ds.getConnection();
		try {
			MovieDAO movieDao = new MovieDaoImpl();

			// WILL ONLY WORK IF DB HAS MOVIE WITH THIS TAG
			List<Movie> movies = movieDao.retrieveByTagName(connection, "Mystery");
			System.out.println(movies.size());
			assertTrue(movies.size() > 0);
		}
		finally {
			connection.close();
		}
	}

}