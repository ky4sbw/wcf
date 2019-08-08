package com.x.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBA {

	protected String url;
	protected Connection cn;

	public DBA() throws Exception {
		url = System.getProperty("com.x.db.url");
		cn = DriverManager.getConnection(url);
	}

	public Connection getConnection() {
		return cn;
	}

	public void createDatabase() {
		try {
			System.out.println(cn.getMetaData().getDriverName());
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static final void deleteDatabase() {
		try {
			new File(System.getProperty("com.x.db.file")).delete();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
