package com.joytech.keyence.bean;

public class IpqcRecord {
	public String itemSN;//項次
	public String testItem;//檢驗項目
	public String testTool;//檢驗量具
	public String ipqc1;//首件/自主檢查記錄實測狀況1
	public String ipqc2;//首件/自主檢查記錄實測狀況2
	public String ipqc3;//首件/自主檢查記錄實測狀況3

	public String getIpqc1() {
		return ipqc1;
	}
	public void setIpqc1(String ipqc1) {
		this.ipqc1 = ipqc1;
	}
	public String getIpqc2() {
		return ipqc2;
	}
	public void setIpqc2(String ipqc2) {
		this.ipqc2 = ipqc2;
	}
	public String getIpqc3() {
		return ipqc3;
	}
	public void setIpqc3(String ipqc3) {
		this.ipqc3 = ipqc3;
	}
	public String getItemSN() {
		return itemSN;
	}
	public void setItemSN(String itemSN) {
		this.itemSN = itemSN;
	}
	public String getTestItem() {
		return testItem;
	}
	public void setTestItem(String testItem) {
		this.testItem = testItem;
	}
	public String getTestTool() {
		return testTool;
	}
	public void setTestTool(String testTool) {
		this.testTool = testTool;
	}	
}
