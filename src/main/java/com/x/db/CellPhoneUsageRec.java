package com.x.db;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CellPhoneUsageRec {

	private int employeeId;
	private Calendar event = Calendar.getInstance();
	private int minutes;
	private double data;
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	public CellPhoneUsageRec() {
		
	}

	public CellPhoneUsageRec(List<String> csl) throws Exception {
		employeeId = Integer.parseInt(csl.get(0));
		event.setTime(sdf.parse(csl.get(1)));
		minutes = Integer.parseInt(csl.get(2));
		data = Double.parseDouble(csl.get(3));
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public int getYear() {
		return event.get(Calendar.YEAR);
	}

	public int getMonth() {
		return event.get(Calendar.MONTH) + 1;
	}

	public int getDay() {
		return event.get(Calendar.DAY_OF_MONTH);
	}

	public Date getEvent() {
		return event.getTime();
	}

	public int getMinutes() {
		return minutes;
	}

	public double getData() {
		return data;
	}
}
