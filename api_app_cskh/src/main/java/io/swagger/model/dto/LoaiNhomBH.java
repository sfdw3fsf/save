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
 * Loại nhóm bán hàng
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class LoaiNhomBH {

    private int loaiNhomId;
    private String loaiNhomBh;
    private int slThanhVien;

    public LoaiNhomBH convertDataFromDatabase(ResultSet rs) throws SQLException {
        if(rs != null) {
            loaiNhomId = rs.getInt("LOAINHOM_ID");
            loaiNhomBh = rs.getString("LOAINHOM_BH");
            slThanhVien = rs.getInt("SL_THANHVIEN");
            return this;
        } else {
            return null;
        }
    }

    public int getLoaiNhomId() {
        return loaiNhomId;
    }

    public void setLoaiNhomId(int loaiNhomId) {
        this.loaiNhomId = loaiNhomId;
    }

    public String getLoaiNhomBh() {
        return loaiNhomBh;
    }

    public void setLoaiNhomBh(String loaiNhomBh) {
        this.loaiNhomBh = loaiNhomBh;
    }

    public int getSlThanhVien() {
        return slThanhVien;
    }

    public void setSlThanhVien(int slThanhVien) {
        this.slThanhVien = slThanhVien;
    }
}
