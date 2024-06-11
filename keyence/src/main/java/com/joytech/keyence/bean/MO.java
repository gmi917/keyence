package com.joytech.keyence.bean;

public class MO {
	public String qc_id;//唯一值
	public String manufactureOrder;//製令單別
	public String manufactureNo;//製令單號
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
	public String getQc_id() {
		return qc_id;
	}
	public void setQc_id(String qc_id) {
		this.qc_id = qc_id;
	}
	
}
