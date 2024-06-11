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
<script src="https://code.jquery.com/jquery-1.9.1.js" integrity="sha256-e9gNBsAcA0DBuRWbm0oZfbiCyhjLrI6bmqAl5o+ZjUA=" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/ui/1.9.2/jquery-ui.js" integrity="sha256-PsB+5ZEsBlDx9Fi/GXc1bZmC7wEQzZK4bM/VwNm1L6c=" crossorigin="anonymous"></script>
<link href="https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.3.0/jquery.form.min.js" integrity="sha384-qlmct0AOBiA2VPZkMY3+2WqkHtIQ9lSdAsAn5RUJD/3vA5MKDgSGcdmIv4ycVxyn" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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
	<form id='form1' name='viewform' action="" method="post">
	<table>
		<tr>	
			<td>
				<table>
				<tr>
				<th>製令單別</th>
				</tr>
				<tr>
				<td>起 : <input type='text' size='8' id = 'ta001stt' name='orderTypeBegin' value=""></td>
				</tr>
				<tr>
				<td>迄 : <input  type='text' size='8' id = 'ta001end' name='orderTypeEnd' onFocus="form1.ta001end.value=form1.ta001stt.value;" value=""></td>
				</tr>
				</table>
			</td>		
			<td>
				<table>
				<tr>
				<th>製令單號</th>
				</tr>
				<tr>
				<td>起 : <input type='text' size='15' id = 'ta002stt' name='orderNoBegin' value=""></td>
				</tr>
				<tr>
				<td>迄 : <input  type='text' size='15' id = 'ta002end'  name='orderNoEnd' onFocus="form1.ta002end.value=form1.ta002stt.value;" value=""></td>
				</tr>
				</table>
			</td>
			<td>
				<table>
				<tr>
				<th>品號</th>
				</tr>
				<tr>
				<td>起 : <input type='text' size='8' name='itemNoBegin' value="" id = 'ta004stt' ></td>
				</tr>
				<tr>
				<td>迄 : <input  type='text' size='8' name='itemNoEnd' onFocus="form1.ta004end.value=form1.ta004stt.value;" value="" id='ta004end'></td>
				</tr>
				</table>
			</td>
			<td>
				<table>
				<tr>
				<th>單據日期</th>
				</tr>
				<tr>
				<td>起 : <input  class='datepicker' size='8' type='text' name='orderDateBegin'  value=""></td>
				</tr>
				<tr>
				<td>迄 : <input  class='datepicker 'size='8' type='text' name='orderDateEnd' onFocus="form1.ta003end.value=form1.ta003stt.value;" value=""></td>
				</tr>
				</table>
			</td>
		</tr>
	</table>
	<input type='submit' name='Submit'  value=' 資 料 查 詢 '>	
	<!--<span id='msg'>state</span>-->
</form>
		<div class="container">
	    	<h2 align="center" class="text-primary"></h2><hr />
	    	<div>&nbsp;</div>
	    	    	
	        <table class="table" align="center" id ='view'>
			    
			      <tr>			        
					<th></th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
			      </tr>
			      	
			  </table>
			  
	    </div>	
</body>
<script>
$(function() {
	$( ".datepicker" ).datepicker({ dateFormat: "yy-mm-dd" ,dayNamesMin: [ "日","一", "二", "三", "四", "五", "六"],monthNames: [ "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二" ],showOn: "button"});
});
$(function() {
	$('#form1').ajaxForm({
	type: "POST",
	url: '/QC/getTestData',
	dataType: 'json',
	data:{
			action:'select'
		},
	beforeSend: function(){
		$("#msg").html('Loading...');
	},
	error: function(){
		$("#msg").html('Error...');
	},
	success: function(data){
		$("#view tbody tr").remove();
		if(!data) {
			$("#view").append("<tr><td>"+"no values"+"</td></tr>");
			return;
		}
		$("#view tbody").append("<tr><th>製令單別</th><th>製令單號</th><th>料號</th><th>品名</th><th>自主檢查日期</th></tr>");
		var numberdate = data.length;
		for (var i = 0; i < numberdate; i++) {
			$("#view tbody").append("<tr>" 
					+"<td><a href='/QC/getHistoryData/"+data[i].qc_id+"/'>"+data[i].manufactureOrder+"</a></td>"
					+"<td><a href='/QC/getHistoryData/"+data[i].qc_id+"/'>"+data[i].manufactureNo+"</a></td>"
					+"<td><a href='/QC/getHistoryData/"+data[i].qc_id+"/'>"+data[i].JYT012a005+"</a></td>" 
					+"<td><a href='/QC/getHistoryData/"+data[i].qc_id+"/'>"+data[i].JYT012a003+"</a></td>" 
					+"<td><a href='/QC/getHistoryData/"+data[i].qc_id+"/'>"+data[i].firstItemDate+"</a></td>" 
			      +"</tr>");
		}
		$("#view").append("</table>");
		/* $("#view nav").remove();
		$("#view").append("<nav><ul class='pagination mb-5'>"
	                +"<li class='page-item'>"
	                +"<a class='page-link' href='/premium/showHistory/1' aria-label='Previous'>"
	                +"<span aria-hidden='true'>&laquo;</span>"
	                +"<span class='sr-only'>Previous</span>"
	                +"</a>"
	                +"</li>"
	                +"<li class='page-item'><a class='page-link' href='/premium/showHistory/1'>1</a></li>"
	                +"<li class='page-item'>"
	                +"<a class='page-link' href=''/premium/showHistory/1' aria-label='Next'>"
	                +"<span aria-hidden='true'>&raquo;</span>"
	                +"<span class='sr-only'>Next</span>"
	                +"</a>"
	                +"</li>"
					+"</ul>"
					+"</nav>"); */
		
	}
});		
	 	
}); 


$(function() { 
		$('#form2').submit(function(){   
			if($(".S1:checked").length==0)
			{
				alert('請先執行資料查詢!!');
				return false;
			} 
		})
}); 






function select_all(formName, elementName, selectAllName) {
	if(document.forms[formName].elements[elementName].length !=null){
		if(document.forms[formName].elements[selectAllName].checked)
			for(var i = 0; i < document.forms[formName].elements[elementName].length; i++)
				document.forms[formName].elements[elementName][i].checked = true;
		else
			for(var i = 0; i < document.forms[formName].elements[elementName].length; i++)
					document.forms[formName].elements[elementName][i].checked = false;
	}
	else{
		if(document.forms[formName].elements[selectAllName].checked)
		document.forms[formName].elements[elementName].checked = true;
		else
		document.forms[formName].elements[elementName].checked = false;
	}
}
</script>
</html>