package vn.vnpt.vnptit.ecms.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;

public class ChuyenDSKhoaMoMaySangHopDongKhoaMoInput implements Serializable {

    @NotEmpty
    private List<HopDongKhoaMoDTO> ds;
    private Long kieuYC;

    private Long chieuKhoa;

    private List<Long> loaiTBId;

    private Long dichVuVTId;

    private Long donViId;
    private String ghiChu;
    private Long loaiChem;

    private Long quyTrinhID;

    public List<HopDongKhoaMoDTO> getDs() {
        return ds;
    }

    public void setDs(List<HopDongKhoaMoDTO> ds) {
        this.ds = ds;
    }

    public Long getKieuYC() {
        return kieuYC;
    }

    public void setKieuYC(Long kieuYC) {
        this.kieuYC = kieuYC;
    }

    public Long getChieuKhoa() {
        return chieuKhoa;
    }

    public void setChieuKhoa(Long chieuKhoa) {
        this.chieuKhoa = chieuKhoa;
    }

    public List<Long> getLoaiTBId() {
        return loaiTBId;
    }

    public void setLoaiTBId(List<Long> loaiTBId) {
        this.loaiTBId = loaiTBId;
    }

    public Long getDichVuVTId() {
        return dichVuVTId;
    }

    public void setDichVuVTId(Long dichVuVTId) {
        this.dichVuVTId = dichVuVTId;
    }

    public Long getDonViId() {
        return donViId;
    }

    public void setDonViId(Long donViId) {
        this.donViId = donViId;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Long getLoaiChem() {
        return loaiChem;
    }

    public void setLoaiChem(Long loaiChem) {
        this.loaiChem = loaiChem;
    }

    public Long getQuyTrinhID() {
        return quyTrinhID;
    }

    public void setQuyTrinhID(Long quyTrinhID) {
        this.quyTrinhID = quyTrinhID;
    }
}
