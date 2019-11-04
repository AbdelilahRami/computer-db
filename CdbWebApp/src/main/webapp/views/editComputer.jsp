<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard.html"> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        ${computer.id}
                    </div>
                    <h1>Edit Computer</h1>

                    <form action="servletEditing" method="POST">
                        <input type="hidden" name="id" value="${computer.id}" id="id"/> <!-- TODO: Change this value with the computer id -->
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input required="required" type="text" name="name" class="form-control" value="${computer.name}" id="computerName" placeholder="Computer name" />
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" name="introduced" value="${computer.introducedDate}" id="introduced" placeholder="Introduced date">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" name="discontinued"  value='<c:out value="${computer.discountedDate}"/>' id="discontinued" placeholder="Discontinued date">
                            </div>
                            <span id="error" style="display: none; color: red">Discontinued date must be greater than introduced date !</span>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId" >
                                    <option value="0"></option>
                                    <c:forEach var="item" items="${companies}">
                                       <option value="${item.id}">${item.name}</option>
                                    </c:forEach>
                                </select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" id="edit" class="btn btn-primary">
                            or
                            <a href="dashboard.jsp" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
    <script type="text/javascript">
    $("#introduced").change(function() {checkDate()})
    $("#discontinued").change(function() {checkDate()})
    
    function checkDate() {
    	if($("#discontinued").val()!="" && $("#introduced").val()!=""){
    		console.log("both are not empty");
    		if(moment($("#introduced").val()).isBefore($("#discontinued").val())){
    	         console.log("introduced is before");
    			$("#edit").attr('disabled', false)
    			$("#error").css('display','none')
    		}else{
                console.log("introduced is after");
    			$("#edit").attr('disabled', true)
                $("#error").css('display','block')
                $("#discontinued").css('border', 'red')
    		}
    	}else{
            console.log("one is empty");
    		$("#edit").attr('disabled', false)
            $("#error").css('display','none')
    	}
		
	};
    </script>
</body>
</html>