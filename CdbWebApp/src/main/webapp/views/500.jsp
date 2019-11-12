<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
<title><spring:message code="application.title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="<c:url value="/ressources/css/bootstrap.css"/>"
	rel="stylesheet" media="screen">
<link href="<c:url value="/ressources/css/font-awesome.css"/>"
	rel="stylesheet" media="screen">
<link href="<c:url value="/ressources/css/main.css"/>" rel="stylesheet"
	media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="getAllComputersByPage"> <spring:message
					code="application.name" />
			</a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="alert alert-danger">
				Error 500: An error has occured! <br />
				<!-- stacktrace -->
			</div>
		</div>
	</section>

	<script src="<c:url value="/ressources/js/jquery.min.js" />"></script>
	<script src="<c:url value="/ressources/js/bootstrap.min.js" />"></script>
	<script src="<c:url value="/ressources/js/dashboard.js" />"></script>

</body>
</html>