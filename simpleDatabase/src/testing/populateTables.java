package testing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import javax.sql.DataSource;


import database.dao.BookDAO;
import database.dao.SongDAO;
import database.dao.TagDAO;
import database.dao.MovieDAO;
import database.dao.impl.BookDaoImpl;
import database.dao.impl.MovieDaoImpl;
import database.dao.impl.SongDaoImpl;
import database.dao.impl.TagDaoImpl;
import database.entity.Book;
import database.entity.Movie;
import database.entity.Song;
import database.entity.Tag;


public class populateTables
{
	private File bookFile;
	private File movieFile;
	private File songFile;
	private File tagFile;

	private void initialize()
	{
		/*
		bookFile = new File("csvData/books.csv");
		movieFile = new File("csvData/movies.csv");
		songFile = new File("csvData/songs.csv");
		tagFile = new File("csvData/tags.csv");
		*/
		
		bookFile = new File("csvData/books2.csv");
		movieFile = new File("csvData/movies2.csv");
		songFile = new File("csvData/songs2.csv");
		tagFile = new File("csvData/tags2.csv");
	}

	public static void main(String args[])
	{
		try {
			DataSource dataSource = datasourceManager.getDataSource();
			Connection connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			
			populateTables app = new populateTables();
			app.initialize();

			Map<Long, Book> bookMap = app.buildBooks();
			System.out.println("Finished building books: " + bookMap.size());
			//app.insertBooks(connection, bookMap);
			//System.out.println("Finished inserting books");

			Map<Long, Movie> movieMap = app.buildMovies();
			System.out.println("Finished building movies: " + movieMap.size());
			app.insertMovies(connection, movieMap);
			System.out.println("Finished inserting movies");
			
			Map<Long, Song> songMap = app.buildSongs();
			System.out.println("Finished building songs: " + songMap.size());
			//app.insertSongs(connection, songMap);
			//System.out.println("Finished inserting songs");
			
			Map<Long, Tag> tagMap = app.buildTags();
			System.out.println("Finished building tags: " + tagMap.size());
			//app.insertTags(connection, tagMap);
			//System.out.println("Finished inserting tags");

			connection.commit();
			System.out.println("Finished Initializing Database");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	private Map<Long, Movie> buildMovies() throws Exception
	{
		Map<Long, Movie> movieMap = new HashMap<Long, Movie>();
		FileReader fr = new FileReader(movieFile);
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		while ((line = br.readLine()) != null) {
			Object item[] = parseMovie(line);
			movieMap.put((Long) item[0], (Movie) item[1]);
		}
		br.close();
		return movieMap;
	}

	private Map<Long, Book> buildBooks() throws Exception
	{
		Map<Long, Book> custMap = new HashMap<Long, Book>();
		FileReader fr = new FileReader(bookFile);
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		while ((line = br.readLine()) != null) {
			Object item[] = parseBook(line);
			custMap.put((Long) item[0], (Book) item[1]);
		}
		br.close();
		return custMap;
	}
	
	private Map<Long, Song> buildSongs() throws Exception
	{
		Map<Long, Song> songMap = new HashMap<Long, Song>();
		FileReader fr = new FileReader(songFile);
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		while ((line = br.readLine()) != null) {
			Object item[] = parseSong(line);
			songMap.put((Long) item[0], (Song) item[1]);
		}
		br.close();
		return songMap;
	}
	
	private Map<Long, Tag> buildTags() throws Exception
	{
		Map<Long, Tag> tagMap = new HashMap<Long, Tag>();
		FileReader fr = new FileReader(tagFile);
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		while ((line = br.readLine()) != null) {
			Object item[] = parseTag(line);
			tagMap.put((Long) item[0], (Tag) item[1]);
		}
		br.close();
		return tagMap;
	}

	private void insertBooks(Connection connection, Map<Long, Book> bookMap) throws Exception
	{
		for (Book book : bookMap.values()) {
			BookDAO bookDAO = new BookDaoImpl();
			System.out.println(book.getTitle()+" ");
			bookDAO.create(connection, book);
		}
	}

	private void insertMovies(Connection connection, Map<Long, Movie> movieMap) throws Exception
	{
		for (Movie movie : movieMap.values()) {
			MovieDAO movieDAO = new MovieDaoImpl();
			System.out.println(movie.getTitle()+" ");
			movieDAO.create(connection, movie);
		}
	}
	
	private void insertSongs(Connection connection, Map<Long, Song> songMap) throws Exception
	{
		for (Song song : songMap.values()) {
			SongDAO songDAO = new SongDaoImpl();
			System.out.println(song.getTitle()+" ");
			songDAO.create(connection, song);
		}
	}
	
	private void insertTags(Connection connection, Map<Long, Tag> tagMap) throws Exception
	{
		for (Tag tag : tagMap.values()) {
			TagDAO tagDAO = new TagDaoImpl();
			System.out.println(tag.getCategoryName()+" "+tag.getCategoryId()+tag.getDescription());
			tagDAO.create(connection, tag);
		}
	}

	private Object[] parseBook(String line) throws ParseException
	{
		StringTokenizer st = new StringTokenizer(line, ",");
		Book book = new Book();
		Long id = Long.parseLong(st.nextToken());
		book.setTitle(st.nextToken());
		book.setAuthor(st.nextToken());
		book.setDescription(st.nextToken());

		Object[] result = { id, book };
		return result;
	}

	private Object[] parseMovie(String line) throws ParseException
	{
		StringTokenizer st = new StringTokenizer(line, ",");
		Movie movie = new Movie();
		Long id = Long.parseLong(st.nextToken());
		movie.setTitle(st.nextToken());
		movie.setDirector(st.nextToken());
		movie.setDescription(st.nextToken());

		Object[] result = { id, movie };
		return result;
	}
	
	private Object[] parseSong(String line) throws ParseException
	{
		StringTokenizer st = new StringTokenizer(line, ",");
		Song song = new Song();
		Long id = Long.parseLong(st.nextToken());
		//System.out.println(id);
		song.setTitle(st.nextToken());
		song.setSinger(st.nextToken());
		song.setDescription(st.nextToken());

		Object[] result = { id, song };
		return result;
	}
	
	private Object[] parseTag(String line) throws ParseException
	{
		StringTokenizer st = new StringTokenizer(line, ",");
		Tag tag = new Tag();
		Long id = Long.parseLong(st.nextToken());
		tag.setCategoryName(st.nextToken());
		int cat_id = Integer.parseInt(st.nextToken());
		tag.setCategoryId(cat_id);
		tag.setDescription(st.nextToken());

		Object[] result = { id, tag };
		return result;
	}

	
}
