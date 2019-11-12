<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<title><spring:message code="application.title"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="<c:url value="/ressources/js/jquery.min.js" />"></script>
<script src="<c:url value="/ressources/js/dashboard.js"/>"></script>
<!-- Bootstrap -->
<script src="<c:url value="/ressources/js/bootstrap.min.js"/>"></script>
<link href="<c:url  value="/ressources/css/bootstrap.min.css"/>" rel="stylesheet" media="screen">
<link href="<c:url  value="/ressources/css/font-awesome.css"/>" rel="stylesheet" media="screen">
<link href="<c:url value="/ressources/css/main.css" />" rel="stylesheet" media="screen">
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard.html"> Application -
                Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">${computer.id}</div>
                    <h1>Edit Computer</h1>

                    <form:form action="editComputer" modelAttribute="computer" method="POST">
                        <input type="hidden" name="id" value="${computer.id}" id="id" />
                        <!-- TODO: Change this value with the computer id -->
                        <fieldset>
                            <div class="form-group">
                                <form:label path="" for="computerName">Computer name</form:label> <form:input
                                    required="required" type="text" name="name" path="name"
                                    class="form-control" value="${computer.name}" id="computerName"
                                    placeholder="Computer name" />
                            </div>
                            <div class="form-group">
                                <form:label path="" for="introduced">Introduced date</form:label> <form:input
                                    type="date" class="form-control" name="introduced" path="localDateIntroduction"
                                    value="${computer.localDateIntroduction}" id="introduced"
                                    placeholder="Introduced date" />
                            </div>
                            <div class="form-group">
                                <form:label path="" for="discontinued">Discontinued date</form:label> <form:input
                                    type="date" class="form-control" name="discontinued" path="localDateDiscontinued"
                                    value='<c:out value="${computer.localDateDiscontinued}"/>'
                                    id="discontinued" placeholder="Discontinued date"/>
                            </div>
                            <span id="error" style="display: none; color: red">Discontinued
                                date must be greater than introduced date !</span>
                            <div class="form-group">
                                <form:label path="" for="companyId">Company</form:label> <form:select 
                                    class="form-control" id="companyId" path="idCompany" name="companyId">
                                    <option value=""></option>
                                    <c:forEach var="item" items="${companies}">
                                        <form:option value="${item.idCompany}">${item.name}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" id="edit"
                                class="btn btn-primary"> or <a href="dashboard.jsp"
                                class="btn btn-default">Cancel</a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
    <script type="text/javascript">
        $("#introduced").change(function() {
            checkDate()
        })
        $("#discontinued").change(function() {
            checkDate()
        })
        function checkDate() {
            if ($("#discontinued").val() != "" && $("#introduced").val() != "") {
                console.log("both are not empty");
                if (moment($("#introduced").val()).isBefore(
                        $("#discontinued").val())) {
                    console.log("introduced is before");
                    $("#edit").attr('disabled', false)
                    $("#error").css('display', 'none')
                } else {
                    console.log("introduced is after");
                    $("#edit").attr('disabled', true)
                    $("#error").css('display', 'block')
                    $("#discontinued").css('border', 'red')
                }
            } else {
                console.log("one is empty");
                $("#edit").attr('disabled', false)
                $("#error").css('display', 'none')
            }
        };
    </script>
</body>
</html>