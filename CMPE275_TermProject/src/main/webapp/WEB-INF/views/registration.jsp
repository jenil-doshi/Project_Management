<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="true"%>
<!Doctype html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Project Manager</title>
<style type="text/css">
*, *:before, *:after {
	box-sizing: border-box;
}

html {
	overflow-y: scroll;
}

body {
	background: #c1bdba;
	font-family: 'Titillium Web', sans-serif;
}

a {
	text-decoration: none;
	color: #1ab188;
	-webkit-transition: .5s ease;
	transition: .5s ease;
}

a:hover {
	color: #179b77;
}

.form {
	background: rgba(19, 35, 47, 0.9);
	padding: 40px;
	max-width: 556px;
	margin: 40px auto;
	border-radius: 4px;
	box-shadow: 0 4px 10px 4px rgba(19, 35, 47, 0.3);
}

.tab-group {
	list-style: none;
	padding: 0;
	margin: 0 0 40px 0;
}

.tab-group:after {
	content: "";
	display: table;
	clear: both;
}

.tab-group li a {
	display: block;
	text-decoration: none;
	padding: 15px;
	background: rgba(160, 179, 176, 0.25);
	color: #a0b3b0;
	font-size: 20px;
	float: left;
	width: 50%;
	text-align: center;
	cursor: pointer;
	-webkit-transition: .5s ease;
	transition: .5s ease;
}

.tab-group li a:hover {
	background: #179b77;
	color: #ffffff;
}

.tab-group .active a {
	background: #1ab188;
	color: #ffffff;
}

.tab-content>div:last-child {
	display: none;
}

label {
	position: absolute;
	-webkit-transform: translateY(6px);
	-ms-transform: translateY(6px);
	transform: translateY(6px);
	left: 13px;
	color: rgba(255, 255, 255, 0.5);
	-webkit-transition: all 0.25s ease;
	transition: all 0.25s ease;
	-webkit-backface-visibility: hidden;
	pointer-events: none;
	font-size: 22px;
}

label .req {
	margin: 2px;
	color: #1ab188;
}

label.active {
	-webkit-transform: translateY(50px);
	-ms-transform: translateY(50px);
	transform: translateY(50px);
	left: 2px;
	font-size: 14px;
}

label.active .req {
	opacity: 0;
}

label.highlight {
	color: #ffffff;
}

input, textarea {
	font-size: 22px;
	display: block;
	width: 100%;
	height: 6.5%;
	padding: 5px 10px;
	background: none;
	background-image: none;
	border: 1px solid #a0b3b0;
	color: #ffffff;
	border-radius: 0;
	-webkit-transition: border-color .25s ease, box-shadow .25s ease;
	transition: border-color .25s ease, box-shadow .25s ease;
}

input:focus, textarea:focus {
	outline: 0;
	border-color: #1ab188;
}

textarea {
	border: 2px solid #a0b3b0;
	resize: vertical;
}

.field-wrap {
	position: relative;
	margin-bottom: 40px;
}

.top-row:after {
	content: "";
	display: table;
	clear: both;
}

.top-row>div {
	float: left;
	width: 48%;
	margin-right: 4%;
}

.top-row>div:last-child {
	margin: 0;
}

.button {
	border: 0;
	outline: none;
	border-radius: 0;
	padding: 15px 0;
	font-size: 2rem;
	font-weight: 600;
	text-transform: uppercase;
	letter-spacing: .1em;
	background: #1ab188;
	color: #ffffff;
	-webkit-transition: all 0.5s ease;
	transition: all 0.5s ease;
	-webkit-appearance: none;
}

.button:hover, .button:focus {
	background: #179b77;
}

.button-block {
	display: block;
	width: 100%;
}

.forgot {
	margin-top: -20px;
	text-align: right;
}

h1 {
	text-align: center;
	color: #ffffff;
	font-weight: 300;
	margin: 0 0 40px;
}

/* .error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}

.msg {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
} */
</style>
<%-- <link type="text/css"
	href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap-responsive.min.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/assets/css/theme.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/assets/images/icons/css/font-awesome.css"
	rel="stylesheet">
<link type="text/css"
	href='http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,400,600'
	rel='stylesheet'> --%>
</head>
<body>



	<div class="form">

		<!-- <ul class="tab-group"> -->
			<button type="button" class="button button-block">Sign Up</button>
			<!-- <li class="tab"><a href="#login">Log In</a></li> -->
		<!-- </ul> -->

		<div class="tab-content">
			<div id="signup">
				<br>
				<br>
				<c:if test="${param.error!=null}">
					<div class="alert alert-danger">
						<h1>Registration Failed</h1>
					</div>
				</c:if>
				<c:if test="${param.success!=null}">
					<div class="alert alert-success">
						<h1>Registration Successful</h1>
					</div>
				</c:if>

				<c:url value="/create" var="regUrl" />

				<form:form action="${regUrl}" commandName="regForm">

					<div class="top-row">
						<div class="field-wrap">
							<label> First Name<span class="req">*</span>
							</label>
							<form:input path="firstName" type="text" required="required" />
						</div>

						<div class="field-wrap">
							<label> Last Name<span class="req">*</span>
							</label>
							<form:input path="lastName" type="text" required="required" />
						</div>
					</div>

					<div class="field-wrap">
						<label> Email Address<span class="req">*</span>
						</label>
						<form:input path="email" type="email" required="required" />
					</div>

					<div class="field-wrap">
						<label> Set A Password<span class="req">*</span>
						</label>
						<form:input path="password" type="password" required="required" />
					</div>

					<input type="submit" class="button button-block" />

				</form:form>

			</div>

			<div id="login">
				<h1>Welcome Back!</h1>

				<form action="/" method="post">

					<div class="field-wrap">
						<label> Email Address<span class="req">*</span>
						</label> <input type="email" required autocomplete="off" />
					</div>

					<div class="field-wrap">
						<label> Password<span class="req">*</span>
						</label> <input type="password" required autocomplete="off" />
					</div>

					<p class="forgot">
						<a href="#">Forgot Password?</a>
					</p>

					<button class="button button-block" />
					Log In
					</button>

				</form>

			</div>

		</div>
		<!-- tab-content -->

	</div>
	<!-- /form -->

	</div>
	</div>

	</div>





	<script
		src="${pageContext.request.contextPath}/assets/scripts/jquery-1.9.1.min.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/assets/scripts/jquery-ui-1.10.1.custom.min.js"
		type="text/javascript"></script>
	<%-- <script
		src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.min.js"
		type="text/javascript"></script> --%>
	<script type="text/javascript">
		$('.form').find('input, textarea').on('keyup blur focus', function(e) {

			var $this = $(this), label = $this.prev('label');

			if (e.type === 'keyup') {
				if ($this.val() === '') {
					label.removeClass('active highlight');
				} else {
					label.addClass('active highlight');
				}
			} else if (e.type === 'blur') {
				if ($this.val() === '') {
					label.removeClass('active highlight');
				} else {
					label.removeClass('highlight');
				}
			} else if (e.type === 'focus') {

				if ($this.val() === '') {
					label.removeClass('highlight');
				} else if ($this.val() !== '') {
					label.addClass('highlight');
				}
			}

		});

		$('.tab a').on('click', function(e) {

			e.preventDefault();

			$(this).parent().addClass('active');
			$(this).parent().siblings().removeClass('active');

			target = $(this).attr('href');

			$('.tab-content > div').not(target).hide();

			$(target).fadeIn(600);

		});
	</script>
</body>
</html>