package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A data access object (DAO) to handle picture objects
 *
 * @author G. Zervas <cs460tf@bu.edu>
 */
public class PictureDao {
  private static final String LOAD_PICTURE_STMT = "SELECT " +
      "\"caption\", \"imgdata\", \"thumbdata\", \"size\", \"content_type\", \"album_id\" FROM Pictures WHERE \"picture_id\" = ?;";

  private static final String SAVE_PICTURE_STMT = "INSERT INTO " +
      "Pictures (\"caption\", \"imgdata\", \"thumbdata\", \"size\", \"content_type\", \"album_id\" ) VALUES (?, ?, ?, ?, ?, ?) RETURNING picture_id;";

  private static final String ALL_PICTURE_IDS_STMT = "SELECT \"picture_id\" FROM Pictures ORDER BY \"picture_id\" DESC";

  private static final String PICTURES_BY_ALBUM = "SELECT " +
      "\"caption\", \"imgdata\", \"thumbdata\", \"size\", \"content_type\", \"album_id\", \"picture_id\" FROM Pictures WHERE \"album_id\" = ?;";

  private static final String PICTURES_BY_TAG = "SELECT " +
      "\"caption\", \"imgdata\", \"thumbdata\", \"size\", \"content_type\", \"album_id\", \"picture_id\" FROM Pictures " +
      "INNER JOIN Tag_photo ON photo_id = picture_id WHERE \"tag_id\" = ?;";

  private static final String PICTURES_BY_TAG_AND_USER = "SELECT " +
      "\"caption\", \"imgdata\", \"thumbdata\", \"size\", \"content_type\", \"album_id\", \"picture_id\" FROM Pictures " +
      "INNER JOIN Tag_photo ON photo_id = picture_id WHERE \"tag_id\" = ? AND album_id IN (SELECT a.album_id FROM Albums a WHERE a.user_id = ?);";

  private static final String PICTURES_BY_USER = "SELECT " +
      "\"caption\", \"imgdata\", \"thumbdata\", \"size\", \"content_type\", \"album_id\", \"picture_id\" FROM Pictures " +
      "WHERE album_id IN (SELECT a.album_id FROM Albums a WHERE a.user_id = ?);";

  private static final String DELETE_PICTURE = "BEGIN; DELETE FROM Pictures WHERE picture_id = ?; DELETE FROM Comments WHERE photo_id = ?; DELETE FROM Likes WHERE photo_id = ?; DELETE FROM Tag_photo WHERE photo_id = ?; COMMIT;";

  public Picture load(int id) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Picture picture = null;
    try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_PICTURE_STMT);
      		stmt.setInt(1, id);
			rs = stmt.executeQuery();
      if (rs.next()) {
        picture = new Picture();
        picture.setId(id);
        picture.setCaption(rs.getString(1));
        picture.setData(rs.getBytes(2));
        picture.setThumbdata(rs.getBytes(3));
        picture.setSize(rs.getLong(4));
        picture.setContentType(rs.getString(5));
        picture.setAlbumId(rs.getInt(6));
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

		return picture;
	}

	public int save(Picture picture) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		int p_id = -1;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(SAVE_PICTURE_STMT);
			stmt.setString(1, picture.getCaption());
			stmt.setBytes(2, picture.getData());
			stmt.setBytes(3, picture.getThumbdata());
			stmt.setLong(4, picture.getSize());
			stmt.setString(5, picture.getContentType());
			stmt.setInt(6, picture.getAlbumId());
			rs = stmt.executeQuery();
			if (rs.next()) {
				p_id = rs.getInt("picture_id");
			}
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
		return p_id;
	}

	public void deletePicture(int photo_id) {
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(DELETE_PICTURE);
			stmt.setInt(1, photo_id);
			stmt.setInt(2, photo_id);
			stmt.setInt(3, photo_id);
			stmt.setInt(4, photo_id);
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

	public List<Integer> allPicturesIds() {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		List<Integer> picturesIds = new ArrayList<Integer>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ALL_PICTURE_IDS_STMT);
			rs = stmt.executeQuery();
			while (rs.next()) {
				picturesIds.add(rs.getInt(1));
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

		return picturesIds;
	}

	public List<Picture> getPicturesByAlbumId(int album_id) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		List<Picture> allPics = new ArrayList<Picture>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(PICTURES_BY_ALBUM);
			stmt.setInt(1, album_id);
			rs = stmt.executeQuery();
			while (rs.next()) {
		        Picture picture = new Picture();
		        picture.setCaption(rs.getString(1));
		        picture.setData(rs.getBytes(2));
		        picture.setThumbdata(rs.getBytes(3));
		        picture.setSize(rs.getLong(4));
		        picture.setContentType(rs.getString(5));
		        picture.setAlbumId(rs.getInt(6));
		        picture.setId(rs.getInt(7));
		        allPics.add(picture);
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

		return allPics;
	}

	public List<Picture> getPicturesByTagID(int tag_id) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		List<Picture> allPics = new ArrayList<Picture>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(PICTURES_BY_TAG);
			stmt.setInt(1, tag_id);
			rs = stmt.executeQuery();
			while (rs.next()) {
		        Picture picture = new Picture();
		        picture.setCaption(rs.getString(1));
		        picture.setData(rs.getBytes(2));
		        picture.setThumbdata(rs.getBytes(3));
		        picture.setSize(rs.getLong(4));
		        picture.setContentType(rs.getString(5));
		        picture.setAlbumId(rs.getInt(6));
		        picture.setId(rs.getInt(7));
		        allPics.add(picture);
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

		return allPics;
	}

	public List<Picture> getPicturesByUserID(int user_id) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		List<Picture> allPics = new ArrayList<Picture>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(PICTURES_BY_USER);
			stmt.setInt(1, user_id);
			rs = stmt.executeQuery();
			while (rs.next()) {
		        Picture picture = new Picture();
		        picture.setCaption(rs.getString(1));
		        picture.setData(rs.getBytes(2));
		        picture.setThumbdata(rs.getBytes(3));
		        picture.setSize(rs.getLong(4));
		        picture.setContentType(rs.getString(5));
		        picture.setAlbumId(rs.getInt(6));
		        picture.setId(rs.getInt(7));
		        allPics.add(picture);
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

		return allPics;
	}

	public List<Picture> getPicturesByTagIDandUserID(int tag_id, int user_id) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		List<Picture> allPics = new ArrayList<Picture>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(PICTURES_BY_TAG_AND_USER);
			stmt.setInt(1, tag_id);
			stmt.setInt(2, user_id);
			rs = stmt.executeQuery();
			while (rs.next()) {
		        Picture picture = new Picture();
		        picture.setCaption(rs.getString(1));
		        picture.setData(rs.getBytes(2));
		        picture.setThumbdata(rs.getBytes(3));
		        picture.setSize(rs.getLong(4));
		        picture.setContentType(rs.getString(5));
		        picture.setAlbumId(rs.getInt(6));
		        picture.setId(rs.getInt(7));
		        allPics.add(picture);
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

		return allPics;
	}

}
