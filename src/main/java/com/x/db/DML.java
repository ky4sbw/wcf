package com.x.db;

import java.sql.PreparedStatement;
import java.util.List;

public class DML extends DDL {

	public static final String cellphone = "cellphone";
	public static final String cellphoneusage = "cellphoneusage";

	public DML() throws Exception {
		super();
	}

	public void loadCP(List<List<String>> saa) {
		String SQL =  " insert into cellphone "
					+ " (employeeId,employeeName,purchaseDate,model) "
					+ " values "
					+ " (?,?,?,?) ";

		try (PreparedStatement ps = cn.prepareStatement(SQL)) {
			for (List<String> sa: saa) {
				CellPhoneRec cpr = new CellPhoneRec(sa);
				adaptCP(cpr, ps);
				ps.execute();
				ps.clearParameters();
			}
			cn.commit();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void adaptCP(CellPhoneRec cpr, PreparedStatement ps) throws Exception {
		ps.setInt(1, cpr.getEmployeeId());
		ps.setString(2, cpr.getEmployeeName());
		ps.setString(3, cpr.getDateAsString());
		ps.setString(4, cpr.getModel());
	}

	public void loadCPU(List<List<String>> saa) {
		String SQL =  " insert into cellphoneusage "
					+ " (employeeId,eventYear,eventMonth,eventDay,minutes,data) "
					+ " values "
					+ " (?,?,?,?,?,?) ";

		try (PreparedStatement ps = cn.prepareStatement(SQL)) {
			for (List<String> sa: saa) {
				CellPhoneUsageRec cpr = new CellPhoneUsageRec(sa);
				adaptCPU(cpr, ps);
				ps.execute();
				ps.clearParameters();
			}
			cn.commit();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void adaptCPU(CellPhoneUsageRec cpr, PreparedStatement ps) throws Exception {
		ps.setInt(1, cpr.getEmployeeId());
		ps.setInt(2, cpr.getYear());
		ps.setInt(3, cpr.getMonth());
		ps.setInt(4, cpr.getDay());
		ps.setInt(5, cpr.getMinutes());
		ps.setDouble(6, cpr.getData());
	}
}
