<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="java.util.*"%>
<%
   /*避免瀏覽器因cache而無法看到最新資料*/
   response.setHeader("Pragma","No-Cache");
   response.setHeader("Cache-Control","No-Cache");
   response.setDateHeader("Expires", 0);
   %>
<!DOCTYPE html>
<html>
<head>
 <meta charset="utf-8">
<title></title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style>
.DIV1{
margin: 0 auto;
float:left;
clear：both;
}
.DIV2{
float:left;
}
</style>
</head>
<body>
<div style="width:100%;text-align:center">
               <form class="login100-form validate-form flex-sb flex-w" action="${pageContext.request.contextPath}/getQCRecord/${pqcCREATOR}" method="post">
                <div >
                     <label>製令單別:</label>
                     <div>
                     <select name='manufactureOrder'>  
                      <option value="" selected>選擇製令單別</option> 
                       <c:forEach items="${allQCMO}" var="MOitem">   
 						
            						<option value="${MOitem.getManufactureOrder()}">${MOitem.getManufactureOrder()}</option>       						
    						</c:forEach>
       					 </select> 
                     </div>
                  </div> 
                 
                   <div >
                     <label>製令單號:</label>
                     <div>
                     <select name='manufactureNo'>  
                      <option value="" selected>選擇製令單號</option> 
                       <c:forEach items="${allQCMN}" var="MNitem">         							
            						<option value="${MNitem.getManufactureNo()}">${MNitem.getManufactureNo()}</option>       						
    						</c:forEach>
       					 </select> 
                     </div>
                  </div>  
				  
				  <br>
				<!--    <label>檢驗時間(起):</label><input type = "date" name = "beginDate" value = "${todaysDate}" />
				  
				  
				  <label>檢驗時間(迄):</label><input type = "date" name = "endDate" value = "${todaysDate}" />-->
                  
				  <br>
				  <br>
                  <div >
                     <button >
                     確定
                     </button>
                  </div>
               </form>
      </div>
</body>
</html>