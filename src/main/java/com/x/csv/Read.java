package com.x.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Read {

	private String cp;
	private String cpu;

	public Read() {
		cp = System.getProperty("com.x.csv.cp");
		cpu = System.getProperty("com.x.csv.cpu");
	}

	public List<List<String>> readCellPhone() {
		// interpretation of date format is undefined in reqs
		return readCSV(cp);
	}

	public List<List<String>> readCellPhoneUsage() {
		return readCSV(cpu);
	}

	private List<List<String>> readCSV(String resource) {
		List<List<String>> ret = new ArrayList<List<String>>();
		int header = 0;
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(resource);
			 BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

			String s = "";
			while ((s = br.readLine()) != null) {
				if (header++ == 0)
					continue;
				ret.add(Arrays.asList(s.split(",")));
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static final void print2dArray(List<List<String>> saa, PrintStream ps) {
		for (List<String> sa : saa) {
			for (String s : sa)
				ps.print(s + " ");
			ps.print("\n");
		}
	}
}
