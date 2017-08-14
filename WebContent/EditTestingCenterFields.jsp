<%@page import="DBWorks.DBConnection"%>
<%@page import="Java.*" %>
<%@ page import="java.util.*" %>

<%
	String examId = request.getParameter("examId");
	
	String email = session.getAttribute("email").toString();
	String id = session.getAttribute("id").toString();
	String name = session.getAttribute("name").toString();
	
	TestingCenter tc = TestingCenter.getTestingCenter();
	System.out.print(id);
	System.out.print(name);
	System.out.print(email);
	
	/* tc.setNumberofSeats(n) */
	
	response.sendRedirect("EditTestingCenter.jsp");
%>