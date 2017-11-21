package com.intellect.assignment.domain;

import java.util.List;

import com.intellect.assignment.domain.ValidationErrors;

public class Response {

	private String resMsg;

	private String userId;

	List<ValidationErrors> valErrors;

	public String getResMsg() {
		return resMsg;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<ValidationErrors> getValErrors() {
		return valErrors;
	}

	public void setValErrors(List<ValidationErrors> valErrors) {
		this.valErrors = valErrors;
	}

	@Override
	public String toString() {
		return "Response [resMsg=" + resMsg + ", userId=" + userId + ", valErrors=" + valErrors + "]";
	}

}
