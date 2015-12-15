<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>View Projects</title>
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
<style>
table, th, td {width =100%;
	
}
</style>
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
				

				<div class="nav-collapse collapse navbar-inverse-collapse"></div>
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
							<li class="active"><a href="<c:url value="/home"/>"> <i
									class="menu-icon icon-dashboard"></i> Dashboard
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
							<sec:authorize access="hasRole('ROLE_ADMIN')">
								<li><a
									href="<c:url value="/project/viewTasks/${sessionScope.USER.userId}/role_admin"/>">
										<i class="menu-icon icon-tasks"></i>View Tasks
								</a></li>
							</sec:authorize>
							<sec:authorize access="hasRole('ROLE_USER')">
								<li><a
									href="<c:url value="/project/viewTasks/${sessionScope.USER.userId}/role_user"/>">
										<i class="menu-icon icon-tasks"></i>View Tasks
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
									<li><a href="<c:url value="/project/getProfilePage"/>"> <i class="icon-inbox"></i> Profile
									</a></li>
									<!-- <li><a href="#"> <i class="icon-inbox"></i> All Users
									</a></li> -->
								</ul></li>


							<li><a href="<c:url value="/logout" />">Logout </a></li>
						</ul>

					</div>
					<!--/.sidebar-->
				</div>
				<!--/.span3-->


				<div class="span9">
					<div class="content">

						<div class="module">
							<div class="module-head">
								<h1>List of Projects</h1>
							</div>
							<div class="module-body">
								<!-- <p>
									<strong></strong>
									
									
								</p> -->
								<table class="table" id="projTable">
									<tr>
										<!-- <th>#</th> -->
										<th><h4>Project ID</h4></th>
										<th><h4>Project Name</h4></th>
										<!--  <th>Description</th>
									  <th>Start Date</th>
									  <th>End Date</th>   -->
										<!-- <th>Owner</th>   -->
										<th><h4>Status</h4></th>
										<th><h4>View</h4></th>
										<th><h4>Task</h4></th>
									</tr>
									</thead>
									<thead>
										<c:forEach items="${projectList}" var="project"
											varStatus="loop">
											<tbody>
												<tr>
													<%--  <td>${loop.index+1}</td> --%>
													<td><center>${project.pid}</center></td>
													<td>${project.name}</td>
													<%-- <td>${project.description}</td>
									  <td>${project.startDate}</td>
									  <td>${project.endDate}</td>
									  <td>${project.owner.firstName}</td> --%>
													<c:choose>
														<c:when test="${project.status == 'planning'}">
														<td style="color:orange;text-transform: uppercase;font-weight: bolder;">${project.status}</td>
														</c:when>
														<c:when test="${project.status == 'new'}">
														<td style="color:blue;text-transform: uppercase;font-weight: bolder;">${project.status}</td>
														</c:when>
														<c:when test="${project.status == 'ongoing'}">
														<td style="color:black;text-transform: uppercase;font-weight: bolder;">${project.status}</td>
														</c:when>
														<c:when test="${project.status == 'completed'}">
														<td style="color:green;text-transform: uppercase;font-weight: bolder;">${project.status}</td>
														</c:when>
														<c:when test="${project.status == 'cancelled'}">
														<td style="color:red;text-transform: uppercase;font-weight: bolder;">${project.status}</td>
														</c:when>
														<c:otherwise>
														<td style="color:black;text-transform: uppercase;font-weight: bolder;">${project.status}</td>
														</c:otherwise>
													</c:choose>
													
													<td><a class="btn btn-primary btn-sm"
														href="<c:url value="/project/getProjectInfo/${project.pid}"/>">Details</td>
													<c:choose>
														<c:when
															test="${(project.status=='completed') || (project.status=='cancelled')}">

														</c:when>
														<c:otherwise>
															<td><a class="btn btn-primary btn-sm"
																href="<c:url value="/project/addTask/${project.pid}"/>">Add
																	Task</td>
														</c:otherwise>
													</c:choose>
												</tr>
											</tbody>
										</c:forEach>
								</table>

								<br />

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
					$(document)
							.ready(
									function() {
										$('.datatable-1').dataTable();
										$('.dataTables_paginate')
												.addClass(
														"btn-group datatable-pagination");
										$('.dataTables_paginate > a')
												.wrapInner('<span />');
										$(
												'.dataTables_paginate > a:first-child')
												.append(
														'<i class="icon-chevron-left shaded"></i>');
										$('.dataTables_paginate > a:last-child')
												.append(
														'<i class="icon-chevron-right shaded"></i>');
									});
					
				</script>
</body>