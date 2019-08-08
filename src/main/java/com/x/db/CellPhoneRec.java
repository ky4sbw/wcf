package com.x.db;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CellPhoneRec {

	private int employeeId;
	private String employeeName;
	private Calendar purchaseDate = Calendar.getInstance();
	private 	String model;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	public CellPhoneRec() {

	}

	public CellPhoneRec(List<String> csl) throws Exception {
		employeeId = Integer.parseInt(csl.get(0));
		employeeName = csl.get(1);
		purchaseDate.setTime(sdf.parse(csl.get(2)));
		model = csl.get(3);
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public Date getDate() {
		return purchaseDate.getTime();
	}

	public String getDateAsString() {
		return sdf.format(purchaseDate.getTime());
	}

	public String getModel() {
		return model;
	}
}
