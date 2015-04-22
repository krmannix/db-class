package photoshare;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class TagController {
	private static final String INSERT_TAG = "INSERT INTO Tag (tag_name) VALUES (?) RETURNING tag_id;";
	private static final String INSERT_TAG_PHOTO = "INSERT INTO Tag_photo (tag_id, photo_id) VALUES (?, ?);";
	private static final String GET_ALL_TAGS = "SELECT * FROM Tag";
	private static final String GET_TAG_BY_ID = "SELECT * FROM Tag WHERE tag_id = ?;";
	private static final String GET_ALL_TAGS_BY_USER = "SELECT t.* FROM Tag t " +
						"INNER JOIN tag_photo tp ON tp.tag_id = t.tag_id " +
						"WHERE tp.photo_id IN " +
						"(SELECT p.picture_id FROM Pictures p WHERE p.album_id IN " +
						"(SELECT a.album_id FROM Albums a WHERE a.user_id = ?))";

	public static List<Tag> getAllTags() {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<Tag> allTags = new ArrayList<Tag>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(GET_ALL_TAGS);
			rs = stmt.executeQuery();
			while (rs.next()) {
				allTags.add(new Tag(rs.getInt("tag_id"), rs.getString("tag_name")));
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
		return allTags;
	}

	public static List<Tag> getTagsByUserId(int user_id) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<Tag> allTags = new ArrayList<Tag>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(GET_ALL_TAGS_BY_USER);
			stmt.setInt(1, user_id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				allTags.add(new Tag(rs.getInt("tag_id"), rs.getString("tag_name")));
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
		return allTags;
	}

	public static Tag getTagById(int tag_id) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Tag tag = null;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(GET_TAG_BY_ID);
			stmt.setInt(1, tag_id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				tag = new Tag(rs.getInt("tag_id"), rs.getString("tag_name"));
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
		return tag;
	}

	public static void setPhotoTags(int p_id, String tagstring) {
		if (tagstring != null && tagstring.length() > 0) {
			String[] tags = tagstring.split(",");
			PreparedStatement stmt = null;
			Connection conn = null;
			ResultSet rs = null;
			if (tags.length > 0) {
				try {
					conn = DbConnection.getConnection();
					for (int i = 0; i < tags.length; i++) {
						stmt = conn.prepareStatement(INSERT_TAG);
						stmt.setString(1, tags[i].trim());
						rs = stmt.executeQuery();
						if (rs.next()) {
							stmt = conn.prepareStatement(INSERT_TAG_PHOTO);
							stmt.setInt(1, rs.getInt("tag_id"));
							stmt.setInt(2, p_id);
							stmt.executeUpdate();
						}
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
			}
		}
	}
}