package io.swagger.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Khu vực tác chiến
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class KhuVucTacChien {

    private Long khuVucId;
    private String maKv;
    private String tenKv;
    private int checked;

    public KhuVucTacChien convertDataFromDatabase(ResultSet rs) throws SQLException {
        if(rs != null) {
            khuVucId = rs.getLong("khuvuc_id");
            maKv = rs.getString("ma_kv");
            tenKv = rs.getString("ten_kv");
            checked = rs.getInt("checked");
            return this;
        } else {
            return null;
        }
    }

    public Long getKhuVucId() {
        return khuVucId;
    }

    public void setKhuVucId(Long khuVucId) {
        this.khuVucId = khuVucId;
    }

    public String getMaKv() {
        return maKv;
    }

    public void setMaKv(String maKv) {
        this.maKv = maKv;
    }

    public String getTenKv() {
        return tenKv;
    }

    public void setTenKv(String tenKv) {
        this.tenKv = tenKv;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }
}
