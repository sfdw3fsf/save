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
 * Khu vực tác chiến đợt bán hàng
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class KhuVucTacChienDotBH {

    private Long khuVucId;
    private Long dotBhId;
    private String ngayBd;
    private String ngayKt;
    private String tenKv;
    private String maKv;


    public KhuVucTacChienDotBH convertDataFromDatabase(ResultSet rs) throws SQLException {
        if (rs != null) {
            DateFormat df = new SimpleDateFormat(Constant.FORMAT_DATETIME);
            Date date;
            khuVucId = rs.getLong("khuvuc_id");
            try {
                dotBhId = rs.getLong("dotbh_id");
            } catch (Exception e) {
                dotBhId = null;
            }
            try {
                date = rs.getDate("ngay_bd");
                ngayBd = df.format(date);
            } catch (Exception e) {
                ngayBd = "";
            }
            try {
                date = rs.getDate("ngay_kt");
                ngayKt = df.format(date);
            } catch (Exception e) {
                ngayKt = "";
            }
            tenKv = rs.getString("ten_kv");
            maKv = rs.getString("ma_kv");
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

    public Long getDotBhId() {
        return dotBhId;
    }

    public void setDotBhId(Long dotBhId) {
        this.dotBhId = dotBhId;
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

    public String getTenKv() {
        return tenKv;
    }

    public void setTenKv(String tenKv) {
        this.tenKv = tenKv;
    }

    public String getMaKv() {
        return maKv;
    }

    public void setMaKv(String maKv) {
        this.maKv = maKv;
    }
}
