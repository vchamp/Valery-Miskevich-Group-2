<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Courses</title>
<link rel="stylesheet" type="text/css" media="screen" href="resources/redmondcss/ui.jqgrid.css" />
<link href="resources/redmond/css/jquery-ui-1.10.3.custom.css" rel="stylesheet">

<script src="resources/js/jquery-2.0.3.min.js" type="text/javascript"></script>
<script src="resources/js/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
<script src="resources/js/i18n/grid.locale-ru.js" type="text/javascript"></script>
<script src="resources/js/jquery.jqGrid.min.js" type="text/javascript"></script>

<script>

	$(function() {
		$("#accordion").accordion();
		$("#startdate").datepicker();
		$("#enddate").datepicker();
		
		$("#theGrid").jqGrid({ 
  			url: "courses", 
  		    editurl:"courses",
			datatype: "json",
		  	mtype: "GET", 
		  	colNames: ['Course Name', 'Start Date', 'End Date', 'Student', 'Tutor'], 
		  	colModel: [ 
            	{name: 'Course Name', index: 'courseName', editable: false}, 
		        {name: 'Start Date', index: 'startdate', editable: true},
		        {name: 'End Date', index: 'enddate', editable: true},
		        {name: 'Student', index: 'student', editable: true},
		        {name: 'Tutor', index: 'tutor', editable: true}
		        
			],
			rowNum:10,
		   	rowList:[10,20,30],
		   	pager: '#pager',
		  	sortname: 'courseName', 
		  	viewrecords: true, 
		  	multiselect: true,
            multiboxonly: true,
		  	sortorder: 'desc', 
		  	caption: 'Courses'
  		});
		
		
		
		
		
		
	});
  
  </script>
 

</head>
<body>

	<div id="accordion">
		<h2>Create new course</h2>
		
		
		<div>
				<p><input type="text" id="courseName">&nbsp;Course Name</p>
				<p><input type="text" id="startdate" />&nbsp;Start Date</p>
				<p><input type="text" id="enddate" />&nbsp;End Date</p>
				<p><select>
					<option>Student 1</option>
					<option>Student 2</option>
				</select>&nbsp;Student
				</p>
				<p>
				<select>
					<option>Tutor 1</option>
					<option>Tutor 2</option>
				</select>&nbsp;Tutor
				</p>
		</div>
		<h2>View all courses</h2>
		<div>
			<table id="theGrid"></table>
			<div id="pager"></div>
		</div>
	</div>
</body>
</html>