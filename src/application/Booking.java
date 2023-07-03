package application;

public class Booking {
	
	//Variable declarations
	private int bookID;
	private int empID;
	private int desk;
	private String date;
	private String timeStart;
	private String timeEnd;
	private int duration;
	
	public Booking(int bookID, int empID, int desk, String date, String timeStart, String timeEnd, int duration) {
		this.bookID = bookID;
		this.empID = empID;
		this.desk = desk;
		this.date = date;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.duration = duration;
	}

	public final int getBookID() {
		return bookID;
	}

	public final int getEmpID() {
		return empID;
	}

	public final int getDesk() {
		return desk;
	}

	public final String getDate() {
		return date;
	}

	public final String getTimeStart() {
		return timeStart;
	}

	public final String getTimeEnd() {
		return timeEnd;
	}

	public final int getDuration() {
		return duration;
	}
	
	
	
}
