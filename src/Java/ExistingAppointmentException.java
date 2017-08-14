package Java;

//this exception is thrown if this appointment already exists in the database

public class ExistingAppointmentException extends RuntimeException {

	public ExistingAppointmentException(String string) {
		super(string);
	}

}
