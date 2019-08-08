package com.x.db;

import java.sql.SQLException;
import java.sql.Statement;

public class DDL extends DBA {

	private String tcp = " create table if not exists cellphone ( "
						+ " employeeId integer , "
						+ " employeeName text ,"
						+ " purchaseDate text , "
						+ " model text); ";

	private String tcpu = " create table if not exists cellphoneusage ( "
						+ " employeeId integer , "
						+ " eventYear integer , "
						+ " eventMonth integer , "
						+ " eventDay integer , "
						+ " minutes integer ,"
						+ " data real); ";

	public DDL() throws Exception {
		super();
	}

	public void createTable() {
		try (Statement stmt = cn.createStatement()) {
			stmt.execute(tcp);
			stmt.execute(tcpu);
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
