package photoshare;

// import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.sql.Date;
import java.sql.*;

public class Album {
	public int user_id;
	public int album_id;
	public String name;

	public Album(int user_id, int album_id, String name) {
		this.user_id = user_id;
		this.album_id = album_id;
		this.name = name;
	}
}