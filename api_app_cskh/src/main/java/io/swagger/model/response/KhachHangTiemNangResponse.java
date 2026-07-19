package io.swagger.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.dto.TTKhachHangTiemNang;

import java.util.List;

/**
 * KhachHangTiemNangResponse
 */
public class KhachHangTiemNangResponse {
    @JsonProperty("dsKhachHangTiemNang")
    protected List<TTKhachHangTiemNang> dsKhachHangTiemNang;
    @JsonProperty("dsThangHetHan")
    protected List<String> dsThangHetHan;

    public List<TTKhachHangTiemNang> getDsKhachHangTiemNang() {
        return dsKhachHangTiemNang;
    }

    public void setDsKhachHangTiemNang(List<TTKhachHangTiemNang> dsKhachHangTiemNang) {
        this.dsKhachHangTiemNang = dsKhachHangTiemNang;
    }

    public List<String> getDsThangHetHan() {
        return dsThangHetHan;
    }

    public void setDsThangHetHan(List<String> dsThangHetHan) {
        this.dsThangHetHan = dsThangHetHan;
    }
}
