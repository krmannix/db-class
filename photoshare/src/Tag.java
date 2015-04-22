package photoshare;

// import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.sql.Date;
import java.sql.*;

public class Tag {
	public int tag_id;
	public String tag_name;

	public Tag(int tag_id, String tag_name) {
		this.tag_id = tag_id;
		this.tag_name = tag_name;
	}
}