package Java;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAppointments {
	
	private static final Logger logger = Logger.getLogger(TestAppointments.class.getName());
	
	private static Database db;
	private static TestingCenter tc;
	
	private static Instructor inst;
	private static Student st;

	@BeforeClass
	public static void beforeClass() {
		logger.info("Preparing dummy database for testing.");		
		logger.info("Testing consecutive statements.");
		
		db = Database.getDatabase();
		tc = TestingCenter.getTestingCenter();
		
		inst = new Instructor("Stoller", "stoller@cs.stonybrook.edu", "sstoller");
		st = new Student("Dan", "Harel", "dharel", "dan.harel@stonybrook.edu", "dharel");
		
		db.updateQuery("CREATE DATABASE Test");
		db.updateQuery("USE Test");
//
		db.updateQuery("CREATE TABLE student (firstName varchar(45), lastName varchar(45), studentId varchar(45), email varchar(45))");
		db.updateQuery("CREATE TABLE appointment (examIdA varchar(45), studentIdA varchar(45), dateId bigint(20), seatId int(11), appointmentId int(11), startTime bigint(20), endTime bigint(20), checkedIn tinyint(1))");
		db.updateQuery("CREATE TABLE exam (examId varchar(45), start bigint(20), end bigint(20), boolCourseExam varchar(45), examStatus varchar(45), instructorIdA varchar(45), numSeats int, examLength int, courseId varchar(45))");
		db.updateQuery("CREATE TABLE instructor (instructorId varchar(45), name varchar(45), email varchar(45))");
//		db.updateQuery("CREATE TABLE courseexam (examIdCE varchar(45), courseIdCE varchar(45))");
		db.updateQuery("CREATE TABLE coursestudent (courseIdCS varchar(45), studentIdCS varchar(45))");
		db.updateQuery("CREATE TABLE course (courseId varchar(45), subject varchar(45), catalogNumber int(11), section varchar(45), instructorIdB varchar(45), termId int(11), courseTerm varchar(45))");
		db.updateQuery("CREATE TABLE timeslots (dateId bigint(11), seatId int(11), studentIdT varchar(45), examIdT varchar(45))");
		
		db.updateQuery("INSERT INTO student VALUES ('Dan', 'Harel', 'dharel', 'dan.harel@stonybrook.edu')");
		db.updateQuery("INSERT INTO instructor VALUES ('SStoller', 'Scott Stoller', 'stoller@cs.stonybrook.edu')");
		db.updateQuery("INSERT INTO course VALUES ('81468', 'CSE', 308, '01', 'SStoller', 1158, '81468-1158')");
		db.updateQuery("INSERT INTO course VALUES ('80450', 'CSE', 373, '01', 'SSkiena', 1158, '80450-1158')");
		// Sat, 01 Jan 2000 08:00:00 GMT -> Mon, 03 Jan 2000 16:00:00 GMT
		db.updateQuery("INSERT INTO exam VALUES ('exam1', 946713600, 946915200, '1', 'A', 'SStoller', 64, 60, '81468-1158')");
		// Wed, 10 May 2000 10:00:00 GMT -> Thu, 11 May 2000 12:00:00 GMT
		db.updateQuery("INSERT INTO exam VALUES ('exam2', 957952800, 958046400, '1', 'A', 'SStoller', 64, 60, '81468-1158')");
		// Thu, 01 Jan 1970 00:00:00 GMT -> Thu, 01 Jan 1970 00:00:00 GMT
		db.updateQuery("INSERT INTO exam VALUES ('exam3', 0, 0, '1', 'A', 'SSkiena', 64, 60, '80450-1158')");
//		db.updateQuery("INSERT INTO courseexam VALUES ('exam1', '81468-1158')");
//		db.updateQuery("INSERT INTO courseexam VALUES ('exam2', '81468-1158')");
//		db.updateQuery("INSERT INTO courseexam VALUES ('exam3', '80450-1158')");
		db.updateQuery("INSERT INTO coursestudent VALUES ('81468-1158', 'dharel')");
		db.updateQuery("INSERT INTO coursestudent VALUES ('80450-1158', 'dharel')");

	}
	
	@Test
	//check to see that given the necessary parameters, an exam is made within the database
	public void AAtestStudentCreateAppointment() {
		logger.info("Testing Student's ability to create an appointment.");
		
		Exam exam = new Exam("exam1", new DateTime(2003,2,3,10,1), new DateTime(2004,2,3,10,1), "SStoller", "81468-1158", 64, 60, true);
		
		List<Appointment> appts;
		
		appts = st.viewAppointments(1158);
		int startSize = appts.size();
		
		// Sat, 01 Jan 2000 10:01:00 GMT -> Sat, 01 Jan 2000 11:01:00 GMT
		boolean examMade = st.makeAppointment("exam1", new DateTime(2000,1,1,10,1), new DateTime(2000,1,1,10,1), new DateTime(2000, 1,1,11,1), 60);
		
		assertTrue(examMade);
		
		appts = st.viewAppointments(1158);
		int endSize = appts.size();
		
		assertEquals(1, endSize - startSize);
	}
	
	@Test
	//test to see that an existing appointment cannot be made again
	public void AtestExistingAppointment() {
		logger.info("Attempted to make appointment for same exam. Should fail due to existing appointment.");
		Exam exam = new Exam("exam1", null, null, "SStoller", "81468-1158", 64, 60, true);
		boolean apptMade = st.makeAppointment("exam1", new DateTime(2000,1,1,10,1), new DateTime(2000,1,2,10,1), new DateTime(2000, 1,2,11,1), 60);
		assertFalse(apptMade);
	}
	
	@Test
	//test to see that a student cannot make an overlapping appointment
	public void AtestConflictingAppointment() {
		logger.info("Attempted to make appointment at same time for different exam. Should fail due to conflicting appointment.");
		Exam exam = new Exam("exam1-copy", null, null, "SStoller", "81468-1158", 64, 60, true);
		boolean apptMade = st.makeAppointment("exam1-copy", new DateTime(2000,1,1,10,1), new DateTime(2000,1,1,10,1), new DateTime(2000, 1,1,11,1), 60);
		
		assertFalse(apptMade);
	}
	
	@Test
	// test to see that a student cannot make an appointment out of the date range for the exam
	public void AtestAppointmentOutOfExamBounds() {
		logger.info("Attempted to make appointment past the date range for this exam. Should fail due to surpassing the established date range.");
		// Exam exam = new Exam("exam1-copy", new DateTime(2005,1,1,10,1), new DateTime(2000,2,1,10,1), "SStoller", "81468-1158", 64, 60, true);
		boolean apptMade = st.makeAppointment("exam2", new DateTime(2000,5,1,0,0), new DateTime(2000,5,1,0,0), new DateTime(2000, 5,11,0,0), 60);
		//assertTrue(apptMade);
		assertFalse(apptMade);
		
		apptMade = st.makeAppointment("exam2", new DateTime(2000,5,11,0,0), new DateTime(2000,5,11,0,0), new DateTime(2000, 5,20,0,0), 60);
		assertFalse(apptMade);
		
		apptMade = st.makeAppointment("exam2", new DateTime(2000,5,11,0,0), new DateTime(2000,5,11,0,0), new DateTime(2000, 5,11,5,0), 60);
		assertTrue(apptMade);
		//assertFalse(apptMade);
	}
	
	@Test
	//get the number of appointments for today
	public void AtestAppointmentPerDay() {
		logger.info("displaying the list of appointments");
		Map<LocalDate, Integer> apptsPerDay = tc.appointmentsPerDay(1158);
		
		for (LocalDate date : apptsPerDay.keySet()) {
			//System.out.println(date);
			assertTrue(date.equals(new LocalDate(2000, 1, 1)));
			assertEquals((int) apptsPerDay.get(date), 1);
		}
	}
	
	@Test
	public void AtestAppointmentPerWeek() {
		Map<LocalDate, Integer> apptsPerWeek = tc.appointmentsPerWeek(1158);
		
		for (LocalDate date : apptsPerWeek.keySet()) {
			assertEquals(date, new LocalDate(1999, 12, 27));
			assertEquals((int) apptsPerWeek.get(date), 1);
		}
	}
	
	@Test
	public void AtestCoursesPerWeek() {
		Map<LocalDate, Set<String>> apptsPerWeek = tc.coursesPerWeek(1158);
		
		for (LocalDate date : apptsPerWeek.keySet()) {
			assertEquals(date, new LocalDate(1999, 12, 27));
			Set<String> courses = (Set<String>) apptsPerWeek.get(date);
			for (String course : courses) {
				assertEquals(course, "81468-1158");
			}
		}
	}
	
	@Test
	public void testCoursesUsed() {
		List<Course> courses = tc.coursesUsed(1158);
		
		for (Course course : courses) {
			assertTrue( course.getCourseTerm().equals("81468-1158") || course.getCourseTerm().equals("80450-1158"));
		}
	}
	
	@Test
	public void testAppointmentsPerTerm() {
		Map<Integer, Integer> apptsPerTerm = tc.appointmentsPerTerm(1150, 1160);
		
		for (Entry<Integer, Integer> entry : apptsPerTerm.entrySet()) {
			if (entry.getKey() == 1158) {
				assertEquals((int) entry.getValue(), 1);
			}
			else {
				assertEquals((int) entry.getValue(), 0);
			}
		}
	}
	
/*
	public void frontAtestStudentCreateAppointment(String examId, String studentIdA, int month, int day, int hour, int seatIdA, int appointmentId, String instructorId ) {
		Exam exam = new Exam(examId, null, null, instructorId, "80450-1158", 64, 60, true);
		
		List<Appointment> appts;
		
		appts = st.viewAppointments(1158);
	//	int startSize = appts.size();
		
		DateTime time = new DateTime(2000,month,day,hour,1);
		
		st.makeAppointment(exam, time, seatIdA, appointmentId);
		
	//	appts = st.viewAppointments();
	//	int endSize = appts.size();
		
		//assertEquals(1, endSize - startSize);
	}
	*/
	
	public void frontAtestStudentViewAppointments(){
		st.viewAppointments(1158);
	}
	
	/*
	@Test
	public void BtestStudentDeleteAppointment() {
		logger.info("Testing Student's ability to delete an appointment.");
		
		List<Appointment> appts;
		
		appts = st.viewAppointments(1158);
		int startSize = appts.size();
		
		boolean examCancelled = st.cancelAppointment(1);
		
		assertTrue(examCancelled);
		
		appts = st.viewAppointments(1158);
		int endSize = appts.size();
		
		assertEquals(-1, endSize - startSize);
	}
	*/
	
	@Test
	public void CtestInstructorCreateExam() {
		logger.info("Testing Instructor's ability to create an exam scheduling request.");
		
		//Instructor inst = new Instructor("Scott Stollerd", "stollerd@cs.stonybrook.edu", tc, "SStollerd");
		List<Exam> exams;
		
		exams = inst.viewExams();
		int startNumExams = exams.size();
		
		Exam exam = new Exam("CSE", null, null, null, "sstollerd", "P", 64, 120, true);
		
		boolean examCreated = inst.makeExam("CSE", new DateTime(2000,1,1,1,1), new DateTime(2000,1,1,1,2), true, 64, 120, "CSE308");
		
		assertTrue(examCreated);
		
		exams = inst.viewExams();
		int endNumExams = exams.size();
		
		assertEquals(1, endNumExams - startNumExams);
		//assertEquals("CSE", exams.get(0).getExamID());
	}
	
	/*
	@Test
	public void DtestViewPendingExams() {
		logger.info("Testing Instructor's ability to view exam scheduling request.");
		
		Administrator admin = new Administrator(null, null, null);
		
		List<Exam> exams = admin.viewPendingExams();
		assertEquals(1, exams.size());
	}
	*/
	
//	@Test
//	public void EtestAcceptExam() {
//		logger.info("Testing Admin's ability to accept an exam scheduling request.");
//		
//		Administrator admin = new Administrator(null, null, null);
//		List<Exam> exams;
//		
//		exams = admin.viewPendingExams();
//		int startNumExams = exams.size();
//		
//		admin.approveDenyExam("CSE", "A");
//		
//		exams = admin.viewPendingExams();
//		int endNumExams = exams.size();
//		
//		assertEquals(-1, endNumExams - startNumExams);
//	}
	
	@Test
	public void FtestInstructorCancelExam() {
		logger.info("Testing Instructor's ability to reject an exam scheduling request.");
		
		List<Exam> exams;
		
		exams = inst.viewExams();
		int startNumExams = exams.size();
		
		inst.cancelExam("CSE");
		
		exams = inst.viewExams();
		int endNumExams = exams.size();
		
		assertEquals(-1, endNumExams - startNumExams);
	}
	
	@Test
	public void GtestStudentAvailableExams() {
		logger.info("Testing Student's ability to get exams they can make an appointment for.");
		
		List<Exam> exams = st.viewExams();
		
		assertEquals(3, exams.size());
	}
	
	@Test
	public void testNotifierThread(){
		logger.info("Testing the existance of one thread for the notifier");
	}
	
	@Test
	public void testCheckIn() {
		logger.info("Testing ability to check student in.");
		assertFalse(tc.checkIn("dharel")==-1);
	}
	
//	@Test
//	public void testGetUpcoming(){
//		Student student = new Student("Safa Sattar", "ssattar", "safa.sattar@stonybrook.edu", null);
//		Exam exam = new Exam("CSE", null, null, "sstoller", 64);
//		
//		student.makeAppointment(exam, new DateTime(2000,1,1,1,1), 1, 1);
//		
//		tc.getUpcoming();
//		assertEquals();
//	}
	
	@Test
	public void testSetExamStatus(){
		logger.info("Testing ability to set exam status");
		List<Map<String, Object>> status = db.query(String.format("SELECT examStatus FROM exam "
				+ "WHERE examId = 'test1'"));
		logger.info(status.get(0).get("examStatus").toString());
		tc.setExamStatus("test1","A");
		status = db.query(String.format("SELECT examStatus FROM exam "
				+ "WHERE examId = 'test1'"));
		logger.info(status.get(0).get("examStatus").toString());
		assertTrue(status.get(0).get("examStatus").toString().equals("A"));
	}
	
	@Test
	public void testEditAppointment(){
		logger.info("Testing ability to edit an existing appointment");
		//
	}
	
	@Test
	public void testEditExam(){
		logger.info("Testing ability to edit an existing appointment");
		//
	}
	
	@AfterClass
	public static void afterClass() {
		logger.info("Testing complete.");
		logger.info("Tearing down dummy database.");
		
		db.updateQuery("DROP DATABASE Test");
	}

}
