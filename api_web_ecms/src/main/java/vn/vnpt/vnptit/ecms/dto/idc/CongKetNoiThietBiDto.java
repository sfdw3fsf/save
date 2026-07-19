package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;

public class CongKetNoiThietBiDto implements Serializable {

    private Long thietBiId;
    private Long phanLoai;
    private Integer soLuong;
    private String ten;
    private Integer batDauTu;

    // Chi tiết
    private Long portId;
    private Long dongDinhDanh;
    private Long dienApDinhDanh;
    private Long loaiNguonDien;
    private Long mucDichSuDung;
    private Long trangThaiPort;
    private Long trangThaiKetNoi;
    private Long thietBiKetNoiId;
    private String ip;
    private String bangThong;
    private Long module;
    private Long chuanDauNoi;
    private Long loaiCong;
    private String ports;

    public CongKetNoiThietBiDto() {
    }

    public CongKetNoiThietBiDto(Long thietBiId, Long phanLoai, Integer soLuong, String ten, Integer batDauTu,
            Long portId,
            Long dongDinhDanh,
            Long dienApDinhDanh, Long loaiNguonDien, Long mucDichSuDung, Long trangThaiPort, Long trangThaiKetNoi,
            Long thietBiKetNoiId, String ip, String bangThong, Long module, Long chuanDauNoi, Long loaiCong,
            String ports) {
        this.thietBiId = thietBiId;
        this.phanLoai = phanLoai;
        this.soLuong = soLuong;
        this.ten = ten;
        this.batDauTu = batDauTu;
        this.portId = portId;
        this.dongDinhDanh = dongDinhDanh;
        this.dienApDinhDanh = dienApDinhDanh;
        this.loaiNguonDien = loaiNguonDien;
        this.mucDichSuDung = mucDichSuDung;
        this.trangThaiPort = trangThaiPort;
        this.trangThaiKetNoi = trangThaiKetNoi;
        this.thietBiKetNoiId = thietBiKetNoiId;
        this.ip = ip;
        this.bangThong = bangThong;
        this.module = module;
        this.chuanDauNoi = chuanDauNoi;
        this.loaiCong = loaiCong;
        this.ports = ports;
    }

    public Long getThietBiId() {
        return thietBiId;
    }

    public void setThietBiId(Long thietBiId) {
        this.thietBiId = thietBiId;
    }

    public Long getPhanLoai() {
        return phanLoai;
    }

    public void setPhanLoai(Long phanLoai) {
        this.phanLoai = phanLoai;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Integer getBatDauTu() {
        return batDauTu;
    }

    public void setBatDauTu(Integer batDauTu) {
        this.batDauTu = batDauTu;
    }

    public Long getPortId() {
        return portId;
    }

    public void setPortId(Long portId) {
        this.portId = portId;
    }

    public Long getDongDinhDanh() {
        return dongDinhDanh;
    }

    public void setDongDinhDanh(Long dongDinhDanh) {
        this.dongDinhDanh = dongDinhDanh;
    }

    public Long getDienApDinhDanh() {
        return dienApDinhDanh;
    }

    public void setDienApDinhDanh(Long dienApDinhDanh) {
        this.dienApDinhDanh = dienApDinhDanh;
    }

    public Long getLoaiNguonDien() {
        return loaiNguonDien;
    }

    public void setLoaiNguonDien(Long loaiNguonDien) {
        this.loaiNguonDien = loaiNguonDien;
    }

    public Long getMucDichSuDung() {
        return mucDichSuDung;
    }

    public void setMucDichSuDung(Long mucDichSuDung) {
        this.mucDichSuDung = mucDichSuDung;
    }

    public Long getTrangThaiPort() {
        return trangThaiPort;
    }

    public void setTrangThaiPort(Long trangThaiPort) {
        this.trangThaiPort = trangThaiPort;
    }

    public Long getTrangThaiKetNoi() {
        return trangThaiKetNoi;
    }

    public void setTrangThaiKetNoi(Long trangThaiKetNoi) {
        this.trangThaiKetNoi = trangThaiKetNoi;
    }

    public Long getThietBiKetNoiId() {
        return thietBiKetNoiId;
    }

    public void setThietBiKetNoiId(Long thietBiKetNoiId) {
        this.thietBiKetNoiId = thietBiKetNoiId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBangThong() {
        return bangThong;
    }

    public void setBangThong(String bangThong) {
        this.bangThong = bangThong;
    }

    public Long getModule() {
        return module;
    }

    public void setModule(Long module) {
        this.module = module;
    }

    public Long getChuanDauNoi() {
        return chuanDauNoi;
    }

    public void setChuanDauNoi(Long chuanDauNoi) {
        this.chuanDauNoi = chuanDauNoi;
    }

    public Long getLoaiCong() {
        return loaiCong;
    }

    public void setLoaiCong(Long loaiCong) {
        this.loaiCong = loaiCong;
    }

    public String getPorts() {
        return ports;
    }

    public void setPorts(String ports) {
        this.ports = ports;
    }
}
