package database;

import javax.swing.JFrame;
import java.util.List;
import javax.sql.DataSource;
import testing.datasourceManager;
import database.entity.Book;
import database.entity.Movie;
import database.entity.Song;
import database.entity.Tag;
import database.services.BookService;
import database.services.MovieService;
import database.services.SongService;
import database.services.TagService;
import database.services.impl.BookServiceImpl;
import database.services.impl.MovieServiceImpl;
import database.services.impl.SongServiceImpl;
import database.services.impl.TagServiceImpl;
import database.util.DAOException;

import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.AbstractButton;
import javax.swing.JOptionPane;

public class RecommendMe extends JFrame implements ActionListener {

	private static final Color BACKGROUND_GUI_COLOR = new Color(128, 128, 128);
	private static final Color BUTTON_COLOR = new Color(90, 90, 90);

	// Main Menu
	private static JLabel title;
	private static JButton accessStaffLogin;
	private static JButton searchBooksGUI;
	private static JButton searchSongsGUI;
	private static JButton searchMoviesGUI;

	// Staff Login
	private static JTextField username;
	private static JTextField password;
	private static JButton loginButton;

	// Book Search Option Menu
	private static JLabel bookSearchHeader;
	private static JButton bookTitleSearchOption;
	private static JButton bookAuthorSearchOption;
	private static JButton bookTagSearchOption;

	// Song Search Option Menu
	private static JLabel songSearchHeader;
	private static JButton songTitleSearchOption;
	private static JButton songArtistSearchOption;
	private static JButton songTagSearchOption;

	// Movie Search Option Menu
	private static JLabel movieSearchHeader;
	private static JButton movieTitleSearchOption;
	private static JButton movieDirectorSearchOption;
	private static JButton movieTagSearchOption;

	// bookTitleSearch
	private static JTextField bookTitleSearchField;
	private static JButton bookTitleSearchButton;

	// bookAuthorSearch
	private static JTextField bookAuthorSearchField;
	private static JButton bookAuthorSearchButton;

	// bookTagSearch
	private static JTextField bookTagSearchField;
	private static JButton bookTagSearchButton;

	// songTitleSearch
	private static JTextField songTitleSearchField;
	private static JButton songTitleSearchButton;

	// songArtistSearch
	private static JTextField songArtistSearchField;
	private static JButton songArtistSearchButton;

	// songTagSearch
	private static JTextField songTagSearchField;
	private static JButton songTagSearchButton;

	// movieTitleSearch
	private static JTextField movieTitleSearchField;
	private static JButton movieTitleSearchButton;

	// movieDirectorSearch
	private static JTextField movieDirectorSearchField;
	private static JButton movieDirectorSearchButton;

	// movieTagSearch
	private static JTextField movieTagSearchField;
	private static JButton movieTagSearchButton;

	private DataSource datasource;
	private BookService bookService;
	private MovieService movieService;
	private SongService songService;
	private TagService tagService;

	private String[] usernames;
	private String[] passwords;

	private boolean isStaff;
	
	//staff create
	private static JButton createBook;
	private static JButton createMovie;
	private static JButton createSong;
	
	//staff delete
	private static JButton deleteBook;
	private static JButton deleteMovie;
	private static JButton deleteSong;
	
	//staff update
	private static JButton updateBook;
	private static JButton updateMovie;
	private static JButton updateSong;
	
	//create book gui
	private static JTextField bookTitleCreate;
	private static JTextField bookAuthorCreate;
	private static JTextField bookTagCreate;
	private static JButton bookTagCreateAdd;
	private static JButton bookCreate;
	private static ArrayList<String> bookTagCreateList = new ArrayList<String>();
	
	//create movie gui
	private static JTextField movieTitleCreate;
	private static JTextField movieDirectorCreate;
	private static JTextField movieDescriptionCreate;
	private static JTextField movieTagCreate;
	private static JButton movieTagCreateAdd;
	private static JButton movieCreate;
	private static ArrayList<String> movieTagCreateList = new ArrayList<String>();
	
	//create song gui
	private static JTextField songTitleCreate;
	private static JTextField songArtistCreate;
	private static JTextField songTagCreate;
	private static JButton songTagCreateAdd;
	private static JButton songCreate;
	private static ArrayList<String> songTagCreateList = new ArrayList<String>();
	
	//delete book gui
	private static JTextField bookDeleteID;
	private static JButton bookDelete;
	
	//delete movie gui
	private static JTextField movieDeleteID;
	private static JButton movieDelete;
	
	//delete song gui
	private static JTextField songDeleteID;
	private static JButton songDelete;
	
	//update book gui
	private static JTextField bookUpdateID;
	private static JTextField bookTitleUpdate;
	private static JTextField bookAuthorUpdate;
	private static JTextField bookTagAddUpdate;
	private static JTextField bookTagDeleteUpdate;
	private static JButton loadBook;
	private static JButton bookTitleUpdateButton;
	private static JButton bookAuthorUpdateButton;
	private static JButton bookTagAddUpdateButton;
	private static JButton bookTagDeleteUpdateButton;
	private static JButton bookUpdate;
	
	//update movie gui
	private static JTextField movieUpdateID;
	private static JTextField movieTitleUpdate;
	private static JTextField movieDirectorUpdate;
	private static JTextField movieDescriptionUpdate;
	private static JTextField movieTagAddUpdate;
	private static JTextField movieTagDeleteUpdate;
	private static JButton loadMovie;
	private static JButton movieTitleUpdateButton;
	private static JButton movieDirectorUpdateButton;
	private static JButton movieDescriptionUpdateButton;
	private static JButton movieTagAddUpdateButton;
	private static JButton movieTagDeleteUpdateButton;
	private static JButton movieUpdate;
	
	//update song gui
	private static JTextField songUpdateID;
	private static JTextField songTitleUpdate;
	private static JTextField songArtistUpdate;
	private static JTextField songTagAddUpdate;
	private static JTextField songTagDeleteUpdate;
	private static JButton loadSong;
	private static JButton songTitleUpdateButton;
	private static JButton songArtistUpdateButton;
	private static JButton songTagAddUpdateButton;
	private static JButton songTagDeleteUpdateButton;
	private static JButton songUpdate;
	
	//update temp
	Book bookToUpdate;
	Movie movieToUpdate;
	Song songToUpdate;

	@SuppressWarnings("unchecked")
	public RecommendMe() throws IOException {
		super("RecommendMe");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		// Main Menu GUI
		title = new JLabel("RecommendMe");
		title.setFont(new Font("Arial", Font.BOLD, 30));
		title.setForeground(new Color(150, 150, 245));

		searchMoviesGUI = new JButton("Search Movies");
		searchMoviesGUI.setActionCommand("movieGUI");
		searchMoviesGUI.addActionListener(this);
		searchMoviesGUI.setBackground(BUTTON_COLOR);
		searchMoviesGUI.setOpaque(true);
		searchMoviesGUI.setForeground(Color.WHITE);

		searchSongsGUI = new JButton("Search Songs");
		searchSongsGUI.setActionCommand("songGUI");
		searchSongsGUI.addActionListener(this);
		searchSongsGUI.setBackground(BUTTON_COLOR);
		searchSongsGUI.setOpaque(true);
		searchSongsGUI.setForeground(Color.WHITE);

		searchBooksGUI = new JButton("Search Books");
		searchBooksGUI.setActionCommand("bookGUI");
		searchBooksGUI.addActionListener(this);
		searchBooksGUI.setBackground(BUTTON_COLOR);
		searchBooksGUI.setOpaque(true);
		searchBooksGUI.setForeground(Color.WHITE);

		accessStaffLogin = new JButton("Staff Login");
		accessStaffLogin.setActionCommand("staffLogin");
		accessStaffLogin.addActionListener(this);
		accessStaffLogin.setBackground(BUTTON_COLOR);
		accessStaffLogin.setOpaque(true);
		accessStaffLogin.setForeground(Color.WHITE);

		// Staff Login GUI
		username = new JTextField("Username", 25);
		password = new JTextField("Password", 25);
		username.setBackground(BUTTON_COLOR);
		password.setBackground(BUTTON_COLOR);
		username.setForeground(Color.WHITE);
		password.setForeground(Color.WHITE);

		loginButton = new JButton("Login");
		loginButton.setActionCommand("checkLogin");
		loginButton.addActionListener(this);
		loginButton.setBackground(BUTTON_COLOR);
		loginButton.setOpaque(true);
		loginButton.setForeground(Color.WHITE);

		// Book Search Option Menu
		bookSearchHeader = new JLabel("Search for Books");
		bookSearchHeader.setFont(new Font("Arial", Font.BOLD, 30));
		bookSearchHeader.setForeground(new Color(150, 150, 245));

		bookTitleSearchOption = new JButton("Search by Title");
		bookTitleSearchOption.setActionCommand("bookTitleSearchOption");
		bookTitleSearchOption.addActionListener(this);
		bookTitleSearchOption.setBackground(BUTTON_COLOR);
		bookTitleSearchOption.setOpaque(true);
		bookTitleSearchOption.setForeground(Color.WHITE);

		bookAuthorSearchOption = new JButton("Search by Author");
		bookAuthorSearchOption.setActionCommand("bookAuthorSearchOption");
		bookAuthorSearchOption.addActionListener(this);
		bookAuthorSearchOption.setBackground(BUTTON_COLOR);
		bookAuthorSearchOption.setOpaque(true);
		bookAuthorSearchOption.setForeground(Color.WHITE);

		bookTagSearchOption = new JButton("Search by Tags");
		bookTagSearchOption.setActionCommand("bookTagSearchOption");
		bookTagSearchOption.addActionListener(this);
		bookTagSearchOption.setBackground(BUTTON_COLOR);
		bookTagSearchOption.setOpaque(true);
		bookTagSearchOption.setForeground(Color.WHITE);

		// Song Search Option Menu
		songSearchHeader = new JLabel("Search for Songs");
		songSearchHeader.setFont(new Font("Arial", Font.BOLD, 30));
		songSearchHeader.setForeground(new Color(150, 150, 245));

		songTitleSearchOption = new JButton("Search by Title");
		songTitleSearchOption.setActionCommand("songTitleSearchOption");
		songTitleSearchOption.addActionListener(this);
		songTitleSearchOption.setBackground(BUTTON_COLOR);
		songTitleSearchOption.setOpaque(true);
		songTitleSearchOption.setForeground(Color.WHITE);

		songArtistSearchOption = new JButton("Search by Artist");
		songArtistSearchOption.setActionCommand("songArtistSearchOption");
		songArtistSearchOption.addActionListener(this);
		songArtistSearchOption.setBackground(BUTTON_COLOR);
		songArtistSearchOption.setOpaque(true);
		songArtistSearchOption.setForeground(Color.WHITE);

		songTagSearchOption = new JButton("Search by Tags");
		songTagSearchOption.setActionCommand("songTagSearchOption");
		songTagSearchOption.addActionListener(this);
		songTagSearchOption.setBackground(BUTTON_COLOR);
		songTagSearchOption.setOpaque(true);
		songTagSearchOption.setForeground(Color.WHITE);

		// Movie Search Option Menu
		movieSearchHeader = new JLabel("Search for Movies");
		movieSearchHeader.setFont(new Font("Arial", Font.BOLD, 30));
		movieSearchHeader.setForeground(new Color(150, 150, 245));

		movieTitleSearchOption = new JButton("Search by Title");
		movieTitleSearchOption.setActionCommand("movieTitleSearchOption");
		movieTitleSearchOption.addActionListener(this);
		movieTitleSearchOption.setBackground(BUTTON_COLOR);
		movieTitleSearchOption.setOpaque(true);
		movieTitleSearchOption.setForeground(Color.WHITE);

		movieDirectorSearchOption = new JButton("Search by Director");
		movieDirectorSearchOption.setActionCommand("movieDirectorSearchOption");
		movieDirectorSearchOption.addActionListener(this);
		movieDirectorSearchOption.setBackground(BUTTON_COLOR);
		movieDirectorSearchOption.setOpaque(true);
		movieDirectorSearchOption.setForeground(Color.WHITE);

		movieTagSearchOption = new JButton("Search by Tags");
		movieTagSearchOption.setActionCommand("movieTagSearchOption");
		movieTagSearchOption.addActionListener(this);
		movieTagSearchOption.setBackground(BUTTON_COLOR);
		movieTagSearchOption.setOpaque(true);
		movieTagSearchOption.setForeground(Color.WHITE);

		// book title search
		bookTitleSearchField = new JTextField("Search by Title", 25);
		bookTitleSearchField.setBackground(BUTTON_COLOR);
		bookTitleSearchField.setForeground(Color.WHITE);

		bookTitleSearchButton = new JButton("Search");
		bookTitleSearchButton.setActionCommand("bookSearchByTitle");
		bookTitleSearchButton.addActionListener(this);
		bookTitleSearchButton.setBackground(BUTTON_COLOR);
		bookTitleSearchButton.setOpaque(true);
		bookTitleSearchButton.setForeground(Color.WHITE);

		// book author search
		bookAuthorSearchField = new JTextField("Search by Author", 25);
		bookAuthorSearchField.setBackground(BUTTON_COLOR);
		bookAuthorSearchField.setForeground(Color.WHITE);

		bookAuthorSearchButton = new JButton("Search");
		bookAuthorSearchButton.setActionCommand("bookSearchByAuthor");
		bookAuthorSearchButton.addActionListener(this);
		bookAuthorSearchButton.setBackground(BUTTON_COLOR);
		bookAuthorSearchButton.setOpaque(true);
		bookAuthorSearchButton.setForeground(Color.WHITE);

		// book tag search
		bookTagSearchField = new JTextField("Search by Tag", 25);
		bookTagSearchField.setBackground(BUTTON_COLOR);
		bookTagSearchField.setForeground(Color.WHITE);

		bookTagSearchButton = new JButton("Search");
		bookTagSearchButton.setActionCommand("bookSearchByTag");
		bookTagSearchButton.addActionListener(this);
		bookTagSearchButton.setBackground(BUTTON_COLOR);
		bookTagSearchButton.setOpaque(true);
		bookTagSearchButton.setForeground(Color.WHITE);

		// song tag search
		songTagSearchField = new JTextField("Search by Tag", 25);
		songTagSearchField.setBackground(BUTTON_COLOR);
		songTagSearchField.setForeground(Color.WHITE);

		songTagSearchButton = new JButton("Search");
		songTagSearchButton.setActionCommand("songSearchByTag");
		songTagSearchButton.addActionListener(this);
		songTagSearchButton.setBackground(BUTTON_COLOR);
		songTagSearchButton.setOpaque(true);
		songTagSearchButton.setForeground(Color.WHITE);

		// song title search
		songTitleSearchField = new JTextField("Search by Title", 25);
		songTitleSearchField.setBackground(BUTTON_COLOR);
		songTitleSearchField.setForeground(Color.WHITE);

		songTitleSearchButton = new JButton("Search");
		songTitleSearchButton.setActionCommand("songSearchByTitle");
		songTitleSearchButton.addActionListener(this);
		songTitleSearchButton.setBackground(BUTTON_COLOR);
		songTitleSearchButton.setOpaque(true);
		songTitleSearchButton.setForeground(Color.WHITE);

		// song artist search
		songArtistSearchField = new JTextField("Search by Artist", 25);
		songArtistSearchField.setBackground(BUTTON_COLOR);
		songArtistSearchField.setForeground(Color.WHITE);

		songArtistSearchButton = new JButton("Search");
		songArtistSearchButton.setActionCommand("songSearchByArtist");
		songArtistSearchButton.addActionListener(this);
		songArtistSearchButton.setBackground(BUTTON_COLOR);
		songArtistSearchButton.setOpaque(true);
		songArtistSearchButton.setForeground(Color.WHITE);

		// movie tag search
		movieTagSearchField = new JTextField("Search by Tag", 25);
		movieTagSearchField.setBackground(BUTTON_COLOR);
		movieTagSearchField.setForeground(Color.WHITE);

		movieTagSearchButton = new JButton("Search");
		movieTagSearchButton.setActionCommand("movieSearchByTag");
		movieTagSearchButton.addActionListener(this);
		movieTagSearchButton.setBackground(BUTTON_COLOR);
		movieTagSearchButton.setOpaque(true);
		movieTagSearchButton.setForeground(Color.WHITE);

		// movie title search
		movieTitleSearchField = new JTextField("Search by Title", 25);
		movieTitleSearchField.setBackground(BUTTON_COLOR);
		movieTitleSearchField.setForeground(Color.WHITE);

		movieTitleSearchButton = new JButton("Search");
		movieTitleSearchButton.setActionCommand("movieSearchByTitle");
		movieTitleSearchButton.addActionListener(this);
		movieTitleSearchButton.setBackground(BUTTON_COLOR);
		movieTitleSearchButton.setOpaque(true);
		movieTitleSearchButton.setForeground(Color.WHITE);

		// movie director Search
		movieDirectorSearchField = new JTextField("Search by Director", 25);
		movieDirectorSearchField.setBackground(BUTTON_COLOR);
		movieDirectorSearchField.setForeground(Color.WHITE);

		movieDirectorSearchButton = new JButton("Search");
		movieDirectorSearchButton.setActionCommand("movieSearchByDirector");
		movieDirectorSearchButton.addActionListener(this);
		movieDirectorSearchButton.setBackground(BUTTON_COLOR);
		movieDirectorSearchButton.setOpaque(true);
		movieDirectorSearchButton.setForeground(Color.WHITE);

		datasource = datasourceManager.getDataSource();
		bookService = new BookServiceImpl(datasource);
		tagService = new TagServiceImpl(datasource);
		movieService = new MovieServiceImpl(datasource);
		songService = new SongServiceImpl(datasource);

		// usernames
		usernames = new String[1];
		passwords = new String[1];
		usernames[0] = "Username";
		passwords[0] = "Password";
		
		//staff create
		createBook = new JButton("Create Book");
		createBook.setActionCommand("createBookGUI");
		createBook.addActionListener(this);
		createBook.setBackground(BUTTON_COLOR);
		createBook.setOpaque(true);
		createBook.setForeground(Color.WHITE);
		
		createMovie = new JButton("Create Movie");
		createMovie.setActionCommand("createMovieGUI");
		createMovie.addActionListener(this);
		createMovie.setBackground(BUTTON_COLOR);
		createMovie.setOpaque(true);
		createMovie.setForeground(Color.WHITE);
		
		createSong = new JButton("Create Song");
		createSong.setActionCommand("createSongGUI");
		createSong.addActionListener(this);
		createSong.setBackground(BUTTON_COLOR);
		createSong.setOpaque(true);
		createSong.setForeground(Color.WHITE);
		
		//staff delete
		deleteBook = new JButton("Delete Book");
		deleteBook.setActionCommand("deleteBookGUI");
		deleteBook.addActionListener(this);
		deleteBook.setBackground(BUTTON_COLOR);
		deleteBook.setOpaque(true);
		deleteBook.setForeground(Color.WHITE);
		
		deleteMovie = new JButton("Delete Movie");
		deleteMovie.setActionCommand("deleteMovieGUI");
		deleteMovie.addActionListener(this);
		deleteMovie.setBackground(BUTTON_COLOR);
		deleteMovie.setOpaque(true);
		deleteMovie.setForeground(Color.WHITE);
		
		deleteSong = new JButton("Delete Song");
		deleteSong.setActionCommand("deleteSongGUI");
		deleteSong.addActionListener(this);
		deleteSong.setBackground(BUTTON_COLOR);
		deleteSong.setOpaque(true);
		deleteSong.setForeground(Color.WHITE);
		
		//staff update
		updateBook = new JButton("Update Book");
		updateBook.setActionCommand("updateBookGUI");
		updateBook.addActionListener(this);
		updateBook.setBackground(BUTTON_COLOR);
		updateBook.setOpaque(true);
		updateBook.setForeground(Color.WHITE);
		
		updateMovie = new JButton("Update Movie");
		updateMovie.setActionCommand("updateMovieGUI");
		updateMovie.addActionListener(this);
		updateMovie.setBackground(BUTTON_COLOR);
		updateMovie.setOpaque(true);
		updateMovie.setForeground(Color.WHITE);
		
		updateSong = new JButton("Update Song");
		updateSong.setActionCommand("updateSongGUI");
		updateSong.addActionListener(this);
		updateSong.setBackground(BUTTON_COLOR);
		updateSong.setOpaque(true);
		updateSong.setForeground(Color.WHITE);
		
		//create book gui
		bookTitleCreate = new JTextField("Book Title", 25);
		bookTitleCreate.setBackground(BUTTON_COLOR);
		bookTitleCreate.setForeground(Color.WHITE);
		
		bookAuthorCreate = new JTextField("Book Author", 25);
		bookAuthorCreate.setBackground(BUTTON_COLOR);
		bookAuthorCreate.setForeground(Color.WHITE);
		
		bookTagCreate = new JTextField("Book Tag", 25);
		bookTagCreate.setBackground(BUTTON_COLOR);
		bookTagCreate.setForeground(Color.WHITE);
		
		bookTagCreateAdd = new JButton("Add Tag");
		bookTagCreateAdd.setActionCommand("bookTagCreateAdd");
		bookTagCreateAdd.addActionListener(this);
		bookTagCreateAdd.setBackground(BUTTON_COLOR);
		bookTagCreateAdd.setOpaque(true);
		bookTagCreateAdd.setForeground(Color.WHITE);
		
		bookCreate = new JButton("Create Book");
		bookCreate.setActionCommand("bookCreate");
		bookCreate.addActionListener(this);
		bookCreate.setBackground(BUTTON_COLOR);
		bookCreate.setOpaque(true);
		bookCreate.setForeground(Color.WHITE);
		
		//create movie gui
		movieTitleCreate = new JTextField("Movie Title", 25);
		movieTitleCreate.setBackground(BUTTON_COLOR);
		movieTitleCreate.setForeground(Color.WHITE);
		
		movieDirectorCreate = new JTextField("Movie Director", 25);
		movieDirectorCreate.setBackground(BUTTON_COLOR);
		movieDirectorCreate.setForeground(Color.WHITE);
		
		movieDescriptionCreate = new JTextField("Movie Description", 25);
		movieDescriptionCreate.setBackground(BUTTON_COLOR);
		movieDescriptionCreate.setForeground(Color.WHITE);
		
		movieTagCreate = new JTextField("Movie Tag", 25);
		movieTagCreate.setBackground(BUTTON_COLOR);
		movieTagCreate.setForeground(Color.WHITE);
		
		movieTagCreateAdd = new JButton("Add Tag");
		movieTagCreateAdd.setActionCommand("movieTagCreateAdd");
		movieTagCreateAdd.addActionListener(this);
		movieTagCreateAdd.setBackground(BUTTON_COLOR);
		movieTagCreateAdd.setOpaque(true);
		movieTagCreateAdd.setForeground(Color.WHITE);
		
		movieCreate = new JButton("Create Movie");
		movieCreate.setActionCommand("movieCreate");
		movieCreate.addActionListener(this);
		movieCreate.setBackground(BUTTON_COLOR);
		movieCreate.setOpaque(true);
		movieCreate.setForeground(Color.WHITE);
		
		//create song gui
		songTitleCreate = new JTextField("Song Title", 25);
		songTitleCreate.setBackground(BUTTON_COLOR);
		songTitleCreate.setForeground(Color.WHITE);
		
		songArtistCreate = new JTextField("Song Artist", 25);
		songArtistCreate.setBackground(BUTTON_COLOR);
		songArtistCreate.setForeground(Color.WHITE);
		
		songTagCreate = new JTextField("Song Tag", 25);
		songTagCreate.setBackground(BUTTON_COLOR);
		songTagCreate.setForeground(Color.WHITE);
		
		songTagCreateAdd = new JButton("Add Tag");
		songTagCreateAdd.setActionCommand("songTagCreateAdd");
		songTagCreateAdd.addActionListener(this);
		songTagCreateAdd.setBackground(BUTTON_COLOR);
		songTagCreateAdd.setOpaque(true);
		songTagCreateAdd.setForeground(Color.WHITE);
		
		songCreate = new JButton("Create Song");
		songCreate.setActionCommand("songCreate");
		songCreate.addActionListener(this);
		songCreate.setBackground(BUTTON_COLOR);
		songCreate.setOpaque(true);
		songCreate.setForeground(Color.WHITE);
		
		//delete book gui
		bookDeleteID = new JTextField("Book ID", 25);
		bookDeleteID.setBackground(BUTTON_COLOR);
		bookDeleteID.setForeground(Color.WHITE);
		
		bookDelete = new JButton("Delete Book");
		bookDelete.setActionCommand("bookDelete");
		bookDelete.addActionListener(this);
		bookDelete.setBackground(BUTTON_COLOR);
		bookDelete.setOpaque(true);
		bookDelete.setForeground(Color.WHITE);
		
		//delete movie gui
		movieDeleteID = new JTextField("Movie ID", 25);
		movieDeleteID.setBackground(BUTTON_COLOR);
		movieDeleteID.setForeground(Color.WHITE);
		
		movieDelete = new JButton("Delete Movie");
		movieDelete.setActionCommand("movieDelete");
		movieDelete.addActionListener(this);
		movieDelete.setBackground(BUTTON_COLOR);
		movieDelete.setOpaque(true);
		movieDelete.setForeground(Color.WHITE);
		
		//delete song gui
		songDeleteID = new JTextField("Song ID", 25);
		songDeleteID.setBackground(BUTTON_COLOR);
		songDeleteID.setForeground(Color.WHITE);
		
		songDelete = new JButton("Delete Song");
		songDelete.setActionCommand("songDelete");
		songDelete.addActionListener(this);
		songDelete.setBackground(BUTTON_COLOR);
		songDelete.setOpaque(true);
		songDelete.setForeground(Color.WHITE);
		
		//update book gui
		bookUpdateID = new JTextField("Book ID", 25);
		bookUpdateID.setBackground(BUTTON_COLOR);
		bookUpdateID.setForeground(Color.WHITE);
		
		bookTitleUpdate = new JTextField("Book Title", 25);
		bookTitleUpdate.setBackground(BUTTON_COLOR);
		bookTitleUpdate.setForeground(Color.WHITE);
		
		bookAuthorUpdate = new JTextField("Book Author", 25);
		bookAuthorUpdate.setBackground(BUTTON_COLOR);
		bookAuthorUpdate.setForeground(Color.WHITE);
		
		bookTagAddUpdate = new JTextField("Book Tag to Add", 25);
		bookTagAddUpdate.setBackground(BUTTON_COLOR);
		bookTagAddUpdate.setForeground(Color.WHITE);
		
		bookTagDeleteUpdate = new JTextField("Book Tag to Delete", 25);
		bookTagDeleteUpdate.setBackground(BUTTON_COLOR);
		bookTagDeleteUpdate.setForeground(Color.WHITE);
		
		loadBook = new JButton("Load Book");
		loadBook.setActionCommand("loadBook");
		loadBook.addActionListener(this);
		loadBook.setBackground(BUTTON_COLOR);
		loadBook.setOpaque(true);
		loadBook.setForeground(Color.WHITE);
		
		bookTitleUpdateButton = new JButton("Update Book Title");
		bookTitleUpdateButton.setActionCommand("bookTitleUpdate");
		bookTitleUpdateButton.addActionListener(this);
		bookTitleUpdateButton.setBackground(BUTTON_COLOR);
		bookTitleUpdateButton.setOpaque(true);
		bookTitleUpdateButton.setForeground(Color.WHITE);
		
		bookAuthorUpdateButton = new JButton("Update Book Author");
		bookAuthorUpdateButton.setActionCommand("bookAuthorUpdate");
		bookAuthorUpdateButton.addActionListener(this);
		bookAuthorUpdateButton.setBackground(BUTTON_COLOR);
		bookAuthorUpdateButton.setOpaque(true);
		bookAuthorUpdateButton.setForeground(Color.WHITE);
		
		bookTagAddUpdateButton = new JButton("Add Book Tag");
		bookTagAddUpdateButton.setActionCommand("bookTagAddUpdate");
		bookTagAddUpdateButton.addActionListener(this);
		bookTagAddUpdateButton.setBackground(BUTTON_COLOR);
		bookTagAddUpdateButton.setOpaque(true);
		bookTagAddUpdateButton.setForeground(Color.WHITE);
		
		bookTagDeleteUpdateButton = new JButton("Delete Book Tag");
		bookTagDeleteUpdateButton.setActionCommand("bookTagDeleteUpdate");
		bookTagDeleteUpdateButton.addActionListener(this);
		bookTagDeleteUpdateButton.setBackground(BUTTON_COLOR);
		bookTagDeleteUpdateButton.setOpaque(true);
		bookTagDeleteUpdateButton.setForeground(Color.WHITE);
		
		bookUpdate = new JButton("Update Book");
		bookUpdate.setActionCommand("bookUpdate");
		bookUpdate.addActionListener(this);
		bookUpdate.setBackground(BUTTON_COLOR);
		bookUpdate.setOpaque(true);
		bookUpdate.setForeground(Color.WHITE);
		
		//update movie gui
		movieUpdateID = new JTextField("Movie ID", 25);
		movieUpdateID.setBackground(BUTTON_COLOR);
		movieUpdateID.setForeground(Color.WHITE);
		
		movieTitleUpdate = new JTextField("Movie Title", 25);
		movieTitleUpdate.setBackground(BUTTON_COLOR);
		movieTitleUpdate.setForeground(Color.WHITE);
		
		movieDirectorUpdate = new JTextField("Movie Director", 25);
		movieDirectorUpdate.setBackground(BUTTON_COLOR);
		movieDirectorUpdate.setForeground(Color.WHITE);
		
		movieDescriptionUpdate = new JTextField("Movie Description", 25);
		movieDescriptionUpdate.setBackground(BUTTON_COLOR);
		movieDescriptionUpdate.setForeground(Color.WHITE);
		
		movieTagAddUpdate = new JTextField("Movie Tag to Add", 25);
		movieTagAddUpdate.setBackground(BUTTON_COLOR);
		movieTagAddUpdate.setForeground(Color.WHITE);
		
		movieTagDeleteUpdate = new JTextField("Movie Tag to Delete", 25);
		movieTagDeleteUpdate.setBackground(BUTTON_COLOR);
		movieTagDeleteUpdate.setForeground(Color.WHITE);
		
		loadMovie = new JButton("Load Movie");
		loadMovie.setActionCommand("loadMovie");
		loadMovie.addActionListener(this);
		loadMovie.setBackground(BUTTON_COLOR);
		loadMovie.setOpaque(true);
		loadMovie.setForeground(Color.WHITE);
		
		movieTitleUpdateButton = new JButton("Update Movie Title");
		movieTitleUpdateButton.setActionCommand("movieTitleUpdate");
		movieTitleUpdateButton.addActionListener(this);
		movieTitleUpdateButton.setBackground(BUTTON_COLOR);
		movieTitleUpdateButton.setOpaque(true);
		movieTitleUpdateButton.setForeground(Color.WHITE);
		
		movieDirectorUpdateButton = new JButton("Update Movie Director");
		movieDirectorUpdateButton.setActionCommand("movieDirectorUpdate");
		movieDirectorUpdateButton.addActionListener(this);
		movieDirectorUpdateButton.setBackground(BUTTON_COLOR);
		movieDirectorUpdateButton.setOpaque(true);
		movieDirectorUpdateButton.setForeground(Color.WHITE);
		
		movieDescriptionUpdateButton = new JButton("Update Movie Description");
		movieDescriptionUpdateButton.setActionCommand("movieDescriptionUpdate");
		movieDescriptionUpdateButton.addActionListener(this);
		movieDescriptionUpdateButton.setBackground(BUTTON_COLOR);
		movieDescriptionUpdateButton.setOpaque(true);
		movieDescriptionUpdateButton.setForeground(Color.WHITE);
		
		movieTagAddUpdateButton = new JButton("Add Movie Tag");
		movieTagAddUpdateButton.setActionCommand("movieTagAddUpdate");
		movieTagAddUpdateButton.addActionListener(this);
		movieTagAddUpdateButton.setBackground(BUTTON_COLOR);
		movieTagAddUpdateButton.setOpaque(true);
		movieTagAddUpdateButton.setForeground(Color.WHITE);
		
		movieTagDeleteUpdateButton = new JButton("Delete Movie Tag");
		movieTagDeleteUpdateButton.setActionCommand("movieTagDeleteUpdate");
		movieTagDeleteUpdateButton.addActionListener(this);
		movieTagDeleteUpdateButton.setBackground(BUTTON_COLOR);
		movieTagDeleteUpdateButton.setOpaque(true);
		movieTagDeleteUpdateButton.setForeground(Color.WHITE);
		
		movieUpdate = new JButton("Update Movie");
		movieUpdate.setActionCommand("movieUpdate");
		movieUpdate.addActionListener(this);
		movieUpdate.setBackground(BUTTON_COLOR);
		movieUpdate.setOpaque(true);
		movieUpdate.setForeground(Color.WHITE);
		
		//update song gui
		songUpdateID = new JTextField("Song ID", 25);
		songUpdateID.setBackground(BUTTON_COLOR);
		songUpdateID.setForeground(Color.WHITE);
		
		songTitleUpdate = new JTextField("Song Title", 25);
		songTitleUpdate.setBackground(BUTTON_COLOR);
		songTitleUpdate.setForeground(Color.WHITE);
		
		songArtistUpdate = new JTextField("Song Artist", 25);
		songArtistUpdate.setBackground(BUTTON_COLOR);
		songArtistUpdate.setForeground(Color.WHITE);
		
		songTagAddUpdate = new JTextField("Song Tag to Add", 25);
		songTagAddUpdate.setBackground(BUTTON_COLOR);
		songTagAddUpdate.setForeground(Color.WHITE);
		
		songTagDeleteUpdate = new JTextField("Song Tag to Delete", 25);
		songTagDeleteUpdate.setBackground(BUTTON_COLOR);
		songTagDeleteUpdate.setForeground(Color.WHITE);
		
		loadSong = new JButton("Load Song");
		loadSong.setActionCommand("loadSong");
		loadSong.addActionListener(this);
		loadSong.setBackground(BUTTON_COLOR);
		loadSong.setOpaque(true);
		loadSong.setForeground(Color.WHITE);
		
		songTitleUpdateButton = new JButton("Update Song Title");
		songTitleUpdateButton.setActionCommand("songTitleUpdate");
		songTitleUpdateButton.addActionListener(this);
		songTitleUpdateButton.setBackground(BUTTON_COLOR);
		songTitleUpdateButton.setOpaque(true);
		songTitleUpdateButton.setForeground(Color.WHITE);
		
		songArtistUpdateButton = new JButton("Update Song Artist");
		songArtistUpdateButton.setActionCommand("songArtistUpdate");
		songArtistUpdateButton.addActionListener(this);
		songArtistUpdateButton.setBackground(BUTTON_COLOR);
		songArtistUpdateButton.setOpaque(true);
		songArtistUpdateButton.setForeground(Color.WHITE);
		
		songTagAddUpdateButton = new JButton("Add Song Tag");
		songTagAddUpdateButton.setActionCommand("songTagAddUpdate");
		songTagAddUpdateButton.addActionListener(this);
		songTagAddUpdateButton.setBackground(BUTTON_COLOR);
		songTagAddUpdateButton.setOpaque(true);
		songTagAddUpdateButton.setForeground(Color.WHITE);
		
		songTagDeleteUpdateButton = new JButton("Delete Song Tag");
		songTagDeleteUpdateButton.setActionCommand("songTagDeleteUpdate");
		songTagDeleteUpdateButton.addActionListener(this);
		songTagDeleteUpdateButton.setBackground(BUTTON_COLOR);
		songTagDeleteUpdateButton.setOpaque(true);
		songTagDeleteUpdateButton.setForeground(Color.WHITE);
		
		songUpdate = new JButton("Update Song");
		songUpdate.setActionCommand("songUpdate");
		songUpdate.addActionListener(this);
		songUpdate.setBackground(BUTTON_COLOR);
		songUpdate.setOpaque(true);
		songUpdate.setForeground(Color.WHITE);
	}

	public static void main(String[] args) throws IOException {
		RecommendMe go = new RecommendMe();

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createMainGUI(go);
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		if ("movieGUI".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					createMovieSearchGUI();
				}
			});
		} else if ("songGUI".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					createSongSearchGUI();
				}
			});
		} else if ("bookGUI".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					createBookSearchGUI();
				}
			});
		} else if ("staffLogin".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					createStaffLoginGUI();
				}
			});
		} else if ("songTitleSearchOption".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					createSongTitleSearchGUI();
				}
			});
		} else if ("songArtistSearchOption".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					createSongArtistSearchGUI();
				}
			});
		} else if ("songTagSearchOption".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					createSongTagSearchGUI();
				}
			});
		} else if ("bookTitleSearchOption".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					createBookTitleSearchGUI();
				}
			});
		} else if ("bookAuthorSearchOption".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					createBookAuthorSearchGUI();
				}
			});
		} else if ("bookTagSearchOption".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					createBookTagSearchGUI();
				}
			});
		} else if ("movieTitleSearchOption".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					createMovieTitleSearchGUI();
				}
			});
		} else if ("movieDirectorSearchOption".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					createMovieDirectorSearchGUI();
				}
			});
		} else if ("movieTagSearchOption".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					createMovieTagSearchGUI();
				}
			});
		} else if ("movieSearchByDirector".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					String director = movieDirectorSearchField.getText();

					try {
						List<Movie> m2 = movieService.retrieveByDirector(director);
						Collections.shuffle(m2);

						System.out.println(
								"Your search by director for: " + director + " yielded " + m2.size() + " results.");
						System.out.println("Your results were:");

						String output = "<html>";
						
						int increment = 1;
						for (Movie mk : m2) {
							List<Tag> tagList = tagService.retrieveForMovieId(mk.getId());
							
							output += increment + ". <br>";
							output += "Movie title: " + mk.getTitle() + "<br>";
							output += "Movie director: " + mk.getDirector() + "<br>";
							output += "Movie description: " + mk.getDescription() + "<br>";
							output += "Movie id: " + mk.getId() + "<br>";
							output += "Tags: ";
							for (int i = 0; i < tagList.size(); i++) {
								if (i != tagList.size() - 1) {
									output += tagList.get(i).getDescription() + ", ";
								} else {
									output += tagList.get(i).getDescription() + "<br>";
								}
							}
							output += "<br>";
							increment++;
							
							if (increment > 9) {
								break;
							}
						}
						
						output += "</html>";
						createTextGUI("Movie Search Results by Director", output);
					} catch (Exception e) {
						System.out.println("There was an unexpected error in Search By Title: " + title);
					}
				}
			});
		} else if ("movieSearchByTitle".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					String title = movieTitleSearchField.getText();

					try {
						List<Movie> m2 = movieService.retrieveByTitle(title);
						Collections.shuffle(m2);

						System.out
								.println("Your search by title for: " + title + " yielded " + m2.size() + " results.");
						System.out.println("Your results were:");

						String output = "<html>";
						
						int increment = 1;
						for (Movie mk : m2) {
							List<Tag> tagList = tagService.retrieveForMovieId(mk.getId());
							
							output += increment + ". <br>";
							output += "Movie title: " + mk.getTitle() + "<br>";
							output += "Movie director: " + mk.getDirector() + "<br>";
							output += "Movie description: " + mk.getDescription() + "<br>";
							output += "Movie id: " + mk.getId() + "<br>";
							output += "Tags: ";
							for (int i = 0; i < tagList.size(); i++) {
								if (i != tagList.size() - 1) {
									output += tagList.get(i).getDescription() + ", ";
								} else {
									output += tagList.get(i).getDescription() + "<br>";
								}
							}
							output += "<br>";
							increment++;
							
							if (increment > 9) {
								break;
							}
						}
						
						output += "</html>";
						createTextGUI("Movie Search Results by Title", output);
					} catch (Exception e) {
						System.out.println("There was an unexpected error in Search By Title: " + title);
					}

				}
			});
		} else if ("movieSearchByTag".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					String tag = movieTagSearchField.getText();

					try {
						List<Movie> m2 = movieService.retrieveByTagName(tag);
						Collections.shuffle(m2);

						System.out.println("Your search by tag for: " + tag + " yielded " + m2.size() + " results.");
						System.out.println("Your results were:");

						String output = "<html>";
						
						int increment = 1;
						for (Movie mk : m2) {
							List<Tag> tagList = tagService.retrieveForMovieId(mk.getId());
							
							output += increment + ". <br>";
							output += "Movie title: " + mk.getTitle() + "<br>";
							output += "Movie director: " + mk.getDirector() + "<br>";
							output += "Movie description: " + mk.getDescription() + "<br>";
							output += "Movie id: " + mk.getId() + "<br>";
							output += "Tags: ";
							for (int i = 0; i < tagList.size(); i++) {
								if (i != tagList.size() - 1) {
									output += tagList.get(i).getDescription() + ", ";
								} else {
									output += tagList.get(i).getDescription() + "<br>";
								}
							}
							output += "<br>";
							increment++;
							
							if (increment > 9) {
								break;
							}
						}
						
						output += "</html>";
						createTextGUI("Movie Search Results by Tag", output);
					} catch (Exception e) {
						System.out.println("There was an unexpected error in Search By tag: " + tag);
					}
				}
			});
		} else if ("bookSearchByTag".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					String tag = bookTagSearchField.getText();

					try {
						List<Book> b2 = bookService.retrieveByTagName(tag);
						Collections.shuffle(b2);

						System.out.println("Your search by tag for: " + tag + " yielded " + b2.size() + " results.");
						System.out.println("Your results were:");

						String output = "<html>";
						
						int increment = 1;
						for (Book bk : b2) {
							List<Tag> tagList = tagService.retrieveForBookId(bk.getId());
							
							output += increment + ". <br>";
							output += "Book title: " + bk.getTitle() + "<br>";
							output += "Book author: " + bk.getAuthor() + "<br>";
							output += "Book id: " + bk.getId() + "<br>";
							output += "Tags: ";
							for (int i = 0; i < tagList.size(); i++) {
								if (i != tagList.size() - 1) {
									output += tagList.get(i).getDescription() + ", ";
								} else {
									output += tagList.get(i).getDescription() + "<br>";
								}
							}
							output += "<br>";
							increment++;
							
							if (increment > 10) {
								break;
							}
						}
						
						output += "</html>";
						createTextGUI("Book Search Results by Tag", output);
					} catch (Exception e) {
						System.out.println("There was an unexpected error in Search By tag: " + tag);
					}
				}
			});
		} else if ("bookSearchByTitle".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					String title = bookTitleSearchField.getText();

					try {
						List<Book> b2 = bookService.retrieveByTitle(title);
						Collections.shuffle(b2);

						System.out
								.println("Your search by title for: " + title + " yielded " + b2.size() + " results.");
						System.out.println("Your results were:");

						String output = "<html>";
						
						int increment = 1;
						for (Book bk : b2) {
							List<Tag> tagList = tagService.retrieveForBookId(bk.getId());
							
							output += increment + ". <br>";
							output += "Book title: " + bk.getTitle() + "<br>";
							output += "Book author: " + bk.getAuthor() + "<br>";
							output += "Book id: " + bk.getId() + "<br>";
							output += "Tags: ";
							for (int i = 0; i < tagList.size(); i++) {
								if (i != tagList.size() - 1) {
									output += tagList.get(i).getDescription() + ", ";
								} else {
									output += tagList.get(i).getDescription() + "<br>";
								}
							}
							output += "<br>";
							increment++;
							
							if (increment > 10) {
								break;
							}
						}
						
						output += "</html>";
						createTextGUI("Book Search Results by Title", output);
					} catch (Exception e) {
						System.out.println("There was an unexpected error in Search By Title: " + title);
					}

				}
			});
		} else if ("bookSearchByAuthor".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {

					String author = bookAuthorSearchField.getText();

					try {
						List<Book> b2 = bookService.retrieveByAuthor(author);
						Collections.shuffle(b2);

						System.out.println(
								"Your search by author for: " + author + " yielded " + b2.size() + " results.");
						System.out.println("Your results were:");

						String output = "<html>";
						
						int increment = 1;
						for (Book bk : b2) {
							List<Tag> tagList = tagService.retrieveForBookId(bk.getId());
							
							output += increment + ". <br>";
							output += "Book title: " + bk.getTitle() + "<br>";
							output += "Book author: " + bk.getAuthor() + "<br>";
							output += "Book id: " + bk.getId() + "<br>";
							output += "Tags: ";
							for (int i = 0; i < tagList.size(); i++) {
								if (i != tagList.size() - 1) {
									output += tagList.get(i).getDescription() + ", ";
								} else {
									output += tagList.get(i).getDescription() + "<br>";
								}
							}
							output += "<br>";
							increment++;
							
							if (increment > 10) {
								break;
							}
						}
						
						output += "</html>";
						createTextGUI("Book Search Results by Author", output);
					} catch (Exception e) {
						System.out.println("There was an unexpected error in Search By Author: " + author);
					}

				}
			});
		} else if ("songSearchByTag".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					String tag = songTagSearchField.getText();

					try {
						List<Song> s2 = songService.retrieveByTagName(tag);
						Collections.shuffle(s2);

						System.out.println("Your search by tag for: " + tag + " yielded " + s2.size() + " results.");
						System.out.println("Your results were:");

						String output = "<html>";
						
						int increment = 1;
						for (Song sk : s2) {
							List<Tag> tagList = tagService.retrieveForSongId(sk.getId());
							
							output += increment + ". <br>";
							output += "Song title: " + sk.getTitle() + "<br>";
							output += "Song artist: " + sk.getSinger() + "<br>";
							output += "Song id: " + sk.getId() + "<br>";
							output += "Tags: ";
							for (int i = 0; i < tagList.size(); i++) {
								if (i != tagList.size() - 1) {
									output += tagList.get(i).getDescription() + ", ";
								} else {
									output += tagList.get(i).getDescription() + "<br>";
								}
							}
							output += "<br>";
							increment++;
							
							if (increment > 10) {
								break;
							}
						}
						
						output += "</html>";
						createTextGUI("Song Search Results by Tags", output);
					} catch (Exception e) {
						System.out.println("There was an unexpected error in Search By tag: " + tag);
					}

				}
			});
		} else if ("songSearchByTitle".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					String title = songTitleSearchField.getText();

					try {
						List<Song> s2 = songService.retrieveByTitle(title);
						Collections.shuffle(s2);

						System.out
								.println("Your search by title for: " + title + " yielded " + s2.size() + " results.");
						System.out.println("Your results were:");

						String output = "<html>";
						
						int increment = 1;
						for (Song sk : s2) {
							List<Tag> tagList = tagService.retrieveForSongId(sk.getId());
							
							output += increment + ". <br>";
							output += "Song title: " + sk.getTitle() + "<br>";
							output += "Song artist: " + sk.getSinger() + "<br>";
							output += "Song id: " + sk.getId() + "<br>";
							output += "Tags: ";
							for (int i = 0; i < tagList.size(); i++) {
								if (i != tagList.size() - 1) {
									output += tagList.get(i).getDescription() + ", ";
								} else {
									output += tagList.get(i).getDescription() + "<br>";
								}
							}
							output += "<br>";
							increment++;
							
							if (increment > 10) {
								break;
							}
						}
						
						output += "</html>";
						createTextGUI("Song Search Results by Title", output);
						
					} catch (Exception e) {
						System.out.println("There was an unexpected error in Search By Title: " + title);
					}

				}
			});
		} else if ("songSearchByArtist".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					String artist = songArtistSearchField.getText();

					try {
						List<Song> s2 = songService.retrieveBySinger(artist);
						Collections.shuffle(s2);

						System.out.println("Your search by artist for: " + artist + " yielded " + s2.size() + " results.");
						System.out.println("Your results were:");

						String output = "<html>";
						
						int increment = 1;
						for (Song sk : s2) {
							List<Tag> tagList = tagService.retrieveForSongId(sk.getId());
							
							output += increment + ". <br>";
							output += "Song title: " + sk.getTitle() + "<br>";
							output += "Song artist: " + sk.getSinger() + "<br>";
							output += "Song id: " + sk.getId() + "<br>";
							output += "Tags: ";
							for (int i = 0; i < tagList.size(); i++) {
								if (i != tagList.size() - 1) {
									output += tagList.get(i).getDescription() + ", ";
								} else {
									output += tagList.get(i).getDescription() + "<br>";
								}
							}
							output += "<br>";
							increment++;
							
							if (increment > 10) {
								break;
							}
						}
						
						output += "</html>";
						createTextGUI("Song Search Results by Artist",output);
						
					} catch (Exception e) {
						System.out.println("There was an unexpected error in Search By Artist: " + artist);
					}
				}
			});
		} else if ("checkLogin".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					String usernameText = username.getText();
					String passwordText = password.getText();

					for (int i = 0; i < usernames.length; i++) {
						if (usernameText.equals(usernames[i]) && passwordText.equals(passwords[i])) {
							isStaff = true;
							createStaffGUI();
						} else {
							createTextGUI("Incorrect Login", "<html>Incorrect Login Credentials</html>");
						}
					}
				}
			});
		} else if ("createBookGUI".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					createBookGUI();
				}
			});
		} else if ("createMovieGUI".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					createMovieGUI();
				}
			});
		} else if ("createSongGUI".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					createSongGUI();
				}
			});
		} else if ("deleteBookGUI".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					deleteBookGUI();
				}
			});
		} else if ("deleteMovieGUI".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					deleteMovieGUI();
				}
			});
		} else if ("deleteSongGUI".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					deleteSongGUI();
				}
			});
		} else if ("updateBookGUI".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					updateBookGUI();
				}
			});
		} else if ("updateMovieGUI".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					updateMovieGUI();
				}
			});
		} else if ("updateSongGUI".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					updateSongGUI();
				}
			});
		} else if ("bookCreate".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					
					Book toCreate = new Book(0, bookTitleCreate.getText(), bookAuthorCreate.getText(), ".");
					try {
						toCreate = bookService.create(toCreate);
						System.out.println("Your book has been created!");
						System.out.println("Book title: " + toCreate.getTitle());
						System.out.println("Book author: " + toCreate.getAuthor());
						System.out.println("Book id: " + toCreate.getId());
						System.out.println("Book description: " + toCreate.getDescription());
						
						createTextGUI("Book created", "<html>Book successfully created</html>");
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					int id = toCreate.getId();
					
					for (int i = 0; i < bookTagCreateList.size(); i++) {
						Tag tag = new Tag(0, "Book", id, bookTagCreateList.get(i));
						try {
							tag = tagService.create(tag);
						} catch (Exception e) {
							System.out.println("Your tag(s) could not be created. Check to see if it had a zero id before inserting");
						}
					}
					
					bookTagCreateList.clear();
					bookTagCreate.setText("");
					bookTitleCreate.setText("");
					bookAuthorCreate.setText("");
				}
			});
		} else if ("movieCreate".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					
					Movie toCreate = new Movie(0, movieTitleCreate.getText(), movieDirectorCreate.getText(), movieDescriptionCreate.getText());
					try {
						toCreate = movieService.create(toCreate);
						System.out.println("Your movie has been created!");
						System.out.println("Movie title: " + toCreate.getTitle());
						System.out.println("Movie director: " + toCreate.getDirector());
						System.out.println("Movie id: " + toCreate.getId());
						System.out.println("Movie description: " + toCreate.getDescription());
						
						createTextGUI("Movie created", "<html>Movie successfully created</html>");
					} catch (Exception e) {
						e.printStackTrace();
					} 
					
					int id = toCreate.getId();
					
					for (int i = 0; i < movieTagCreateList.size(); i++) {
						Tag tag = new Tag(0, "Movie", id, movieTagCreateList.get(i));
						try {
							tag = tagService.create(tag);
						} catch (Exception e) {
							System.out.println("Your tag(s) could not be created. Check to see if it had a zero id before inserting");
						}
					}
					
					movieTagCreateList.clear();
					movieTagCreate.setText("");
					movieTitleCreate.setText("");
					movieDirectorCreate.setText("");
					movieDescriptionCreate.setText("");
				}
			});
		} else if ("songCreate".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					
					Song toCreate = new Song(0, songTitleCreate.getText(), songArtistCreate.getText(), ".");
					try {
						toCreate = songService.create(toCreate);
						System.out.println("Your song has been created!");
						System.out.println("Song title: " + toCreate.getTitle());
						System.out.println("Song artist: " + toCreate.getSinger());
						System.out.println("Song id: " + toCreate.getId());
						System.out.println("Song description: " + toCreate.getDescription());
						
						createTextGUI("Song created", "<html>Song successfully created</html>");
					} catch (Exception e) {
						e.printStackTrace();
					} 
					
					int id = toCreate.getId();
					
					for (int i = 0; i < songTagCreateList.size(); i++) {
						Tag tag = new Tag(0, "Song", id, songTagCreateList.get(i));
						try {
							tag = tagService.create(tag);
						} catch (Exception e) {
							System.out.println("Your tag(s) could not be created. Check to see if it had a zero id before inserting");
						}
					}
					
					songTagCreateList.clear();
					songTagCreate.setText("");
					songTitleCreate.setText("");
					songArtistCreate.setText("");
				}
			});
		} else if ("bookTagCreateAdd".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					bookTagCreateList.add(bookTagCreate.getText());
					bookTagCreate.setText("");
				}
			});
		} else if ("movieTagCreateAdd".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					movieTagCreateList.add(movieTagCreate.getText());
					movieTagCreate.setText("");
				}
			});
		} else if ("songTagCreateAdd".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					songTagCreateList.add(songTagCreate.getText());
					songTagCreate.setText("");
				}
			});
		} else if ("bookDelete".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if (bookDeleteID.getText().equals("") || bookDeleteID.getText().equals("Book ID")) {
						createTextGUI("Warning", "<html>Make sure to load object to delete using ID</html>");
					} else {
						int id = Integer.parseInt(bookDeleteID.getText());
						try {
							if (bookService.retrieve(id) != null) {
								try {
									bookService.delete(id);
									System.out.println("Book deleted, id: " + id);
									createTextGUI("Book Deleted", "<html>Book successfully deleted</html>");
								} catch (Exception e) {
									e.printStackTrace();
								}
							} else {
								createTextGUI("Error", "<html>Invalid ID</html>");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});
		} else if ("movieDelete".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if (movieDeleteID.getText().equals("") || movieDeleteID.getText().equals("Movie ID")) {
						createTextGUI("Warning", "<html>Make sure to load object to delete using ID</html>");
					} else {
						int id = Integer.parseInt(movieDeleteID.getText());
						try {
							if (movieService.retrieve(id) != null) {
								try {
									movieService.delete(id);
									System.out.println("Movie deleted, id: " + id);
									createTextGUI("Movie Deleted", "<html>Movie successfully deleted</html>");
								} catch (Exception e) {
									e.printStackTrace();
								} 
							} else {
								createTextGUI("Error", "<html>Invalid ID</html>");
							}
						} catch (Exception e) {
							e.printStackTrace();
						} 
					}
				}
			});
		} else if ("songDelete".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if (songDeleteID.getText().equals("") || songDeleteID.getText().equals("Song ID")) {
						createTextGUI("Warning", "<html>Make sure to load object to delete using ID</html>");
					} else {
						int id = Integer.parseInt(songDeleteID.getText());
						try {
							if (songService.retrieve(id) != null) { 
								try {
									songService.delete(id);
									createTextGUI("Song Deleted", "<html>Song successfully deleted</html>");
									
								} catch (Exception e) {
									e.printStackTrace();
								}
							} else {
								createTextGUI("Error", "<html>Invalid ID</html>");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});
		} else if ("loadBook".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						if (bookUpdateID.getText().equals("")) {
							createTextGUI("Error", "<html>Invalid ID</html>");
						} else {
							bookToUpdate = bookService.retrieve(Integer.parseInt(bookUpdateID.getText()));
							if (bookToUpdate != null) {
								createTextGUI("Book loaded", "<html>Book successfully loaded by ID</html>");
							} else {
								createTextGUI("Error", "<html>Invalid ID</html>");
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else if ("bookTitleUpdate".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if(bookToUpdate == null) {
						createTextGUI("Warning", "<html>Make sure to load object using ID</html>");
					} else {
						bookToUpdate.setTitle(bookTitleUpdate.getText());
						bookTitleUpdate.setText("");
					}
				}
			});
		} else if ("bookAuthorUpdate".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if(bookToUpdate == null) {
						createTextGUI("Warning", "<html>Make sure to load object using ID</html>");
					} else {
						bookToUpdate.setAuthor(bookAuthorUpdate.getText());
						bookAuthorUpdate.setText("");
					}
				}
			});
		} else if ("bookTagAddUpdate".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if(bookToUpdate == null) {
						createTextGUI("Warning", "<html>Make sure to load object using ID</html>");
					} else {
						Tag tag = new Tag(0, "Book", bookToUpdate.getId(), bookTagAddUpdate.getText());
						try {
							tag = tagService.create(tag);
							bookTagAddUpdate.setText("");
							createTextGUI("Tag added", "<html>The tag was successfully added</html>");
						} catch (Exception e) {
							System.out.println("Your tag(s) could not be created. Check to see if it had a zero id before inserting");
						}
					}
				}
			});
		} else if ("bookTagDeleteUpdate".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						if(bookToUpdate == null) {
							createTextGUI("Warning", "<html>Make sure to load object using ID</html>");
						} else {
							List<Tag> tagList = tagService.retrieveForBookId(bookToUpdate.getId());
							
							int tagID = 0;
							for (int i = 0; i < tagList.size(); i++) {
								if (tagList.get(i).getDescription().equals(bookTagDeleteUpdate.getText())) {
									tagID = tagList.get(i).getId();
								}
							}
							
							if (tagID > 0) {
								tagService.delete(tagID);
								bookTagDeleteUpdate.setText("");
								createTextGUI("Tag Deleted", "<html>The tag was successfully deleted</html>");
							}
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else if ("bookUpdate".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						if(bookToUpdate == null) {
							createTextGUI("Warning", "<html>Make sure to load object using ID</html>");
						} else {
							bookService.update(bookToUpdate);
							createTextGUI("Book Updated", "<html>The book was successfully updated</html");
							
							bookToUpdate = null;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else if ("loadMovie".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						if (movieUpdateID.getText().equals("")) {
							createTextGUI("Error", "<html>Invalid ID</html>");
						} else {
							movieToUpdate = movieService.retrieve(Integer.parseInt(movieUpdateID.getText()));
							if (movieToUpdate != null) {
								createTextGUI("Movie loaded", "<html>Movie successfully loaded by ID</html>");
							} else {
								createTextGUI("Error", "<html>Invalid ID</html>");
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else if ("movieTitleUpdate".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if(movieToUpdate == null) {
						createTextGUI("Warning", "<html>Make sure to load object using ID</html>");
					} else {
						movieToUpdate.setTitle(movieTitleUpdate.getText());
						movieTitleUpdate.setText("");
					}
				}
			});
		} else if ("movieDirectorUpdate".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if(movieToUpdate == null) {
						createTextGUI("Warning", "<html>Make sure to load object using ID</html>");
					} else {
						movieToUpdate.setDirector(movieDirectorUpdate.getText());
						movieDirectorUpdate.setText("");
					}
				}
			});
		} else if ("movieDescriptionUpdate".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if(movieToUpdate == null) {
						createTextGUI("Warning", "<html>Make sure to load object using ID</html>");
					} else {
						movieToUpdate.setDescription(movieDescriptionUpdate.getText());
						movieDescriptionUpdate.setText("");
					}
				}
			});
		} else if ("movieTagAddUpdate".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if(movieToUpdate == null) {
						createTextGUI("Warning", "<html>Make sure to load object using ID</html>");
					} else {
						Tag tag = new Tag(0, "Movie", movieToUpdate.getId(), movieTagAddUpdate.getText());
						try {
							tag = tagService.create(tag);
							movieTagAddUpdate.setText("");
							createTextGUI("Tag Added", "<html>The tag was successfully added</html>");
						} catch (Exception e) {
							System.out.println("Your tag(s) could not be created. Check to see if it had a zero id before inserting");
						}
					}
				}
			});
		} else if ("movieTagDeleteUpdate".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						if(movieToUpdate == null) {
							createTextGUI("Warning", "<html>Make sure to load object using ID</html>");
						} else {
							List<Tag> tagList = tagService.retrieveForMovieId(movieToUpdate.getId());
							
							int tagID = 0;
							for (int i = 0; i < tagList.size(); i++) {
								if (tagList.get(i).getDescription().equals(movieTagDeleteUpdate.getText())) {
									tagID = tagList.get(i).getId();
								}
							}
							
							if (tagID > 0) {
								tagService.delete(tagID);
								movieTagDeleteUpdate.setText("");
								createTextGUI("Tag Deleted", "<html>The tag was successfully deleted</html>");
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else if ("movieUpdate".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						if(movieToUpdate == null) {
							createTextGUI("Warning", "<html>Make sure to load object using ID</html>");
						} else {
							movieService.update(movieToUpdate);
							createTextGUI("Movie Updated", "<html>The movie was successfully updated</html");
							movieToUpdate = null;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else if ("loadSong".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						if (songUpdateID.getText().equals("")) {
							createTextGUI("Error", "<html>Invalid ID</html>");
						} else {
							songToUpdate = songService.retrieve(Integer.parseInt(songUpdateID.getText()));
							if (songToUpdate != null) {
								createTextGUI("Song loaded", "<html>Song successfully loaded by ID</html>");
							} else {
								createTextGUI("Error", "<html>Invalid ID</html>");
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else if ("songTitleUpdate".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if(songToUpdate == null) {
						createTextGUI("Warning", "<html>Make sure to load object using ID</html>");
					} else {
						songToUpdate.setTitle(songTitleUpdate.getText());
						songTitleUpdate.setText("");
					}
				}
			});
		} else if ("songArtistUpdate".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if(songToUpdate == null) {
						createTextGUI("Warning", "<html>Make sure to load object using ID</html>");
					} else {
						songToUpdate.setSinger(songArtistUpdate.getText());
						songArtistUpdate.setText("");
					}
				}
			});
		} else if ("songTagAddUpdate".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if(songToUpdate == null) {
						createTextGUI("Warning", "<html>Make sure to load object using ID</html>");
					} else {
						Tag tag = new Tag(0, "Song", songToUpdate.getId(), songTagAddUpdate.getText());
						try {
							tag = tagService.create(tag);
							songTagAddUpdate.setText("");
							createTextGUI("Tag Added", "<html>The tag was successfully added</html>");
						} catch (Exception e) {
							System.out.println("Your tag(s) could not be created. Check to see if it had a zero id before inserting");
						}
					}
				}
			});
		} else if ("songTagDeleteUpdate".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						if(songToUpdate == null) {
							createTextGUI("Warning", "<html>Make sure to load object using ID</html>");
						} else {
						
							List<Tag> tagList = tagService.retrieveForSongId(songToUpdate.getId());
							
							int tagID = 0;
							for (int i = 0; i < tagList.size(); i++) {
								if (tagList.get(i).getDescription().equals(songTagDeleteUpdate.getText())) {
									tagID = tagList.get(i).getId();
								}
							}
							
							if (tagID > 0) {
								tagService.delete(tagID);
								songTagDeleteUpdate.setText("");
								createTextGUI("Tag Deleted", "<html>The tag was successfully deleted</html>");
							}
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else if ("songUpdate".equals(e.getActionCommand())) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						songService.update(songToUpdate);
						createTextGUI("Song Updated", "<html>The song was successfully updated</html");
						songToUpdate = null;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	protected void createTextGUI(String frameName, String text) {
		JFrame frame = new JFrame(frameName);
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.getContentPane().setBackground(BACKGROUND_GUI_COLOR);
		
		c.insets = new Insets(10,30,10,30);
		c.gridx = 0;
		c.gridy = 0;
		
		JLabel textLabel = new JLabel();
		textLabel.setForeground(Color.WHITE);
		textLabel.setText(text);
		frame.add(textLabel, c);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	protected void updateMovieGUI() {
		JFrame frame = new JFrame("Update Song");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.getContentPane().setBackground(BACKGROUND_GUI_COLOR);

		c.insets = new Insets(25, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 0;
		frame.add(movieUpdateID, c);
		
		c.insets = new Insets(25, 30, 15, 30);
		c.gridx = 1;
		c.gridy = 0;
		frame.add(loadMovie, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 1;
		frame.add(movieTitleUpdate, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 1;
		c.gridy = 1;
		frame.add(movieTitleUpdateButton, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 2;
		frame.add(movieDirectorUpdate, c);

		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 1;
		c.gridy = 2;
		frame.add(movieDirectorUpdateButton, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 3;
		frame.add(movieDescriptionUpdate, c);

		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 1;
		c.gridy = 3;
		frame.add(movieDescriptionUpdateButton, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 4;
		frame.add(movieTagAddUpdate, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 1;
		c.gridy = 4;
		frame.add(movieTagAddUpdateButton, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 5;
		frame.add(movieTagDeleteUpdate, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 1;
		c.gridy = 5;
		frame.add(movieTagDeleteUpdateButton, c);
		
		c.insets = new Insets(15, 30, 25, 30);
		c.gridx = 0;
		c.gridy = 6;
		frame.add(movieUpdate, c);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	protected void updateBookGUI() {
		JFrame frame = new JFrame("Update Book");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.getContentPane().setBackground(BACKGROUND_GUI_COLOR);

		c.insets = new Insets(25, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 0;
		frame.add(bookUpdateID, c);
		
		c.insets = new Insets(25, 30, 15, 30);
		c.gridx = 1;
		c.gridy = 0;
		frame.add(loadBook, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 1;
		frame.add(bookTitleUpdate, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 1;
		c.gridy = 1;
		frame.add(bookTitleUpdateButton, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 2;
		frame.add(bookAuthorUpdate, c);

		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 1;
		c.gridy = 2;
		frame.add(bookAuthorUpdateButton, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 3;
		frame.add(bookTagAddUpdate, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 1;
		c.gridy = 3;
		frame.add(bookTagAddUpdateButton, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 4;
		frame.add(bookTagDeleteUpdate, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 1;
		c.gridy = 4;
		frame.add(bookTagDeleteUpdateButton, c);
		
		c.insets = new Insets(15, 30, 25, 30);
		c.gridx = 0;
		c.gridy = 5;
		frame.add(bookUpdate, c);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	protected void updateSongGUI() {
		JFrame frame = new JFrame("Update Song");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.getContentPane().setBackground(BACKGROUND_GUI_COLOR);

		c.insets = new Insets(25, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 0;
		frame.add(songUpdateID, c);
		
		c.insets = new Insets(25, 30, 15, 30);
		c.gridx = 1;
		c.gridy = 0;
		frame.add(loadSong, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 1;
		frame.add(songTitleUpdate, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 1;
		c.gridy = 1;
		frame.add(songTitleUpdateButton, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 2;
		frame.add(songArtistUpdate, c);

		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 1;
		c.gridy = 2;
		frame.add(songArtistUpdateButton, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 3;
		frame.add(songTagAddUpdate, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 1;
		c.gridy = 3;
		frame.add(songTagAddUpdateButton, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 4;
		frame.add(songTagDeleteUpdate, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 1;
		c.gridy = 4;
		frame.add(songTagDeleteUpdateButton, c);

		c.insets = new Insets(15, 30, 25, 30);
		c.gridx = 0;
		c.gridy = 5;
		frame.add(songUpdate, c);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	protected void deleteSongGUI() {
		JFrame frame = new JFrame("Delete Song");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.getContentPane().setBackground(BACKGROUND_GUI_COLOR);

		c.insets = new Insets(25, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 0;
		frame.add(songDeleteID, c);

		c.insets = new Insets(15, 30, 25, 30);
		c.gridx = 0;
		c.gridy = 1;
		frame.add(songDelete, c);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	protected void deleteMovieGUI() {
		JFrame frame = new JFrame("Delete Movie");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.getContentPane().setBackground(BACKGROUND_GUI_COLOR);

		c.insets = new Insets(25, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 0;
		frame.add(movieDeleteID, c);

		c.insets = new Insets(15, 30, 25, 30);
		c.gridx = 0;
		c.gridy = 1;
		frame.add(movieDelete, c);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	protected void deleteBookGUI() {
		JFrame frame = new JFrame("Delete Book");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.getContentPane().setBackground(BACKGROUND_GUI_COLOR);

		c.insets = new Insets(25, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 0;
		frame.add(bookDeleteID, c);

		c.insets = new Insets(15, 30, 25, 30);
		c.gridx = 0;
		c.gridy = 1;
		frame.add(bookDelete, c);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	protected void createSongGUI() {
		JFrame frame = new JFrame("Create Song");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.getContentPane().setBackground(BACKGROUND_GUI_COLOR);

		c.insets = new Insets(25, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 0;
		frame.add(songTitleCreate, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 1;
		frame.add(songArtistCreate, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 2;
		frame.add(songTagCreate, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 1;
		c.gridy = 2;
		frame.add(songTagCreateAdd, c);
		
		c.insets = new Insets(15, 30, 25, 30);
		c.gridx = 0;
		c.gridy = 3;
		frame.add(songCreate, c);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	protected void createMovieGUI() {
		JFrame frame = new JFrame("Create Movie");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.getContentPane().setBackground(BACKGROUND_GUI_COLOR);

		c.insets = new Insets(25, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 0;
		frame.add(movieTitleCreate, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 1;
		frame.add(movieDirectorCreate, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 2;
		frame.add(movieDescriptionCreate, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 3;
		frame.add(movieTagCreate, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 1;
		c.gridy = 3;
		frame.add(movieTagCreateAdd, c);
		
		c.insets = new Insets(15, 30, 25, 30);
		c.gridx = 0;
		c.gridy = 4;
		frame.add(movieCreate, c);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	protected void createBookGUI() {
		JFrame frame = new JFrame("Create Book");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.getContentPane().setBackground(BACKGROUND_GUI_COLOR);

		c.insets = new Insets(25, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 0;
		frame.add(bookTitleCreate, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 1;
		frame.add(bookAuthorCreate, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 2;
		frame.add(bookTagCreate, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 1;
		c.gridy = 2;
		frame.add(bookTagCreateAdd, c);
		
		c.insets = new Insets(15, 30, 25, 30);
		c.gridx = 0;
		c.gridy = 3;
		frame.add(bookCreate, c);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	protected void createStaffGUI() {
		JFrame frame = new JFrame("Staff Menu");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.getContentPane().setBackground(BACKGROUND_GUI_COLOR);

		c.insets = new Insets(25, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 0;
		frame.add(createBook, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 1;
		frame.add(createMovie, c);

		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 2;
		frame.add(createSong, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 3;
		frame.add(deleteBook, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 4;
		frame.add(deleteMovie, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 5;
		frame.add(deleteSong, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 6;
		frame.add(updateBook, c);
		
		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 7;
		frame.add(updateMovie, c);

		c.insets = new Insets(15, 30, 25, 30);
		c.gridx = 0;
		c.gridy = 8;
		frame.add(updateSong, c);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	protected void createMovieTagSearchGUI() {
		JFrame frame = new JFrame("Search Movies by Tag");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.getContentPane().setBackground(BACKGROUND_GUI_COLOR);

		c.insets = new Insets(25, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 0;
		frame.add(movieTagSearchField, c);

		c.insets = new Insets(15, 30, 25, 30);
		c.gridx = 0;
		c.gridy = 1;
		frame.add(movieTagSearchButton, c);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	protected void createMovieDirectorSearchGUI() {
		JFrame frame = new JFrame("Search Movies by Director");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.getContentPane().setBackground(BACKGROUND_GUI_COLOR);

		c.insets = new Insets(25, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 0;
		frame.add(movieDirectorSearchField, c);

		c.insets = new Insets(15, 30, 25, 30);
		c.gridx = 0;
		c.gridy = 1;
		frame.add(movieDirectorSearchButton, c);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	protected void createMovieTitleSearchGUI() {
		JFrame frame = new JFrame("Search Movies by Title");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.getContentPane().setBackground(BACKGROUND_GUI_COLOR);

		c.insets = new Insets(25, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 0;
		frame.add(movieTitleSearchField, c);

		c.insets = new Insets(15, 30, 25, 30);
		c.gridx = 0;
		c.gridy = 1;
		frame.add(movieTitleSearchButton, c);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	protected void createBookTagSearchGUI() {
		JFrame frame = new JFrame("Search Books by Tag");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.getContentPane().setBackground(BACKGROUND_GUI_COLOR);

		c.insets = new Insets(25, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 0;
		frame.add(bookTagSearchField, c);

		c.insets = new Insets(15, 30, 25, 30);
		c.gridx = 0;
		c.gridy = 1;
		frame.add(bookTagSearchButton, c);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	protected void createBookAuthorSearchGUI() {
		JFrame frame = new JFrame("Search Books by Author");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.getContentPane().setBackground(BACKGROUND_GUI_COLOR);

		c.insets = new Insets(25, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 0;
		frame.add(bookAuthorSearchField, c);

		c.insets = new Insets(15, 30, 25, 30);
		c.gridx = 0;
		c.gridy = 1;
		frame.add(bookAuthorSearchButton, c);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	protected void createBookTitleSearchGUI() {
		JFrame frame = new JFrame("Search Books by Title");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.getContentPane().setBackground(BACKGROUND_GUI_COLOR);

		c.insets = new Insets(25, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 0;
		frame.add(bookTitleSearchField, c);

		c.insets = new Insets(15, 30, 25, 30);
		c.gridx = 0;
		c.gridy = 1;
		frame.add(bookTitleSearchButton, c);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	protected void createSongTagSearchGUI() {
		JFrame frame = new JFrame("Search Songs by Tag");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.getContentPane().setBackground(BACKGROUND_GUI_COLOR);

		c.insets = new Insets(25, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 0;
		frame.add(songTagSearchField, c);

		c.insets = new Insets(15, 30, 25, 30);
		c.gridx = 0;
		c.gridy = 1;
		frame.add(songTagSearchButton, c);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	protected void createSongArtistSearchGUI() {
		JFrame frame = new JFrame("Search Songs by Artist");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.getContentPane().setBackground(BACKGROUND_GUI_COLOR);

		c.insets = new Insets(25, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 0;
		frame.add(songArtistSearchField, c);

		c.insets = new Insets(15, 30, 25, 30);
		c.gridx = 0;
		c.gridy = 1;
		frame.add(songArtistSearchButton, c);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	protected void createSongTitleSearchGUI() {
		JFrame frame = new JFrame("Search Songs by Title");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.getContentPane().setBackground(BACKGROUND_GUI_COLOR);

		c.insets = new Insets(25, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 0;
		frame.add(songTitleSearchField, c);

		c.insets = new Insets(15, 30, 25, 30);
		c.gridx = 0;
		c.gridy = 1;
		frame.add(songTitleSearchButton, c);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	protected void createStaffLoginGUI() {
		JFrame frame = new JFrame("Staff Login");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.getContentPane().setBackground(BACKGROUND_GUI_COLOR);

		c.insets = new Insets(25, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 0;
		frame.add(username, c);

		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 1;
		frame.add(password, c);

		c.insets = new Insets(15, 30, 25, 30);
		c.gridx = 0;
		c.gridy = 2;
		frame.add(loginButton, c);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	protected void createMovieSearchGUI() {
		JFrame frame = new JFrame("Movie Search");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.getContentPane().setBackground(BACKGROUND_GUI_COLOR);

		c.insets = new Insets(30, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 0;
		frame.add(movieSearchHeader, c);

		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 1;
		frame.add(movieTitleSearchOption, c);

		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 2;
		frame.add(movieDirectorSearchOption, c);

		c.insets = new Insets(15, 30, 30, 30);
		c.gridx = 0;
		c.gridy = 3;
		frame.add(movieTagSearchOption, c);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	protected void createSongSearchGUI() {
		JFrame frame = new JFrame("Song Search");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.getContentPane().setBackground(BACKGROUND_GUI_COLOR);

		c.insets = new Insets(30, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 0;
		frame.add(songSearchHeader, c);

		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 1;
		frame.add(songTitleSearchOption, c);

		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 2;
		frame.add(songArtistSearchOption, c);

		c.insets = new Insets(15, 30, 30, 30);
		c.gridx = 0;
		c.gridy = 3;
		frame.add(songTagSearchOption, c);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	protected void createBookSearchGUI() {
		JFrame frame = new JFrame("Book Search");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.getContentPane().setBackground(BACKGROUND_GUI_COLOR);

		c.insets = new Insets(30, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 0;
		frame.add(bookSearchHeader, c);

		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 1;
		frame.add(bookTitleSearchOption, c);

		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 2;
		frame.add(bookAuthorSearchOption, c);

		c.insets = new Insets(15, 30, 30, 30);
		c.gridx = 0;
		c.gridy = 3;
		frame.add(bookTagSearchOption, c);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void createMainGUI(JFrame frame) {
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(BACKGROUND_GUI_COLOR);

		c.insets = new Insets(30, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 0;
		frame.add(title, c);

		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 1;
		frame.add(searchMoviesGUI, c);

		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 2;
		frame.add(searchSongsGUI, c);

		c.insets = new Insets(15, 30, 15, 30);
		c.gridx = 0;
		c.gridy = 3;
		frame.add(searchBooksGUI, c);

		c.insets = new Insets(15, 30, 30, 30);
		c.gridx = 0;
		c.gridy = 4;
		frame.add(accessStaffLogin, c);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}