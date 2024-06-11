<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>		
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
		<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>
<style>
		table {
			border-collapse: collapse;
			width: 100%;
		}
		th, td {
			text-align: left;
			padding: 8px;
			border-bottom: 1px solid #ddd;
		}
		tr:nth-child(even) {
			background-color: #f2f2f2;
		}
	</style>
	</head>
	<body>
	<c:choose>
	<c:when test="${data.size()>0}">		
		<div class="container">
	    	<h2 align="center" class="text-primary"></h2><hr />
	    	<div>&nbsp;</div>
	    	
	    	<!-- Table to show the data fetched from the db. -->   	    	
	        <table class="table" align="center">
			    
			      <tr>			        
					<th></th>
					<th>製令單別</th>
					<th>製令單號</th>
					<th>料號</th>
					<th>品名</th>
					<th>自主檢查日期</th>
			      </tr>
			  
			    
			    	<c:forEach var="data" items="${data}" varStatus="index">
				      <tr>
				        <td><a href="${pageContext.request.contextPath}/getHistoryData/${data.getQc_id()}/${pqcCREATOR}">${index.index + 1}</a></td>
						<td><a href="${pageContext.request.contextPath}/getHistoryData/${data.getQc_id()}/${pqcCREATOR}">${data.getManufactureOrder()}</a></td>
						<td><a href="${pageContext.request.contextPath}/getHistoryData/${data.getQc_id()}/${pqcCREATOR}">${data.getManufactureNo()}</a></td>
						<td><a href="${pageContext.request.contextPath}/getHistoryData/${data.getQc_id()}/${pqcCREATOR}">${data.getPartNumber()}</a></td> 
						<td><a href="${pageContext.request.contextPath}/getHistoryData/${data.getQc_id()}/${pqcCREATOR}">${data.getItemName()}</a></td> 
						<td><a href="${pageContext.request.contextPath}/getHistoryData/${data.getQc_id()}/${pqcCREATOR}">${data.getCheckDate()}</a></td> 
				      </tr>
				    </c:forEach>
			  
			  </table>
			  <c:if test="${pageInfo.hasPreviousPage}">
    <a href="showHistory/pageNum=${pageInfo.prePage}&pageSize=${pageSize}">上一頁</a>
</c:if>
<c:if test="${pageInfo.hasNextPage}">
    <a href="showHistory/pageNum=${pageInfo.nextPage}&pageSize=${pageSize}">下一頁</a>
</c:if>
			  <!-- Pagination links in spring mvc. -->	
			   <nav>		  
			  <ul class="pagination mb-5">
			  <!--Previous Icon
			  <c:if test="${not empty PrevPage}">-->
                <li class="page-item">
                    <a class="page-link" href="${pageContext.request.contextPath}/showHistory/${PrevPage}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                        <span class="sr-only">Previous</span>
                    </a>
                </li>
			 <!-- </c:if>-->
			  <c:forEach var="num" begin="${beginPageNum}" end="${endPageNum}" step="1">
			  	<li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/showHistory/${num}">${num}</a></li>			  	
				</c:forEach>
				<!--Next Icon
				<c:if test="${not empty NextPage}">-->
                <li class="page-item">
                    <a class="page-link" href="${pageContext.request.contextPath}/showHistory/${NextPage}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                        <span class="sr-only">Next</span>
                    </a>
                </li>
				<!--</c:if>-->
			  </ul>
			  </nav>
	    </div>	
</c:when>
<c:otherwise>
<div style="font-size: 30px;text-align: center;">
  <p>查無資料</p>
</div>
</c:otherwise>	
</c:choose>	
	</body>
</html>