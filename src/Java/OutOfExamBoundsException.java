package Java;
//this is thrown if the appointment being made is out of the constraints 
//of the exam time range

public class OutOfExamBoundsException extends RuntimeException {

	public OutOfExamBoundsException() {
		// TODO Auto-generated constructor stub
	}

	public OutOfExamBoundsException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public OutOfExamBoundsException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public OutOfExamBoundsException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public OutOfExamBoundsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
