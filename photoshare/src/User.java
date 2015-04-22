package photoshare;

// import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.sql.Date;
import java.sql.*;

public class User {

	public int id;
	public String first_name;
	public String last_name;
	public String email;
	public String password;
	public Date dob;
	public boolean male;
	public String current_city;
	public String current_state;
	public String current_country;
	public String home_city;
	public String home_state;
	public String home_country;

	public User() {

	}

	public User(String firstName, String lastName, String email, 
				String password, String gender, String bday, String bmonth, String byear, 
				String c_city, String c_state, String c_country, String h_city, String h_state, 
				String h_country) {
		this.first_name = firstName;
		this.last_name = lastName;
		this.email = email;
		this.password = password;
		this.male = gender.equals("Female") ? false : true;
		this.current_city = c_city;
		this.current_country = c_country;
		this.current_state = c_state;
		this.home_city = h_city;
		this.home_country = h_country;
		this.home_state = h_state;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.dob = new java.sql.Date(sdf.parse(bday + "/" + bmonth +  "/" + byear).getTime());
		} catch (ParseException e) {
			this.dob = null;
		}
	}

	// Used when we're grabbing Users from the database
	public User(int user_id, String firstName, String lastName, String email, 
				String password, boolean male, java.sql.Timestamp date,
				String c_city, String c_state, String c_country, String h_city, String h_state, 
				String h_country) {
		this.id = user_id;
		this.first_name = firstName;
		this.last_name = lastName;
		this.email = email;
		this.password = password;
		this.male = male;
		this.current_city = c_city;
		this.current_country = c_country;
		this.current_state = c_state;
		this.home_city = h_city;
		this.home_country = h_country;
		this.home_state = h_state;
		this.dob = new java.sql.Date(date.getTime());
	}

	public Date getDOB() { return this.dob; }
}