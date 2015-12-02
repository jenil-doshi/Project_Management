<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<style type="text/css">
#projTable.td {
	word-spacing: 30px;
}

.btn-disable {
	cursor: default;
	pointer-events: none;
	/*Button disabled - CSS color class*/
	color: #c0c0c0;
	background-color: #ffffff;
}
</style>
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
							<li class="active"><a href="<c:url value="/home"/>"> <i
									class="menu-icon icon-dashboard"></i> Dashboard
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
								<h3>
									<strong>Project Information</strong>
								</h3>
							</div>
							<!-- Update Project-->
							<c:if test="${param.successfulUpdate!=null}">
								<div class="alert alert-success">
									${param.successfulUpdate}</div>
							</c:if>
							<c:if test="${param.error!=null}">
								<div class="alert alert-danger">${param.error}</div>
							</c:if>
							<c:if test="${param.ExceptionError!=null}">
								<div class="alert alert-danger">${param.ExceptionError}</div>
							</c:if>
							<!-- Start Project -->
							<c:if test="${param.startProjSuccess!=null}">
								<div class="alert alert-success">${param.startProjSuccess}</div>
							</c:if>
							<c:if test="${param.startProjError!=null}">
								<div class="alert alert-danger">${param.startProjError}</div>
							</c:if>
							<c:if test="${param.startProjException!=null}">
								<div class="alert alert-danger">${param.startProjException}</div>
							</c:if>
							<!-- Complete Project -->
							<c:if test="${param.completeProjSuccess!=null}">
								<div class="alert alert-success">${param.completeProjSuccess}</div>
							</c:if>
							<c:if test="${param.completeProjError!=null}">
								<div class="alert alert-danger">${param.completeProjError}</div>
							</c:if>
							<c:if test="${param.completeProjException!=null}">
								<div class="alert alert-danger">${param.completeProjException}</div>
							</c:if>
							<!-- Cancel Project -->
							<c:if test="${param.cancelProjSuccess!=null}">
								<div class="alert alert-success">${param.cancelProjSuccess}</div>
							</c:if>
							<c:if test="${param.cancelProjError!=null}">
								<div class="alert alert-danger">${param.cancelProjError}</div>
							</c:if>
							<c:if test="${param.cancelProjException!=null}">
								<div class="alert alert-danger">${param.cancelProjException}</div>
							</c:if>
							<table style="width: 100%;" id="projTable">
								<tr>
									<td rowspan="4">
										<div class="module-body">
											<table id="projTable" style="width: 100%;">
												<tr>
													<td>ID</td>
													<td>${project.pid}</td>
												</tr>
												<tr>
													<td>Name</td>
													<td>${project.name}</td>
												</tr>
												<tr>
													<td>Description</td>
													<td>${project.description}</td>
												</tr>

												<tr>
													<td>Start Date</td>
													<td><fmt:formatDate type="date"
															value="${project.startDate}" /></td>
												</tr>
												<tr>
													<td>End Date</td>
													<td><fmt:formatDate type="date"
															value="${project.endDate}" /></td>
												</tr>
												<tr>
													<td>Owner</td>
													<td>${project.owner.firstName}</td>
												</tr>
												<tr>

													<td>Status</td>
													<td id="status">${project.status}</td>
												</tr>

												<sec:authorize access="hasRole('ROLE_ADMIN')">
													<tr>
														<td><section>
																<div id="hideDivUpdate">
																	<a class="btn btn-primary"
																		href="<c:url value="/project/updateProjectFormView/${project.pid}"/>">Update
																		Project</a>
																</div>
															</section></td>
													</tr>
												</sec:authorize>

											</table>

										</div>
									</td>
									<td><sec:authorize access="hasRole('ROLE_ADMIN')">
											<div class="pull-right ">
												<div id="hideDivInvite">
													<a
														href="<c:url value="/project/getUsersListForAddProject/${pageContext.request.userPrincipal.name}/${project.pid}/${project.name}/${project.owner.firstName}"/>"
														class="btn btn-primary"
														style="margin-left: -17%; margin-top: 11%;">Invite
														Users </a>
												</div>
											</div>
										</sec:authorize> <!-- </div> --></td>
								</tr>
								<tr>
									<td><sec:authorize access="hasRole('ROLE_ADMIN')">
											<div class="pull-right ">
												<div id="hideDivStart">
													<a
														href="<c:url value="/project/start/${project.pid}/${project.owner.userId}"/>"
														class="btn btn-primary"
														style="margin-left: -17%; margin-top: 11%;">Start
														Project </a>
												</div>
											</div>
										</sec:authorize></td>
								</tr>
								<tr>
									<td><sec:authorize access="hasRole('ROLE_ADMIN')">
											<div class="pull-right ">
												<div id="hideDivCancel">
													<a
														href="<c:url value="/project/cancel/${project.pid}/${project.owner.userId}"/>"
														class="btn btn-primary"
														style="margin-left: -17%; margin-top: 11%;">Cancel
														Project </a>
												</div>
											</div>
										</sec:authorize></td>
								</tr>
								<tr>
									<td><sec:authorize access="hasRole('ROLE_ADMIN')">
											<div class="pull-right ">
												<div id="hideDivComplete">
													<a
														href="<c:url value="/project/complete/${project.pid}/${project.owner.userId}"/>"
														class="btn btn-primary"
														style="margin-left: -17%; margin-top: 11%;">Complete
														Project </a>
												</div>
											</div>
										</sec:authorize></td>
								</tr>

							</table>

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
											<th>Description</th>
											<th>Estimated Units</th>
											<th>Actual Units</th>
											<th>Assignee</th>
											<th>State</th>
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
														<td>${task.assigneeName}</td>
														<td>${task.taskState}</td>
													<tr>
														<c:choose>
															<c:when
																test="${(task.taskState=='finished') || (task.taskState=='cancelled')}">
															</c:when>
															<c:otherwise>
																<td></td>
																<td></td>
																<td><a class="btn btn-info"
																	href="<c:url value="/project/updateTask/${task.tid}"/>">Update
																		Task</td>

																<c:url value="/project/task/start/${task.tid}"
																	var="formUrl" />
																<form:form action="${formUrl}"
																	commandName="startTaskForm"
																	class="form-horizontal row-fluid">
																	<td><input type="submit" class="btn btn-info"
																		value="Start task" /></td>
																</form:form>

																<c:url
																	value="/project/task/cancel/${task.tid}/${sessionScope.USER.userId}"
																	var="formUrl" />
																<form:form action="${formUrl}"
																	commandName="cancelTaskForm"
																	class="form-horizontal row-fluid">
																	<td><input type="submit" class="btn btn-info"
																		value="Cancel task" /></td>
																</form:form>

																<c:url value="/project/task/finish/${task.tid}"
																	var="formUrl" />
																<form:form action="${formUrl}"
																	commandName="finishTaskForm"
																	class="form-horizontal row-fluid">
																	<td><input type="submit" class="btn btn-info"
																		value="Finish task" /></td>
																</form:form>
																<td></td>
																<td></td>
															</c:otherwise>
														</c:choose>
													</tr>

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
		$(document)
				.ready(
						function() {
							//alert($("#projTable #status").text());
							if ($.trim($("#projTable #status").text()) == "completed"
									|| $.trim($("#projTable #status").text()) == "cancelled") {
								//alert("hi");
								$("div#hideDivUpdate").hide();
								$("div#hideDivInvite").hide();
								$("div#hideDivStart").hide();
								$("div#hideDivComplete").hide();
								$("div#hideDivCancel").hide();
							}
						});
	</script>
</body>