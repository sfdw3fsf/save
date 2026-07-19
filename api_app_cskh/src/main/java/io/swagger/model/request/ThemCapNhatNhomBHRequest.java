package io.swagger.model.request;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Thêm cập nhật nhóm bán hàng request
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class ThemCapNhatNhomBHRequest {

    @JsonProperty("nhomBhId")
    private Long nhomBhId; // -1 (thêm mới)

    @JsonProperty("nhomBh")
    private String nhomBh;

    @JsonProperty("loaiNhomId")
    private int loaiNhomId;

    @JsonProperty("dotBhId")
    private Long dotBhId;

    @JsonProperty("diaDiem")
    private String diaDiem;

    @JsonProperty("doiTruongId")
    private Long doiTruongId; // (không insert truyền = -1)

    @JsonProperty("dsChiTieu")
    private ChiTieu[] dsChiTieu;

    @JsonProperty("dsDiaBan")
    private DiaBan[] dsDiaBan;

    @JsonProperty("dsNhanVien")
    private NhanVien[] dsNhanVien;

    public Long getNhomBhId() {
        return nhomBhId;
    }

    public void setNhomBhId(Long nhomBhId) {
        this.nhomBhId = nhomBhId;
    }

    public String getNhomBh() {
        return nhomBh;
    }

    public void setNhomBh(String nhomBh) {
        this.nhomBh = nhomBh;
    }

    public int getLoaiNhomId() {
        return loaiNhomId;
    }

    public void setLoaiNhomId(int loaiNhomId) {
        this.loaiNhomId = loaiNhomId;
    }

    public Long getDotBhId() {
        return dotBhId;
    }

    public void setDotBhId(Long dotBhId) {
        this.dotBhId = dotBhId;
    }

    public String getDiaDiem() {
        return diaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        this.diaDiem = diaDiem;
    }

    public Long getDoiTruongId() {
        return doiTruongId;
    }

    public void setDoiTruongId(Long doiTruongId) {
        this.doiTruongId = doiTruongId;
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

    public NhanVien[] getDsNhanVien() {
        return dsNhanVien;
    }

    public void setDsNhanVien(NhanVien[] dsNhanVien) {
        this.dsNhanVien = dsNhanVien;
    }
}

class NhanVien {

    @JsonProperty("nhanvien_id")
    private Long nhanvien_id;

    @JsonProperty("truongnhom")
    private int truongnhom;

    @JsonProperty("tyle")
    private int tyle;

    public Long getNhanvien_id() {
        return nhanvien_id;
    }

    public void setNhanvien_id(Long nhanvien_id) {
        this.nhanvien_id = nhanvien_id;
    }

    public int getTruongnhom() {
        return truongnhom;
    }

    public void setTruongnhom(int truongnhom) {
        this.truongnhom = truongnhom;
    }

    public int getTyle() {
        return tyle;
    }

    public void setTyle(int tyle) {
        this.tyle = tyle;
    }
}
