package fr.excilys.db.model;
public class Page {
	private int pageNumber;
	private int pageSize;
	
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public  int getPageSize() {
		return pageSize;
	}
	public Page(int pageNumber, int pageSize) {
		super();
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}
	public Page(int pageSize) {
		super();
		this.pageSize = pageSize;
	}
}
