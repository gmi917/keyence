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
		.red {
			border: 2px solid red;
		}
		.blue {
			border: 2px solid blue;
		}
	</style>

<body>
<div style="width: 95%; margin:auto;"> 
    <table style="width:100%; text-align: center; border: 1px solid black;" rules="all">
        <tbody>
            <tr>
                <td colspan=29>久裕興業科技股份有限公司工程檢驗說明書</td>
            </tr>
            <tr>
                <td colspan=2 rowspan=2 width="5%">品名</td>
                <td colspan=16 rowspan=2  width="50%" ><c:out value="${finishedPrimumQC.JYT012a003}" default="" /></td>				
				<td rowspan=2 width="5%">規格</td>
                <td colspan=10 rowspan=2  width="50%"><c:out value="${finishedPrimumQC.UDF02}" default="" /></td>                				
            </tr>
			<tr>
			</tr>
            
			<tr>
				<td colspan=2 width="5%">圖號</td>
				<td colspan=4 width="13%"><c:out value="${finishedPrimumQC.JYT012a004}" /></td>				
				<td colspan=3 width="8%">製令編號</td>
				<td colspan=9 width="16%"><c:out value="${finishedPrimumQC.getManufactureOrder()}    ${finishedPrimumQC.getManufactureNo()}" /></td>				
				<td colspan=2 width="8%">首件日期</td>
				<td colspan=2 width="13%"><c:out value="${finishedPrimumQC.getFirstItemDate()}" default="" /></td>
				<td colspan=2 width="8%">預計產量</td>
				<td colspan=3 width="13%"><c:out value="${finishedPrimumQC.getPredictQty()}" default="" /></td>
				<td colspan=2 width="5%">版次</td>
			</tr>
			<tr>
				<td colspan=2 width="5%">料號</td>
				<td colspan=4 ><c:out value="${finishedPrimumQC.JYT012a005}" default="" /></td>				
				<td colspan=3>客戶代號</td>
				<td colspan=9><c:out value="${finishedPrimumQC.getCustomerCode()}" /></td>
				<td colspan=2>首件人員</td>
				<td colspan=2><c:out value="${finishedPrimumQC.getFirstItemStaff()}" /></td>
				<td colspan=2>機台編號</td>
				<td colspan=3><c:out value="${finishedPrimumQC.getMachineNumber()}" /></td>
				<td colspan=2><c:out value="${finishedPrimumQC.JYT012a006}" default="" /></td>				
			</tr>   
            <tr>
				<td colspan="29" ><img style="width: 50%;" src="<c:out value="${finishedPrimumQC.fullImageName}" default="" />" ></td>
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
			<c:forEach items="${qcProductList}" var="ipqcItem" varStatus="status"> 
			 <tr>			 
				<td align="center" colspan=1>${ipqcItem.getJYT012b003()}</td>				
				<td align="left"  style="overflow-wrap: break-word;">${ipqcItem.getJYT012b005()} : ${ipqcItem.getJYT012b007()} ${ipqcItem.getJYT012b006()} (${ipqcItem.getJYT012b008()}/${ipqcItem.getJYT012b009()})</td>				
				<td align="center" style="overflow-wrap: break-word;">${ipqcItem.getJYT012b010()}</td>				
				<td align="center" width="5%">${ipqcItem.getIpqc1()}</td>	
				<td align="center" width="5%">${ipqcItem.getIpqc2()}</td>	
				<td align="center" width="5%">${ipqcItem.getIpqc3()}</td>
				<c:choose>
					<c:when test="${ipqcItem.getUDF03()=='1'}">
						<c:choose>
							<c:when test="${ipqcItem.getPqc1Color()!= ''}">	
								<td align="center" width="5%"><input type="number" value="${ipqcItem.getPqc1()}" readonly class="${ipqcItem.getPqc1Color()}"></td>				
							</c:when>	
							
						</c:choose>	
					
					</c:when>	
					<c:otherwise>
						<td width="10%"><input type="text" value="${ipqcItem.getPqc1()}" readonly> </td>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${ipqcItem.getUDF03()=='1'}">
						<c:choose>
							<c:when test="${ipqcItem.getPqc2Color() != ''}">								
								<td align="center" width="5%"><input type="number" value="${ipqcItem.getPqc2()}" readonly class="${ipqcItem.getPqc2Color()}"></td>				
							</c:when>	
						
						</c:choose>					
					</c:when>	
					<c:otherwise>
						<td width="10%"><input type="text" value="${ipqcItem.getPqc2()}" readonly> </td>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${ipqcItem.getUDF03()=='1'}">
						<c:choose>
							<c:when test="${ipqcItem.getPqc3Color() != ''}">								
								<td align="center" width="5%"><input type="number" value="${ipqcItem.getPqc3()}" readonly class="${ipqcItem.getPqc3Color()}"></td>				
							</c:when>
						
						</c:choose>					
					</c:when>	
<c:otherwise>
						<td width="10%"><input type="text" value="${ipqcItem.getPqc3()}" readonly> </td>
					</c:otherwise>
				</c:choose>							
			 </tr>				
    		</c:forEach>							
		</tbody>
	</table>       		    
    <table style="width:100%; margin-bottom: 0px; text-align: center; border: 1px solid black;" rules="all">
        <tr>
            <td rowspan="2" width="10%">不良原因敘述</td>
            <td rowspan="2" width="30%">${qcResult.getDefective()}</td>
            <td rowspan="2" width="5%">綜合判定</td>
            <td rowspan="2" width="20%">${qcResult.getDetermination()}</td>
            <td width="5%">主管</td>
            <td width="15%">${qcResult.getSupervisor()}</td>
        </tr>
        <tr>
            <td>檢驗</td>
            <td>${qcResult.getInspectors()}</td>
        </tr>
        <tr>
            <td>處置狀況</td>
            <td colspan="5">${qcResult.getHandleResult()}</td>
        </tr>
    </table>  	
	 </div>
</body>
<script>