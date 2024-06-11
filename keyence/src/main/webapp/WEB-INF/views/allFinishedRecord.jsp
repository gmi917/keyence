<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="//unpkg.com/layui@2.7.6/dist/css/layui.css">
<style>	
	th {
	background-color: #359f6a; /* MediumSeaGreen */
	color: #fff;
	font-weight: bold
}
</style>
</head>
<body>
<table class="layui-table" lay-even lay-skin="nob">
				<thead>
					<tr>
						
						<th></th>
						<th>製令單別</th>
						<th>製令單號</th>
						<th>料號</th>
						<th>品名</th>
						<th>自主檢查日期</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${allQCRecord}" var="recordItem" varStatus="index">
					<c:if test="${index.index%2==0}">
					<tr>						
						<td><a href="${pageContext.request.contextPath}/getFinishedRecordData/${recordItem.getQc_id()}/${pqcCREATOR}">${index.index + 1}</a></td>
						<td>${recordItem.getManufactureOrder()}</td>
						<td>${recordItem.getManufactureNo()}</td>
						<td>${recordItem.getPartNumber()}</td>
						<td>${recordItem.getItemName()}</td>
						<td>${recordItem.getCheckDate()}</td>							
					</tr>
					</c:if>
					<c:if test="${index.index%2!=0}">
					<tr>						
						<td><a href="${pageContext.request.contextPath}/getFinishedRecordData/${recordItem.getQc_id()}/${pqcCREATOR}">${index.index + 1}</a></td>
						<td>${recordItem.getManufactureOrder()}</td>
						<td>${recordItem.getManufactureNo()}</td>
						<td>${recordItem.getPartNumber()}</td>
						<td>${recordItem.getItemName()}</td>
						<td>${recordItem.getCheckDate()}</td>						
					</tr>
					</c:if>
					</c:forEach>
				</tbody>
			</table>
</body>
</html>
