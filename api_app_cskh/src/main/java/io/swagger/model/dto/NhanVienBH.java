package io.swagger.model.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Nhân viên bán hàng
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class NhanVienBH {

    private Long nhanVienId;
    private String tenNv;
    private int truongNhom;
    private int tyle;

    public NhanVienBH convertDataFromDatabase(ResultSet rs) throws SQLException {
        if (rs != null) {
            nhanVienId = rs.getLong("NHANVIEN_ID");
            tenNv = rs.getString("TENNV");
            truongNhom = rs.getInt("TRUONGNHOM");
            tyle = rs.getInt("TYLE");
            return this;
        } else {
            return null;
        }
    }

    public Long getNhanVienId() {
        return nhanVienId;
    }

    public void setNhanVienId(Long nhanVienId) {
        this.nhanVienId = nhanVienId;
    }

    public String getTenNv() {
        return tenNv;
    }

    public void setTenNv(String tenNv) {
        this.tenNv = tenNv;
    }

    public int getTruongNhom() {
        return truongNhom;
    }

    public void setTruongNhom(int truongNhom) {
        this.truongNhom = truongNhom;
    }

    public int getTyle() {
        return tyle;
    }

    public void setTyle(int tyle) {
        this.tyle = tyle;
    }
}
