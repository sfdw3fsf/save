package io.swagger.model.dto;

import vn.vnptit.sapi.oneapp.util.Constant;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ThoiGianKhuVuc {

    private Long khuVucId;
    private String ngayBd;
    private String gioBd;
    private String ngayKt;
    private String gioKt;

    public ThoiGianKhuVuc convertDataFromDatabase(ResultSet rs) throws SQLException {
        if(rs != null) {
            DateFormat df = new SimpleDateFormat(Constant.FORMAT_DATETIME);
            Date date;
            khuVucId = rs.getLong("khuvuc_id");
            try {
                date = rs.getDate("ngay_bd");
                ngayBd = df.format(date);
            } catch (Exception e) {
                ngayBd = "";
            }
            gioBd = rs.getString("gio_bd");
            try {
                date = rs.getDate("ngay_kt");
                ngayKt = df.format(date);
            } catch (Exception e) {
                ngayKt = "";
            }
            gioKt = rs.getString("gio_kt");
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

    public String getNgayBd() {
        return ngayBd;
    }

    public void setNgayBd(String ngayBd) {
        this.ngayBd = ngayBd;
    }

    public String getGioBd() {
        return gioBd;
    }

    public void setGioBd(String gioBd) {
        this.gioBd = gioBd;
    }

    public String getNgayKt() {
        return ngayKt;
    }

    public void setNgayKt(String ngayKt) {
        this.ngayKt = ngayKt;
    }

    public String getGioKt() {
        return gioKt;
    }

    public void setGioKt(String gioKt) {
        this.gioKt = gioKt;
    }
}
