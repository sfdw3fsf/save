package vn.vnpt.vnptit.ecms.dto.hatang;

import java.io.Serializable;
import java.util.Date;

public class TimKiemThanhPhanDto implements Serializable {

    private Long type;

    // Type 2: Máy ảo
    private String ip;
    private String tenMayAo;
    private Long loaiMayAoId;
    private Long tinhPhi;
    private Long doiTuongId;
    private Long cumHaTangId;
    private Long trangThaiId;
    private Date ngayTaoTu;
    private Date ngayTaoDen;
    private String thuocVpc;
    private Long donViQlId;
    private Date ngayHetHanTu;
    private Date ngayHetHanDen;
    private Long donViChuQuanId;
    private String caNhanQuanLy;

    // Type 3: Thiết bị vật lý
    private String khongGian;
    private String rack;
    private Long soHuuId;
    private Long loaiThietBiId;
    private Long hangSxId;
    private String serialNumber;
    private String tenThietBi;
    private String idChiTiet;
    private String theTaiSan;

    // Type 4 & 5: Dịch vụ IDG / Dịch vụ hạ tầng khác
    private String maDichVu;
    private String tenDichVu;
    private Long datTaiIdcId;
    private Date ngayCapTu;
    private Date ngayCapDen;
    private Long loaiLuuTruId;

    // Type 6: VPC
    private String maVpc;
    private String tenVpc;
    private Long loaiVpcId;
    private Long hieuLucId;
    private Long donViYeuCauId;
    private String phieuYeuCau;
    private Long tinhPhiId;

    public TimKiemThanhPhanDto() {
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTenMayAo() {
        return tenMayAo;
    }

    public void setTenMayAo(String tenMayAo) {
        this.tenMayAo = tenMayAo;
    }

    public Long getLoaiMayAoId() {
        return loaiMayAoId;
    }

    public void setLoaiMayAoId(Long loaiMayAoId) {
        this.loaiMayAoId = loaiMayAoId;
    }

    public Long getTinhPhi() {
        return tinhPhi;
    }

    public void setTinhPhi(Long tinhPhi) {
        this.tinhPhi = tinhPhi;
    }

    public Long getDoiTuongId() {
        return doiTuongId;
    }

    public void setDoiTuongId(Long doiTuongId) {
        this.doiTuongId = doiTuongId;
    }

    public Long getCumHaTangId() {
        return cumHaTangId;
    }

    public void setCumHaTangId(Long cumHaTangId) {
        this.cumHaTangId = cumHaTangId;
    }

    public Long getTrangThaiId() {
        return trangThaiId;
    }

    public void setTrangThaiId(Long trangThaiId) {
        this.trangThaiId = trangThaiId;
    }

    public Date getNgayTaoTu() {
        return ngayTaoTu;
    }

    public void setNgayTaoTu(Date ngayTaoTu) {
        this.ngayTaoTu = ngayTaoTu;
    }

    public Date getNgayTaoDen() {
        return ngayTaoDen;
    }

    public void setNgayTaoDen(Date ngayTaoDen) {
        this.ngayTaoDen = ngayTaoDen;
    }

    public String getThuocVpc() {
        return thuocVpc;
    }

    public void setThuocVpc(String thuocVpc) {
        this.thuocVpc = thuocVpc;
    }

    public Long getDonViQlId() {
        return donViQlId;
    }

    public void setDonViQlId(Long donViQlId) {
        this.donViQlId = donViQlId;
    }

    public Date getNgayHetHanTu() {
        return ngayHetHanTu;
    }

    public void setNgayHetHanTu(Date ngayHetHanTu) {
        this.ngayHetHanTu = ngayHetHanTu;
    }

    public Date getNgayHetHanDen() {
        return ngayHetHanDen;
    }

    public void setNgayHetHanDen(Date ngayHetHanDen) {
        this.ngayHetHanDen = ngayHetHanDen;
    }

    public Long getDonViChuQuanId() {
        return donViChuQuanId;
    }

    public void setDonViChuQuanId(Long donViChuQuanId) {
        this.donViChuQuanId = donViChuQuanId;
    }

    public String getCaNhanQuanLy() {
        return caNhanQuanLy;
    }

    public void setCaNhanQuanLy(String caNhanQuanLy) {
        this.caNhanQuanLy = caNhanQuanLy;
    }

    public String getKhongGian() {
        return khongGian;
    }

    public void setKhongGian(String khongGian) {
        this.khongGian = khongGian;
    }

    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }

    public Long getSoHuuId() {
        return soHuuId;
    }

    public void setSoHuuId(Long soHuuId) {
        this.soHuuId = soHuuId;
    }

    public Long getLoaiThietBiId() {
        return loaiThietBiId;
    }

    public void setLoaiThietBiId(Long loaiThietBiId) {
        this.loaiThietBiId = loaiThietBiId;
    }

    public Long getHangSxId() {
        return hangSxId;
    }

    public void setHangSxId(Long hangSxId) {
        this.hangSxId = hangSxId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getTenThietBi() {
        return tenThietBi;
    }

    public void setTenThietBi(String tenThietBi) {
        this.tenThietBi = tenThietBi;
    }

    public String getIdChiTiet() {
        return idChiTiet;
    }

    public void setIdChiTiet(String idChiTiet) {
        this.idChiTiet = idChiTiet;
    }

    public String getTheTaiSan() {
        return theTaiSan;
    }

    public void setTheTaiSan(String theTaiSan) {
        this.theTaiSan = theTaiSan;
    }

    public String getMaDichVu() {
        return maDichVu;
    }

    public void setMaDichVu(String maDichVu) {
        this.maDichVu = maDichVu;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public Long getDatTaiIdcId() {
        return datTaiIdcId;
    }

    public void setDatTaiIdcId(Long datTaiIdcId) {
        this.datTaiIdcId = datTaiIdcId;
    }

    public Date getNgayCapTu() {
        return ngayCapTu;
    }

    public void setNgayCapTu(Date ngayCapTu) {
        this.ngayCapTu = ngayCapTu;
    }

    public Date getNgayCapDen() {
        return ngayCapDen;
    }

    public void setNgayCapDen(Date ngayCapDen) {
        this.ngayCapDen = ngayCapDen;
    }

    public Long getLoaiLuuTruId() {
        return loaiLuuTruId;
    }

    public void setLoaiLuuTruId(Long loaiLuuTruId) {
        this.loaiLuuTruId = loaiLuuTruId;
    }

    public String getMaVpc() {
        return maVpc;
    }

    public void setMaVpc(String maVpc) {
        this.maVpc = maVpc;
    }

    public String getTenVpc() {
        return tenVpc;
    }

    public void setTenVpc(String tenVpc) {
        this.tenVpc = tenVpc;
    }

    public Long getLoaiVpcId() {
        return loaiVpcId;
    }

    public void setLoaiVpcId(Long loaiVpcId) {
        this.loaiVpcId = loaiVpcId;
    }

    public Long getHieuLucId() {
        return hieuLucId;
    }

    public void setHieuLucId(Long hieuLucId) {
        this.hieuLucId = hieuLucId;
    }

    public Long getDonViYeuCauId() {
        return donViYeuCauId;
    }

    public void setDonViYeuCauId(Long donViYeuCauId) {
        this.donViYeuCauId = donViYeuCauId;
    }

    public String getPhieuYeuCau() {
        return phieuYeuCau;
    }

    public void setPhieuYeuCau(String phieuYeuCau) {
        this.phieuYeuCau = phieuYeuCau;
    }

    public Long getTinhPhiId() {
        return tinhPhiId;
    }

    public void setTinhPhiId(Long tinhPhiId) {
        this.tinhPhiId = tinhPhiId;
    }
}

