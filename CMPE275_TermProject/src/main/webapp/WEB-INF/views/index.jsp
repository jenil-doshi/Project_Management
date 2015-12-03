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
									<li><a href="<c:url value="/project/getProfilePage"/>"><i class="icon-inbox"></i>Profile </a></li>
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
				<div class="span9">
					<div class="content">
						<div class="btn-controls">
							<div class="btn-box-row row-fluid" style="margin-left: 1%;">
								<a href="#" class="btn-box big span4" style="width: 22%;"><i
									class=" icon-random"></i><b>65</b><br>
									<p class="text-muted">Total number of task units finished</p> </a>

								<a href="#" class="btn-box big span4" style="width: 22%;"><i
									class="icon-random"></i><b>15</b><br>
									<p class="text-muted">Total number of task units to be
										finished</p> </a> <a href="#" class="btn-box big span4"
									style="width: 22%;"><i class="icon-random"></i><b>40</b><br>
									<p class="text-muted">Total number of task units during the
										planning phase</p> </a> <a href="#" class="btn-box big span4"
									style="width: 22%;"><i class="icon-random"></i><b>10</b><br>
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
						<div class="module">
							<div class="module-head">
								<h3>Profit Chart</h3>
							</div>
							<div class="module-body">
								<div class="chart inline-legend grid">
									<div id="placeholder2" class="graph" style="height: 500px">
									</div>
								</div>
							</div>
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
								<table cellpadding="0" cellspacing="0" border="0"
									class="datatable-1 table table-bordered table-striped	 display"
									width="100%">
									<thead>
										<tr>
											<th>Team Member Name</th>
											<!-- <th></th>
											<th>Platform(s)</th>
											<th>Engine version</th> -->
											<th>No of task units completed</th>
											<th>Score</th>
										</tr>
									</thead>
									<tbody>
										<tr class="odd gradeX">
											<td>John</td>
											<!-- <td>Internet Explorer 4.0</td>
											<td>Win 95+</td> -->
											<td class="center">4</td>
											<td class="center">B</td>
										</tr>
										<tr class="even gradeC">
											<td>Trident</td>
											<!-- <td>Internet Explorer 5.0</td>
											<td>Win 95+</td> -->
											<td class="center">5</td>
											<td class="center">C</td>
										</tr>
										<tr class="odd gradeA">
											<td>Trident</td>
											<!-- 	<td>Internet Explorer 5.5</td>
											<td>Win 95+</td> -->
											<td class="center">5.5</td>
											<td class="center">A</td>
										</tr>
										<tr class="even gradeA">
											<td>Trident</td>
											<!-- <td>Internet Explorer 6</td>
											<td>Win 98+</td> -->
											<td class="center">6</td>
											<td class="center">A</td>
										</tr>
										<tr class="odd gradeA">
											<td>Trident</td>
											<!-- <td>Internet Explorer 7</td>
											<td>Win XP SP2+</td> -->
											<td class="center">20</td>
											<td class="center">A</td>
										</tr>
										<tr class="even gradeA">
											<td>Trident</td>
											<!-- <td>AOL browser (AOL desktop)</td>
											<td>Win XP</td> -->
											<td class="center">29</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Gecko</td>
											<!-- <td>Firefox 1.0</td>
											<td>Win 98+ / OSX.2+</td> -->
											<td class="center">50</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Gecko</td>
											<!-- <td>Firefox 1.5</td>
											<td>Win 98+ / OSX.2+</td> -->
											<td class="center">2</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Gecko</td>
											<!-- <td>Firefox 2.0</td>
											<td>Win 98+ / OSX.2+</td> -->
											<td class="center">1.8</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Gecko</td>
											<!-- <td>Firefox 3.0</td>
											<td>Win 2k+ / OSX.3+</td> -->
											<td class="center">1.9</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Gecko</td>
											<!-- <td>Camino 1.0</td>
											<td>OSX.2+</td> -->
											<td class="center">1.8</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Gecko</td>
											<!-- <td>Camino 1.5</td>
											<td>OSX.3+</td> -->
											<td class="center">1.8</td>
											<td class="center">C</td>
										</tr>
										<tr class="gradeA">
											<td>Gecko</td>
											<!-- <td>Netscape 7.2</td>
											<td>Win 95+ / Mac OS 8.6-9.2</td> -->
											<td class="center">1.7</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Gecko</td>
											<!-- <td>Netscape Browser 8</td>
											<td>Win 98SE+</td> -->
											<td class="center">1.7</td>
											<td class="center">B</td>
										</tr>
										<tr class="gradeA">
											<td>Gecko</td>
											<!-- <td>Netscape Navigator 9</td>
											<td>Win 98+ / OSX.2+</td> -->
											<td class="center">1.8</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Gecko</td>
											<!-- <td>Mozilla 1.0</td>
											<td>Win 95+ / OSX.1+</td> -->
											<td class="center">1</td>
											<td class="center">A</td>
										</tr>
										<!-- End -->
										<tr class="gradeA">
											<td>Gecko</td>
											<!-- <td>Mozilla 1.1</td>
											<td>Win 95+ / OSX.1+</td> -->
											<td class="center">1.1</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Gecko</td>
											<!-- <td>Mozilla 1.2</td>
											<td>Win 95+ / OSX.1+</td> -->
											<td class="center">1.2</td>
											<td class="center">B</td>
										</tr>
										<tr class="gradeA">
											<td>Gecko</td>
											<!-- <td>Mozilla 1.3</td>
											<td>Win 95+ / OSX.1+</td> -->
											<td class="center">1.3</td>
											<td class="center">C</td>
										</tr>
										<!-- <tr class="gradeA">
											<td>Gecko</td>
											<td>Mozilla 1.4</td>
											<td>Win 95+ / OSX.1+</td>
											<td class="center">1.4</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Gecko</td>
											<td>Mozilla 1.5</td>
											<td>Win 95+ / OSX.1+</td>
											<td class="center">1.5</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Gecko</td>
											<td>Mozilla 1.6</td>
											<td>Win 95+ / OSX.1+</td>
											<td class="center">1.6</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Gecko</td>
											<td>Mozilla 1.7</td>
											<td>Win 98+ / OSX.1+</td>
											<td class="center">1.7</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Gecko</td>
											<td>Mozilla 1.8</td>
											<td>Win 98+ / OSX.1+</td>
											<td class="center">1.8</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Gecko</td>
											<td>Seamonkey 1.1</td>
											<td>Win 98+ / OSX.2+</td>
											<td class="center">1.8</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Gecko</td>
											<td>Epiphany 2.20</td>
											<td>Gnome</td>
											<td class="center">1.8</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Webkit</td>
											<td>Safari 1.2</td>
											<td>OSX.3</td>
											<td class="center">125.5</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Webkit</td>
											<td>Safari 1.3</td>
											<td>OSX.3</td>
											<td class="center">312.8</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Webkit</td>
											<td>Safari 2.0</td>
											<td>OSX.4+</td>
											<td class="center">419.3</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Webkit</td>
											<td>Safari 3.0</td>
											<td>OSX.4+</td>
											<td class="center">522.1</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Webkit</td>
											<td>OmniWeb 5.5</td>
											<td>OSX.4+</td>
											<td class="center">420</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Webkit</td>
											<td>iPod Touch / iPhone</td>
											<td>iPod</td>
											<td class="center">420.1</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Webkit</td>
											<td>S60</td>
											<td>S60</td>
											<td class="center">413</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Presto</td>
											<td>Opera 7.0</td>
											<td>Win 95+ / OSX.1+</td>
											<td class="center">-</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Presto</td>
											<td>Opera 7.5</td>
											<td>Win 95+ / OSX.2+</td>
											<td class="center">-</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Presto</td>
											<td>Opera 8.0</td>
											<td>Win 95+ / OSX.2+</td>
											<td class="center">-</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Presto</td>
											<td>Opera 8.5</td>
											<td>Win 95+ / OSX.2+</td>
											<td class="center">-</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Presto</td>
											<td>Opera 9.0</td>
											<td>Win 95+ / OSX.3+</td>
											<td class="center">-</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Presto</td>
											<td>Opera 9.2</td>
											<td>Win 88+ / OSX.3+</td>
											<td class="center">-</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Presto</td>
											<td>Opera 9.5</td>
											<td>Win 88+ / OSX.3+</td>
											<td class="center">-</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Presto</td>
											<td>Opera for Wii</td>
											<td>Wii</td>
											<td class="center">-</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Presto</td>
											<td>Nokia N800</td>
											<td>N800</td>
											<td class="center">-</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>Presto</td>
											<td>Nintendo DS browser</td>
											<td>Nintendo DS</td>
											<td class="center">8.5</td>
											<td class="center">C/A<sup>1</sup>
											</td>
										</tr>
										<tr class="gradeC">
											<td>KHTML</td>
											<td>Konqureror 3.1</td>
											<td>KDE 3.1</td>
											<td class="center">3.1</td>
											<td class="center">C</td>
										</tr>
										<tr class="gradeA">
											<td>KHTML</td>
											<td>Konqureror 3.3</td>
											<td>KDE 3.3</td>
											<td class="center">3.3</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeA">
											<td>KHTML</td>
											<td>Konqureror 3.5</td>
											<td>KDE 3.5</td>
											<td class="center">3.5</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeX">
											<td>Tasman</td>
											<td>Internet Explorer 4.5</td>
											<td>Mac OS 8-9</td>
											<td class="center">-</td>
											<td class="center">X</td>
										</tr>
										<tr class="gradeC">
											<td>Tasman</td>
											<td>Internet Explorer 5.1</td>
											<td>Mac OS 7.6-9</td>
											<td class="center">1</td>
											<td class="center">C</td>
										</tr>
										<tr class="gradeC">
											<td>Tasman</td>
											<td>Internet Explorer 5.2</td>
											<td>Mac OS 8-X</td>
											<td class="center">1</td>
											<td class="center">C</td>
										</tr>
										<tr class="gradeA">
											<td>Misc</td>
											<td>NetFront 3.1</td>
											<td>Embedded devices</td>
											<td class="center">-</td>
											<td class="center">C</td>
										</tr>
										<tr class="gradeA">
											<td>Misc</td>
											<td>NetFront 3.4</td>
											<td>Embedded devices</td>
											<td class="center">-</td>
											<td class="center">A</td>
										</tr>
										<tr class="gradeX">
											<td>Misc</td>
											<td>Dillo 0.8</td>
											<td>Embedded devices</td>
											<td class="center">-</td>
											<td class="center">X</td>
										</tr>
										<tr class="gradeX">
											<td>Misc</td>
											<td>Links</td>
											<td>Text only</td>
											<td class="center">-</td>
											<td class="center">X</td>
										</tr>
										<tr class="gradeX">
											<td>Misc</td>
											<td>Lynx</td>
											<td>Text only</td>
											<td class="center">-</td>
											<td class="center">X</td>
										</tr>
										<tr class="gradeC">
											<td>Misc</td>
											<td>IE Mobile</td>
											<td>Windows Mobile 6</td>
											<td class="center">-</td>
											<td class="center">C</td>
										</tr>
										<tr class="gradeC">
											<td>Misc</td>
											<td>PSP browser</td>
											<td>PSP</td>
											<td class="center">-</td>
											<td class="center">C</td>
										</tr>
										<tr class="gradeU">
											<td>Other browsers</td>
											<td>All others</td>
											<td>-</td>
											<td class="center">-</td>
											<td class="center">U</td>
										</tr> -->
									</tbody>
									<tfoot>
										<tr>
											<th>Team Member Name</th>
											<th>No of task units completed</th>
											<th>Score</th>
										</tr>
									</tfoot>
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

</body>