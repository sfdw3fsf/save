package io.swagger.model.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Thông tin Vasc
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class Vasc {

    private String trangThai;

    private String system;

    private String serialStb;

    private String pass;

    private String cellNo;

    private String mac;

    private String portDsl;

    private String deviceName;

    private String maHd;

    private String loaiKh;

    private String ngayKyHd;

    private String goiCuoc;

    private String ngayTinhCuoc;

    private String tenKh;

    private String ngaySinh;

    private String sdt;

    private String gioiTinh;

    private String diaChi;

    private String tinh;

    private String email;

    private String fax;

    private String soCmt;

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getSerialStb() {
        return serialStb;
    }

    public void setSerialStb(String serialStb) {
        this.serialStb = serialStb;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCellNo() {
        return cellNo;
    }

    public void setCellNo(String cellNo) {
        this.cellNo = cellNo;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getPortDsl() {
        return portDsl;
    }

    public void setPortDsl(String portDsl) {
        this.portDsl = portDsl;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getMaHd() {
        return maHd;
    }

    public void setMaHd(String maHd) {
        this.maHd = maHd;
    }

    public String getLoaiKh() {
        return loaiKh;
    }

    public void setLoaiKh(String loaiKh) {
        this.loaiKh = loaiKh;
    }

    public String getNgayKyHd() {
        return ngayKyHd;
    }

    public void setNgayKyHd(String ngayKyHd) {
        this.ngayKyHd = ngayKyHd;
    }

    public String getGoiCuoc() {
        return goiCuoc;
    }

    public void setGoiCuoc(String goiCuoc) {
        this.goiCuoc = goiCuoc;
    }

    public String getNgayTinhCuoc() {
        return ngayTinhCuoc;
    }

    public void setNgayTinhCuoc(String ngayTinhCuoc) {
        this.ngayTinhCuoc = ngayTinhCuoc;
    }

    public String getTenKh() {
        return tenKh;
    }

    public void setTenKh(String tenKh) {
        this.tenKh = tenKh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getTinh() {
        return tinh;
    }

    public void setTinh(String tinh) {
        this.tinh = tinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getSoCmt() {
        return soCmt;
    }

    public void setSoCmt(String soCmt) {
        this.soCmt = soCmt;
    }
}
