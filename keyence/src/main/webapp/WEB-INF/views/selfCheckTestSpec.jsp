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
    font-size: 8px;
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
 <form id="myForm" onsubmit="return validateForm() action="${pageContext.request.contextPath}/insFinishedSpec/${qc_id}/${pqcCREATOR}" method="post">
    <table style="width:100%; text-align: center; border: 1px solid black;" rules="all">
        <tbody>
            <tr>
                <td colspan=29>久裕興業科技股份有限公司工程檢驗說明書</td>
            </tr>
            <tr>
                <td colspan=2 rowspan=2 width="5%">品名</td>
                <td colspan=16 rowspan=2  width="50%" ><c:out value="${finishedPrimumQC.JYT012a003}" default="" /></td>
				<input type="hidden" value="${finishedPrimumQC.JYT012a003}" name="JYT012a003">
				<td rowspan=2 width="5%">規格</td>
                <td colspan=10 rowspan=2  width="50%"><c:out value="${finishedPrimumQC.UDF02}" default="" /></td>                
				<input type="hidden" value="${finishedPrimumQC.UDF02}" name="UDF02">
            </tr>
			<tr>
			</tr>
            
			<tr>
				<td colspan=2 width="5%">圖號</td>
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
				<td colspan=2 width="5%">版次</td>
			</tr>
			<tr>
				<td colspan=2 width="5%">料號</td>
				<td colspan=4 ><c:out value="${finishedPrimumQC.JYT012a005}" default="" /></td>
				<input type="hidden" value="${finishedPrimumQC.JYT012a005}" name="JYT012a005">
				<td colspan=3>客戶代號</td>
				<td colspan=9><c:out value="${finishedPrimumQC.getCustomerCode()}" /></td>
				<td colspan=2>首件人員</td>
				<td colspan=2><c:out value="${finishedPrimumQC.getFirstItemStaff()}" /></td>
				<td colspan=2>機台編號</td>
				<td colspan=3><c:out value="${finishedPrimumQC.getMachineNumber()}" /></td>
				<td colspan=2><c:out value="${finishedPrimumQC.JYT012a006}" default="" /></td>
				<input type="hidden" value="${finishedPrimumQC.JYT012a006}" name="JYT012a006">
				<input type="hidden" value="${finishedPrimumQC.JYT012a002}" name="JYT012a002">
				<input type="hidden" value="${finishedPrimumQC.UDF01}" name="UDF01">
			</tr>   
            <tr>
				<td colspan="29" ><img style="width: 50%;" src="<c:out value="${finishedPrimumQC.fullImageName}" default="" />" ></td>
            </tr>
		</tbody>
	</table>
	<table id="inputTable" style="width:100%; text-align: center; border: 1px solid black;" rules="all" >
		<tbody>
			<tr>		   
				<td align="center" colspan=1 width="5%">項次</td>
				<td align="center" width="30%">規格</td>
				<td align="center" width="10%">檢驗量具</td>
				<td align="center" colspan="6" width="40%">實測狀況</td>
			</tr>
			<tr>
			
			</tr>
			<c:forEach items="${finishedIpqc}" var="ipqcItem" varStatus="status"> 
			 <tr>			 
				<td align="center" colspan=1>${ipqcItem.getJYT012b003()}</td>
				<input type="hidden" value="${ipqcItem.getJYT012b003()}" name="ipqcRecords[${status.index}].itemSN">
				<td align="left"  style="overflow-wrap: break-word;">${ipqcItem.getJYT012b005()} : ${ipqcItem.getJYT012b007()} ${ipqcItem.getJYT012b006()} (${ipqcItem.getJYT012b008()}/${ipqcItem.getJYT012b009()})</td>				
				<td align="center" style="overflow-wrap: break-word;">${ipqcItem.getJYT012b010()}</td>				
				<td align="center" width="5%">${ipqcItem.getIpqc1()}</td>	
				<td align="center" width="5%">${ipqcItem.getIpqc2()}</td>	
				<td align="center" width="5%">${ipqcItem.getIpqc3()}</td>	
				<c:choose>
					<c:when test="${ipqcItem.getUDF03()=='1'}">
						<c:choose>
							<c:when test="${ipqcItem.getJYT012b008() != ''} && ${ipqcItem.getJYT012b009() != ''}">
								<td width="10%"><input type="number" onblur="MinMaxInput(this, ${ipqcItem.getMin()}, ${ipqcItem.getMax()})" id="ipqc1${status.index}" class="ipqc1" name="ipqcRecords[${status.index}].ipqc1" step="any" required> </td>
							</c:when>
							<c:when test="${ipqcItem.getJYT012b008() != ''}">
								<td width="10%"><input type="number" onblur="MinMaxInput(this, ${ipqcItem.getMin()}, ${ipqcItem.getMax()})" id="ipqc1${status.index}" class="ipqc1" name="ipqcRecords[${status.index}].ipqc1" step="any" required> </td>
							</c:when>
							<c:when test="${ipqcItem.getJYT012b009() != ''}">
								<td width="10%"><input type="number" onblur="MinInput(this, ${ipqcItem.getMin()}, ${ipqcItem.getMax()})" id="ipqc1${status.index}" class="ipqc1" name="ipqcRecords[${status.index}].ipqc1" step="any" required> </td>
							</c:when>						
						</c:choose>				
					</c:when>
					<c:otherwise>
						<td width="10%"><input type="text" id="ipqc1${status.index}" class="ipqc1" name="ipqcRecords[${status.index}].ipqc1" required> </td>
					</c:otherwise>	
				</c:choose>
				
				<c:choose>
					<c:when test="${ipqcItem.getUDF03()=='1'}">
						<c:choose>
							<c:when test="${ipqcItem.getJYT012b008() != ''} && ${ipqcItem.getJYT012b009() != ''}">
								<td width="10%"><input type="number" onblur="MinMaxInput(this, ${ipqcItem.getMin()}, ${ipqcItem.getMax()})" id="ipqc2${status.index}" class="ipqc2" name="ipqcRecords[${status.index}].ipqc2" step="any" required> </td>
							</c:when>
							<c:when test="${ipqcItem.getJYT012b008() != ''}">
								<td width="10%"><input type="number" onblur="MinMaxInput(this, ${ipqcItem.getMin()}, ${ipqcItem.getMax()})" id="ipqc2${status.index}" class="ipqc2" name="ipqcRecords[${status.index}].ipqc2" step="any" required> </td>
							</c:when>
							<c:when test="${ipqcItem.getJYT012b009() != ''}">
								<td width="10%"><input type="number" onblur="MinInput(this, ${ipqcItem.getMin()}, ${ipqcItem.getMax()})" id="ipqc2${status.index}" class="ipqc2" name="ipqcRecords[${status.index}].ipqc2" step="any" required> </td>
							</c:when>						
						</c:choose>				
					</c:when>
					<c:otherwise>
						<td width="10%"><input type="text" id="ipqc2${status.index}" class="ipqc2" name="ipqcRecords[${status.index}].ipqc2" required> </td>
					</c:otherwise>	
				</c:choose>
				
				<c:choose>
					<c:when test="${ipqcItem.getUDF03()=='1'}">
						<c:choose>
							<c:when test="${ipqcItem.getJYT012b008() != ''} && ${ipqcItem.getJYT012b009() != ''}">
								<td width="10%"><input type="number" onblur="MinMaxInput(this, ${ipqcItem.getMin()}, ${ipqcItem.getMax()})" id="ipqc3${status.index}" class="ipqc3" name="ipqcRecords[${status.index}].ipqc3" step="any" required> </td>
							</c:when>
							<c:when test="${ipqcItem.getJYT012b008() != ''}">
								<td width="10%"><input type="number" onblur="MinMaxInput(this, ${ipqcItem.getMin()}, ${ipqcItem.getMax()})" id="ipqc3${status.index}" class="ipqc3" name="ipqcRecords[${status.index}].ipqc3" step="any" required> </td>
							</c:when>
							<c:when test="${ipqcItem.getJYT012b009() != ''}">
								<td width="10%"><input type="number" onblur="MinInput(this, ${ipqcItem.getMin()}, ${ipqcItem.getMax()})" id="ipqc3${status.index}" class="ipqc3" name="ipqcRecords[${status.index}].ipqc3" step="any" required> </td>
							</c:when>						
						</c:choose>				
					</c:when>
					<c:otherwise>
						<td width="10%"><input type="text" id="ipqc3${status.index}" class="ipqc3" name="ipqcRecords[${status.index}].ipqc3" required> </td>
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
            <td><input type="text" name="inspectors" required></td>
        </tr>
        <tr>
            <td>處置狀況</td>
            <td colspan="5"><input type="text" name="handleResult" align="left" style="width:90%;" /></td>
        </tr>
    </table>  
	<br>
	<input type="hidden" id="myNumberInput" name="myNumberInput" value='1'>
	<div align="center">
	<input type="button" name="button" value="確定" /> 
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

function validateForm() {
    var table = document.getElementById("inputTable");

    // 验证第一列的每个输入框都有值
    var firstColumnInputs = table.querySelectorAll("tr td:first-child input");
    for (var i = 0; i < firstColumnInputs.length; i++) {
        if (firstColumnInputs[i].value.trim() === "") {
            alert("第一列的每个输入框都必须有值！");
            return false;
        }
    }

    // 验证其他列的输入框是否符合要求
    for (var col = 1; col < table.rows[0].cells.length; col++) {
        var firstInput = table.rows[0].cells[col].querySelector("input");

        if (firstInput.value.trim() !== "") {
            // 如果第一行的输入框有值，则该列的每个输入框都必须有值
            var columnInputs = table.querySelectorAll("tr td:nth-child(" + (col + 1) + ") input");
            for (var j = 0; j < columnInputs.length; j++) {
                if (columnInputs[j].value.trim() === "") {
                    alert("第 " + (col + 1) + " 列的每个输入框都必须有值！");
                    return false;
                }
            }
        } else {
            // 如果第一行的输入框没有值，则同一列的任何一个输入框都不能有值
            var columnInputs = table.querySelectorAll("tr td:nth-child(" + (col + 1) + ") input");
            for (var j = 0; j < columnInputs.length; j++) {
                if (columnInputs[j].value.trim() !== "") {
                    alert("第 " + (col + 1) + " 列的输入框不能有值！");
                    return false;
                }
            }
        }
    }

    var text = "實測狀況有未符合上下限值，你確定要儲存？";
    if (myNumberInput.value === '1') {
        if (confirm(text) == true) {
            $("#myForm").submit();
        } else {
            return false;
        }
    } else {
        $("#myForm").submit();
    }

    return true;
}

</script>
</html>