package vn.vnpt.vnptit.ecms.dto.idc;

import java.util.Date;

public class KetQuaThiCongRackDto {
    private Long loaiCvId;
    private Long donviId;
    private Long trangthaiId;
    private Long nhanvienThId;
    private Date ngayYcStart;
    private Date ngayYcEnd;
    private Date ngayYchtStart;
    private Date ngayYchtEnd;
    private Integer phanLoai;
    private String khachHang;
    private String congViec;
    private Long idcId;
    private String tenRack;
    private String loaiRack;

    public KetQuaThiCongRackDto() {
    }

    public KetQuaThiCongRackDto(Long loaiCvId, Long donviId, Long trangthaiId, Long nhanvienThId, Date ngayYcStart,
                                Date ngayYcEnd, Date ngayYchtStart, Date ngayYchtEnd, Integer phanLoai, String khachHang,
                                String congViec, Long idcId, String tenRack, String loaiRack) {
        this.loaiCvId = loaiCvId;
        this.donviId = donviId;
        this.trangthaiId = trangthaiId;
        this.nhanvienThId = nhanvienThId;
        this.ngayYcStart = ngayYcStart;
        this.ngayYcEnd = ngayYcEnd;
        this.ngayYchtStart = ngayYchtStart;
        this.ngayYchtEnd = ngayYchtEnd;
        this.phanLoai = phanLoai;
        this.khachHang = khachHang;
        this.congViec = congViec;
        this.idcId = idcId;
        this.tenRack = tenRack;
        this.loaiRack = loaiRack;
    }

    public Long getLoaiCvId() {
        return loaiCvId;
    }

    public void setLoaiCvId(Long loaiCvId) {
        this.loaiCvId = loaiCvId;
    }

    public Long getDonviId() {
        return donviId;
    }

    public void setDonviId(Long donviId) {
        this.donviId = donviId;
    }

    public Long getTrangthaiId() {
        return trangthaiId;
    }

    public void setTrangthaiId(Long trangthaiId) {
        this.trangthaiId = trangthaiId;
    }

    public Long getNhanvienThId() {
        return nhanvienThId;
    }

    public void setNhanvienThId(Long nhanvienThId) {
        this.nhanvienThId = nhanvienThId;
    }

    public Date getNgayYcStart() {
        return ngayYcStart;
    }

    public void setNgayYcStart(Date ngayYcStart) {
        this.ngayYcStart = ngayYcStart;
    }

    public Date getNgayYcEnd() {
        return ngayYcEnd;
    }

    public void setNgayYcEnd(Date ngayYcEnd) {
        this.ngayYcEnd = ngayYcEnd;
    }

    public Date getNgayYchtStart() {
        return ngayYchtStart;
    }

    public void setNgayYchtStart(Date ngayYchtStart) {
        this.ngayYchtStart = ngayYchtStart;
    }

    public Date getNgayYchtEnd() {
        return ngayYchtEnd;
    }

    public void setNgayYchtEnd(Date ngayYchtEnd) {
        this.ngayYchtEnd = ngayYchtEnd;
    }

    public Integer getPhanLoai() {
        return phanLoai;
    }

    public void setPhanLoai(Integer phanLoai) {
        this.phanLoai = phanLoai;
    }

    public String getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(String khachHang) {
        this.khachHang = khachHang;
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

    public String getTenRack() {
        return tenRack;
    }

    public void setTenRack(String tenRack) {
        this.tenRack = tenRack;
    }

    public String getLoaiRack() {
        return loaiRack;
    }

    public void setLoaiRack(String loaiRack) {
        this.loaiRack = loaiRack;
    }
}
