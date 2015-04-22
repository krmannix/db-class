package photoshare;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class AlbumController {

	private static final String MAKE_ALBUM = "INSERT INTO albums (user_id, name) VALUES (?, ?);";
	private static final String SEARCH_ALBUMS = "SELECT * FROM albums WHERE user_id = ?;";

	public static void makeAlbum(int user_id, String name) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(MAKE_ALBUM);
    		stmt.setInt(1, user_id);
    		stmt.setString(2, name);
			stmt.executeUpdate();
			stmt.close();
			stmt = null;
			conn.close();
			conn = null;
		} catch (SQLException e) {
      		e.printStackTrace();
      		throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}
	}

	public static List<Album> getAlbums(int user_id) {
		// SQL Part
		List<Album> foundAlbums = new ArrayList<Album>();
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(SEARCH_ALBUMS);
			stmt.setInt(1, user_id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				foundAlbums.add(new Album(rs.getInt("user_id"), rs.getInt("album_id"), rs.getString("name")));
			}
			rs.close();
			rs = null;
			stmt.close();
			stmt = null;
			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
      		throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try { rs.close(); } catch (SQLException e) { ; }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}
		return foundAlbums;
	}
}