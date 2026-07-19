package vn.vnpt.vnptit.ecms.dto.idc;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class SearchKetQuaCongSuatDienDto {
    private Long loaiCvId;
    private Long donViId;
    private Long nhanVienThucHienId;
    private Long trangThaiId;
    private Date ngayYcStart;
    private Date ngayYcEnd;
    private String khachHang;
    private String congViec;
    private Long idcId;
    private Date ngayHoanThanhStart;
    private Date ngayHoanThanhEnd;
    private String thietBi;
    private Long oCamId;

    // Getter & Setter

    public String getThietBi() {
        return thietBi;
    }

    public void setThietBi(String thietBi) {
        this.thietBi = thietBi;
    }

    public Long getoCamId() {
        return oCamId;
    }

    public void setoCamId(Long oCamId) {
        this.oCamId = oCamId;
    }

    public String getCongViec() {
        return congViec;
    }

    public void setCongViec(String congViec) {
        this.congViec = congViec;
    }

    public Long getIdcId() {
        return idcId;
    }

    public void setIdcId(Long idcId) {
        this.idcId = idcId;
    }

    public Date getNgayHoanThanhStart() {
        return ngayHoanThanhStart;
    }

    public void setNgayHoanThanhStart(Date ngayHoanThanhStart) {
        this.ngayHoanThanhStart = ngayHoanThanhStart;
    }

    public Date getNgayHoanThanhEnd() {
        return ngayHoanThanhEnd;
    }

    public void setNgayHoanThanhEnd(Date ngayHoanThanhEnd) {
        this.ngayHoanThanhEnd = ngayHoanThanhEnd;
    }

    public String getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(String khachHang) {
        this.khachHang = khachHang;
    }

    public Long getLoaiCvId() {
        return loaiCvId;
    }

    public void setLoaiCvId(Long loaiCvId) {
        this.loaiCvId = loaiCvId;
    }

    public void setNgayYcStart(Date ngayYcStart) {
        this.ngayYcStart = ngayYcStart;
    }

    public void setNgayYcEnd(Date ngayYcEnd) {
        this.ngayYcEnd = ngayYcEnd;
    }

    public Long getDonViId() {
        return donViId;
    }

    public void setDonViId(Long donViId) {
        this.donViId = donViId;
    }

    public Long getNhanVienThucHienId() {
        return nhanVienThucHienId;
    }

    public void setNhanVienThucHienId(Long nhanVienThucHienId) {
        this.nhanVienThucHienId = nhanVienThucHienId;
    }

    public Long getTrangThaiId() {
        return trangThaiId;
    }

    public void setTrangThaiId(Long trangThaiId) {
        this.trangThaiId = trangThaiId;
    }

    public Date getNgayYcStart() {
        return ngayYcStart;
    }


    public Date getNgayYcEnd() {
        return ngayYcEnd;
    }


}