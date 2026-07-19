/**
 * EwsdproSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.subman;

public interface EwsdproSoap extends java.rmi.Remote {

    /**
     * Khai báo mới
     */
    public boolean taoMayMoi(String _sKey, int tongDai, String soMay, String viTri, javax.xml.rpc.holders.StringHolder _sMessage) throws java.rmi.RemoteException;

    /**
     * Xóa máy<br>  - Nếu là thuê bao thường: xóa máy, giải phóng
     * vị trí<br>  - Nếu là số trượt: xóa số trượt<br>  - Nếu là số chủ của
     * line trượt: xóa cả nhóm
     */
    public boolean xoaMay(String _sKey, int tongDai, String soMay, String viTri, javax.xml.rpc.holders.StringHolder _sMessage) throws java.rmi.RemoteException;

    /**
     * Đổi số<br>  - Số mới và số cũ cùng tổng đài
     */
    public boolean doiSo(String _sKey, int tongDai, String soMayCu, String viTri, String soMayMoi, javax.xml.rpc.holders.StringHolder _sMessage) throws java.rmi.RemoteException;

    /**
     * Đổi vị trí<br>  - Vị trí mới và vị trí cũ cùng tổng đài
     */
    public boolean doiViTri(String _sKey, int tongDai, String soMay, String viTriCu, String viTriMoi, javax.xml.rpc.holders.BooleanHolder _bServiceLoss, javax.xml.rpc.holders.StringHolder _sMessage) throws java.rmi.RemoteException;

    /**
     * Thay đổi cấu hình
     */
    public boolean thayDoiCauHinh(String _sKey, int tongDaiCu, String soMayCu, int tongDaiMoi, String soMayMoi, String viTriMoi, javax.xml.rpc.holders.BooleanHolder _bServiceLoss, javax.xml.rpc.holders.StringHolder _sMessage) throws java.rmi.RemoteException;

    /**
     * Khóa gọi đi (không kiểm tra NE)
     */
    public boolean khoaGoiDi(String _sKey, int tongDai, String soMay, String viTri, javax.xml.rpc.holders.StringHolder _sMessage) throws java.rmi.RemoteException;

    /**
     * Khóa gọi đến (không kiểm tra NE)
     */
    public boolean khoaGoiDen(String _sKey, int tongDai, String soMay, String viTri, javax.xml.rpc.holders.StringHolder _sMessage) throws java.rmi.RemoteException;

    /**
     * Khóa cước (không kiểm tra NE)
     */
    public boolean khoaCuoc(String _sKey, int tongDai, String soMay, String viTri, javax.xml.rpc.holders.StringHolder _sMessage) throws java.rmi.RemoteException;

    /**
     * Khóa hai chiều (không kiểm tra NE)
     */
    public boolean khoaHaiChieu(String _sKey, int tongDai, String soMay, String viTri, javax.xml.rpc.holders.StringHolder _sMessage) throws java.rmi.RemoteException;

    /**
     * Mở gọi đi (không kiểm tra NE)
     */
    public boolean moGoiDi(String _sKey, int tongDai, String soMay, String viTri, javax.xml.rpc.holders.StringHolder _sMessage) throws java.rmi.RemoteException;

    /**
     * Mở gọi đến (không kiểm tra NE)
     */
    public boolean moGoiDen(String _sKey, int tongDai, String soMay, String viTri, javax.xml.rpc.holders.StringHolder _sMessage) throws java.rmi.RemoteException;

    /**
     * Mở cước (không kiểm tra NE)
     */
    public boolean moCuoc(String _sKey, int tongDai, String soMay, String viTri, javax.xml.rpc.holders.StringHolder _sMessage) throws java.rmi.RemoteException;

    /**
     * Mở hai chiều (không kiểm tra NE)
     */
    public boolean moHaiChieu(String _sKey, int tongDai, String soMay, String viTri, javax.xml.rpc.holders.StringHolder _sMessage) throws java.rmi.RemoteException;

    /**
     * Đăng ký dịch vụ (không kiểm tra NE)<br>  402: Chuyển thoại
     * nhân công<br>  2: Chuyển thoại sau 5 hồi chuông<br>  711: Hiển thị
     * số gọi đến<br>  4: Chuyển thoại trực tiếp (có tham số)<br>  286: Khóa
     * quốc tế<br>  407: Khóa liên tỉnh và quốc tế<br>  620: Khóa mã cá nhân
     * (có tham số)<br>  622: Đường dây nóng<br>  39: Tạm dừng
     */
    public boolean dangKyDichVu(String _sKey, int tongDai, String soMay, String viTri, int maDichVu, String thamSo, javax.xml.rpc.holders.StringHolder _sMessage) throws java.rmi.RemoteException;

    /**
     * Hủy dịch vụ (không kiểm tra NE)<br>  402: Hủy chuyển thoại<br>
     * 2: Hủy chuyển thoại<br>  711: Hủy hiển thị số gọi đến<br>  4: Hủy
     * chuyển thoại<br>  286: Hủy khóa liên tỉnh cuốc tế<br>  407: Hủy khóa
     * liên tỉnh quốc tế<br>  620: Hủy mã cá nhân<br>  622: Hủy đường dây
     * nóng<br>  39: Tạm dừng
     */
    public boolean huyDichVu(String _sKey, int tongDai, String soMay, String viTri, int maDichVu, String thamSo, javax.xml.rpc.holders.StringHolder _sMessage) throws java.rmi.RemoteException;

    /**
     * Thực thi lệnh
     */
    public String thucThiLenh(String _sKey, int tongDai, String _sCommand) throws java.rmi.RemoteException;

    /**
     * Kiểm tra thông tin thuê bao
     */
    public SubInfo kiemTraThongTin(String _sKey, int tongDai, String soMay) throws java.rmi.RemoteException;
}
