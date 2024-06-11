package com.joytech.keyence.bean;

import java.util.List;

public class EngineeringSpec {
	public String itemName;//品號
	public String specification;//規格
	public String imageNumber;//圖號
	public String partNumber;//料號
	public String manufactureOrder;//製令單別
	public String manufactureNo;//製令單號
	public String customerCode;//客戶代號
	public String firstItemDate;//首件日期
	public String firstItemStaff;//首件人員
	public String predictQty;//預計產量
	public String machineNumber;//機台編號
	public String version;//版次
	public String imageURL;//圖片路徑
	public List<ItemRecord> itemRecord;//首件/自主檢查項目
	public String defective;//不良原因敘述
	public String determination;//綜合判定
	public String supervisor;//主管
	public String inspectors;//檢驗
	public String handleResult;//處置狀況
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public String getImageNumber() {
		return imageNumber;
	}
	public void setImageNumber(String imageNumber) {
		this.imageNumber = imageNumber;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	
	public List<ItemRecord> getItemRecord() {
		return itemRecord;
	}
	public void setItemRecord(List<ItemRecord> itemRecord) {
		this.itemRecord = itemRecord;
	}
	public String getDefective() {
		return defective;
	}
	public void setDefective(String defective) {
		this.defective = defective;
	}
	public String getDetermination() {
		return determination;
	}
	public void setDetermination(String determination) {
		this.determination = determination;
	}
	public String getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}
	public String getInspectors() {
		return inspectors;
	}
	public void setInspectors(String inspectors) {
		this.inspectors = inspectors;
	}
	public String getHandleResult() {
		return handleResult;
	}
	public void setHandleResult(String handleResult) {
		this.handleResult = handleResult;
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

}
