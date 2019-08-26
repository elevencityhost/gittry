package com.redn.connect.o3klen018.pojo.response;

import java.io.Serializable;
import java.util.List;

public class ET_IRESP implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<Item> row;

	public List<Item> getRow() {
		return row;
	}

	public void setRow(List<Item> row) {
		this.row = row;
	}
}
