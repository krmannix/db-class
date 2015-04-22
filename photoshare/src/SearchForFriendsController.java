package photoshare;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class SearchForFriendsController {
	private static final String SEARCH_USERS = "SELECT * FROM Users WHERE first_name ILIKE ? OR last_name ILIKE ?";

	public static List<User> searchForFriends(String searchfield) {
		List<User> foundUsers = new ArrayList<User>();
		String[] names;
		if (searchfield.indexOf(' ') > -1) {
			names = searchfield.split(" ", 2);
		} else {
			names = new String[] {searchfield};
		}

		// SQL Part
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Picture picture = null;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(SEARCH_USERS);
			stmt.setString(1, '%' + names[0] + '%');
			if (names.length > 1)
				stmt.setString(2, '%' + names[1] + '%');
			else
				stmt.setString(2, '%' + names[0] + '%');
			rs = stmt.executeQuery();
			while (rs.next()) {
				foundUsers.add(new User(rs.getInt("user_id"),
					rs.getString("first_name"), rs.getString("last_name"), 
					rs.getString("email"), rs.getString("password"), rs.getBoolean("male"), rs.getTimestamp("dob"),
					rs.getString("current_city"), rs.getString("current_state"), rs.getString("current_country"),
					rs.getString("home_city"), rs.getString("home_city"), rs.getString("home_country")));
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

		return foundUsers;
	}
}