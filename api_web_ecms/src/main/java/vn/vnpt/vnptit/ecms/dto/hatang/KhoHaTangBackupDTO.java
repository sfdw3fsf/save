package vn.vnpt.vnptit.ecms.dto.hatang;

import com.fasterxml.jackson.annotation.JsonFormat;
import vn.vnpt.common.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KhoHaTangBackupDTO {
    private Number id;
    private String maHaTangInfra;
    private String ten;
    private Number hinhThucDauTu;   // 1- Hợp tác, 2- Đầu tư nội bộ, 3- Thuê quản trị, 4- Khác (number)
    private Number loaiHaTangId;
    private Number idcId;
    private List<Number> haTangMangId = new ArrayList<>();
    private List<Number> ipCungCapDichVu = new ArrayList<>();
    private List<Number> ipReplicaData = new ArrayList<>();
    private List<Number> ipQuanTri = new ArrayList<>();
    private String adminLink;
    private String avatar;      //image base64
    private String ghiChu;
    private Number hieuLuc;
    private Number donViQLyId;
    private Number phongBanQLyId;
    private Number nguoiQLyId;
    private Number nhanVienId;
    private String mayCapNhap;
    private String nguoiCapNhap;
    private String ipCapNhap;
    private List<ThietBiHinhThanh> thietBiHinhThanhList = new ArrayList<>();
    private List<ThietBiTuDiaDauNoi> thietBiTuDiaDauNoiList = new ArrayList<>();


    public static class ThietBiHinhThanh {
        private int id;
        private int thietbi_id;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
        private Date ngay_cn;

        public int getId() {
            return id;
        }

        public int getThietbi_id() {
            return thietbi_id;
        }

        public Date getNgay_cn() {
            return ngay_cn;
        }
    }

    public static class ThietBiTuDiaDauNoi {
        private int id;
        private int thietbi_id;
        private int sothutu;

        public int getId() {
            return id;
        }

        public int getThietbi_id() {
            return thietbi_id;
        }

        public int getSothutu() {
            return sothutu;
        }
    }

    public List<ThietBiTuDiaDauNoi> getThietBiTuDiaDauNoiList() {
        return thietBiTuDiaDauNoiList;
    }

    public List<ThietBiHinhThanh> getThietBiHinhThanhList() {
        return thietBiHinhThanhList;
    }

    public Number getId() {
        return id;
    }

    public String getMaHaTangInfra() {
        return Utils.trim(this.maHaTangInfra);
    }

    public String getTen() {
        return Utils.trim(ten);
    }

    public Number getHinhThucDauTu() {
        return hinhThucDauTu;
    }

    public Number getLoaiHaTangId() {
        return loaiHaTangId;
    }

    public Number getIdcId() {
        return idcId;
    }

    public List<Number> getHaTangMangId() {
        return haTangMangId;
    }

    public String getAdminLink() {
        return adminLink == null ? null : adminLink.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public String getGhiChu() {
        return ghiChu == null ? null : ghiChu.trim();
    }

    public Number getHieuLuc() {
        return hieuLuc;
    }

    public Number getDonViQLyId() {
        return donViQLyId;
    }

    public Number getPhongBanQLyId() {
        return phongBanQLyId;
    }

    public Number getNguoiQLyId() {
        return nguoiQLyId;
    }

    public Number getNhanVienId() {
        return nhanVienId;
    }

    public String getMayCapNhap() {
        return mayCapNhap;
    }

    public String getNguoiCapNhap() {
        return nguoiCapNhap;
    }

    public String getIpCapNhap() {
        return ipCapNhap;
    }

    public List<Number> getIpCungCapDichVu() {
        return ipCungCapDichVu;
    }

    public List<Number> getIpReplicaData() {
        return ipReplicaData;
    }

    public List<Number> getIpQuanTri() {
        return ipQuanTri;
    }
}
