<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Members</title>
    <link type="text/css" href="<c:url value="/assets/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">
    <link type="text/css" href="<c:url value="/assets/bootstrap/css/bootstrap-responsive.min.css"/>" rel="stylesheet">
    <link type="text/css" href="<c:url value="/assets/css/theme.css"/>" rel="stylesheet">
    <link type="text/css" href="<c:url value="/assets/images/icons/css/font-awesome.css"/>" rel="stylesheet">
    <link type="text/css" href='http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,400,600'
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
                        <li class="active"><a href="#"><i class="icon-envelope"></i></a></li>
                        <li><a href="#"><i class="icon-eye-open"></i></a></li>
                        <li><a href="#"><i class="icon-bar-chart"></i></a></li>
                    </ul>
                    <form class="navbar-search pull-left input-append" action="#">
                    <input type="text" class="span3">
                    <button class="btn" type="button">
                        <i class="icon-search"></i>
                    </button>
                    </form>
                    <ul class="nav pull-right">
                        <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown
                            <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="#">Item No. 1</a></li>
                                <li><a href="#">Don't Click</a></li>
                                <li class="divider"></li>
                                <li class="nav-header">Example Header</li>
                                <li><a href="#">A Separated link</a></li>
                            </ul>
                        </li>
                        <li><a href="#">Support </a></li>
                       
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
									class="menu-icon icon-tasks"></i>Add Project </a>
							</li>
							<sec:authorize access="hasRole('ROLE_ADMIN')">
							<li><a href="<c:url value="/project/viewProjects/${sessionScope.USER.userId}/role_admin"/>">
							<i class="menu-icon icon-tasks"></i>View Projects </a></li>
 									
							</sec:authorize>
							<sec:authorize access="hasRole('ROLE_USER')">
 							<li><a href="<c:url value="/project/viewProjects/${sessionScope.USER.userId}/role_user"/>">
							<i class="menu-icon icon-tasks"></i>View Projects </a></li>
							</sec:authorize>
						</ul>
                        <!--/.widget-nav-->
                        <!--/.widget-nav-->
                        <ul class="widget widget-menu unstyled">
                            <li><a class="collapsed" data-toggle="collapse" href="#togglePages"><i class="menu-icon icon-cog">
                            </i><i class="icon-chevron-down pull-right"></i><i class="icon-chevron-up pull-right">
                            </i>More Pages </a>
                                <ul id="togglePages" class="collapse unstyled">
                                    <li><a href="other-user-profile.html"><i class="icon-inbox"></i>Profile </a></li>
                                    <li><a href="other-user-listing.html"><i class="icon-inbox"></i>All Users </a></li>
                                </ul>
                            </li>
                            
							<li>
								<a href="<c:url value="/logout" />" >Logout </a>
									
								
							</li>
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
                                    All Members</h3>
                            </div>
                            <div class="module-option clearfix">
                                <form>
                                <div class="input-append pull-left">
                                    <input type="text" class="span3" placeholder="Filter by name...">
                                    <button type="submit" class="btn">
                                        <i class="icon-search"></i>
                                    </button>
                                </div>
                                </form>
                                
                            </div>
                           <strong><center>${status}</center></strong>
                            <c:forEach items="${inviteesList}" var="user" varStatus="loop">
                            <div class="module-body">
                                <div class="row-fluid">
                                    <div class="span6">
                                        <div class="media user">
                                            <a class="media-avatar pull-left" href="#">
                                                <img src="${pageContext.request.contextPath}/assets/images/user.png"/>
                                            </a>
                                            <div class="media-body">
                                                <h3 class="media-title">                               
                                                  ${user.firstName}
                                                </h3>
                                                <p>
                                                    <small class="muted">${user.email}</small></p>
                                                    <small class="muted">${user.status}</small></p>
                                                      
                                                <div class="media-option btn-group shaded-icon">
                                                    <button class="btn btn-small">
                                                        <i class="icon-envelope"></i>
                                                    </button>
                                                    <button class="btn btn-small">
                                                        <i class="icon-share-alt"></i>
                                                    </button>
                                                    <br>
                                                    <a href="<c:url value="/project/sendInvite/${user.userId}/${user.email}/${pid}/${pname}/${owner}"/>" class="btn btn-primary" >Send Invitation</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    </div>
                                    </div>
                                    </c:forEach>                                   
                                </div>
                                <!--/.row-fluid-->
                                <br />
                               
                                
                                <div class="pagination pagination-centered">
                                    <ul>
                                        <li><a href="#"><i class="icon-double-angle-left"></i></a></li>
                                        <li><a href="#">1</a></li>
                                        <li><a href="#">2</a></li>
                                        <li><a href="#">3</a></li>
                                        <li><a href="#"><i class="icon-double-angle-right"></i></a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
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
        <div class="container">
            <b class="copyright">&copy; 2014 Edmin - EGrappler.com </b>All rights reserved.
        </div>
    </div>
    <script src="<c:url value="/assets/scripts/jquery-1.9.1.min.js"/>"
		type="text/javascript"></script>
	<script
		src="<c:url value="/assets/scripts/jquery-ui-1.10.1.custom.min.js"/>"
		type="text/javascript"></script>
	<script src="<c:url value="/assets/bootstrap/js/bootstrap.min.js"/>"
		type="text/javascript"></script>
	<script
		src="<c:url value="/assets/scripts/datatables/jquery.dataTables.js"/>"></script>
</body>
