<!DOCTYPE html>
<%@page import="fr.excilys.db.dto.ComputerDto"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="<c:url value="/ressources/css/bootstrap.css"/>" rel="stylesheet" media="screen">
<link href="<c:url value="/ressources/css/font-awesome.css"/>" rel="stylesheet" media="screen">
<link href="<c:url value="/ressources/css/main.css"/>" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="computerServlet"><spring:message code="application.name"/> 
				 </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<c:out value="${fn:length(computers)}"></c:out>
				Computers found
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="getAllComputersByPage" method="GET"
						class="form-inline">

						<input type="search" id="searchbox" name="search"
							value="${search}" class="form-control" placeholder="Search name" />
						<input type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
					</div>
					<div>
					<form action="getAllComputersByPage?order=${order}" method="GET" class="form-inline">	
					<label for="exampleFormControlSelect1"></label> <select style="width: 200px;" name="order"
                            class="form-control" id="exampleFormControlSelect1">
                            <option value="" disabled selected="selected">Sort</option>
                            <option value="DESC">Ordre décroissant</option>
                            <option value="ASC">Ordre croisant</option>
                        </select>
                        <input type="submit" id="sortSubmit" value="Sort computers" class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer"
						href="showAddingForm">Add Computer</a> <a
						class="btn btn-default" id="editComputer" href=""
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>
		<form id="deleteForm" action=""method="POST">
			<input type="hidden"  name="selection" value="${computer.id}" >
		</form>

		<div class="container" style="margin-top: 10px;">
			<table id="contentTable" class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th>Computer name</th>
						<th>Introduced date</th>
						<!-- Table header for Discontinued Date -->
						<th>Discontinued date</th>
						<!-- Table header for Company -->
						<th>Company</th>
					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach var="item" items="${computers}">
						<tr>
						<c:url var="updateLink" value="/showEditForm">
						  <c:param name="computerId" value="${item.id}"></c:param>
						</c:url>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${item.id}"></td>
							<td><a href="${updateLink}" onclick="">
									${item.name}</a></td>
							<td>${item.localDateIntroduction}</td>
							<td>${item.localDateDiscontinued}</td>
							<td>${item.idCompany}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">

			<ul class="pagination">
				<%--For displaying Previous link except for the 1st page --%>
				<c:if test="${beginPage!=1 && (endPage-beginPage+1) lt 5}">
					<li><a aria-label="Previous"
						href="getAllComputersByPage?beginPage=${beginPage -1}&endPage=${endPage}&size=${size}&search=${search}&ordre=${order}"><span
							aria-hidden="true">&laquo;</span></a></li>
				</c:if>
				<c:if test="${beginPage!=1 && (endPage-beginPage+1) ge 5}">
					<li class="page-item"><a aria-label="Previous"
						href="getAllComputersByPage?beginPage=${beginPage -1}&endPage=${endPage-1}&size=${size}&search=${search}&ordre=${order}"><span
							aria-hidden="true">&laquo;</span></a></li>
				</c:if>

				<c:forEach begin="${beginPage}" end="${endPage}" var="i">
					<c:choose>
						<c:when test="${beginPage eq i}">
							<li class="page-item active"><a class="page-link"> ${i}
									<span class="sr-only">(current)</span>
							</a></li>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when
									test="${(endPage-beginPage+i) lt numberOfPages}&search=${search}&ordre=${order}">
									<li class="page-item"><a class="page-link"
										href="getAllComputersByPage?beginPage=${i}&size=${size}&endPage=${i+(endPage-beginPage)}&search=${search}&ordre=${order}">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li class="page-item"><a class="page-link"
										href="getAllComputersByPage?beginPage=${i}&size=${size}&endPage=${endPage}&search=${search}&ordre=${order}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${endPage lt numberOfPages}">
					<li class="page-item"><a aria-label="Next"
						href="getAllComputersByPage?beginPage=${beginPage +1}&endPage=${endPage + 1}&search=${search}&size=${size}&ordre=${order}"><span
							aria-hidden="true">&raquo;</span></a></li>
				</c:if>
			</ul>
			<div class="btn-group btn-group-sm pull-right" role="group">
				<a
					href="getAllComputersByPage?beginPage=${1}&endPage=${5}&size=${10}&search=${search}&ordre=${order}">
					<button type="button" class="btn btn-default">10</button>
				</a> <a class="page-link"
					href="getAllComputersByPage?beginPage=${1}&endPage=${5}&size=${50}&search=${search}&ordre=${order}">
					<button type="button" class="btn btn-default">50</button>
				</a> <a
					href="getAllComputersByPage?beginPage=${1}&endPage=${5}&size=${100}&search=${search}&ordre=${order}">
					<button type="button" class="btn btn-default">100</button>
				</a>
			</div>
		</div>
	</footer>
	<script src="<c:url value="/ressources/js/jquery.min.js" />"></script>
	<script src="<c:url value="/ressources/js/bootstrap.min.js" />"></script>
	<script src="<c:url value="/ressources/js/dashboard.js" />"></script>
</body>
</html>