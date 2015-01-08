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
<title>Admin Users page</title>
	<script>
		$(document).ready(function() {
			$("#adminGrid").jqGrid({ 
	  			url: "users", 
	  		    editurl:"users",
				datatype: "json",
			  	mtype: "GET", 
			  	colNames: ['id', 'email', 'login', 'firstName', 'lastName', 'address', 'phone', 'isActive', 'role'], 
			  	colModel: [ 
	            	{name: 'id', index: 'id', width: 25, editable: false}, 
			        {name: 'email', index: 'email', editable: true},
			        {name: 'login', index: 'login', editable: true},
			        {name: 'firstName', index: 'firstName', editable: true},
			        {name: 'lastName', index: 'lastName', editable: true},
			        {name: 'address', index: 'address', editable: true},
			        {name: 'phone', index: 'phone', editable: true},
			        {name: 'isActive', index: 'isActive', width: 50, align: 'center', editable: true, edittype: 'checkbox', formatter: 'checkbox'},
			        {name: 'role', index: 'role', editable: true, edittype: 'select', editoptions: {dataUrl: 'users/roles', multiple: true}}
				],
				ajaxSelectOptions: {},
				rowNum:10,
			   	rowList:[10,20,30],
			   	pager: '#adminPager',
			  	sortname: 'id', 
			  	viewrecords: true, 
			  	multiselect: true,
	            multiboxonly: true,
	            cellEdit: true,
	            cellsubmit: 'remote',
	        	cellurl: 'users/update',
	        	loadonce: false,
			  	sortorder: 'asc', 
			  	caption: 'Users'
	  		});
			
			$("#adminGrid").jqGrid('navGrid','#adminPager',{edit: false, add: true, del: true}, {url: 'users/update'}, {url: 'users/create'}, {url: 'users/delete'});
		});
	</script>	
</head>
<body>
	<table id="adminGrid"></table>
	<div id="adminPager"></div>
</body>
</html>