package com.joytech.keyence.bean;

public class QCResult {
	public String defective;//不良原因敘述(抓erp內的不良原因)
	public String determination;//綜合判定(合格/不合格)
	public String supervisor;//主管
	public String inspectors;//檢驗
	public String handleResult;//處置狀況
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

}
