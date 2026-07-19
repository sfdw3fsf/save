
package com.vn.vnptit.Oneapp_Com.common;

/**
 * @author danhd
 *
 */
public class ErrorCode {
	private ErrorCode() {

	}
	// Thành công
	public static final String BSS_00000000 = "BSS-00000000";
	// KHông có thông tin
	public static final String BSS_00004000 = "BSS-00004000";
	// Chưa đăng nhập
	public static final String BSS_00004001 = "BSS-00004001";
	// Bad request
	public static final String BSS_00004002 = "BSS-00004002";

	// Internal server error
	public static final String BSS_00000500 = "BSS-00000500";
	// Phát hành hóa đơn không thành công bước 1
	public static final String BSS_00004008 = "BSS-00004008";
	// Phát hành hóa đơn không thành công bước 2
	public static final String BSS_00004009 = "BSS-00004009";
	// Phát hành hóa đơn không thành công bước 3
	public static final String BSS_00004010 = "BSS-00004010";
}
