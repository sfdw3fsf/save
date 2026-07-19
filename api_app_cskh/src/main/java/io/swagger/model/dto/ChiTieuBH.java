package io.swagger.model.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import vn.vnptit.sapi.oneapp.util.Constant;

import javax.annotation.Generated;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Chỉ tiêu bán hàng
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class ChiTieuBH {

    private int chiTieuId;
    private int soLuong;
    private String chiTieuCd;

    public ChiTieuBH convertDataFromDatabase(ResultSet rs) throws SQLException {
        if(rs != null) {
            chiTieuId = rs.getInt("chitieu_id");
            soLuong = rs.getInt("soluong");
            chiTieuCd = rs.getString("chitieu_cd");
            return this;
        } else {
            return null;
        }
    }

    public int getChiTieuId() {
        return chiTieuId;
    }

    public void setChiTieuId(int chiTieuId) {
        this.chiTieuId = chiTieuId;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getChiTieuCd() {
        return chiTieuCd;
    }

    public void setChiTieuCd(String chiTieuCd) {
        this.chiTieuCd = chiTieuCd;
    }
}
