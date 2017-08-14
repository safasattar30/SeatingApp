package Java;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 * This class provides functionality for the student to interact with the TestingCenter.  An instance
 * of the class is created when the student logs in using information from the database.
 */

/**
 * @author Daniel
 *
 */
public class Student {

	/** Student's first name */
	private String firstName;
	/** Student's last name */
	private String lastName;
	/** Student's net ID */
	private String netID;
	/** Student's email address */
	private String email;
	/** Student's user IdB (?????) */
	private String userIdB;
	
	private final TestingCenter tC = TestingCenter.getTestingCenter();
	
	/**
	 * 
	 * @param firstName	Student's first name
	 * @param lastName	Student's last name
	 * @param netID		Student's net ID
	 * @param email		Student's email address
	 * @param userIdB	Literally any string, I guess
	 */
	public Student(String firstName, String lastName, String netID, String email, String userIdB) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.netID = netID;
		this.email = email;
		this.userIdB = userIdB;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNetID() {
		return netID;
	}

	public void setNetID(String netID) {
		this.netID = netID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserIdB() {
		return userIdB;
	}

	public void setUserIdB(String userIdB) {
		this.userIdB = userIdB;
	}

	/**
	 * This is used to create an exam appointment for a specific exam.
	 * @param examId	Exam ID of the corresponding exam
	 * @param time		Literally any DateTime
	 * @param start		Start time of the appointment
	 * @param end		End time of the appointment
	 * @param duration	Duration of the appointment
	 * @return
	 */
	public boolean makeAppointment(String examId, DateTime time, DateTime start, DateTime end, int duration) {
		return tC.makeAppointment(examId, time, netID, start, end);
	}
	
	/**
	 * Cancel an appointment with the given appointment ID
	 * @param string
	 * @return	True if the appointment was successfully cancelled, else false
	 */
	public boolean cancelAppointment(int string) {
		return tC.cancelAppointment(string);
	}
	
	/**
	 * When called, returns a list of appointments associated with the student
	 * 		from the given term.
	 * @param termId	The term ID of the term in question
	 * @return
	 */
	public List<Appointment> viewAppointments(int termId) {
		return tC.showAppointments(netID, termId);
	}
	
	public void checkAvailability() {
		
	}
	
	/**
	 * Displays reservations for all approved exams for courses this student is 
	 * 		enrolled in.
	 * @return
	 */
	public List<Exam> viewExams() {
		return tC.viewAvailableExams(this);

	}

}