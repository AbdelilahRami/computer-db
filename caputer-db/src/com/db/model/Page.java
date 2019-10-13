package com.db.model;

public class Page {
	private int pageNumber;
	private static final int pageSize =10;
	public Page(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public static int getPageSize() {
		return pageSize;
	}

	
}
