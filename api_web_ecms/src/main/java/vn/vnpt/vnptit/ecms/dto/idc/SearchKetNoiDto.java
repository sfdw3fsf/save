package vn.vnpt.vnptit.ecms.dto.idc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

public class SearchKetNoiDto {
    private Long idLoaiCv;
    private Long idPhieuTc;
    @JsonProperty("donViId")
    private Long donViId;
    private Long idcId;
    private Long trangThaiVlId;
    private Long trangThaiVhId;
    private Long lopKnId;
    private Long hinhThucKn;
    private Long nhanVienThucHien;
    private Long heThongNguon;
    private Long heThongDich;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date ngayTaoFrom;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date ngayTaoTo;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date ngayHoanThanhFrom;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date ngayHoanThanhTo;
    private Integer isKhachHang;
    private String maTb;
    private Long tenKhachHang;
    private Integer trangThaiThiCongKn;
    private String phuongThuc;

    public Long getIdPhieuTc() { return idPhieuTc; }
    public Long getIdcId() {
        return idcId;
    }
    public Long getDonviId() { return donViId; }

    public Long getTrangThaiVlId() {
        return trangThaiVlId;
    }

    public Long getTrangThaiVhId() {
        return trangThaiVhId;
    }

    public Long getLopKnId() {
        return lopKnId;
    }

    public Long getNhanVienThucHien() {
        return nhanVienThucHien;
    }

    public Long getHeThongNguon() {
        return heThongNguon;
    }

    public Long getHeThongDich() {
        return heThongDich;
    }

    public Date getNgayTaoFrom() {
        return ngayTaoFrom;
    }

    public Date getNgayTaoTo() {
        return ngayTaoTo;
    }

    public Date getNgayHoanThanhFrom() {
        return ngayHoanThanhFrom;
    }

    public Date getNgayHoanThanhTo() {
        return ngayHoanThanhTo;
    }

    public Long getHinhThucKn() {
        return hinhThucKn;
    }

    public Long getIdLoaiCv() {
        return idLoaiCv;
    }
    public Integer getIsKhachHang() {
        return isKhachHang;
    }
    public String getMaTb() {
        return maTb;
    }
    public Long getTenKhachHang() {
        return tenKhachHang;
    }

    public Integer getTrangThaiThiCongKn() {
        return trangThaiThiCongKn;
    }
    public String getPhuongThuc() {
        return phuongThuc;
    }
}
