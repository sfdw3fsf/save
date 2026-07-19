package vn.vnpt.vnptit.ecms.dto.idc;

import java.util.Date;

public class DanhSachCongViecV2Dto {
    private Integer loaicvId;
    private Integer donviId;
    private Integer idcId;
    private Integer nhanvienId;
    private Integer phanloai; // Khách hàng: 1; Nội bộ: 2
    private Date ycBatdau;
    private Date ycKetthuc;
    private Date ychtBatdau;
    private Date ychtKetthuc;
    private String khachhang; // ma_tb, ma_gd, so_hd, ten_tb
    private String congviec; // ma_congviec, ten_congviec, noidung
    private Integer trangthaiId;

    public DanhSachCongViecV2Dto() {
    }

    public DanhSachCongViecV2Dto(Integer loaicvId, Integer donviId, Integer idcId, 
                                Integer nhanvienId, Integer phanloai, Date ycBatdau, 
                                Date ycKetthuc, Date ychtBatdau, Date ychtKetthuc, 
                                String khachhang, String congviec, Integer trangthaiId) {
        this.loaicvId = loaicvId;
        this.donviId = donviId;
        this.idcId = idcId;
        this.nhanvienId = nhanvienId;
        this.phanloai = phanloai;
        this.ycBatdau = ycBatdau;
        this.ycKetthuc = ycKetthuc;
        this.ychtBatdau = ychtBatdau;
        this.ychtKetthuc = ychtKetthuc;
        this.khachhang = khachhang;
        this.congviec = congviec;
        this.trangthaiId = trangthaiId;
    }

    public Integer getLoaicvId() {
        return loaicvId;
    }

    public void setLoaicvId(Integer loaicvId) {
        this.loaicvId = loaicvId;
    }

    public Integer getDonviId() {
        return donviId;
    }

    public void setDonviId(Integer donviId) {
        this.donviId = donviId;
    }

    public Integer getIdcId() {
        return idcId;
    }

    public void setIdcId(Integer idcId) {
        this.idcId = idcId;
    }

    public Integer getNhanvienId() {
        return nhanvienId;
    }

    public void setNhanvienId(Integer nhanvienId) {
        this.nhanvienId = nhanvienId;
    }

    public Integer getPhanloai() {
        return phanloai;
    }

    public void setPhanloai(Integer phanloai) {
        this.phanloai = phanloai;
    }

    public Date getYcBatdau() {
        return ycBatdau;
    }

    public void setYcBatdau(Date ycBatdau) {
        this.ycBatdau = ycBatdau;
    }

    public Date getYcKetthuc() {
        return ycKetthuc;
    }

    public void setYcKetthuc(Date ycKetthuc) {
        this.ycKetthuc = ycKetthuc;
    }

    public Date getYchtBatdau() {
        return ychtBatdau;
    }

    public void setYchtBatdau(Date ychtBatdau) {
        this.ychtBatdau = ychtBatdau;
    }

    public Date getYchtKetthuc() {
        return ychtKetthuc;
    }

    public void setYchtKetthuc(Date ychtKetthuc) {
        this.ychtKetthuc = ychtKetthuc;
    }

    public String getKhachhang() {
        return khachhang;
    }

    public void setKhachhang(String khachhang) {
        this.khachhang = khachhang;
    }

    public String getCongviec() {
        return congviec;
    }

    public void setCongviec(String congviec) {
        this.congviec = congviec;
    }

    public Integer getTrangthaiId() {
        return trangthaiId;
    }

    public void setTrangthaiId(Integer trangthaiId) {
        this.trangthaiId = trangthaiId;
    }
}
