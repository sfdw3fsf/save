package vn.vnpt.vnptit.ecms.dto.hatang;

import org.apache.commons.lang3.StringUtils;
import vn.vnpt.common.Utils;

public class TaiNguyenBackupDTO {
    private int id;
    private String ma;
    private String ten;
    private int hatangbackup_id;
    private int backup_loai_id;
    private int tansuat_id;
    private int ip_backup_id;
    private String link_backup;
    private int phieuyeucau_id;
    private String ghi_chu;
    private int hieu_luc = 1;
    private int loaihatang_luutru;
    private String hatang_luutru_id;
    private int loailuutru;
    private int nangluc;                    //Khả năng cấp phát
    private int dungluong;                  //Dung lượng đã cấp
    private int donviqly_id;
    private int phongbanqly_id;
    private int nguoiqly_id;
    private int nhanvien_id;
    private String may_capnhap;
    private String nguoi_capnhap;
    private String ip_capnhap;

    public boolean valid() {
        return StringUtils.isNotBlank(this.ma) && StringUtils.isNotBlank(this.ten)
                && this.hatangbackup_id != 0 && this.backup_loai_id != 0
                && this.nangluc >= this.dungluong;
    }

    public int getId() {
        return id;
    }

    public String getMa() {
        return Utils.trim(ma);
    }

    public String getTen() {
        return Utils.trim(ten);
    }

    public int getHatangbackup_id() {
        return hatangbackup_id;
    }

    public int getBackup_loai_id() {
        return backup_loai_id;
    }

    public int getTansuat_id() {
        return tansuat_id;
    }

    public int getIp_backup_id() {
        return ip_backup_id;
    }

    public String getLink_backup() {
        return link_backup;
    }

    public int getPhieuyeucau_id() {
        return phieuyeucau_id;
    }

    public String getGhi_chu() {
        return ghi_chu;
    }

    public int getHieu_luc() {
        return hieu_luc;
    }

    public int getLoaihatang_luutru() {
        return loaihatang_luutru;
    }

    public String getHatang_luutru_id() {
        return hatang_luutru_id;
    }

    public int getLoailuutru() {
        return loailuutru;
    }

    public int getNangluc() {
        return nangluc;
    }

    public int getDungluong() {
        return dungluong;
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
