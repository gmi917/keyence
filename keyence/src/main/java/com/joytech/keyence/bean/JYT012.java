package com.joytech.keyence.bean;

import java.util.List;

public class JYT012 {	
	public String manufactureOrder;//製令單別
	public String manufactureNo;//製令單號
	public String processCode;//製程代號
	public String productionLine;//生產線別	
	public String JYT012a002;//表單單號
	public String JYT012a003;//品名
	public String UDF02;//規格
	public String JYT012a004;//圖號
	public String JYT012a005;//料號
	public String JYT012b006;//檢驗單位
	public String customerCode;//客戶代號
	public String firstItemDate;//首件日期
	public String firstItemStaff;//首件人員
	public String predictQty;//預計產量
	public String machineNumber;//機台編號
	public String JYT012a006;//版次資訊
	public String UDF01;//圖檔檔名
	public String fullImageName;//URL+圖檔檔名
	public String qc_id;
		
//	public List<IpqcItem> ipqcItem;//首件/自主檢查記錄實測狀況
//	public List<Pqc> pqc;//品檢成品檢驗記錄實測狀況
//	
//	public String defective;//不良原因敘述(抓erp內的不良原因)
//	public String determination;//綜合判定(合格/不合格)
//	public String supervisor;//主管
//	public String inspectors;//檢驗
//	public String handleResult;//處置狀況
	
	public String getQc_id() {
		return qc_id;
	}
	public void setQc_id(String qc_id) {
		this.qc_id = qc_id;
	}
	public String getJYT012a002() {
		return JYT012a002;
	}
	public String getJYT012b006() {
		return JYT012b006;
	}
	public void setJYT012b006(String jYT012b006) {
		JYT012b006 = jYT012b006;
	}
	public String getFullImageName() {
		return fullImageName;
	}
	public void setFullImageName(String fullImageName) {
		this.fullImageName = fullImageName;
	}
	public void setJYT012a002(String jYT012a002) {
		JYT012a002 = jYT012a002;
	}
	public String getJYT012a003() {
		return JYT012a003;
	}
	public void setJYT012a003(String jYT012a003) {
		JYT012a003 = jYT012a003;
	}
	public String getJYT012a004() {
		return JYT012a004;
	}
	public void setJYT012a004(String jYT012a004) {
		JYT012a004 = jYT012a004;
	}
	public String getJYT012a005() {
		return JYT012a005;
	}
	public void setJYT012a005(String jYT012a005) {
		JYT012a005 = jYT012a005;
	}
	public String getJYT012a006() {
		return JYT012a006;
	}
	public void setJYT012a006(String jYT012a006) {
		JYT012a006 = jYT012a006;
	}
	
	public String getUDF01() {
		return UDF01;
	}
	public void setUDF01(String uDF01) {
		UDF01 = uDF01;
	}
	
	
	public String getUDF02() {
		return UDF02;
	}
	public void setUDF02(String uDF02) {
		UDF02 = uDF02;
	}
	public String getManufactureOrder() {
		return manufactureOrder;
	}
	public void setManufactureOrder(String manufactureOrder) {
		this.manufactureOrder = manufactureOrder;
	}
	public String getManufactureNo() {
		return manufactureNo;
	}
	public void setManufactureNo(String manufactureNo) {
		this.manufactureNo = manufactureNo;
	}
	public String getProcessCode() {
		return processCode;
	}
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getFirstItemDate() {
		return firstItemDate;
	}
	public void setFirstItemDate(String firstItemDate) {
		this.firstItemDate = firstItemDate;
	}
	public String getFirstItemStaff() {
		return firstItemStaff;
	}
	public void setFirstItemStaff(String firstItemStaff) {
		this.firstItemStaff = firstItemStaff;
	}
	public String getPredictQty() {
		return predictQty;
	}
	public void setPredictQty(String predictQty) {
		this.predictQty = predictQty;
	}
	public String getMachineNumber() {
		return machineNumber;
	}
	public void setMachineNumber(String machineNumber) {
		this.machineNumber = machineNumber;
	}
	public String getProductionLine() {
		return productionLine;
	}
	public void setProductionLine(String productionLine) {
		this.productionLine = productionLine;
	}
	
//	public List<IpqcItem> getIpqcItem() {
//		return ipqcItem;
//	}
//	public void setIpqcItem(List<IpqcItem> ipqcItem) {
//		this.ipqcItem = ipqcItem;
//	}
//	public List<Pqc> getPqc() {
//		return pqc;
//	}
//	public void setPqc(List<Pqc> pqc) {
//		this.pqc = pqc;
//	}
//	public String getDefective() {
//		return defective;
//	}
//	public void setDefective(String defective) {
//		this.defective = defective;
//	}
//	public String getDetermination() {
//		return determination;
//	}
//	public void setDetermination(String determination) {
//		this.determination = determination;
//	}
//	public String getSupervisor() {
//		return supervisor;
//	}
//	public void setSupervisor(String supervisor) {
//		this.supervisor = supervisor;
//	}
//	public String getInspectors() {
//		return inspectors;
//	}
//	public void setInspectors(String inspectors) {
//		this.inspectors = inspectors;
//	}
//	public String getHandleResult() {
//		return handleResult;
//	}
//	public void setHandleResult(String handleResult) {
//		this.handleResult = handleResult;
//	}
		
}
