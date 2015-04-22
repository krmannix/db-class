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

public class FriendController {
	private static final String SEARCH_FRIENDS = "SELECT * FROM Friends WHERE user_id_1 = ? AND user_id_2 = ? UNION SELECT * FROM Friends WHERE user_id_1 = ? AND user_id_2 = ?";
	private static final String MAKE_FRIENDS = "INSERT INTO Friends (user_id_1, user_id_2) VALUES (?, ?);";

	public static boolean areFriends(int user_id_1, int user_id_2) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		boolean areFriends = false;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(SEARCH_FRIENDS);
			stmt.setInt(1, user_id_1);
			stmt.setInt(2, user_id_2);
			stmt.setInt(3, user_id_2);
			stmt.setInt(4, user_id_1);
			rs = stmt.executeQuery();
			if (rs.next()) {
				areFriends = true;
			}
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
		return areFriends;
	}

	public static void makeFriends(int user_id_1, int user_id_2) {
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(MAKE_FRIENDS);
			stmt.setInt(1, user_id_1);
			stmt.setInt(2, user_id_2);
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
}