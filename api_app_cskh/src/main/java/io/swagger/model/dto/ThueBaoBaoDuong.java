package io.swagger.model.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Thuê bao bảo dưỡng
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class ThueBaoBaoDuong {

    private Long baoHongId;
    private String maTb;
    private String tenTb;
    private String diaChi;
    private int checked;

    public ThueBaoBaoDuong convertDataFromDatabase(ResultSet rs) throws SQLException {
        if (rs != null) {
            baoHongId = rs.getLong("baohong_id");
            maTb = rs.getString("ma_tb");
            tenTb = rs.getString("ten_tb");
            diaChi = rs.getString("diachi");
            checked = rs.getInt("checked");
            return this;
        } else {
            return null;
        }
    }

    public Long getBaoHongId() {
        return baoHongId;
    }

    public void setBaoHongId(Long baoHongId) {
        this.baoHongId = baoHongId;
    }

    public String getMaTb() {
        return maTb;
    }

    public void setMaTb(String maTb) {
        this.maTb = maTb;
    }

    public String getTenTb() {
        return tenTb;
    }

    public void setTenTb(String tenTb) {
        this.tenTb = tenTb;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }
}
