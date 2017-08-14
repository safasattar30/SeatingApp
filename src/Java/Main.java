package Java;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.Period;



/**
 * This is the master class that creates the testingCenter and the DB class. 
 * For the time being it is also serving as the base of our text interface in the absence of a GUI.
 */

/**
 * @author WdNnD
 *
 */
public class Main {
	
	private static final Logger log = Logger.getLogger(Main.class.getName());
	
	
	public static void main(String[] args) throws SecurityException, IOException
	{
//		long time=1448998200;
////		DateTime start = new DateTime(time*1000);
//		
//		DateTime start = new DateTime(((long) time)*1000);
//		System.out.println(start);
//		log.info("Logger Name: "+log.getName());
//		log.warning("Can cause ArrayIndexOutOfBoundsException");
//		//An array of size 3
//		int []a = {1,2,3};
//		int index = 4;
//		
//		log.config("index is set to "+index);
//		
//		try
//		{
//			System.out.println(a[index]);
//		}catch(ArrayIndexOutOfBoundsException ex)
//		{
//			log.log(Level.SEVERE, "Exception occur", ex);
//		}

		Database.createDatabase();

		
		/*
		 * Test that the database actually works
		 */

		Database db = Database.getDatabase();
		List<Map<String,Object>> results = db.query("SHOW DATABASES");
		for (Map<String, Object> map : results) {
			for (Object name : map.values()) {
				System.out.println(name);
			}
		}
		

		TestingCenter tC = new TestingCenter(
				64,
				0,
				new LocalTime(8,0),
				new LocalTime(8,0),
				new Period(1,0,0,0),
				new Period(1,0,0,0)
				);

		boolean running = true;
		while(running) {
			System.out.println("Pick a user type:");
			System.out.println("1) Admin 2) Instructor 3) Student 4) close server");
			Scanner s = new Scanner(System.in);
			int option = s.nextInt();
			Instructor inst = new Instructor("Scott Stoller", "stoller@cs.stonybrook.edu", tC, "sstoller");
			if(option == 1) {
				Administrator ad = new Administrator("admin","admin@help.edu");
				System.out.println("Pick a user type:");
				System.out.println("1) import 2) checkin");
				s = new Scanner(System.in);
				option = s.nextInt();
				if(option == 1) {
				ad.importData("user.csv","class.csv","roster.csv");
				} else if (option == 2) {
					System.out.println(ad.checkInStudent("a"));
				} else if (option == 3) {
					List<Exam> exams = ad.viewExams();
					System.out.println(exams.toString());
				}
				
			} else if (option == 2) {
				/*
				Exam exam = new Exam("CSE", null, null, inst.getInstructorId(), 0,2);
				
				inst.makeExam(exam, new DateTime(2000,1,1,1,1), new DateTime(2000,1,1,1,2), true);
				*/
			} else if (option == 3) {
				/*
				Student student = new Student("Anvika .", "a", "a@example.com", null);
				Exam exam = new Exam("CSE", null, null, inst.getInstructorId(), 0,2);

				
				student.makeAppointment(exam, new DateTime(2015,10,25,19,0), 7, 1);
				*/
			} else if (option == 4) {
				running = false;
			}

		}

	}


}
