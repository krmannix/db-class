package photoshare;

// import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.sql.Date;
import java.sql.*;

public class Comment {
	public String text;
	public int id;
	public int photo_id;
	public int user_id;
	public String first_name;
	public String last_name;

	public Comment(int id, String text, int photo_id, int user_id) {
		this.id = id;
		this.text = text;
		this.photo_id = photo_id;
		this.user_id = user_id;
	}

	public Comment(int id, String text, int photo_id, int user_id, String first, String last) {
		this.id = id;
		this.text = text;
		this.photo_id = photo_id;
		this.user_id = user_id;
		this.first_name = first;
		this.last_name = last;
	}
}