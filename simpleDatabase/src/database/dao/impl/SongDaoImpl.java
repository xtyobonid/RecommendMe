package database.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

import database.dao.SongDAO;
import database.entity.Song;
import database.util.DAOException;

public class SongDaoImpl implements SongDAO {
	private static final String insertSQL = "INSERT INTO Song (title, singer, description) VALUES (?, ?, ?);";

	@Override
	public Song create(Connection connection, Song song) throws SQLException, DAOException {
		if (song.getId() != 0) {
			throw new DAOException("Trying to insert Song with NON-NULL ID");
		}

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, song.getTitle());
			ps.setString(2, song.getSinger());
			ps.setString(3, song.getDescription());
			ps.executeUpdate();

			// Copy the assigned ID to the book instance.
			ResultSet keyRS = ps.getGeneratedKeys();
			keyRS.next();
			int lastKey = keyRS.getInt(1);
			song.setId(lastKey);
			return song;
		} finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	private static final String selectSQL = "SELECT id, title, singer, description FROM Song where id = ?";

	@Override
	public Song retrieve(Connection connection, int id) throws SQLException, DAOException {
		if (id == 0)
			throw new DAOException("Tried to retrieve song with null id");

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(selectSQL);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next() == false) {
				return null;
			}

			Song song = new Song();
			song.setId(rs.getInt("id"));
			song.setTitle(rs.getString("title"));
			song.setSinger(rs.getString("singer"));
			song.setDescription(rs.getString("description"));
			return song;
		} finally {
			// if ps is not null and is open... close it
			if ((ps != null) && (ps.isClosed() == false))
				ps.close();
		}

	}

	private static final String updateSQL = "UPDATE Song SET title =?, singer =?, description =? WHERE id=?";

	@Override
	public int update(Connection connection, Song song) throws SQLException, DAOException {
		int id = song.getId();

		if (id == 0)
			throw new DAOException("Trying to update song with null id");

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(updateSQL);
			ps.setString(1, song.getTitle());
			ps.setString(2, song.getSinger());
			ps.setString(3, song.getDescription());
			ps.setInt(4, id);

			int rowCount = ps.executeUpdate();
			return rowCount;
		} finally {
			if ((ps != null) && (ps.isClosed() == false))
				ps.close();
		}
	}

	private static final String deleteSQL = "DELETE FROM Song WHERE id=?";

	@Override
	public int delete(Connection connection, int id) throws SQLException, DAOException {
		if (id == 0)
			throw new DAOException("Trying to delete song with null id");

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(deleteSQL);
			ps.setLong(1, id);

			int rowCount = ps.executeUpdate();
			if (rowCount != 1)
				throw new DAOException("Delete produced unexpected result");
			else
				return rowCount;
		} finally {
			if ((ps != null) && (ps.isClosed() == false))
				ps.close();
		}
	}

	private static final String retrieveSingerSQL = "SELECT * FROM Song WHERE singer=?";

	public List<Song> retrieveBySinger(Connection connection, String singer) throws SQLException, DAOException {
		PreparedStatement ps = null;
		try {
			List<Song> list = new ArrayList<Song>();
			ps = connection.prepareStatement(retrieveSingerSQL);
			ps.setString(1, singer);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				/*
				int id = rs.getInt("id");
				String description = rs.getString("description");
				String titleName = rs.getString("title");
				String singerName = rs.getString("singer"); */
				int id = rs.getInt(1);							//Column 1 (id)
				String description = rs.getString(2);			//Column 2 (desc)
				String titleName = rs.getString(3);				//Column 3 (title)
				String singerName = rs.getString(4);			//Column 4 (singer)
				// set the retrieved values to the temp object
				Song temp = new Song();
				temp.setId(id);
				temp.setDescription(description);
				temp.setTitle(titleName);
				temp.setSinger(singerName);
				list.add(temp);
			}
			return list;
		} finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	private static final String retrieveTitleSQL = "SELECT * FROM Song WHERE title=?";

	public List<Song> retrieveByTitle(Connection connection, String title) throws SQLException, DAOException {
		PreparedStatement ps = null;
		try {
			List<Song> list = new ArrayList<Song>();
			ps = connection.prepareStatement(retrieveTitleSQL);
			ps.setString(1, title);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				/*
				int id = rs.getInt("id");
				String description = rs.getString("description");
				String titleName = rs.getString("title");
				String singerName = rs.getString("singer"); */
				int id = rs.getInt(1);							//Column 1 (id)
				String description = rs.getString(2);			//Column 2 (desc)
				String titleName = rs.getString(3);				//Column 3 (title)
				String singerName = rs.getString(4);			//Column 4 (singer)
				// set the retrieved values to the temp object
				Song temp = new Song();
				temp.setId(id);
				temp.setDescription(description);
				temp.setTitle(titleName);
				temp.setSinger(singerName);
				list.add(temp);
			}
			return list;
		} finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}
	
	private static final String retrieveTagSQL = "Select * FROM Song Join Tag on Song.id = Tag.category_id WHERE Tag.category_name='Song' AND Tag.description=?";
	
	public List<Song> retrieveByTagName(Connection connection, String tag) throws SQLException, DAOException {
		PreparedStatement ps = null;
		try {
			List<Song> list = new ArrayList<Song>();
			ps = connection.prepareStatement(retrieveTagSQL);
			ps.setString(1, tag);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				/*
				int id = rs.getInt("id");
				String description = rs.getString("description");
				String titleName = rs.getString("title");
				String singerName = rs.getString("singer"); */
				int id = rs.getInt(1);							//Column 1 (id)
				String description = rs.getString(2);			//Column 2 (desc)
				String titleName = rs.getString(3);				//Column 3 (title)
				String singerName = rs.getString(4);			//Column 4 (singer)
				// set the retrieved values to the temp object
				Song temp = new Song();
				temp.setId(id);
				temp.setDescription(description);
				temp.setTitle(titleName);
				temp.setSinger(singerName);
				list.add(temp);
			}
			return list;
		} finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

}