package com.x.main;

import java.io.File;

import com.x.csv.Read;
import com.x.db.DBA;
import com.x.db.DDL;
import com.x.db.DML;
import com.x.report.Report;

public class Main {

	public static void main(String[] argv) {
		try {

			File f = File.createTempFile("abc", "db");

			System.setProperty("com.x.db.file", f.getAbsolutePath());
			System.setProperty("com.x.db.url" , "jdbc:sqlite:" + f.getAbsolutePath());
			System.setProperty("com.x.csv.cp" , "CellPhone.csv");
			System.setProperty("com.x.csv.cpu", "CellPhoneUsageByMonth.csv");

			Runtime.getRuntime().addShutdownHook(
				new Thread(
					new Runnable() {
						public void run() {
							com.x.db.DBA.deleteDatabase();
						}
					}
				)
			);

			DBA dba = new DBA();
			dba.createDatabase();

			DDL ddl = new DDL();
			ddl.createTable();

			Read read = new Read();

			DML dml = new DML();
			dml.loadCP(read.readCellPhone());
			dml.loadCPU(read.readCellPhoneUsage());

			Report report = new Report();
			report.printReport(dba.getConnection(), System.out);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
		}
	}
}
