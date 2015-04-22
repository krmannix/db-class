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

public class LikeController {

	private static final String MAKE_LIKE = "INSERT INTO likes (user_id, photo_id) VALUES (?, ?);";
	private static final String DELETE_LIKE = "DELETE FROM likes WHERE user_id = ? AND photo_id = ?;";
	private static final String GET_LIKES = "SELECT COUNT(*) AS total_likes FROM likes WHERE photo_id = ?;";
	private static final String LIKES_PHOTO = "SELECT * FROM likes WHERE photo_id = ? AND user_id = ?;";

	public static void makeLike(int user_id, int photo_id) {
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(MAKE_LIKE);
    		stmt.setInt(1, user_id);
    		stmt.setInt(2, photo_id);
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

	public static void deleteLike(int user_id, int photo_id) {
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(DELETE_LIKE);
    		stmt.setInt(1, user_id);
    		stmt.setInt(2, photo_id);
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

	public static boolean userLikePhoto(int photo_id, int user_id) {
		// SQL Part
		boolean likesPhoto = false;
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LIKES_PHOTO);
			stmt.setInt(1, photo_id);
			stmt.setInt(2, user_id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				likesPhoto = true;
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
		return likesPhoto;
	}

	public static int getLikesForPhoto(int photo_id) {
		// SQL Part
		int numLikes = 0;
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(GET_LIKES);
			stmt.setInt(1, photo_id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				numLikes = rs.getInt("total_likes");
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
		return numLikes;
	}
	
}