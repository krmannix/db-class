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

public class CommentController {

	private static final String COMMENTS_BY_PHOTO = "SELECT c.*, u.first_name, u.last_name FROM Comments c INNER JOIN Users u ON u.user_id = c.user_id WHERE photo_id = ?;";
	private static final String MAKE_PHOTO = "INSERT INTO Comments (user_id, photo_id, text) VALUES (?, ?, ?);";

	public static void makeComment(int user_id, int photo_id, String text) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(MAKE_PHOTO);
    		stmt.setInt(1, user_id);
    		stmt.setInt(2, photo_id);
    		stmt.setString(3, text);
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

	public static List<Comment> getCommentsForPhoto(int photo_id) {
		// SQL Part
		List<Comment> foundComments = new ArrayList<Comment>();
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(COMMENTS_BY_PHOTO);
			stmt.setInt(1, photo_id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				foundComments.add(new Comment(rs.getInt("comment_id"), rs.getString("text"), rs.getInt("photo_id"), rs.getInt("user_id"), rs.getString("first_name"), rs.getString("last_name")));
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
		return foundComments;
	}

}