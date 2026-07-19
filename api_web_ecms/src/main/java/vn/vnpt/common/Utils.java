package vn.vnpt.common;

import org.apache.commons.validator.GenericValidator;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Utils {
	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd hh:mm a zzz");
		Date date = new Date();
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
		return sdf.format(date);
	}

	public static boolean isDate(String value, String formatDate) {
		if(value == null)
			return true;
		return GenericValidator.isDate(value, formatDate, true);
	}

	public static String getNodeName() {
		try {
			// Lấy tên host (tên node đang chạy API)
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			return "Không thể lấy tên node!";
		}
	}

	public static String getNodeIp() {
		try {
			// Lấy tên host (tên node đang chạy API)
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "Không thể lấy ip!";
		}
	}

	public static String trim(String str) {
		return str == null ? null : str.trim();
	}

	public static boolean isEmpty(Object val) {
		return val == null || "null".equals(String.valueOf(val)) || val.toString().trim().isEmpty();
	}
}
