package io.github.elishaSword.demo;

import java.sql.Date;

public class DateClass {
	public static void main(String[] args) {
		final Date d = new Date(java.lang.System.currentTimeMillis());
//		final java.util.Date d2 = new java.util.Date();
		System.out.println(d);
	}
}
