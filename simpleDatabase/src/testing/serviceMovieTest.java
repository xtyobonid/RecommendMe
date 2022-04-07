package testing;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;

import database.dao.MovieDAO;
import database.dao.TagDAO;
import database.dao.impl.MovieDaoImpl;
import database.dao.impl.TagDaoImpl;
import database.entity.Movie;
import database.entity.Tag;
import database.services.MovieService;
import database.services.impl.MovieServiceImpl;
import database.util.DAOException;
public class serviceMovieTest {

	@Test
	public void testCreate() throws SQLException, DAOException, IOException {
		DataSource dataSource = datasourceManager.getDataSource();
		Connection connection = dataSource.getConnection();

		MovieService mvService = new MovieServiceImpl(dataSource);
		Movie movie = buildMovie();

		Movie movie2 = mvService.create(movie);
		assertNotNull(movie2);
		int movieID = movie2.getId();
		assertNotNull(movieID);
		System.out.println("Generated Key: " + movie2.getId());

		MovieDAO movieDAO = new MovieDaoImpl();
		Movie movie3 = movieDAO.retrieve(connection, movieID);
		assertNotNull(movie3);
	}

	@Test
	public void testRetrieve() throws Exception{
		DataSource dataSource = datasourceManager.getDataSource();
		Movie movie = buildMovie();
		MovieService movieService = new MovieServiceImpl(dataSource);

		Movie movie2 = movieService.create(movie);
		int id = movie2.getId();

		Movie movie3 = movieService.retrieve(id);
		assertNotNull(movie3);
		assertEquals(movie2.getId(), movie3.getId());
		assertEquals(movie2.getTitle(), movie3.getTitle());
		assertEquals(movie2.getDirector(), movie3.getDirector());
		assertEquals(movie2.getDescription(), movie3.getDescription());
	}

	@Test
	public void testUpdate() throws Exception{
		DataSource dataSource = datasourceManager.getDataSource();
		Movie movie = buildMovie();
		MovieService movieService = new MovieServiceImpl(dataSource);
		
		Movie movie2 = movieService.create(movie);
		
		String newTitle = "TryMeFool" + System.currentTimeMillis();
		movie2.setTitle(newTitle);
		int rows = movieService.update(movie2);
		assertEquals(1, rows);
	}

	@Test
	public void testDelete() throws Exception{
		DataSource dataSource = datasourceManager.getDataSource();
		Connection connection = dataSource.getConnection();
		Movie movie = buildMovie();
		MovieService movieService = new MovieServiceImpl(dataSource);
		
		Movie movie2 = movieService.create(movie);
		int movieID = movie2.getId();
		
		int rows = movieService.delete(movieID);
		assertEquals(1, rows);

		// This code assumes that all associated tags will be deleted too
		TagDAO tagDAO = new TagDaoImpl();
		List<Tag> tag = tagDAO.retrieveForMovieId(connection, movieID);
		assertTrue(tag.size()==0);
	}

	@Test
	public void testRetrieveByAuthor() throws Exception {
		DataSource dataSource = datasourceManager.getDataSource();
		MovieService mvService = new MovieServiceImpl(dataSource);
		
		Movie movie = buildMovie();
		movie = mvService.create(movie);
		
		List<Movie> movies = mvService.retrieveByDirector(movie.getDirector()); // Need a movie with this director
		assertTrue(movies.size() > 0);
	}

	@Test
	public void testRetrieveByTitle() throws Exception{
		DataSource dataSource = datasourceManager.getDataSource();
		MovieService mvService = new MovieServiceImpl(dataSource);
		
		Movie movie = buildMovie();
		movie = mvService.create(movie);
		
		List<Movie> movies = mvService.retrieveByTitle(movie.getTitle()); // Need a movie with this title
		assertTrue(movies.size() > 0);
	}

	@Test
	public void testRetrieveByTagName() throws Exception{
		DataSource dataSource = datasourceManager.getDataSource();
		MovieService mvService = new MovieServiceImpl(dataSource);
		
		Movie movie = buildMovie();
		Movie movie2 = mvService.create(movie);
		int movieId = movie2.getId();
		assertTrue(movie2!=null);	//proves movie was created
		
		TagDAO tagDAO = new TagDaoImpl();
		Tag tag = new Tag();
		tag.setCategoryName("Movie");
		tag.setCategoryId(movieId);
		tag.setDescription("Classic");
		
		Connection connection = dataSource.getConnection();
		Tag tag2 = tagDAO.create(connection, tag);
		assertTrue(tag2!=null);	//proves tag was created
		
		List<Movie> movies = mvService.retrieveByTagName(tag2.getDescription()); // Need a movie with this tagname
		assertTrue(movies.size() > 0);
	}

	private Movie buildMovie() {
		Movie result = new Movie();
		result.setTitle("Goodnight Bebop");
		result.setDirector("Jason Dupre");
		result.setDescription("Short film detailing the world of rat vices.");
		return result;
	}
}