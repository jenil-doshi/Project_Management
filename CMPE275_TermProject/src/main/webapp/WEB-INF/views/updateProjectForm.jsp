<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<!DOCTYPE html>
<head>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Update a Project</title>
	<link type="text/css" href="<c:url value="/assets/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">
    <link type="text/css" href="<c:url value="/assets/bootstrap/css/bootstrap-responsive.min.css"/>" rel="stylesheet">
    <link type="text/css" href="<c:url value="/assets/css/theme.css"/>" rel="stylesheet">
    <link type="text/css" href="<c:url value="/assets/images/icons/css/font-awesome.css"/>" rel="stylesheet">
    <link rel="stylesheet"
	href="http://code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">
	<link type="text/css" href='http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,400,600' rel='stylesheet'>
</head>
<style>
.required{
      color:red;
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

				<div class="nav-collapse collapse navbar-inverse-collapse">
					
				</div><!-- /.nav-collapse -->
			</div>
		</div><!-- /navbar-inner -->
	</div><!-- /navbar -->



	<div class="wrapper">
		<div class="container">
			<div class="row">
				<div class="span3">
					<div class="sidebar">

						<ul class="widget widget-menu unstyled">
							<li class="active">
								<a href="<c:url value="/home"/>">
									<i class="menu-icon icon-dashboard"></i>
									Dashboard
								</a>
							</li>
							<li>
								<a href="#">
									<i class="menu-icon icon-bullhorn"></i>
									Calendar
								</a>
							</li>
							<li>
								<a href="#">
									<i class="menu-icon icon-inbox"></i>
									Statistics
								</a>
							</li>
							
							<li>
								<a href="<c:url value="/project/addProjectFormView"/>"><i
									class="menu-icon icon-tasks"></i>Add a Project</a>
							</li>
							<sec:authorize access="hasRole('ROLE_ADMIN')">
							<li><a href="<c:url value="/project/viewProjects/${sessionScope.USER.userId}/role_admin"/>">
							<i class="menu-icon icon-tasks"></i>View Projects </a></li>
 									
							</sec:authorize>
							<sec:authorize access="hasRole('ROLE_USER')">
 							<li><a href="<c:url value="/project/viewProjects/${sessionScope.USER.userId}/role_user"/>">
							<i class="menu-icon icon-tasks"></i>View Projects </a></li>
							</sec:authorize>
						</ul><!--/.widget-nav-->

						

						<ul class="widget widget-menu unstyled">
							<li>
								<a class="collapsed" data-toggle="collapse" href="#togglePages">
									<i class="menu-icon icon-cog"></i>
									<i class="icon-chevron-down pull-right"></i><i class="icon-chevron-up pull-right"></i>
									More Pages
								</a>
								<ul id="togglePages" class="collapse unstyled">
									
									<li>
										<a href="#">
											<i class="icon-inbox"></i>
											Profile
										</a>
									</li>
									<li>
										<a href="#">
											<i class="icon-inbox"></i>
											All Users
										</a>
									</li>
									
									
								</ul>
							</li>
							
							<li>
								<a href="<c:url value="/logout" />" >Logout </a>
									
								
							</li>
						</ul>

						

					
					</div><!--/.sidebar-->
				</div><!--/.span3-->


				<div class="span9">
					<div class="content">

						<div class="module">
							<div class="module-head">
								<h1>Update a Project</h1>
							</div>
							<div class="module-body">

									<c:if test="${param.error!=null}">
									<div class="alert alert-danger">${param.error}</div>
								</c:if>
									<c:url value="/project/update/${sessionScope.USER.userId}/${pid}" var="formUrl"/>
									<form:form action="${formUrl}" commandName="updateProjectForm" class="form-horizontal row-fluid">
										<div class="control-group">
											<label class="control-label" for="basicinput"><h4>Project Name<span class="required">*</span></h4></label>
											<div class="controls">
												<form:input path="name" type="text" id="basicinput" placeholder="Name of the Project" class="span8" required="required"/>
											</div>								
										</div>

										<div class="control-group">
											<label class="control-label" for="basicinput"><h4>Project Description</h4></label>
											<div class="controls">
												<form:textarea path="description" class="span8" rows="5"/>
											</div>											
										</div>

										<div class="control-group">
											<label class="control-label" for="basicinput"><h4>Start Date <span class="required">*</span></h4></label>
											<div class="controls">
												<form:input path="startDate" id="startDate" placeholder="When will the project Start?" class="span8" required="required"/>
											</div>
										</div>

										<div class="control-group">
											<label class="control-label" for="basicinput"><h4>End Date <span class="required">*</span></h4></label>
											<div class="controls">
												<form:input path="endDate" id="endDate" placeholder="When will the project end." class="span8" required="required"/>
											</div>
										</div>
										
										<div class="control-group">
											<div class="controls">
												<input type="submit" class="btn"/>
											</div>
										</div>
									</form:form>
							</div>
						</div>

						
						
					</div><!--/.content-->
				</div>
			</div>
		</div><!--/.container-->
	</div><!--/.wrapper-->

	<div class="footer">
		<div class="container">
			 
		</div>
	</div>

	 <script src="<c:url value="/assets/scripts/jquery-1.9.1.min.js"/>" type="text/javascript"></script>
     <script src="<c:url value="/assets/scripts/jquery-ui-1.10.1.custom.min.js"/>" type="text/javascript"></script>
     <script src="<c:url value="/assets/bootstrap/js/bootstrap.min.js"/>" type="text/javascript"></script>
     <script>
     $(document).ready(function() {
			/* $(function() { */
				$("#startDate").datepicker({
					dateFormat : 'mm/dd/yy',
						 minDate: "-30D",
					        maxDate: "+60D",
					        numberOfMonths: 2,
					        onSelect: function(selected) {
					          $("#endDate").datepicker("option","minDate", selected)
					        }
				});
				$("#endDate").datepicker({
					dateFormat : 'mm/dd/yy',
						 minDate: 0,
					        maxDate: "+60D",
					        numberOfMonths: 2,
					        onSelect: function(selected) {
					          $("#startDate").datepicker("option","maxDate", selected)
					        }
				});
			/* }); */
		});
	</script>
     

</body>