package photoshare;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class UserController {

	private static final String SEARCH_USER = "SELECT * FROM Users WHERE user_id = ?";
	private static final String SEARCH_USER_EMAIL = "SELECT * FROM Users WHERE email = ?";

	public static int getUserIdByEmail(String email) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Picture picture = null;
		int i = -1;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(SEARCH_USER_EMAIL);
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			if (rs.next()) {
				i = rs.getInt("user_id");
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
		return i;
	}

	public static User getUser(String user_id) {
		if (user_id == null) return null;
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Picture picture = null;
		User u;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(SEARCH_USER);
			stmt.setInt(1, Integer.parseInt(user_id));
			rs = stmt.executeQuery();
			if (rs.next() && rs.getString("first_name") != null) {
				u = new User(rs.getInt("user_id"),
					rs.getString("first_name"), rs.getString("last_name"), 
					rs.getString("email"), rs.getString("password"), rs.getBoolean("male"), rs.getTimestamp("dob"),
					rs.getString("current_city"), rs.getString("current_state"), rs.getString("current_country"),
					rs.getString("home_city"), rs.getString("home_city"), rs.getString("home_country"));
			} else {
				u = null;
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
		return u;
	}

	public static User getUser(int user_id) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Picture picture = null;
		User u;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(SEARCH_USER);
			stmt.setInt(1, user_id);
			rs = stmt.executeQuery();
			if (rs.next() && rs.getString("first_name") != null) {
				u = new User(rs.getInt("user_id"),
					rs.getString("first_name"), rs.getString("last_name"), 
					rs.getString("email"), rs.getString("password"), rs.getBoolean("male"), rs.getTimestamp("dob"),
					rs.getString("current_city"), rs.getString("current_state"), rs.getString("current_country"),
					rs.getString("home_city"), rs.getString("home_city"), rs.getString("home_country"));
			} else {
				u = null;
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
		return u;
	}

	public static String fireEvent() {
		return "EVENT WAS FIRED";
	}
}