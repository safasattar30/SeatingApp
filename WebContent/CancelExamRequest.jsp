<%@page import="DBWorks.DBConnection"%>
<%@page import="Java.*" %>
<%@ page import="java.util.*" %>

<%
	String examId = request.getParameter("examId");
	
	String email = session.getAttribute("email").toString();
	String id = session.getAttribute("id").toString();
	String name = session.getAttribute("name").toString();
	
	Instructor instr = new Instructor(name, email, id);
	System.out.print(instr.getInstructorId());
	System.out.print(id);
	System.out.print(name);
	System.out.print(email);
	
	instr.cancelExam(examId);
	
	response.sendRedirect("SeeExamRequests.jsp");
%>