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

public class RegistrationController {
	private static final String CHECK_EXISTING_USER = "SELECT COUNT(*) FROM Users WHERE email = ?";
	private static final String INSERT_USER = "INSERT INTO Users (email, password, first_name, last_name, home_city, home_state, home_country, current_city, current_state, current_country, dob, male) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    public static boolean registerUser(User user) {
    	Timestamp timestamp = new Timestamp((user.getDOB()).getTime());
    	PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Picture picture = null;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(INSERT_USER);
    		stmt.setString(1, user.email);
    		stmt.setString(2, user.password);
    		stmt.setString(3, user.first_name);
    		stmt.setString(4, user.last_name);
    		stmt.setString(5, user.home_city);
    		stmt.setString(6, user.home_state);
    		stmt.setString(7, user.home_country);
    		stmt.setString(8, user.current_city);
    		stmt.setString(9, user.current_state);
    		stmt.setString(10, user.current_country);
    		stmt.setTimestamp(11, timestamp);
    		stmt.setBoolean(12, user.male);
			stmt.executeUpdate();
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