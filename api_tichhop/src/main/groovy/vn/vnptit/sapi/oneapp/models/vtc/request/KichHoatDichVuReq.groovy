package vn.vnptit.sapi.oneapp.models.vtc.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import vn.vnptit.sapi.oneapp.models.vtc.models.BhHoGiaDinh
import vn.vnptit.sapi.oneapp.models.vtc.models.BhHoTroVienPhi
import vn.vnptit.sapi.oneapp.models.vtc.models.BhHocSinhSinhVien
import vn.vnptit.sapi.oneapp.models.vtc.models.BhTaiNanHoGiaDinh
import vn.vnptit.sapi.oneapp.models.vtc.models.BhXeCoGioi
import vn.vnptit.sapi.oneapp.models.vtc.models.ThongTinQuanLyBh

@JsonIgnoreProperties(["metaClass"])
class KichHoatDichVuReq {
    @JsonProperty("supplyCode")
    public String supplyCode
    @JsonProperty("phone")
    public String phone
    @JsonProperty("email")
    public String email
    @JsonProperty("fullname")
    public String fullname
    @JsonProperty("address")
    public String address
    @JsonProperty("productCode")
    public String productCode
    @JsonProperty("custCode")
    public String custCode

    @JsonProperty("bhHocSinhSinhVien")
    public BhHocSinhSinhVien bhHocSinhSinhVien

    @JsonProperty("bhHoGiaDinh")
    public BhHoGiaDinh bhHoGiaDinh

    @JsonProperty("bhXeCoGioi")
    public BhXeCoGioi bhXeCoGioi

    @JsonProperty("bhHoTroVienPhi")
    public BhHoTroVienPhi bhHoTroVienPhi

    @JsonProperty("bhTaiNanHoGiaDinh")
    public BhTaiNanHoGiaDinh bhTaiNanHoGiaDinh

    @JsonProperty("thongTinQuanLyBh")
    public ThongTinQuanLyBh thongTinQuanLyBh

    String getSupplyCode() {
        return supplyCode
    }

    void setSupplyCode(String supplyCode) {
        this.supplyCode = supplyCode
    }

    String getPhone() {
        return phone
    }

    void setPhone(String phone) {
        this.phone = phone
    }

    String getEmail() {
        return email
    }

    void setEmail(String email) {
        this.email = email
    }

    String getFullname() {
        return fullname
    }

    void setFullname(String fullname) {
        this.fullname = fullname
    }

    String getAddress() {
        return address
    }

    void setAddress(String address) {
        this.address = address
    }

    String getProductCode() {
        return productCode
    }

    void setProductCode(String productCode) {
        this.productCode = productCode
    }

    String getCustCode() {
        return custCode
    }

    void setCustCode(String custCode) {
        this.custCode = custCode
    }

    BhHocSinhSinhVien getBhHocSinhSinhVien() {
        return bhHocSinhSinhVien
    }

    void setBhHocSinhSinhVien(BhHocSinhSinhVien bhHocSinhSinhVien) {
        this.bhHocSinhSinhVien = bhHocSinhSinhVien
    }

    BhHoGiaDinh getBhHoGiaDinh() {
        return bhHoGiaDinh
    }

    void setBhHoGiaDinh(BhHoGiaDinh bhHoGiaDinh) {
        this.bhHoGiaDinh = bhHoGiaDinh
    }

    BhXeCoGioi getBhXeCoGioi() {
        return bhXeCoGioi
    }

    void setBhXeCoGioi(BhXeCoGioi bhXeCoGioi) {
        this.bhXeCoGioi = bhXeCoGioi
    }

    BhHoTroVienPhi getBhHoTroVienPhi() {
        return bhHoTroVienPhi
    }

    void setBhHoTroVienPhi(BhHoTroVienPhi bhHoTroVienPhi) {
        this.bhHoTroVienPhi = bhHoTroVienPhi
    }

    BhTaiNanHoGiaDinh getBhTaiNanHoGiaDinh() {
        return bhTaiNanHoGiaDinh
    }

    void setBhTaiNanHoGiaDinh(BhTaiNanHoGiaDinh bhTaiNanHoGiaDinh) {
        this.bhTaiNanHoGiaDinh = bhTaiNanHoGiaDinh
    }

    ThongTinQuanLyBh getThongTinQuanLyBh() {
        return thongTinQuanLyBh
    }

    void setThongTinQuanLyBh(ThongTinQuanLyBh thongTinQuanLyBh) {
        this.thongTinQuanLyBh = thongTinQuanLyBh
    }
}
