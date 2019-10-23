<!DOCTYPE html>
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard.jsp"> Application -
				Computer Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<c:if test="${sucess!=null}">
						<div class="alert alert-success">
							<span>${message}</span>
						</div>
					</c:if>
					<c:if test="${fails!=null}">
						<div class="alert alert-danger">
							<span>${message}</span>
						</div>
					</c:if>
					<form id="form" action="servletAddingComputer" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control" name="computerName"
									id="computerName" placeholder="Computer name" required="required">
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									type="date" class="form-control" name="introduced"
									id="introduced" placeholder="Introduced date"
									required="required">
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									type="date" class="form-control" name="discontinued"
									id="discontinued" placeholder="Discontinued date">
							</div>
							<div id="error" style="display:none; color: red">Discontinued date must be greater than introduced date !</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" id="companyId" name="companyName">
									<option></option>
									<c:forEach var="company" items="${companies}">
										<option value="${company.id}">${company.name}</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" id="add" class="btn btn-primary">
							or <a href="dashboard.html" class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<script type="text/javascript">
$("#introduced").change(function(){console.log("toto"); checkDate()})
$("#discontinued").change(function() {checkDate()})
function checkDate() {
    if($("#introduced").val()!="" && $("#discontinued").val()!=""){
        if(moment($("#introduced").val()).isBefore($("#discontinued").val())){
            console.log("display")
            $("#add").attr("disabled",false);
            $("#error").css("display", 'none');
        } else{
               console.log("non display")
                $("#add").attr("disabled",true);
                $("#error").css("display", 'block');
        }
    }else{
            console.log("123")
               $("#add").attr("disabled", false);
               $("#error").css("display", 'none');
    }
};
</script>
</body>
</html>
