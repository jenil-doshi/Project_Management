<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Project Information</title>
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

	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<a class="btn btn-navbar" data-toggle="collapse"
						data-target=".navbar-inverse-collapse"> <i
						class="icon-reorder shaded"></i></a>
					<a class="brand" href="index.html">Owner -
						${sessionScope.USER.firstName} </a>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_USER')">
					<a class="btn btn-navbar" data-toggle="collapse"
						data-target=".navbar-inverse-collapse"> <i
						class="icon-reorder shaded"></i></a>
					<a class="brand" href="index.html">Team Member -
						${sessionScope.USER.firstName} </a>
				</sec:authorize>

				<div class="nav-collapse collapse navbar-inverse-collapse">
					<ul class="nav nav-icons">
						<li class="active"><a href="#"> <i class="icon-envelope"></i>
						</a></li>
						<li><a href="#"> <i class="icon-eye-open"></i>
						</a></li>
						<li><a href="#"> <i class="icon-bar-chart"></i>
						</a></li>
					</ul>

					<form class="navbar-search pull-left input-append" action="#">
						<input type="text" class="span3">
						<button class="btn" type="button">
							<i class="icon-search"></i>
						</button>
					</form>

					<ul class="nav pull-right">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown">Dropdown <b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="#">Item No. 1</a></li>

								<li><a href="#">Don't Click</a></li>
								<li class="divider"></li>
								<li class="nav-header">Example Header</li>
								<li><a href="#">A Separated link</a></li>
							</ul></li>

						<li><a href="#"> Support </a></li>

					</ul>
				</div>
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
							<li class="active"><a href="<c:url value="/home"/>">
									<i class="menu-icon icon-dashboard"></i>
									Dashboard
								</a></li>
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

						<ul class="widget widget-menu unstyled">
							<li><a class="collapsed" data-toggle="collapse"
								href="#togglePages"> <i class="menu-icon icon-cog"></i> <i
									class="icon-chevron-down pull-right"></i><i
									class="icon-chevron-up pull-right"></i> More Pages
							</a>
								<ul id="togglePages" class="collapse unstyled">
									<li><a href="#"> <i class="icon-inbox"></i> Profile
									</a></li>
									<li><a href="#"> <i class="icon-inbox"></i> All Users
									</a></li>
								</ul></li>

							<li><a href="#"> <i class="menu-icon icon-signout"></i>
									Logout
							</a></li>
						</ul>

					</div>
					<!--/.sidebar-->
				</div>
				<!--/.span3-->


				<div class="span9">
					<div class="content">

						<div class="module">
							<div class="module-head">
								<h3>
									<strong>Project Information</strong>
								</h3>
							</div>


							<div class="pull-right ">
								<a href="<c:url value="/project/getUsersListForAddProject/${pageContext.request.userPrincipal.name}/${project.pid}/${project.name}/${project.owner.firstName}"/>" class="btn btn-primary"
									style="margin-left: -17%; margin-top: 11%;">Invite Users</a>
							</div>

							<section>
								<div class="module-body">
									<b>ID: ${project.pid}</b><br /> <b>Name:${project.name}</b><br />
									<b>Description: ${project.description}</b><br /> <b>Start
										Date: ${project.startDate}</b><br /> <b>End Date:
										${project.endDate}</b><br /> <b>Owner:${project.owner.userId}</b><br />
									<b>Status:${project.status}</b><br /> <br />
								</div>
							</section>
							<section>
								<div class="module-body">
									<p>
										<strong>Task information</strong>

									</p>
									<table class="table">
										<tr>
											<th>#</th>
											<th>ID</th>
											<th>Name</th>
											<th>Estimated Units</th>
											<th>Actual Units</th>
											<th>Assignee</th>
											<th>State</th>
											<th>View</th>
										</tr>
										<thead>
											<c:forEach items="${taskList}" var="task" varStatus="loop">
												<tbody>
													<tr>
														<td>${loop.index+1}</td>
														<td>${task.tid}</td>
														<td>${task.taskName}</td>
														<td>${task.description}</td>
														<td>${task.estimated_time}</td>
														<td>${task.actual_time}</td>
														<%--  <td>${task.owner.firstName}</td> --%>
														<td>${task.taskState}</td>

														<td><a class="btn btn-info"
															href="<c:url value="/project/getProjectInfo/${project.pid}"/>">VIEW</td>

													</tr>
												</tbody>
											</c:forEach>
									</table>
							</section>
						</div>
						<br />

					</div>

				</div>
				<!--/.span9-->
			</div>
		</div>
		<!--/.container-->
	</div>
	<!--/.wrapper-->



	<script src="<c:url value="/assets/scripts/jquery-1.9.1.min.js"/>"
		type="text/javascript"></script>
	<script
		src="<c:url value="/assets/scripts/jquery-ui-1.10.1.custom.min.js"/>"
		type="text/javascript"></script>
	<script src="<c:url value="/assets/bootstrap/js/bootstrap.min.js"/>"
		type="text/javascript"></script>
	<script
		src="<c:url value="/assets/scripts/datatables/jquery.dataTables.js"/>"></script>
	<script>
		$(document).ready(
				function() {
					$('.datatable-1').dataTable();
					$('.dataTables_paginate').addClass(
							"btn-group datatable-pagination");
					$('.dataTables_paginate > a').wrapInner('<span />');
					$('.dataTables_paginate > a:first-child').append(
							'<i class="icon-chevron-left shaded"></i>');
					$('.dataTables_paginate > a:last-child').append(
							'<i class="icon-chevron-right shaded"></i>');
				});
	</script>
</body>