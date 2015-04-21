package photoshare;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class RegistrationController {
	private static final String CHECK_EXISTING_USER = "SELECT COUNT(*) FROM Users WHERE email = ?";
	private static final String INSERT_USER = "INSERT INTO Users () VALUES (?, ?, ?, ?);";

    public static boolean registerUser(User user) {
    	Date data = new Timestamp((user.getDOB()).getTime());
		// try {
		// 	PreparedStatement stmt = null;
		// 	Connection conn = null;
		// 	ResultSet rs = null;
		// 	Picture picture = null;
		// 	conn = DbConnection.getConnection();
		// 	stmt = conn.prepareStatement(LOAD_PICTURE_STMT);
  //   		stmt.setInt(1, id);
		// 	rs = stmt.executeQuery();
		// 	rs.close();
		// 	rs = null;
		// 	stmt.close();
		// 	stmt = null;
		// 	conn.close();
		// 	conn = null;
		// } finally {
		// 	if (rs != null) {
		// 		try { rs.close(); } catch (SQLException e) { ; }
		// 		rs = null;
		// 	}
		// 	if (stmt != null) {
		// 		try { stmt.close(); } catch (SQLException e) { ; }
		// 		stmt = null;
		// 	}
		// 	if (conn != null) {
		// 		try { conn.close(); } catch (SQLException e) { ; }
		// 		conn = null;
		// 	}
		// }

		return true;
    }

    public static boolean validateUser(String firstName, String lastName, String email, 
    							String password, String gender, String bday, String bmonth, String byear, 
    							String c_city, String c_state, String c_country, String h_city, String h_state, 
    							String h_country) {

		if (doesUserExist(email)) return false;
    	else if (firstName.length() == 0) return false;
    	else if (lastName.length() == 0) return false;
    	else if (email.length() == 0) return false;
    	else if (password.length() == 0) return false;
    	else if (gender.length() == 0) return false;
    	else if (bday == null || bmonth == null || byear == null) return false;
    	else return true;
    }

    // SQL METHODS

    private static boolean doesUserExist(String email) {
    	boolean userExists = false;
    	PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Picture picture = null;
    	try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(CHECK_EXISTING_USER);
    		stmt.setString(1, email);
			rs = stmt.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				if (count > 0) userExists = true;
			} else {
				userExists = true;
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
		return userExists;
    }
}