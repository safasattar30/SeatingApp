package Java;

import org.joda.time.DateTime;

public class Attendance {
	private String student;
	private DateTime appointmentTime;
	private int seatId;
	boolean attended;
	
	public Attendance(String student, DateTime appointmentTime, int seatId, boolean attended) {
		super();
		this.student = student;
		this.appointmentTime = appointmentTime;
		this.seatId = seatId;
		this.attended = attended;
	}

	public String getStudent() {
		return student;
	}

	public void setStudent(String student) {
		this.student = student;
	}

	public DateTime getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(DateTime appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public int getSeatId() {
		return seatId;
	}

	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}

	public boolean isAttended() {
		return attended;
	}

	public void setAttended(boolean attended) {
		this.attended = attended;
	}
	
	
}
