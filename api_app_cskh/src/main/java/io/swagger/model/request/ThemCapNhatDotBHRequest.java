package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Thêm cập nhật đợt bán hàng request
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class ThemCapNhatDotBHRequest {

    @JsonProperty("dotBhId")
    private Long dotBhId; // -1 = insert

    @JsonProperty("tenDotBh")
    private String tenDotBh;

    @JsonProperty("htbhId")
    private int htbhId;

    @JsonProperty("noiDung")
    private String noiDung;

    @JsonProperty("ngayBd")
    private String ngayBd;

    @JsonProperty("ngayKt")
    private String ngayKt;

    @JsonProperty("dsChiTieu")
    private ChiTieu[] dsChiTieu;

    @JsonProperty("dsDiaBan")
    private DiaBan[] dsDiaBan;

    @JsonProperty("dsBaoDuong")
    private BaoDuong[] dsBaoDuong;


    public Long getDotBhId() {
        return dotBhId;
    }

    public void setDotBhId(Long dotBhId) {
        this.dotBhId = dotBhId;
    }

    public String getTenDotBh() {
        return tenDotBh;
    }

    public void setTenDotBh(String tenDotBh) {
        this.tenDotBh = tenDotBh;
    }

    public int getHtbhId() {
        return htbhId;
    }

    public void setHtbhId(int htbhId) {
        this.htbhId = htbhId;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getNgayBd() {
        return ngayBd;
    }

    public void setNgayBd(String ngayBd) {
        this.ngayBd = ngayBd;
    }

    public String getNgayKt() {
        return ngayKt;
    }

    public void setNgayKt(String ngayKt) {
        this.ngayKt = ngayKt;
    }

    public ChiTieu[] getDsChiTieu() {
        return dsChiTieu;
    }

    public void setDsChiTieu(ChiTieu[] dsChiTieu) {
        this.dsChiTieu = dsChiTieu;
    }

    public DiaBan[] getDsDiaBan() {
        return dsDiaBan;
    }

    public void setDsDiaBan(DiaBan[] dsDiaBan) {
        this.dsDiaBan = dsDiaBan;
    }

    public BaoDuong[] getDsBaoDuong() {
        return dsBaoDuong;
    }

    public void setDsBaoDuong(BaoDuong[] dsBaoDuong) {
        this.dsBaoDuong = dsBaoDuong;
    }
}

class ChiTieu {
    @JsonProperty("chitieu_id")
    private int chitieu_id;

    @JsonProperty("soluong")
    private int soluong;

    public int getChitieu_id() {
        return chitieu_id;
    }

    public void setChitieu_id(int chitieu_id) {
        this.chitieu_id = chitieu_id;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}

class DiaBan {

    @JsonProperty("khuvuc_id")
    private Long khuvuc_id;

    @JsonProperty("ngay_bd")
    private String ngay_bd;

    @JsonProperty("ngay_kt")
    private String ngay_kt;

    public Long getKhuvuc_id() {
        return khuvuc_id;
    }

    public void setKhuvuc_id(Long khuvuc_id) {
        this.khuvuc_id = khuvuc_id;
    }

    public String getNgay_bd() {
        return ngay_bd;
    }

    public void setNgay_bd(String ngay_bd) {
        this.ngay_bd = ngay_bd;
    }

    public String getNgay_kt() {
        return ngay_kt;
    }

    public void setNgay_kt(String ngay_kt) {
        this.ngay_kt = ngay_kt;
    }

}

class BaoDuong {

    @JsonProperty("baohong_id")
    private Long baohong_id;

    public Long getBaohong_id() {
        return baohong_id;
    }

    public void setBaohong_id(Long baohong_id) {
        this.baohong_id = baohong_id;
    }
}
