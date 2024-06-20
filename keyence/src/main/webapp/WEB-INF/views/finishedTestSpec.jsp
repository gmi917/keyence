<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
 <meta charset="UTF-8">
 <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<title></title>
<style>
html, body {
    font-size: 16px;
}

.error{
	border:2px solid red;
	text-align: center;
}

.succeed{
	border:2px solid blue;
	text-align: center;
}    
</style>

<body>
<div style="width: 95%; margin:auto;">
 <form id="myForm" action="${pageContext.request.contextPath}/updFinishedSpec/${qc_id}/${pqcCREATOR}" method="post">
    <table style="width:100%; text-align: center; border: 1px solid black;" rules="all">
        <tbody>
            <tr>
                <td colspan=29>久裕興業科技股份有限公司工程檢驗說明書</td>
            </tr>
          <!--   <tr>
                <td colspan=2 rowspan=2 width="5%">品名</td>
                <td colspan=16 rowspan=2  width="50%" ><c:out value="${finishedPrimumQC.JYT012a003}" default="" /></td>
				<input type="hidden" value="${finishedPrimumQC.JYT012a003}" name="JYT012a003">
				<td rowspan=2 width="5%">規格</td>
                <td colspan=10 rowspan=2  width="50%"><c:out value="${finishedPrimumQC.UDF02}" default="" /></td>                
				<input type="hidden" value="${finishedPrimumQC.UDF02}" name="UDF02">
            </tr>--> 
			<tr>
			</tr>
            
			<tr>
			<td colspan=2 width="5%">單位:車削組</td>
			<td colspan=2 width="5%">設備名稱: </td>
			<td colspan=2 width="15%">生產品目/工作內容:
			<c:out value="${finishedQC.JYT012a003}" /></td>
			<td colspan=2 width="8%"><c:out value="${todaysDate.getYear()}" />年<c:out value="${todaysDate.getMonthValue()}" />月<c:out value="${todaysDate.getDayOfMonth()}" />日</td>
			<!--  	<td colspan=2 width="5%">圖號</td>
				<td colspan=4 width="13%"><c:out value="${finishedPrimumQC.JYT012a004}" /></td>
				<input type="hidden" value="${finishedPrimumQC.JYT012a004}" name="JYT012a004">
				<td colspan=3 width="8%">製令編號</td>
				<td colspan=9 width="16%"><c:out value="${finishedPrimumQC.getManufactureOrder()}    ${finishedPrimumQC.getManufactureNo()}" /></td>
				<input type="hidden" value="${finishedPrimumQC.getManufactureOrder()}" name="manufactureOrder">
				<input type="hidden" value="${finishedPrimumQC.getManufactureNo()}" name="manufactureNo">
				<td colspan=2 width="8%">首件日期</td>
				<td colspan=2 width="13%"><c:out value="${finishedPrimumQC.getFirstItemDate()}" default="" /></td>
				<td colspan=2 width="8%">預計產量</td>
				<td colspan=3 width="13%"><c:out value="${finishedPrimumQC.getPredictQty()}" default="" /></td>
				<td colspan=2 width="5%">版次</td>-->
			</tr>
			<tr>
			<td colspan=2 width="5%">圖號:
		<c:out value="${finishedQC.getJYT012a004()}" /></td>
			<!--  	<td colspan=2 width="5%">料號</td>
				<td colspan=4 ><c:out value="${finishedPrimumQC.JYT012a005}" default="" /></td>
				<input type="hidden" value="${finishedPrimumQC.JYT012a005}" name="JYT012a005">
				<td colspan=3></td>
				<td colspan=9></td>
				<td colspan=2>首件人員</td>
				<td colspan=2><c:out value="${finishedPrimumQC.getFirstItemStaff()}" /></td>
				<td colspan=2>機台編號</td>
				<td colspan=3><c:out value="${finishedPrimumQC.getMachineNumber()}" /></td>
				<td colspan=2><c:out value="${finishedPrimumQC.JYT012a006}" default="" /></td>
				<input type="hidden" value="${finishedPrimumQC.JYT012a006}" name="JYT012a006">
				<input type="hidden" value="${finishedPrimumQC.JYT012a002}" name="JYT012a002">
				<input type="hidden" value="${finishedPrimumQC.UDF01}" name="UDF01">-->
			</tr>   
            <tr>
				<td colspan="29" ><img style="width: 50%;" src="<c:out value="${finishedQC.fullImageName}" default="" />" ></td>
            </tr>
		</tbody>
	</table>
	<table style="width:100%; text-align: center; border: 1px solid black;" rules="all" >
		<tbody>
			<tr>		   
				<td align="center" colspan=1 width="5%">項次</td>
				<td align="center" width="30%">規格</td>
				<td align="center" width="10%">檢驗量具</td>
				<td align="center" colspan="6" width="40%">實測狀況</td>
			</tr>
			<tr>
			
			</tr>
			<c:forEach items="${finishedPqc}" var="pqcItem" varStatus="status"> 
			 <tr>			 
				<td align="center" colspan=1>${pqcItem.getJYT012b003()}</td>
				<input type="hidden" value="${pqcItem.getJYT012b003()}" name="ipqcRecords[${status.index}].itemSN">
				<td align="left"  style="overflow-wrap: break-word;">${pqcItem.getJYT012b005()} : ${pqcItem.getJYT012b007()} ${pqcItem.getJYT012b006()} (${pqcItem.getJYT012b008()}/${pqcItem.getJYT012b009()})</td>				
				<td align="center" style="overflow-wrap: break-word;">${pqcItem.getJYT012b010()}</td>				
				<td align="center" width="5%">${pqcItem.getIpqc1()}</td>	
				<td align="center" width="5%">${pqcItem.getIpqc2()}</td>	
				<td align="center" width="5%">${pqcItem.getIpqc3()}</td>	
				<c:choose>
					<c:when test="${pqcItem.getUDF03()=='1'}">
						<c:choose>
							<c:when test="${pqcItem.getJYT012b008() != ''} && ${pqcItem.getJYT012b009() != ''}">
								<td width="10%"><input type="number" value="${pqcItem.getPqc1()}" onblur="MinMaxInput(this, ${pqcItem.getMin()}, ${pqcItem.getMax()})" id="ipqc1${status.index}" class="ipqc1" name="ipqcRecords[${status.index}].ipqc1" step="any" required> </td>
							</c:when>
							<c:when test="${pqcItem.getJYT012b008() != ''}">
								<td width="10%"><input type="number" value="${pqcItem.getPqc1()}" onblur="MinMaxInput(this, ${pqcItem.getMin()}, ${pqcItem.getMax()})" id="ipqc1${status.index}" class="ipqc1" name="ipqcRecords[${status.index}].ipqc1" step="any" required> </td>
							</c:when>
							<c:when test="${pqcItem.getJYT012b009() != ''}">
								<td width="10%"><input type="number" value="${pqcItem.getPqc1()}" onblur="MinInput(this, ${pqcItem.getMin()}, ${pqcItem.getMax()})" id="ipqc1${status.index}" class="ipqc1" name="ipqcRecords[${status.index}].ipqc1" step="any" required> </td>
							</c:when>						
						</c:choose>				
					</c:when>
					<c:otherwise>
						<td width="10%"><input type="text" value="${pqcItem.getPqc1()}" onblur="TextInput(this)" id="ipqc1${status.index}" class="ipqc1" name="ipqcRecords[${status.index}].ipqc1" required> </td>
					</c:otherwise>	
				</c:choose>
				
				<c:choose>
					<c:when test="${pqcItem.getUDF03()=='1'}">
						<c:choose>
							<c:when test="${pqcItem.getJYT012b008() != ''} && ${pqcItem.getJYT012b009() != ''}">
								<td width="10%"><input type="number" value="${pqcItem.getPqc2()}" onblur="MinMaxInput(this, ${pqcItem.getMin()}, ${pqcItem.getMax()})" id="ipqc2${status.index}" class="ipqc2" name="ipqcRecords[${status.index}].ipqc2" step="any" required> </td>
							</c:when>
							<c:when test="${pqcItem.getJYT012b008() != ''}">
								<td width="10%"><input type="number" value="${pqcItem.getPqc2()}" onblur="MinMaxInput(this, ${pqcItem.getMin()}, ${pqcItem.getMax()})" id="ipqc2${status.index}" class="ipqc2" name="ipqcRecords[${status.index}].ipqc2" step="any" required> </td>
							</c:when>
							<c:when test="${pqcItem.getJYT012b009() != ''}">
								<td width="10%"><input type="number" value="${pqcItem.getPqc2()}" onblur="MinInput(this, ${pqcItem.getMin()}, ${pqcItem.getMax()})" id="ipqc2${status.index}" class="ipqc2" name="ipqcRecords[${status.index}].ipqc2" step="any" required> </td>
							</c:when>						
						</c:choose>				
					</c:when>
					<c:otherwise>
						<td width="10%"><input type="text" value="${pqcItem.getPqc2()}" onblur="TextInput(this)" id="ipqc2${status.index}" class="ipqc2" name="ipqcRecords[${status.index}].ipqc2" required> </td>
					</c:otherwise>	
				</c:choose>
				
				<c:choose>
					<c:when test="${pqcItem.getUDF03()=='1'}">
						<c:choose>
							<c:when test="${pqcItem.getJYT012b008() != ''} && ${pqcItem.getJYT012b009() != ''}">
								<td width="10%"><input type="number" value="${pqcItem.getPqc3()}" onblur="MinMaxInput(this, ${pqcItem.getMin()}, ${pqcItem.getMax()})" id="ipqc3${status.index}" class="ipqc3" name="ipqcRecords[${status.index}].ipqc3" step="any" required> </td>
							</c:when>
							<c:when test="${pqcItem.getJYT012b008() != ''}">
								<td width="10%"><input type="number" value="${pqcItem.getPqc3()}" onblur="MinMaxInput(this, ${pqcItem.getMin()}, ${pqcItem.getMax()})" id="ipqc3${status.index}" class="ipqc3" name="ipqcRecords[${status.index}].ipqc3" step="any" required> </td>
							</c:when>
							<c:when test="${pqcItem.getJYT012b009() != ''}">
								<td width="10%"><input type="number" value="${pqcItem.getPqc3()}" onblur="MinInput(this, ${pqcItem.getMin()}, ${pqcItem.getMax()})" id="ipqc3${status.index}" class="ipqc3" name="ipqcRecords[${status.index}].ipqc3" step="any" required> </td>
							</c:when>						
						</c:choose>				
					</c:when>
					<c:otherwise>
						<td width="10%"><input type="text" value="${pqcItem.getPqc3()}" onblur="TextInput(this)" id="ipqc3${status.index}" class="ipqc3" name="ipqcRecords[${status.index}].ipqc3" required> </td>
					</c:otherwise>	
				</c:choose>				
			 </tr>				
    		</c:forEach>							
		</tbody>
	</table>       		    
    <table style="width:100%; margin-bottom: 0px; text-align: center; border: 1px solid black;" rules="all">
        <tr>
            <td rowspan="2" width="10%">不良原因敘述</td>
            <td rowspan="2" width="30%">
			<div>
                     <select name='badReasons' style="width:300px;">  
                      <option value="" selected>請選擇不良原因敘述</option> 
                       <c:forEach items="${badReasons}" var="badReasonsitem">       						
            						<option value="${badReasonsitem.getMH002()}">${badReasonsitem.getMH002()}</option>       						
    						</c:forEach>
       					 </select> 
                     </div></td>
            <td rowspan="2" width="5%">綜合判定</td>
            <td rowspan="2" width="20%"></td>
            <td width="5%">主管</td>
            <td width="15%"><input type="text" name="supervisor" required></td>
        </tr>
        <tr>
            <td>檢驗</td>
            <td><input type="text" id="inspectors" name="inspectors" required></td>
        </tr>
        <tr>
            <td>處置狀況</td>
            <td colspan="5"><input type="text" name="handleResult" align="left" style="width:90%;" /></td>
        </tr>
    </table>  
	<br>
	<input type="hidden" id="myNumberInput" name="myNumberInput" value='1'>
	<div align="center">
	<input type="button" onclick="check()" name="button" value="確定" /> 
	</div>
	 </form>
	 </div>
</body>
<script>
var myNumberInput = document.getElementById("myNumberInput");
function MinMaxInput(element, min, max){
 $('#myForm input[class="ipqc1"]').blur(function(){
	if(!$(element).val()){
	    $(element).addClass("error");
		$(element).removeClass("succeed"); 
		myNumberInput.value = '1';
	}else{
		if($(element).val()>= Number(min) && $(element).val()<= Number(max)){
			myNumberInput.value = '0';
			$(element).addClass("succeed");
			$(element).removeClass("error");		
		}else{
			myNumberInput.value = '1';
			$(element).addClass("error");
			$(element).removeClass("succeed");			
		}
	}
 });
	
 $('#myForm input[class="ipqc2"]').blur(function(){
	if(!$(element).val()){
		myNumberInput.value = '1';
        $(element).addClass("error");
		$(element).removeClass("succeed");  			
	}else{
		if($(element).val()>= Number(min) && $(element).val()<= Number(max)){
			myNumberInput.value = '0';
			$(element).addClass("succeed");
			$(element).removeClass("error");		
		}else{
			myNumberInput.value = '1';
			$(element).addClass("error");
			$(element).removeClass("succeed");			
		}
	}
 });

 $('#myForm input[class="ipqc3"]').blur(function(){
	if(!$(element).val()){
		myNumberInput.value = '1';
        $(element).addClass("error");
		$(element).removeClass("succeed");  		
	}else{
		if($(element).val()>= Number(min) && $(element).val()<= Number(max)){
			myNumberInput.value = '0';
			$(element).addClass("succeed");
			$(element).removeClass("error");		
		}else{
			myNumberInput.value = '1';
			$(element).addClass("error");
			$(element).removeClass("succeed");		
		}
	}
 });
}

function MinInput(element, min){
 $('#myForm input[class="ipqc1"]').blur(function(){
	if(!$(element).val()){
		myNumberInput.value = '1';
        $(element).addClass("error");
		$(element).removeClass("succeed");  		
	}else{
		if($(element).val()>= Number(min)){
			myNumberInput.value = '0';
			$(element).addClass("succeed");
			$(element).removeClass("error");		
		}else{
			myNumberInput.value = '1';
			$(element).addClass("error");
			$(element).removeClass("succeed");
		}
	}
 });
	
 $('#myForm input[class="ipqc2"]').blur(function(){
	if(!$(element).val()){
		myNumberInput.value = '1';
        $(element).addClass("error");
		$(element).removeClass("succeed");       
	}else{
		if($(element).val()>= Number(min)){
			myNumberInput.value = '0';
			$(element).addClass("succeed");
			$(element).removeClass("error");		
		}else{
			myNumberInput.value = '1';
			$(element).addClass("error");
			$(element).removeClass("succeed");
		}
	}
 });	
	
 $('#myForm input[class="ipqc3"]').blur(function(){
	if(!$(element).val()){
		myNumberInput.value = '1';
        $(element).addClass("error");
		$(element).removeClass("succeed");       
	}else{
		if($(element).val()>= Number(min)){
			myNumberInput.value = '0';
			$(element).addClass("succeed");
			$(element).removeClass("error");		
		}else{
			myNumberInput.value = '1';
			$(element).addClass("error");
			$(element).removeClass("succeed");
		}
	}
 });
}

function TextInput(element){
	$('#myForm input[class="ipqc1"]').blur(function(){
		if($(element).val()!== ""){
			myNumberInput.value = '0';
			$(element).addClass("succeed");
			$(element).removeClass("error"); 		
		}else{			
				myNumberInput.value = '1';
				$(element).addClass("error");
				$(element).removeClass("succeed");
			}		
	 });
		
	 $('#myForm input[class="ipqc2"]').blur(function(){
		if($(element).val()!== ""){
			myNumberInput.value = '0';
			$(element).addClass("succeed");
			$(element).removeClass("error");      
		}else{			
				myNumberInput.value = '1';
				$(element).addClass("error");
				$(element).removeClass("succeed");
			}		
	 });	
		
	 $('#myForm input[class="ipqc3"]').blur(function(){
		if($(element).val()!== ""){
			myNumberInput.value = '0';
			$(element).addClass("succeed");
			$(element).removeClass("error");       
		}else{			
				myNumberInput.value = '1';
				$(element).addClass("error");
				$(element).removeClass("succeed");
			}		
	 });
}

function check() {
	try {
	var inspectorsInput = document.getElementById('inspectors');
	if (inspectorsInput.value === '') {
		throw new Error('檢驗者欄位必填');
	    event.preventDefault(); // 阻止表單提交
	}
	let text = "實測狀況有未符合上下限值,你確定要儲存?";
	if(myNumberInput.value=='1'){
		if (confirm(text) == true) {
			$("#myForm").submit();
		} else {
			return false;
		}   
    }else{
		$("#myForm").submit(); 
	}
	} catch (error) {
	    alert(error);
	  }
}
</script>
</html>