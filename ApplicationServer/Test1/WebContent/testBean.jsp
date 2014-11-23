<%@page import="com.epam.test.TestBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Test bean</title>
</head>
<body>
	<jsp:useBean id="bean" scope="session" class="com.epam.test.TestBean"/>
	<jsp:useBean id="userBean" scope="request" class="com.epam.test.UserBean"/>
	<%=bean.getString()%>
</body>
</html>