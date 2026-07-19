package vn.vnpt.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Converter {

	/**
	 * Convert string to datetime with format yyyy-MM-dd HH:mm:ss
	 */
	public static Date toDateTime(String value) {
		return toDateTime(value, false);
	}

	/**
	 * Convert string to datetime
	 * 
	 * @param value
	 * @param isShortDate : true is with format yyyy-MM-dd, else with format
	 *                    yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date toDateTime(String value, boolean isShortDate) {
		if (!(value == null || value == "" || value.length() == 0)) {
			SimpleDateFormat formatter = new SimpleDateFormat(
					isShortDate ? Patterns.YYYYMMDD : Patterns.YYYYMMDDHHMMSS);
			try {
				return formatter.parse(value);
			} catch (ParseException e) {
			}
		}
		return null;
	}
}
