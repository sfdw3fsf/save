package vn.vnpt.vnptit.ecms.dto.hatang;

import org.apache.logging.log4j.util.Strings;

public class KhayDiaTrongTuDiaDTO {
    private int id;
    private int thietbi_id;
    private String mathietbi;
    private String sothutu;
    private String ten;
    private String kyhieu;
    private String serial_number;
    private int ip_mgmt_id;
    private int ip_controller_id;
    private String ghi_chu;
    private int hieu_luc = 1;
    private int hdd_tong;
    private int hdd_capphat;
    private int ssd_tong;
    private int ssd_capphat;
    private int trangthaisd;
    private String maduan;
    private String tenduan;
    private String sothe;
    private int donviqly_id;
    private int phongbanqly_id;
    private int nguoiqly_id;
    private int nhanvien_id;
    private String may_capnhap;
    private String nguoi_capnhap;
    private String ip_capnhap;

    public boolean valid() {
        return this.thietbi_id != 0 && Strings.isNotBlank(this.kyhieu)
                && Strings.isNotBlank(this.ten)
                && this.hdd_tong >= this.hdd_capphat
                && this.ssd_tong >= this.ssd_capphat;
    }

    public int getId() {
        return id;
    }

    public int getThietbi_id() {
        return thietbi_id;
    }

    public String getMathietbi() {
        return mathietbi;
    }

    public String getSothutu() {
        return sothutu;
    }

    public String getTen() {
        return ten;
    }

    public String getKyhieu() {
        return kyhieu;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public int getIp_mgmt_id() {
        return ip_mgmt_id;
    }

    public int getIp_controller_id() {
        return ip_controller_id;
    }

    public String getGhi_chu() {
        return ghi_chu;
    }

    public int getHieu_luc() {
        return hieu_luc;
    }

    public int getHdd_tong() {
        return hdd_tong;
    }

    public int getHdd_capphat() {
        return hdd_capphat;
    }

    public int getSsd_tong() {
        return ssd_tong;
    }

    public int getSsd_capphat() {
        return ssd_capphat;
    }

    public int getTrangthaisd() {
        return trangthaisd;
    }

    public String getMaduan() {
        return maduan;
    }

    public String getTenduan() {
        return tenduan;
    }

    public String getSothe() {
        return sothe;
    }

    public int getDonviqly_id() {
        return donviqly_id;
    }

    public int getPhongbanqly_id() {
        return phongbanqly_id;
    }

    public int getNguoiqly_id() {
        return nguoiqly_id;
    }

    public int getNhanvien_id() {
        return nhanvien_id;
    }

    public String getMay_capnhap() {
        return may_capnhap;
    }

    public String getNguoi_capnhap() {
        return nguoi_capnhap;
    }

    public String getIp_capnhap() {
        return ip_capnhap;
    }
}
