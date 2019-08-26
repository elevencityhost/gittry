package com.redn.connect.exception;

/**
 * @author Vinay Kumar Thota
 * 
 * This class represents a custom exception with error code, message and category
 * 
 */
public class ConnectException extends Exception {

	private static final long serialVersionUID = -4539933717436101337L;
	
	private String code;
	
	private String description;
	
	private Category category;
	
	private String origin;

	public ConnectException(String code, String description, Category category, String origin) {
		super(description);
		this.code = code;
		this.description = description;
		this.category = category;
		this.origin = origin;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
}