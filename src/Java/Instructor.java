package Java;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 * This class represents and Instructor user and provides functionality for the instructor to interact
 * with the TestingCenter.  In instance of this class will be created on log in with information from
 * the database.
 */

/**
 * @author WdNnD
 *
 */
public class Instructor {

	private String name;
	private String email;
	private String instructorId;
	
	private final TestingCenter tC = TestingCenter.getTestingCenter();
	
	/**
	 * 
	 */
	
	public Instructor(String name, String email, String instructorId) {
		this.name = name;
		this.email = email;
		this.instructorId = instructorId;
	}
	//
	public Instructor(String name, String email,TestingCenter tc, String instructorId) {
		this.name = name;
		this.email = email;
		this.instructorId = instructorId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(String instructorId) {
		this.instructorId = instructorId;
	}

	/*
	 * The instructor receives a list of all exams associated with his ID.
	 * (NOTE: The internal functionality may be moved later and called by this function.)
	 */
	public List<Exam> viewExams() {
		return tC.getInstructorExams(this.instructorId);
		
	}
	
	/*
	 * The instructor can make an exam reservation in the database. A query with the relevent information
	 * is created and sent.
	 */
	public boolean makeExam(String examId, DateTime start, DateTime end, boolean courseExam, int numSeats, int duration, String courseId) {
		return tC.makeReservation(examId, start, end, courseExam, this.instructorId, numSeats, duration, courseId);
	}
	
	public void viewAvailability() {
		
	}
	
	/*
	 * The instructor can cancle any exam reservation he has made.
	 */
	public void cancelExam(String examId) {
		tC.cancelExam(examId, this.instructorId);
	}
	
	public List<Attendance> viewAttendanceStats(String examId) {
		return tC.viewAttendanceStats(examId);
	}
	
	/**
	 * Creates an Ad Hoc Exam
	 * @param termId	Term in which the exam will take place
	 * @param examName	Name of the exam. WIll also be the examId
	 * @param start		Time at which the exam starts
	 * @param end		Time at which the exam ends
	 * @param duration	Duration of the exam in minutes
	 * @param students	List of netIds of the students to take the course 
	 * @return
	 */
	public boolean makeAdHocExam(int termId, String examName, DateTime start, DateTime end, int duration, List<String> students) {
		String queryString = String.format("INSERT INTO course "
				+ "(courseTerm, termId, instructorIdB) "
				+ "VALUES ('%s', %d, '%s')",
				examName,
				termId,
				instructorId);
		Database.getDatabase().updateQuery(queryString);
		
		//Exam exam = new Exam(examName, start, end, instructorId, examName, students.size(), duration, true);
		if (makeExam(examName, start, end, false, students.size(), duration, examName)) {
			enrollStudents(examName, students);
			return true;	
		}
		else {
			return false;
		}
	}
	
	private void enrollStudents(String courseId, List<String> students) {
		for (String netId : students) {
			String queryString = String.format("INSERT INTO coursestudent "
					+ "(courseIdCS, studentIdCS) "
					+ "VALUES ('%s', '%s')",
					courseId, netId);
			Database.getDatabase().updateQuery(queryString);
		}
	}
	
	public static void main(String[] args) {
		Instructor inst = new Instructor("Meredith Roberts", "Meredith.Roberts@example.com", "MRoberts");
		Instructor inst2 = new Instructor("Lila Little", "Lila.Little@example.com", "LLittle");
		
		List<String> netIds = new ArrayList<String>();
		netIds.add("a");
		netIds.add("abak");
		
		//inst.makeAdHocExam(1158, "test-adhoc-exam", new DateTime(2015, 12, 15, 0, 0), new DateTime(2015, 12, 20, 0, 0), 60, netIds);
		
		List<Exam> exams = inst.viewExams();
		for (Exam exam : exams) {
			System.out.println(exam);	
		}
		
		
		inst.makeExam("test-exam-1", new DateTime(2015, 1, 1, 0, 0), new DateTime(2015, 1,2,0,0), true, 400, 60, "80450-1158");
		inst.makeExam("test-exam-2", new DateTime(2015, 12, 1, 8, 0), new DateTime(2015, 12, 3, 12, 0), true, 97, 85, "80450-1158");
		inst2.makeExam("test-exam-3", new DateTime(2015, 12, 7, 1, 0), new DateTime(2015, 12, 8, 3, 0), true, 46, 45, "85023-1158");
		
	}
	
	/**
	 * Current expected utilization of the TestingCenter for a range of dates
	 * @param start		Start of the utilization range
	 * @param end		End of the utilization range
	 * @return
	 */
	public Map<LocalDate, Double> expectedUtilizationPerDay(LocalDate start, LocalDate end) {
		return tC.expectedUtilizationPerDay(start, end);
	}
	
	/**
	 * Expected utilization of the TestingCenter for a range of dates if a
	 * 		particular exam were scheduled.
	 * @param start		Start of the utilization range
	 * @param end		End of the utilization request range
	 * @param duration	Duration of the exam being scheduled
	 * @param numSeats	Number of seats requested for the exam
	 * @return
	 */
	public Map<LocalDate, Double> expectedUtilizationPerDayWithExam(LocalDate start, LocalDate end, int duration, int numSeats) {
		return tC.expectedUtilizationPerDayWithExam(start, end, duration, numSeats);
	}
}
