package Java;

//this exception is thrown when an appointment attempts to overlap another appointment
public class ConflictingAppointmentException extends RuntimeException {

	public ConflictingAppointmentException() {
		// TODO Auto-generated constructor stub
	}

	public ConflictingAppointmentException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ConflictingAppointmentException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ConflictingAppointmentException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ConflictingAppointmentException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
