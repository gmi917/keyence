<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>	
<script>
function validateAndSubmit() {
	try {
	var isValid = true;
	 
    // 获取所有具有类名 "ipqc1" 的输入框
    var ipqc1Inputs = document.getElementsByClassName("ipqc1");    
    // 遍历每个 ipqc1 输入框进行验证
    //for (var i = 0; i < ipqc1Inputs.length; i++) {
    var input = ipqc1Inputs[0];
    if(input.value.trim()==''){    	
    	isValid = false;
    	 throw new Error("第一行資料全部必填");
    }else{
    	// 获取当前行的索引
        var rowIndex = input.id.substring(5);
        // 获取第一列的输入框
        var firstColumnInput = document.getElementById("ipqc1" + rowIndex);
        // 检查第一列的输入框是否有值
        if (firstColumnInput.value.trim() !== '') {
        	// 获取当前列的所有输入框
            var currentColumnInputs = document.querySelectorAll('[id^="ipqc"][id$="' + rowIndex + '"]');
            // 遍历当前列的所有输入框，除第一列的输入框外
            for (var j = 1; j < ipqc1Inputs.length; j++) {
            	var currentInput = ipqc1Inputs[j];
                // 检查当前输入框是否为空
                if (currentInput.value.trim() === '') {
                	throw new Error("第一行資料全部不能有空白");
                	isValid = false;
                	break;
                } else {
                	// 清除错误提示信息
                	currentInput.setCustomValidity("");
                }
             }
          }
      }
    
    // 获取所有具有类名 "ipqc2" 的输入框
    var ipqc2Inputs = document.getElementsByClassName("ipqc2");    
    // 遍历每个 ipqc1 输入框进行验证
    //for (var i = 0; i < ipqc1Inputs.length; i++) {
    var input2 = ipqc2Inputs[0];
    if(input2.value.trim()!==''){
    	// 获取当前行的索引
        var rowIndex2 = input2.id.substring(5);
        // 获取第一列的输入框
        var firstColumnInput = document.getElementById("ipqc2" + rowIndex2);
        // 检查第一列的输入框是否有值
        if (firstColumnInput.value.trim() !== '') {
        	// 获取当前列的所有输入框
            var currentColumnInputs = document.querySelectorAll('[id^="ipqc"][id$="' + rowIndex2 + '"]');
            // 遍历当前列的所有输入框，除第一列的输入框外
            for (var j = 1; j < ipqc2Inputs.length; j++) {
            	var currentInput2 = ipqc2Inputs[j];
                // 检查当前输入框是否为空
                if (currentInput2.value.trim() === '') {
                	throw new Error("第二行第一個輸入框有輸入資料;因此整行全部要填寫完");
                	isValid = false;
                	break;
                } else {
                	// 清除错误提示信息
                	currentInput2.setCustomValidity("");
                }
             }
         }    	
      }
    
    // 获取所有具有类名 "ipqc3" 的输入框
    var ipqc3Inputs = document.getElementsByClassName("ipqc3");    
    // 遍历每个 ipqc1 输入框进行验证
    //for (var i = 0; i < ipqc1Inputs.length; i++) {
    var input3 = ipqc3Inputs[0];
    if(input3.value.trim()!==''){
    	// 获取当前行的索引
        var rowIndex3 = input3.id.substring(5);
        // 获取第一列的输入框
        var firstColumnInput = document.getElementById("ipqc3" + rowIndex3);
        // 检查第一列的输入框是否有值
        if (firstColumnInput.value.trim() !== '') {
        	// 获取当前列的所有输入框
            var currentColumnInputs = document.querySelectorAll('[id^="ipqc"][id$="' + rowIndex3 + '"]');
            // 遍历当前列的所有输入框，除第一列的输入框外
            for (var j = 1; j < ipqc3Inputs.length; j++) {
            	var currentInput3 = ipqc3Inputs[j];
                // 检查当前输入框是否为空
                if (currentInput3.value.trim() === '') {
                	throw new Error("第三行第一個輸入框有輸入資料;因此整行全部要填寫完");
                	isValid = false;
                	break;
                } else {
                	// 清除错误提示信息
                	currentInput3.setCustomValidity("");
                }
             }
         }    	
      }

    let text = "實測狀況有未符合上下限值,你確定要儲存?";
  	if(myNumberInput.value=='1'){
  		if (confirm(text) == true) {
  			isValid = true;
  		} else {
  			return false;
  		}   
      //}else{
  		//$("#myForm").submit(); 
  	}  
    
    // 提交表单
    if (isValid) {
      document.getElementById("myForm").submit();
    }
} catch (error) {
    alert(error);
    // 在這裡可以進行相應的錯誤處理
  }
  }
</script>
</head>

<style>
.error {
      border: 2px solid red;
    }
.success {
  border: 2px solid blue;
}
  
</style>


<body>
<div style="width: 95%; margin:auto;">
 <form id="myForm"  action="${pageContext.request.contextPath}/updSpec/${staffData.qc_id}/${ipqcCREATOR}" method="post">
    <table style="width:100%; text-align: center; border: 1px solid black;" rules="all">
       
            <tr>
                <td colspan=29>久裕興業科技股份有限公司工程檢驗說明書</td>
            </tr>
           <!--   <tr>
                <td colspan=2 rowspan=2 width="5%">品名</td>
                <td colspan=16 rowspan=2  width="50%" ><c:out value="${primumQC.JYT012a003}" default="" /></td>
				<input type="hidden" value="${primumQC.JYT012a003}" name="JYT012a003">
				<td rowspan=2 width="5%">規格</td>
                <td colspan=10 rowspan=2  width="50%"><c:out value="${primumQC.UDF02}" default="" /></td>                
				<input type="hidden" value="${primumQC.UDF02}" name="UDF02">
            </tr>-->
			<tr>
			</tr>
            
			<tr>
			<td colspan=2 width="5%">單位:車削組</td>
			<td colspan=2 width="5%">設備名稱: </td>
			<td colspan=2 width="15%">生產品目/工作內容:
			<c:out value="${staffData.JYT012a003}" /></td>
			<td colspan=2 width="8%"><c:out value="${todaysDate.getYear()}" />年<c:out value="${todaysDate.getMonthValue()}" />月<c:out value="${todaysDate.getDayOfMonth()}" />日</td>
			<!-- 	<td colspan=2 width="5%">圖號</td>
				<td colspan=4 width="13%"><c:out value="${primumQC.JYT012a004}" /></td>
				<input type="hidden" value="${primumQC.JYT012a004}" name="JYT012a004">
				<td colspan=3 width="8%">製令編號</td>
				<td colspan=9 width="16%"><c:out value="${primumQC.getManufactureOrder()}    ${primumQC.getManufactureNo()}" /></td>
				<input type="hidden" value="${primumQC.getManufactureOrder()}" name="manufactureOrder">
				<input type="hidden" value="${primumQC.getManufactureNo()}" name="manufactureNo">
				<td colspan=2 width="8%">首件日期</td>
				<td colspan=2 width="13%"><input type = "date" name = "firstItemDate" value = "${todaysDate}" required></td>
				<td colspan=2 width="8%">預計產量</td>
				<td colspan=3 width="13%"><input type="number" id="predictQty" placeholder="請輸入預計產量" name="predictQty" required></td>
				<td colspan=2 width="5%">版次</td> -->
			</tr>
	<tr>
	<td colspan=2 width="5%">圖號:
		<c:out value="${staffData.getJYT012a004()}" /></td>
	<!--  	<td colspan=2 width="5%">圖號:
		<c:out value="${staffData.getJYT012a004()}" /></td>
		<td colspan=2 width="5%">料號</td>
				<td colspan=4 ><c:out value="${primumQC.JYT012a005}" default="" /></td>
				<input type="hidden" value="${primumQC.JYT012a005}" name="JYT012a005">
				<td colspan=3></td>
				<td colspan=9></td>
				<td colspan=2>首件人員</td>
				<td colspan=2><input type="text" id="firstItemStaff" placeholder="請輸入首件人員" name="firstItemStaff" required></td>
				<td colspan=2>機台編號</td>
				<td colspan=3><input type="text" id="machineNumber" placeholder="請輸入機台編號" name="machineNumber" ></td>
				<td colspan=2><c:out value="${primumQC.JYT012a006}" default="" /></td>
				<input type="hidden" value="${primumQC.JYT012a006}" name="JYT012a006">
				<input type="hidden" value="${primumQC.JYT012a002}" name="JYT012a002">
				<input type="hidden" value="${primumQC.UDF01}" name="UDF01">
			</tr> -->   
            <tr>            
				<td colspan="29" ><img style="width: 50%;" src="<c:out value="${staffData.fullImageName}" default="" />" ></td>
            </tr>
		</tbody>
	</table>
	<table style="width:100%; text-align: center; border: 1px solid black;" rules="all" >
		<!--<tbody>-->
			<tr>		   
				<td align="center" colspan=1>項次</td>
				<td align="center" width="30%">規格</td>
				<td align="center" width="15%">檢驗量具</td>
				<td align="center" colspan="6" width="50%">實測狀況</td>
			</tr>
			
			<c:forEach items="${finishedIpqc}" var="ipqcItem" varStatus="status"> 
			 <tr>			 
				<td align="center" colspan=1>${ipqcItem.getJYT012b003()}</td>
				<input type="hidden" value="${ipqcItem.getJYT012b003()}" name="ipqcRecords[${status.index}].itemSN">
				<td align="left">${ipqcItem.getJYT012b005()} : ${ipqcItem.getJYT012b007()} ${ipqcItem.getJYT012b006()} (${ipqcItem.getJYT012b008()}/${ipqcItem.getJYT012b009()})</td>
				<!--<input type="hidden" value="${ipqcItem.getJYT012b005()}" name="ipqcRecords[${status.index}].testItem">-->
				<td align="center" width="15%">${ipqcItem.getJYT012b010()}</td>
				<!--<input type="hidden" value="${ipqcItem.getJYT012b010()}" name="ipqcRecords[${status.index}].testTool">-->
				<c:choose>
					<c:when test="${ipqcItem.getUDF03()=='1'}">
						<c:choose>
							<c:when test="${ipqcItem.getJYT012b008() != ''} && ${ipqcItem.getJYT012b009() != ''}"> <!-- 有上下限值 -->
								<td width="16%"><input type="number"  value="${ipqcItem.getIpqc1()}" class="ipqc1" onblur="checkMinMaxRange(this, ${ipqcItem.getMin()}, ${ipqcItem.getMax()})" id="ipqc1${status.index}" name="ipqcRecords[${status.index}].ipqc1"  step="any"  onkeydown="moveToNextInputIpqc1(event, this)" /> </td>
							</c:when>
							<c:when test="${ipqcItem.getJYT012b008() != ''}"> <!-- 只有上限值 -->
								<td width="16%"><input type="number" value="${ipqcItem.getIpqc1()}" class="ipqc1" onblur="checkMinMaxRange(this, ${ipqcItem.getMin()}, ${ipqcItem.getMax()})" id="ipqc1${status.index}" name="ipqcRecords[${status.index}].ipqc1"  step="any" onkeydown="moveToNextInputIpqc1(event, this)" /> </td>
							</c:when>
							<c:when test="${ipqcItem.getJYT012b009() != ''}"> <!-- 只有下限值 -->
								<td width="16%"><input type="number" value="${ipqcItem.getIpqc1()}" class="ipqc1" onblur="checkMinRange(this, ${ipqcItem.getMin()})" id="ipqc1${status.index}" name="ipqcRecords[${status.index}].ipqc1" step="any"  onkeydown="moveToNextInputIpqc1(event, this)" /> </td>
							</c:when>						
						</c:choose>				
					</c:when>
					<c:otherwise>
						<td width="16%"><input type="text" value="${ipqcItem.getIpqc1()}" class="ipqc1" onblur="checkText(this)" id="ipqc1${status.index}" name="ipqcRecords[${status.index}].ipqc1"  onkeydown="moveToNextInputIpqc1(event, this)" /> </td>
					</c:otherwise>	
				</c:choose>
				
				<c:choose>
					<c:when test="${ipqcItem.getUDF03()=='1'}">
						<c:choose>
							<c:when test="${ipqcItem.getJYT012b008() != ''} && ${ipqcItem.getJYT012b009() != ''}">
								<td width="16%"><input type="number" value="${ipqcItem.getIpqc2()}" class="ipqc2" onblur="checkMinMaxRange(this, ${ipqcItem.getMin()}, ${ipqcItem.getMax()})" id="ipqc2${status.index}" name="ipqcRecords[${status.index}].ipqc2" step="any"  onkeydown="moveToNextInputIpqc2(event, this)" /> </td>
							</c:when>
							<c:when test="${ipqcItem.getJYT012b008() != ''}">
								<td width="16%"><input type="number" value="${ipqcItem.getIpqc2()}" class="ipqc2" onblur="checkMinMaxRange(this, ${ipqcItem.getMin()}, ${ipqcItem.getMax()})" id="ipqc2${status.index}" name="ipqcRecords[${status.index}].ipqc2" step="any"  onkeydown="moveToNextInputIpqc2(event, this)" /> </td>
							</c:when>
							<c:when test="${ipqcItem.getJYT012b009() != ''}">
								<td width="16%"><input type="number" value="${ipqcItem.getIpqc2()}" class="ipqc2" onblur="checkMinRange(this, ${ipqcItem.getMin()})" id="ipqc2${status.index}" name="ipqcRecords[${status.index}].ipqc2" step="any"  onkeydown="moveToNextInputIpqc2(event, this)" /> </td>
							</c:when>						
						</c:choose>				
					</c:when>
					<c:otherwise>
						<td width="16%"><input type="text" value="${ipqcItem.getIpqc2()}"  class="ipqc2" onblur="checkText(this)" id="ipqc2${status.index}" name="ipqcRecords[${status.index}].ipqc2"  onkeydown="moveToNextInputIpqc2(event, this)" /> </td>
					</c:otherwise>	
				</c:choose>
				
				<c:choose>
					<c:when test="${ipqcItem.getUDF03()=='1'}">
						<c:choose>
							<c:when test="${ipqcItem.getJYT012b008() != ''} && ${ipqcItem.getJYT012b009() != ''}">
								<td width="16%"><input type="number" value="${ipqcItem.getIpqc3()}" class="ipqc3" onblur="checkMinMaxRange(this, ${ipqcItem.getMin()}, ${ipqcItem.getMax()})" id="ipqc3${status.index}" name="ipqcRecords[${status.index}].ipqc3" step="any"  onkeydown="moveToNextInputIpqc3(event, this)" /> </td>
							</c:when>
							<c:when test="${ipqcItem.getJYT012b008() != ''}">
								<td width="16%"><input type="number" value="${ipqcItem.getIpqc3()}" class="ipqc3" onblur="checkMinMaxRange(this, ${ipqcItem.getMin()}, ${ipqcItem.getMax()})" id="ipqc3${status.index}" name="ipqcRecords[${status.index}].ipqc3" step="any"  onkeydown="moveToNextInputIpqc3(event, this)" /> </td>
							</c:when>
							<c:when test="${ipqcItem.getJYT012b009() != ''}">
								<td width="16%"><input type="number" value="${ipqcItem.getIpqc3()}" class="ipqc3" onblur="checkMinRange(this, ${ipqcItem.getMin()})" id="ipqc3${status.index}" name="ipqcRecords[${status.index}].ipqc3" step="any"  onkeydown="moveToNextInputIpqc3(event, this)" /> </td>
							</c:when>						
						</c:choose>				
					</c:when>
					<c:otherwise>
						<td width="16%"><input type="text" value="${ipqcItem.getIpqc3()}" class="ipqc3" onblur="checkText(this)" id="ipqc3${status.index}" name="ipqcRecords[${status.index}].ipqc3"  onkeydown="moveToNextInputIpqc3(event, this)" /> </td>
					</c:otherwise>	
				</c:choose>	
			 </tr>				
    		</c:forEach>									
	</table>       		    
	<br>
	<input type="hidden" id="myNumberInput" name="myNumberInput" value='1'>
	<div align="center">
	<!--<input type="button" onclick="check()" name="button" value="確定" /> -->
	<input type="button" onclick="validateAndSubmit()" name="button" value="確定" /> 
	</div>
	 </form>
	 </div>
</body>
<script>
function moveToNextInputIpqc1(event, currentInput) {
    if (event.key === "Enter") {
      var allInputs = document.getElementsByClassName("ipqc1");
      var currentIndex = Array.prototype.indexOf.call(allInputs, currentInput);
      var nextInput = allInputs[currentIndex + 1];

      if (nextInput) {
        event.preventDefault();
        nextInput.focus();
      }
    }
  }
  function moveToNextInputIpqc2(event, currentInput) {
    if (event.key === "Enter") {
      var allInputs = document.getElementsByClassName("ipqc2");
      var currentIndex = Array.prototype.indexOf.call(allInputs, currentInput);
      var nextInput = allInputs[currentIndex + 1]; // Assumes 3x3 matrix

      if (nextInput) {
        event.preventDefault();
        nextInput.focus();
      }
    }
  }
  function moveToNextInputIpqc3(event, currentInput) {
    if (event.key === "Enter") {
      var allInputs = document.getElementsByClassName("ipqc3");
      var currentIndex = Array.prototype.indexOf.call(allInputs, currentInput);
      var nextInput = allInputs[currentIndex + 1]; // Assumes 3x3 matrix

      if (nextInput) {
        event.preventDefault();
        nextInput.focus();
      }
    }
  }
  
  document.addEventListener("keydown", function(event) {
        if (event.key === "Enter") {
            event.preventDefault();
        }
    });
  
  function checkInput(inputElement) {
	  var inputs = document.querySelectorAll('input[type="text"]');
	  var startIndex = Array.from(inputs).indexOf(inputElement);
	  var hasValue = inputElement.value.trim() !== '';

	  if (hasValue) {
	    var columnSize = 3; // 每列输入框的数量

	    var columnIndex = startIndex % columnSize; // 计算当前输入框在所在列中的索引

	    // 检查整列输入框是否都有值
	    for (var i = startIndex; i < inputs.length; i += columnSize) {
	      if (inputs[i].value.trim() === '') {
	        inputs[i].focus(); // 将焦点设置在第一个未填写的输入框上
	        return;
	      }
	    }
	  }
	}
  
  var myNumberInput = document.getElementById("myNumberInput");
  
  
  function checkMinMaxRange(input, min, max) {
	  var value = parseFloat(input.value);
	  if (!isNaN(value) && value >= min && value <= max) {
		  myNumberInput.value = '0';
		  input.classList.remove('error');
	      input.classList.add('success');
	  } else {
		  myNumberInput.value = '1';
		  input.classList.remove('success');
	    input.classList.add('error');
	  }
	}
  
  function checkMinRange(input, min) {
	  var value = parseFloat(input.value);
	  if (!isNaN(value) && value >= min) {
		  myNumberInput.value = '0';
		  input.classList.remove('error');
		  input.classList.add('success');
	  }else{
		  myNumberInput.value = '1';
		  input.classList.remove('success');
		  input.classList.add('error');
	  }
  }
  
  function checkText(input){
	  var value = input.value;
	  if (input.value !== "") {
		  myNumberInput.value = '0';
		  input.classList.remove('error');
		  input.classList.add('success');
	  }else{
		  myNumberInput.value = '1';
		  input.classList.remove('success');
		  input.classList.add('error');
	  }
  }
  
 
  
  function limitInputLength(input, maxLength) {
	  var value = input.value;
	  
	  if (value.length > maxLength) {
	    input.value = value.slice(0, maxLength);  // 截取前maxLength个字符
	  }
	}


</script>
</html>
