package fr.excilys.db.model;
public class Page {
	private int pageNumber;
	private static final int PAGE_SIZE = 10;
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
		return PAGE_SIZE;
	}
}
