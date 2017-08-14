package Java;
import java.util.Date;
import org.joda.time.DateTime;

/**
 * Super class for all exams. Contains basic exam information.
 */

/**
 * 
 * @author WdNnD
 *
 */
public class Exam {

	protected String examID;
	protected DateTime start;
	protected DateTime end;
	protected String status;
	protected String instructorId;
	protected String courseId;
	protected int numSeats;
	protected int duration;
	protected boolean adHocExam;
	
	public Exam(String examID, DateTime start, DateTime end, String instructorId, String courseId, int seats, int duration, boolean adHocExam) {
		this(examID, start, end, "P", instructorId, courseId, seats, duration, adHocExam);
	}
	
	public Exam(String examID, DateTime start, DateTime end, String status, String instructorId, String courseId, int seats, int duration, boolean adHocExam) {
		super();
		this.examID = examID;
		this.start = start;
		this.end = end;
		this.status = status;
		this.instructorId = instructorId;
		this.courseId = courseId;
		this.numSeats = seats;
		this.duration = duration;
		this.adHocExam = adHocExam;
	}

	public DateTime getStart() {
		return start;
	}
	
	public String getStatus(){
		return status;
	}
	
	public DateTime getEnd() {
		return end;
	}

	public String getExamID() {
		return examID;
	}
	
	public String getInstructorId() {
		return instructorId;
	}
	
	public boolean isSchedulable() {
		TestingCenter tc = TestingCenter.getTestingCenter();
		return tc.isExamSchedulable(this);
	}

	public int getNumSeats() {
		// TODO Auto-generated method stub
		return numSeats;
	}
	
	public String toString() {
		String s = String.format("{"
				+ "examId:%s, "
				+ "startTime:%s, "
				+ "endTime:%s, "
				+ "status:%s, "
				+ "instructorId:%s, "
				+ "courseId:%s"
				+ "numSeats:%d, "
				//+ "duration:%d minutes, "
				+ "adHocExam:%b}",
				examID,
				start.toString(),
				end.toString(),
				status,
				instructorId,
				courseId,
				numSeats,
				adHocExam);
		
		return s;
		
	}

	public int getLength() {
		return duration;
	}

	public boolean isAdHocExam() {
		return adHocExam;
	}

	public String getCourseId() {
		return courseId;
	}

}
