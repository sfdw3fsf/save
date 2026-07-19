package io.swagger.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import vn.vnptit.sapi.oneapp.util.Utils;

import javax.annotation.Generated;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Nhóm bán hàng
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class NhomBanHang {

    private Long nhomBhId;
    private String nhomBh;
    private Long dotBhId;
    private String loaiNhomBh;
    private String tenNv;
    private String tenKv;
    private String diaDiem;
    private Long loaiNhomId;
    private Long nhanVienId;

    public NhomBanHang convertDataFromDatabase(ResultSet rs) throws SQLException {
        if(rs != null) {
            nhomBhId = rs.getLong("nhombh_id");
            nhomBh = rs.getString("nhom_bh");
            dotBhId = rs.getLong("dotbh_id");
            loaiNhomBh = rs.getString("loainhom_bh");
            tenNv = rs.getString("tennv");
            tenKv = rs.getString("ten_kv");
            diaDiem = rs.getString("diadiem");
            loaiNhomId = Utils.getLongDataByKey(rs, "loainhom_id");
            nhanVienId = Utils.getLongDataByKey(rs, "nhanvien_id");
            return this;
        } else {
            return null;
        }
    }

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

    public Long getDotBhId() {
        return dotBhId;
    }

    public void setDotBhId(Long dotBhId) {
        this.dotBhId = dotBhId;
    }

    public String getLoaiNhomBh() {
        return loaiNhomBh;
    }

    public void setLoaiNhomBh(String loaiNhomBh) {
        this.loaiNhomBh = loaiNhomBh;
    }

    public String getTenNv() {
        return tenNv;
    }

    public void setTenNv(String tenNv) {
        this.tenNv = tenNv;
    }

    public String getTenKv() {
        return tenKv;
    }

    public void setTenKv(String tenKv) {
        this.tenKv = tenKv;
    }

    public String getDiaDiem() {
        return diaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        this.diaDiem = diaDiem;
    }

    public Long getLoaiNhomId() {
        return loaiNhomId;
    }

    public void setLoaiNhomId(Long loaiNhomId) {
        this.loaiNhomId = loaiNhomId;
    }

    public Long getNhanVienId() {
        return nhanVienId;
    }

    public void setNhanVienId(Long nhanVienId) {
        this.nhanVienId = nhanVienId;
    }
}
