package photoshare;

import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class User {

	private int id;
	private String first_name;
	private String last_name;
	private String email;
	private String password;
	private Date dob;
	private boolean male;
	private String current_city;
	private String current_state;
	private String current_country;
	private String home_city;
	private String home_state;
	private String home_country;

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
			this.dob = sdf.parse(bday + "/" + bmonth +  "/" + byear);
		} catch (ParseException e) {
			this.dob = null;
		}
	}

	public Date getDOB() { return this.dob; }
}