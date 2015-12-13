<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<head>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Welcome</title>
<link type="text/css"
	href="<c:url value="/assets/bootstrap/css/bootstrap.min.css"/>"
	rel="stylesheet">
<link type="text/css"
	href="<c:url value="/assets/bootstrap/css/bootstrap-responsive.min.css"/>"
	rel="stylesheet">
<link type="text/css" href="<c:url value="/assets/css/theme.css"/>"
	rel="stylesheet">
<link type="text/css"
	href="<c:url value="/assets/images/icons/css/font-awesome.css"/>"
	rel="stylesheet">
<link type="text/css"
	href='http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,400,600'
	rel='stylesheet'>

</head>
<body>

	<div class="navbar navbar-fixed-top" style="height: 80px;">
		<div class="navbar-inner">
			<div class="container">
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<a class="btn btn-navbar" data-toggle="collapse"
						data-target=".navbar-inverse-collapse"> <i
						class="icon-reorder shaded"></i></a>
					<a class="brand" href="#">Owner -
						${sessionScope.USER.firstName} </a>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_USER')">
					<a class="btn btn-navbar" data-toggle="collapse"
						data-target=".navbar-inverse-collapse"> <i
						class="icon-reorder shaded"></i></a>
					<a class="brand" href="#">Team Member -
						${sessionScope.USER.firstName} </a>
				</sec:authorize>
				<!-- <div class="nav-collapse collapse navbar-inverse-collapse"> -->
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".navbar-inverse-collapse"> <i
					class="icon-reorder shaded"></i></a><a class="brand" href="#">
					<div style="width: 100%; margin-left: 120%; margin-top: -3%;">
						<center style="font-size: 37px;">PRO-MAN</center>
						<br>
						<center style="font-size: 16px;">A PROject MANagement
							Application</center>
					</div>
				</a>
				<!-- 	</div> -->
				<!-- /.nav-collapse -->
			</div>
		</div>
		<!-- /navbar-inner -->
	</div>
	<!-- /navbar -->
	<div class="wrapper">
		<div class="container">
			<div class="row">
				<div class="span3">
					<div class="sidebar">
						<ul class="widget widget-menu unstyled">
							<li class="active"><a href="#"><i
									class="menu-icon icon-dashboard"></i>Dashboard </a></li>
							<li><a href="#"><i class="menu-icon icon-bullhorn"></i>Calendar
							</a></li>
							<li><a href="#"><i class="menu-icon icon-inbox"></i>Statistics
							</a></li>
							<sec:authorize access="hasRole('ROLE_ADMIN')">
								<li><a href="<c:url value="/project/addProjectFormView"/>"><i
										class="menu-icon icon-tasks"></i>Add Project </a></li>
							</sec:authorize>

							<sec:authorize access="hasRole('ROLE_ADMIN')">
								<li><a
									href="<c:url value="/project/viewProjects/${sessionScope.USER.userId}/role_admin"/>">
										<i class="menu-icon icon-tasks"></i>View Projects
								</a></li>
							</sec:authorize>

							<sec:authorize access="hasRole('ROLE_USER')">
								<li><a
									href="<c:url value="/project/viewProjects/${sessionScope.USER.userId}/role_user"/>">
										<i class="menu-icon icon-tasks"></i>View Projects
								</a></li>
							</sec:authorize>

						</ul>
						<!--/.widget-nav-->



						<!--/.widget-nav-->
						<ul class="widget widget-menu unstyled">
							<li><a class="collapsed" data-toggle="collapse"
								href="#togglePages"><i class="menu-icon icon-cog"> </i><i
									class="icon-chevron-down pull-right"></i><i
									class="icon-chevron-up pull-right"> </i>More Pages </a>
								<ul id="togglePages" class="collapse unstyled">
									<li><a href="<c:url value="/project/getProfilePage"/>"><i
											class="icon-inbox"></i>Profile </a></li>
									<!-- <li><a href="#"><i class="icon-inbox"></i>All Users </a></li> -->
								</ul></li>
							<%-- <c:url value="/login?logout" var="logoutUrl" />
							<form action="${logoutUrl}" method="post" id="logoutForm">
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
							</form> --%>
							<li><a href="<c:url value="/logout" />">Logout </a></li>
						</ul>
					</div>
					<!--/.sidebar-->
				</div>
				<!--/.span3-->
				<div class="module-head"
					style="margin-left: 28.3%; margin-right: 2.3%; background-color: #2d2b32;">
					<b style="color: white; font-size: initial">Project:</b>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select>
						<c:forEach var="project" items="${projectList}">
							<option value=${project.pid}>${project.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="span9">
					<div class="content">
						<div class="btn-controls">
							<div class="btn-box-row row-fluid" style="margin-left: 1%;">
								<a href="#" class="btn-box big span4" style="width: 22%;"><b
									id="finished">${report.taskUnitsFinished}</b><br />
								<i class=" icon-random"></i><br>
									<p class="text-muted">Total number of task units finished</p> </a>

								<a href="#" class="btn-box big span4" style="width: 22%;"> <b
									id="toBefinished">${report.taskUnitsTobeFinished}</b></br/>
								<i class="icon-random"></i><br>
									<p class="text-muted">Total number of task units to be
										finished</p>
								</a> <a href="#" class="btn-box big span4" style="width: 22%;"><b
									id="planningPhase">${report.taskUnitsAtPlanningPhase}</b><br />
								<i class="icon-random"></i><br>
									<p class="text-muted">Total number of task units during the
										planning phase</p> </a> <a href="#" class="btn-box big span4"
									style="width: 22%;"><b id="cancelled">${report.taskUnitsCancelled}</b><br />
								<i class="icon-random"></i><br>
									<p class="text-muted">Total number of task units cancelled
										so far.</p> </a>
							</div>
							<div class="btn-box-row row-fluid">
								<!-- <div class="span8">
									<div class="row-fluid">
										<div class="span12">
											<a href="#" class="btn-box small span4"><i
												class="icon-envelope"></i><b>Messages</b> </a><a href="#"
												class="btn-box small span4"><i class="icon-group"></i><b>Clients</b>
											</a><a href="#" class="btn-box small span4"><i
												class="icon-exchange"></i><b>Expenses</b> </a>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span12">
											<a href="#" class="btn-box small span4"><i
												class="icon-save"></i><b>Total Sales</b> </a><a href="#"
												class="btn-box small span4"><i class="icon-bullhorn"></i><b>Social
													Feed</b> </a><a href="#" class="btn-box small span4"><i
												class="icon-sort-down"></i><b>Bounce Rate</b> </a>
										</div>
									</div>
								</div> -->
								<ul class="widget widget-usage unstyled span4"
									style="width: 100%">
									<li>
										<p>
											<strong>Task Units finished</strong> <span
												class="pull-right small muted">78%</span>
										</p>
										<div class="progress tight">
											<div class="bar" style="width: 78%;"></div>
										</div>
									</li>
									<li>
										<p>
											<strong>Task Units to be finished</strong> <span
												class="pull-right small muted">56%</span>
										</p>
										<div class="progress tight">
											<div class="bar bar-success" style="width: 56%;"></div>
										</div>
									</li>
									<li>
										<p>
											<strong>Task units during planning phase</strong> <span
												class="pull-right small muted">44%</span>
										</p>
										<div class="progress tight">
											<div class="bar bar-warning" style="width: 44%;"></div>
										</div>
									</li>
									<li>
										<p>
											<strong>Task units cancelled</strong> <span
												class="pull-right small muted">67%</span>
										</p>
										<div class="progress tight">
											<div class="bar bar-danger" style="width: 67%;"></div>
										</div>
									</li>
								</ul>
							</div>
						</div>
						<!--/#btn-controls-->
						<!-- <div class="module">
							<div class="module-head">
								<h3>Profit Chart</h3>
							</div>
							<div class="module-body">
								
								</div>
							</div> -->
						</div>
						<!--/.module-->
						<!-- <div class="module hide">
							<div class="module-head">
								<h3>Adjust Budget Range</h3>
							</div>
							<div class="module-body">
								<div class="form-inline clearfix">
									<a href="#" class="btn pull-right">Update</a> <label
										for="amount"> Price range:</label> &nbsp; <input type="text"
										id="amount" class="input-" />
								</div>
								<hr />
								<div class="slider-range"></div>
							</div>
						</div> -->
						<div class="module">
							<div class="module-head">
								<h3>Team Members Score board</h3>
							</div>
							<div class="module-body table">
								<table id="table" cellpadding="0" cellspacing="0" border="0"
									class="datatable-1 table table-bordered table-striped	 display"
									width="100%">
									<thead id="thead">
										<tr>
											<th>Team Member Name</th>
											<th>Task Name</th>
											<th>Estimated units</th>
											<th>Actual units</th>
											<th>Rating</th>
										</tr>
									</thead>
									<tbody id="tbody">
									<!-- <div id="table"></div> -->
										<c:forEach items="${taskList}" var="task" varStatus="loop"> 
													 <tbody> 
														<tr>
															<td>${task.assigneeName}</td>
															<td>${task.taskName}</td>
															<td>${task.estimated_time}</td>
															<td>${task.actual_time}</td>
															<td>${task.grade}</td>
														</tr>
													</tbody> 
										</c:forEach> 
									</tbody>
								</table>
							</div>
						</div>
						<!--/.module-->
					</div>
					<!--/.content-->
				</div>
				<!--/.span9-->
			</div>
		</div>
		<!--/.container-->
	</div>
	<!--/.wrapper-->
	<div class="footer">
		<div class="container"></div>
	</div>
	  
	<script src="<c:url value="/assets/scripts/jquery-1.9.1.min.js"/>"
		type="text/javascript"></script>
	<script
		src="<c:url value="/assets/scripts/jquery-ui-1.10.1.custom.min.js"/>"
		type="text/javascript"></script>
	<script src="<c:url value="/assets/bootstrap/js/bootstrap.min.js"/>"
		type="text/javascript"></script>
	<script src="<c:url value="/assets/scripts/flot/jquery.flot.js"/>"
		type="text/javascript"></script>
	<script
		src="<c:url value="/assets/scripts/flot/jquery.flot.resize.js"/>"
		type="text/javascript"></script>
	<script
		src="<c:url value="/assets/scripts/datatables/jquery.dataTables.js"/>"
		type="text/javascript"></script>
	<script src="<c:url value="/assets/scripts/common.js"/>"
		type="text/javascript"></script>
		<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
	<script>
		$('select').on('change', function() {
			
			$.getJSON("project/report/" + this.value, function(data) {
				$("#finished").text(data.taskUnitsFinished);
				$("#toBefinished").text(data.taskUnitsTobeFinished);
				$("#planningPhase").text(data.taskUnitsAtPlanningPhase);
				$("#cancelled").text(data.taskUnitsCancelled);
			});
			
			$.getJSON("project/getScoreCard/" + this.value, function(data) {
				$("#table tr").remove();
				$('<tr>').append(
						$('<th>').text("Team Memeber Name"),
						$('<th>').text("Task Name"),
						$('<th>').text("Estimated_time"),
						$('<th>').text("Actual_time"),
						$('<th>').text("Ratings")
				).appendTo('#thead');
				for(var i in data){
					
					$('<tr>').append(
							$('<td>').text(data[i].assigneeName),
							$('<td>').text(data[i].taskName),
							$('<td>').text(data[i].estimated_time),
							$('<td>').text(data[i].actual_time),
							$('<td>').text(data[i].grade)
					).appendTo('#tbody');		
				}
			});
		});
	</script>
</body>