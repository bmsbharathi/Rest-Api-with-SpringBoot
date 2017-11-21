package com.intellect.assignment.domain;

public class ValidationErrors {

	private String code;

	private String field;

	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ValErrors [code=" + code + ", field=" + field + ", message=" + message + "]";
	}

}
