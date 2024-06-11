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
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style>
#juzhong{width:320px;height:100px; line-height:100px; text-align:center; border:1px solid #F00}
</style>
</head>
<body>
<div style="width:100%;text-align:center">

               <form class="login100-form validate-form flex-sb flex-w" action="${pageContext.request.contextPath}/allMONO/${ipqcCREATOR}" method="post">
                <div class="juzhong">
                     <label>製令狀態碼:</label>
                     <div>
                     <select name='MOStateCode'>  
                      <option value="" selected>選擇製令狀態碼</option> 
                        <option value="1">未生產</option>
						<option value="2">已發料</option>
						<option value="3">生產中</option>
						<!-- <option value="Y">已完工</option>
						<option value="y">指定完工</option> -->
       				 </select> 
                     </div>
                     <br>
                     <label>製程代號:</label>
                     <div>
                     	<select name="processCode">
                     	<option value="" selected>選擇製程代號</option>
                     		<c:forEach items="${allProcessCode}" var="processCode">
                     			<option value="${processCode.processCode}">${processCode.processName}</option>
  							</c:forEach>
						</select>
                     
                     </div>
                  </div> 
                  <br>               
                  <br>
                  <div class="juzhong">
                     <button  onclick="return submitForm()">
                     確定
                     </button>
                  </div>
               </form>
			    <script>
function submitForm() {
  var MOStateCode = document.getElementsByName("MOStateCode")[0].value;
  if (MOStateCode === "") {
    alert("請選擇製令狀態碼");
    return false;
  } else {
    return true;
  }
}
</script>
      </div>
</body>
</html>