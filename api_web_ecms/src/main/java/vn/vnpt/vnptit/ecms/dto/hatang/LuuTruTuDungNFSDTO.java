package vn.vnpt.vnptit.ecms.dto.hatang;

import vn.vnpt.common.Utils;

public class LuuTruTuDungNFSDTO {
    private int id;
    private String mahatang_infra;
    private String thanhphan;
    private int hatang_congnghe_id;
    private int ip_cungcapdv_id;
    private int ip_quantri_id;
    private String ghi_chu;
    private int hieu_luc = 1;
    private int hdd_tong;
    private int hdd_nangluc;
    private int hdd_capphat;
    private int ssd_tong;
    private int ssd_nangluc;
    private int ssd_capphat;
    private int iops_tong;
    private int iops_nangluc;
    private int iops_capphat;
    private int donviqly_id;
    private int phongbanqly_id;
    private int nguoiqly_id;
    private int nhanvien_id;
    private String may_capnhap;
    private String nguoi_capnhap;
    private String ip_capnhap;

    public boolean valid() {
        return true;
    }

    public int getId() {
        return id;
    }

    public String getMahatang_infra() {
        return Utils.trim(this.mahatang_infra);
    }

    public String getThanhphan() {
        return Utils.trim(this.thanhphan);
    }

    public int getHatang_congnghe_id() {
        return hatang_congnghe_id;
    }

    public int getIp_cungcapdv_id() {
        return ip_cungcapdv_id;
    }

    public int getIp_quantri_id() {
        return ip_quantri_id;
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

    public int getHdd_nangluc() {
        return hdd_nangluc;
    }

    public int getHdd_capphat() {
        return hdd_capphat;
    }

    public int getSsd_tong() {
        return ssd_tong;
    }

    public int getSsd_nangluc() {
        return ssd_nangluc;
    }

    public int getSsd_capphat() {
        return ssd_capphat;
    }

    public int getIops_tong() {
        return iops_tong;
    }

    public int getIops_nangluc() {
        return iops_nangluc;
    }

    public int getIops_capphat() {
        return iops_capphat;
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
