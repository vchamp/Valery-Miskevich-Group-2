<!DOCTYPE html>
<html>
<head>
<!-- JQuery CSS -->
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.14/themes/redmond/jquery-ui.css" type="text/css" />
<!-- jqGrid CSS -->
<link rel="stylesheet" href="resources/css/ui.jqgrid.css" type="text/css" />
<!-- The actual JQuery code -->
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js" ></script>
<!-- The JQuery UI code -->
<script type="text/javascript" src="http://code.jquery.com/ui/1.10.3/jquery-ui.min.js" ></script>
<!-- The jqGrid language file code-->
<script type="text/javascript" src="resources/js/i18n/grid.locale-en.js" ></script>
<!-- The actual jqGrid code -->
<script type="text/javascript" src="resources/js/jquery.jqGrid.src.js" ></script>
<title>Students page</title>
	<script>
		$(document).ready(function() {
			$("#theGrid").jqGrid({ 
	  			url: "students", 
	  		    editurl:"students",
				datatype: "json",
			  	mtype: "GET", 
			  	colNames: ['id', 'email', 'login', 'password', 'firstName', 'lastName', 'address', 'phone', 'isActive', 'role'], 
			  	colModel: [ 
	            	{name: 'id', index: 'id', editable: false}, 
			        {name: 'email', index: 'email', editable: true},
			        {name: 'login', index: 'login', editable: true},
			        {name: 'password', index: 'password', hidden: false, editable: false},
			        {name: 'firstName', index: 'firstName', editable: true},
			        {name: 'lastName', index: 'lastName', editable: true},
			        {name: 'address', index: 'address', editable: true},
			        {name: 'phone', index: 'phone', editable: true},
			        {name: 'isActive', index: 'isActive', editable: true},
			        {name: 'role', index: 'role', editable: false}
				],
				rowNum:10,
			   	rowList:[10,20,30],
			   	pager: '#pager',
			  	sortname: 'id', 
			  	viewrecords: true, 
			  	multiselect: true,
	            multiboxonly: true,
			  	sortorder: 'desc',
			  	caption: 'Students'
	  		});
			
			$("#theGrid").jqGrid('navGrid','#pager',{edit: true, add: true, del: true}, 
					{url: 'students/update'}, 
					{url: 'students/create',}, 
					{url: 'students/delete'});
		});
	</script>	
</head>
<body>
	<table id="theGrid"></table>
	<div id="pager"></div>
</body>
</html>