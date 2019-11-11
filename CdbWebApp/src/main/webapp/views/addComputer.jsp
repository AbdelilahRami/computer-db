<!DOCTYPE html>
<html>
<head>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="<c:url value="/ressources/js/jquery.min.js" />"></script>
    <script src="<c:url value="/ressources/js/bootstrap.min.js" />"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
<!-- Bootstrap -->
<link href="<c:url value="/ressources/css/bootstrap.css"/>" rel="stylesheet" media="screen">
<link href="<c:url value="/ressources/css/font-awesome.css"/>" rel="stylesheet" media="screen">
<link href="<c:url value="/ressources/css/main.css"/>"  rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard.jsp"> Application -
				Comp </a>
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
					<form:form id="form" action="addComputer" modelAttribute="computer"  method="POST">
						<fieldset>
							<div class="form-group">
							<spring:message code="computer.name" var="computer.name"/>
								<label for="computerName"><spring:message code="computer.name"/></label> <form:input
									type="text" class="form-control" name="computerName" path="name"
									id="computerName" placeholder="${computer.name}" required="required"/>
							</div>
							<div class="form-group">
								<label for="introduced"><spring:message code="introduced"/></label> <form:input
									type="date" class="form-control" id="introduced" path="localDateIntroduction" 
									 placeholder="Introduced date"
									/>
							</div>
							<div class="form-group">
								<label for="discontinued"><spring:message code="discontinued"/></label> <form:input
									type="date" class="form-control" path="localDateDiscontinued" 
									id="discontinued" placeholder="Discontinued date"/>
							</div>
							<div id="error" style="display:none; color: red">Discontinued date must be greater than introduced date !</div>
							<div class="form-group">
								<form:label for="name" path="name"><spring:message code="company"/></form:label> <form:select
									class="form-control" path="idCompany" >
									<form:option value=""></form:option>
									<form:options items="${companies}" itemValue="idCompany" itemLabel="name"/>
								</form:select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" id="add" class="btn btn-primary">
							or <a href="computerServlet" class="btn btn-default">Cancel</a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
	
	<script type="text/javascript">
$("#introduced").change(function(){checkDate()})
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
            console.log("I'm in the last condition")
               $("#add").attr("disabled", false);
               $("#error").css("display", 'none');
    }
};
</script>
</body>
</html>
