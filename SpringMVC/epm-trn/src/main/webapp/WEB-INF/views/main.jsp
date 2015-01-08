<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>EPM-TRN</title>
<link rel="stylesheet" type="text/css" media="screen" href="resources/redmondcss/ui.jqgrid.css" />
<link href="resources/redmond/css/jquery-ui-1.10.3.custom.css" rel="stylesheet">

<script src="resources/js/jquery-2.0.3.min.js" type="text/javascript"></script>
<script src="resources/js/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
<script src="resources/js/i18n/grid.locale-ru.js" type="text/javascript"></script>
<script src="resources/js/jquery.jqGrid.min.js" type="text/javascript"></script>

<script>

$(function() {
    $("#tabs" ).tabs({
      beforeLoad: function( event, ui ) {
        ui.jqXHR.error(function() {
          ui.panel.html(
            "Couldn't load this tab." );
        });
      }
    });
  });
  
  </script>


</head>
<body>
	
	<div>
		<a href="<c:url value="/logout" />"> <spring:message code="label.logout" />
		</a>
	</div>
	
	
<div id="tabs">
  <ul>
    <li><a href="admin">Admin Users page</a></li>
    <li><a href="coursespage">Courses</a></li>
    <li><a href="studentpage">Students</a></li>
    <li><a href="tutorpage">Tutors</a></li>
    <li><a href="ajax/content4-broken.html">Timetable</a></li>
  </ul>
  <div id="tabs-1">
    <p></p>
  </div>
</div>
	
	
</body>
</html>