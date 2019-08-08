package com.x.report;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Report {

	private String headerSql =
			" select  employeeId , employeeName , model , purchaseDate " +
			"   from  cellphone ";

	private String dataSql =
			"   select  employeeId , eventYear , eventMonth , sum(minutes) , sum(data) " +
			"     from  cellphoneusage " +
			" group by  employeeId , eventYear , eventMonth " +
			" order by  employeeId , eventYear , eventMonth ";

	public void printReport(Connection cn, PrintStream ps) {
		List<Object[]> header = select(cn, headerSql);
		List<Object[]> data = select(cn, dataSql);
		int minutesSum = 0;
		double dataSum = 0.0;

		Set<String> dateSet = new TreeSet<String>();
		for (Object[] oa: data)
			dateSet.add(oa[1] + "-" + zed(""+oa[2]));

		Map<String,Integer> dateLookup = new TreeMap<String,Integer>();
		int idx = 0;
		for (String s: dateSet)
			dateLookup.put(s, idx++);

		Map<Integer,Integer> empLookup = new TreeMap<Integer,Integer>();
		idx = 1;
		for (Object[] oa: header)
			empLookup.put((Integer)oa[0], idx++);

		Object[][] oaa = new Object[header.size() + 1][dateLookup.size() + 4];

		oaa[0][0] = "empId";
		oaa[0][1] = "empName";
		oaa[0][2] = "phone";
		oaa[0][3] = "purchased";

		idx = 4;
		for (String key: dateLookup.keySet())
			oaa[0][idx++] = key;


		idx = 1;
		for (Object[] oa: header) {
			oaa[idx][0] = oa[0];
			oaa[idx][1] = oa[1];
			oaa[idx][2] = oa[2];
			oaa[idx][3] = oa[3];
			idx++;
		}

		DecimalFormat df = new DecimalFormat("##.##");
		for (Object[] oa: data) {
			int row = empLookup.get((Integer)oa[0]);
			int col = dateLookup.get(oa[1] + "-" + zed(""+oa[2]));
			oaa[row][col+4] = df.format(oa[3]) + " | " + df.format(oa[4]);
			minutesSum += (Integer)oa[3];
			dataSum += (Double)oa[4];
		}

		int[] maxWidth = new int[oaa[0].length];
		for (Object[] oa: oaa) {
			for (int i=0; i<oa.length; i++)
				maxWidth[i] = (maxWidth[i] < (""+oa[i]).length()) ? (""+oa[i]).length() : maxWidth[i];
		}

		System.out.println();
		System.out.println();

		System.out.print("                  run date: ");
		System.out.println(new Date());
		System.out.print("          number of phones: ");
		System.out.println(empLookup.size());
		System.out.print("             total minutes: ");
		System.out.println(minutesSum);
		System.out.print("                total data: ");
		System.out.println(df.format(dataSum));
		System.out.print("   minutes per month*phone: ");
		System.out.println(df.format(((double)minutesSum) / ((double)(dateLookup.size()*empLookup.size()))));
		System.out.print("      data per month*phone: ");
		System.out.println(df.format(((double)dataSum) / ((double)(dateLookup.size()*empLookup.size()))));

		System.out.println();
		System.out.println();

		for (Object[] oa: oaa) {
			for (int i=0; i<oa.length; i++) {
				ps.print(pad(true, oa[i], maxWidth[i] + 2));
			}
			ps.println();
		}

		System.out.println();
		System.out.println();
	}

	private String zed(String s) {
		return s.length() == 2 ? s : "0"+s;
	}
	
	private String pad(boolean left, Object value, int column) {
		String v = "" + value;
		return v + "                                             ".substring(v.length(), column);
	}

	private List<Object[]> select(Connection cn, String sql) {
		List<Object[]> ret = new ArrayList<Object[]>();
		try (Statement stmt = cn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)) {
			int cc = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				Object[] oa = new Object[cc];
				for (int i=0; i<cc; i++)
					oa[i] = rs.getObject(i+1);
				ret.add(oa);
			}
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return ret;
	}
}
