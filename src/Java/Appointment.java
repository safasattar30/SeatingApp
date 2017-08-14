package Java;
import java.util.Date;
import org.joda.time.DateTime;


public class Appointment 
{
/**
 * This class contains all of the information in an appointment for a student to take an exam.
 */

/**
 * @autor WdNnD
 * 
 */

	private String examID;
	private String StudentNetID;
	private DateTime startTime;
	private DateTime endTime;
	private int appointmentId;
	private int seatNumber;
	private boolean checkedIn;
	
	public Appointment() {
		// TODO Auto-generated constructor stub
	}

	public Appointment(String examID, String studentNetID, DateTime start, DateTime end, int appointmentId, int seatNumber, boolean checkedIn) {
		this.examID = examID;
		this.StudentNetID = studentNetID;
		this.startTime = start;
		this.endTime = end;
		this.appointmentId = appointmentId;
		this.seatNumber = seatNumber;
		this.checkedIn = checkedIn;
	}
	
	public String getNetId() {
		return StudentNetID;
	}
	
	//jsp helper methods
	public String getViewForAllAppointments()
	{
		return examID+" "+startTime;
	}
	
	public int getAppointmentId() {
		return appointmentId;
	}
	
	public int getSeatNumber() {
		return seatNumber;
	}
	
	public DateTime getStartTime(){
		return startTime;
	}
	
	public boolean checkedIn() {
		return checkedIn;
	}
	
	public boolean cancelAppointment() {
		return TestingCenter.getTestingCenter().cancelAppointment(appointmentId);
	}
	
	public boolean canBeCancelled() {
		return TestingCenter.getTestingCenter().canAppointmentBeCancelled(appointmentId);
	}
}