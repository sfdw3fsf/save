package vn.vnpt.vnptit.ecms.dto.hatang;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class MayAoDto {
    private long id;
    @NotNull(message = "Mã máy ảo CĐS ĐHIT không thể để trống")
    private String maMayAoCDSDHIT;
    @NotNull(message = "Tên máy ảo không thể để trống")
    private String ten;
    private Long haTangAoHoaId;
    private Long haTangIdgId;
    private Long loaiVaiTroId;
    private Short nguonYeuCau;
    private Long nguoiYeuCau;
    @NotNull(message = "Hệ điều hành không thể để trống")
    private Long heDieuHanhId;
    private Long haTangPhanLoaiId;
    private Long trangThaiMayAoId;
    private String ngayTao;
    private String ngayHetHan;
    private Long nguoiCapNhat;
    private String ngayCapNhat;
    private Long tinhGia;
    private String ghiChu;
    private Long hieuLuc;
    private Long donViQlyId;
    private Long phongBanQlyId;
    private Long nguoiQlyId;
    private String cumDichVuId;
    private Long spdvId;
    private Long donViChuQuanId;
    private Long vCPU;
    private Long vRAM;
    private Long dichVuIopsId;
    private Long dichVuThroughtputId;
    private Long gpu;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMaMayAoCDSDHIT() {
        return maMayAoCDSDHIT;
    }

    public void setMaMayAoCDSDHIT(String maMayAoCDSDHIT) {
        this.maMayAoCDSDHIT = maMayAoCDSDHIT;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Long getHaTangAoHoaId() {
        return haTangAoHoaId;
    }

    public void setHaTangAoHoaId(Long haTangAoHoaId) {
        this.haTangAoHoaId = haTangAoHoaId;
    }

    public Long getHaTangIdgId() {
        return haTangIdgId;
    }

    public void setHaTangIdgId(Long haTangIdgId) {
        this.haTangIdgId = haTangIdgId;
    }

    public Long getLoaiVaiTroId() {
        return loaiVaiTroId;
    }

    public void setLoaiVaiTroId(Long loaiVaiTroId) {
        this.loaiVaiTroId = loaiVaiTroId;
    }

    public Short getNguonYeuCau() {
        return nguonYeuCau;
    }

    public void setNguonYeuCau(Short nguonYeuCau) {
        this.nguonYeuCau = nguonYeuCau;
    }

    public Long getNguoiYeuCau() {
        return nguoiYeuCau;
    }

    public void setNguoiYeuCau(Long nguoiYeuCau) {
        this.nguoiYeuCau = nguoiYeuCau;
    }

    public Long getHeDieuHanhId() {
        return heDieuHanhId;
    }

    public void setHeDieuHanhId(Long heDieuHanhId) {
        this.heDieuHanhId = heDieuHanhId;
    }

    public Long getHaTangPhanLoaiId() {
        return haTangPhanLoaiId;
    }

    public void setHaTangPhanLoaiId(Long haTangPhanLoaiId) {
        this.haTangPhanLoaiId = haTangPhanLoaiId;
    }

    public Long getTrangThaiMayAoId() {
        return trangThaiMayAoId;
    }

    public void setTrangThaiMayAoId(Long trangThaiMayAoId) {
        this.trangThaiMayAoId = trangThaiMayAoId;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(String ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    public Long getNguoiCapNhat() {
        return nguoiCapNhat;
    }

    public void setNguoiCapNhat(Long nguoiCapNhat) {
        this.nguoiCapNhat = nguoiCapNhat;
    }

    public String getNgayCapNhat() {
        return ngayCapNhat;
    }

    public void setNgayCapNhat(String ngayCapNhat) {
        this.ngayCapNhat = ngayCapNhat;
    }

    public Long getTinhGia() {
        return tinhGia;
    }

    public void setTinhGia(Long tinhGia) {
        this.tinhGia = tinhGia;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Long getHieuLuc() {
        return hieuLuc;
    }

    public void setHieuLuc(Long hieuLuc) {
        this.hieuLuc = hieuLuc;
    }

    public Long getDonViQlyId() {
        return donViQlyId;
    }

    public void setDonViQlyId(Long donViQlyId) {
        this.donViQlyId = donViQlyId;
    }

    public Long getPhongBanQlyId() {
        return phongBanQlyId;
    }

    public void setPhongBanQlyId(Long phongBanQlyId) {
        this.phongBanQlyId = phongBanQlyId;
    }

    public Long getNguoiQlyId() {
        return nguoiQlyId;
    }

    public void setNguoiQlyId(Long nguoiQlyId) {
        this.nguoiQlyId = nguoiQlyId;
    }

    public String getCumDichVuId() {
        return cumDichVuId;
    }

    public void setCumDichVu(String cumDichVuId) {
        this.cumDichVuId = cumDichVuId;
    }

    public Long getSpdvId() {
        return spdvId;
    }

    public void setSpdvId(Long spdvId) {
        this.spdvId = spdvId;
    }

    public Long getDonViChuQuanId() {
        return donViChuQuanId;
    }

    public void setDonViChuQuanId(Long donViChuQuanId) {
        this.donViChuQuanId = donViChuQuanId;
    }

    public Long getvCPU() {
        return vCPU;
    }

    public void setvCPU(Long vCPU) {
        this.vCPU = vCPU;
    }

    public Long getvRAM() {
        return vRAM;
    }

    public void setvRAM(Long vRAM) {
        this.vRAM = vRAM;
    }

    public Long getDichVuIopsId() {
        return dichVuIopsId;
    }

    public void setDichVuIopsId(Long dichVuIopsId) {
        this.dichVuIopsId = dichVuIopsId;
    }

    public Long getDichVuThroughtputId() {
        return dichVuThroughtputId;
    }

    public void setDichVuThroughtputId(Long dichVuThroughtputId) {
        this.dichVuThroughtputId = dichVuThroughtputId;
    }

    public Long getGpu() {
        return gpu;
    }

    public void setGpu(Long gpu) {
        this.gpu = gpu;
    }
}
