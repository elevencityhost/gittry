package com.redn.connect.exception;

/**
 * @author Vinay Kumar Thota
 * 
 * This enum represents exception category
 *
 */
public enum Category {
	
	BIZ("Business Exception"),
	COMM("Communication Exception"),
	TECH("Technical Exception"),
	DATA("Data Exception"),
	UNKNOWN("Unknown Category");

	String desc;
	
	private Category(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	/**
	 * This method converts input String to {@link Category} with name match
	 * 
	 * @param categoryStr input category string
	 * @return {@link Category} whose name equals to the input categoryStr
	 */
	public static Category toCategory(String categoryStr){
		
		Category category;
		
		try{
			category = Category.valueOf(categoryStr);
		}catch(IllegalArgumentException exp){
			category = UNKNOWN;
		}
		
		return category;
	}
}
