package photoshare;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class TagController {
	private static final String INSERT_TAG = "INSERT INTO Tag (tag_name) VALUES (?) RETURNING tag_id;";
	private static final String INSERT_TAG_PHOTO = "INSERT INTO Tag_photo (tag_id, photo_id) VALUES (?, ?);";
	private static final String GET_ALL_TAGS = "SELECT * FROM Tag";
	private static final String GET_POPULAR_TAGS = "SELECT t.* FROM Tag t INNER JOIN (SELECT tp.tag_id, COUNT(*) AS numPhotos FROM Tag_photo tp GROUP BY tp.tag_id ORDER BY numPhotos DESC LIMIT 10 OFFSET 0) p ON t.tag_id = p.tag_id ORDER BY p.numPhotos DESC;";
	private static final String GET_TAG_BY_NAME = "SELECT * FROM Tag WHERE tag_name = ?;";
	private static final String GET_TAG_BY_ID = "SELECT * FROM Tag WHERE tag_id = ?;";
	private static final String GET_ALL_TAGS_BY_USER = "SELECT t.* FROM Tag t " +
						"INNER JOIN tag_photo tp ON tp.tag_id = t.tag_id " +
						"WHERE tp.photo_id IN " +
						"(SELECT p.picture_id FROM Pictures p WHERE p.album_id IN " +
						"(SELECT a.album_id FROM Albums a WHERE a.user_id = ?))";
	private static final String GET_RECOMMENDED_TAGS_1 = "SELECT DISTINCT t_.tag_name, t_.tag_id FROM tag t_ INNER JOIN tag_photo tp_ ON tp_.tag_id = t_.tag_id WHERE tp_.photo_id IN (SELECT tp.photo_id FROM tag_photo tp WHERE tp.tag_id IN (SELECT t.tag_id FROM tag t WHERE t.tag_name IN ("; 
	private static final String GET_RECOMMENDED_TAGS_2 = ")) AND tp_.tag_id NOT IN (SELECT t.tag_id FROM tag t WHERE t.tag_name IN (";
	private static final String GET_RECOMMENDED_TAGS_3 = ")));";

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

	public static List<Tag> getRecommendedTags(String taglist) {
		String[] broken_tags = taglist.split(",");
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<Tag> allTags = new ArrayList<Tag>();
		StringBuilder builder = new StringBuilder("");
    	for (int i = 0; i < broken_tags.length; i++) {
    		builder.append("?,");
    	}
		try {
			conn = DbConnection.getConnection();
			String qs = builder.deleteCharAt(builder.length()-1).toString();
			stmt = conn.prepareStatement(GET_RECOMMENDED_TAGS_1 + qs + GET_RECOMMENDED_TAGS_2 + qs + GET_RECOMMENDED_TAGS_3);
			for (int i = 0; i < broken_tags.length; i++) {
				stmt.setString(i+1, broken_tags[i].trim());
				stmt.setString(i+1+broken_tags.length, broken_tags[i].trim());
			}
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

	public static List<Tag> getPopularTags() {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<Tag> allTags = new ArrayList<Tag>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(GET_POPULAR_TAGS);
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
						stmt = conn.prepareStatement(GET_TAG_BY_NAME);
						stmt.setString(1, tags[i].trim());
						rs = stmt.executeQuery();
						if (rs.next()) {
							// Then id already existed
							stmt = conn.prepareStatement(INSERT_TAG_PHOTO);
							stmt.setInt(1, rs.getInt("tag_id"));
							stmt.setInt(2, p_id);
							stmt.executeUpdate();
						} else {
							// Id didn't exist, insert and then add tag_photo
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