package com.redn.connect.jenkins.vo;

import com.redn.connect.exception.Category;

/**
 * @author Vinay Kumar Thota
 * 
 * This class represents a custom exception with error code, message and category
 * 
 */
public class JenkinsMuleException extends Exception {

	private static final long serialVersionUID = -4539933717436101337L;
	
	private String code;
	private String description;
	
	public JenkinsMuleException(String code, String description, Category category, String origin) {
		super(description);
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}