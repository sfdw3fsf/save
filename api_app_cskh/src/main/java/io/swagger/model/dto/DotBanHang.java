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
 * Đợt bán hàng
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class DotBanHang {
    private String thangBd;
    private Long phanVungId;
    private Long dotBhId;
    private String dotBanHang;
    private Integer htBhId;
    private String noiDung;
    private String ngayBd;
    private String ngayKt;
    private String stt;
    private String ngayCn;
    private String nguoiCn;
    private String ipCn;
    private Long nhanVienId;
    private String hinhThucBh;


    public DotBanHang convertDataFromDatabase(ResultSet rs) throws SQLException {
        if (rs != null) {
            DateFormat df = new SimpleDateFormat(Constant.FORMAT_DATETIME);
            Date date;
            thangBd = rs.getString("thangbd");
            phanVungId = rs.getLong("phanvung_id");
            dotBhId = rs.getLong("dotbh_id");
            dotBanHang = rs.getString("dot_banhang");
            htBhId = rs.getInt("htbh_id");
            noiDung = rs.getString("noidung");
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
            stt = rs.getString("stt");
            try {
                date = rs.getDate("ngay_cn");
                ngayCn = df.format(date);
            } catch (Exception e) {
                ngayCn = "";
            }
            nguoiCn = rs.getString("nguoi_cn");
            ipCn = rs.getString("ip_cn");
            nhanVienId = rs.getLong("nhanvien_id");
            hinhThucBh = rs.getString("hinhthuc_bh");
            return this;
        } else {
            return null;
        }
    }

    public String getThangBd() {
        return thangBd;
    }

    public void setThangBd(String thangBd) {
        this.thangBd = thangBd;
    }

    public Long getPhanVungId() {
        return phanVungId;
    }

    public void setPhanVungId(Long phanVungId) {
        this.phanVungId = phanVungId;
    }

    public Long getDotBhId() {
        return dotBhId;
    }

    public void setDotBhId(Long dotBhId) {
        this.dotBhId = dotBhId;
    }

    public String getDotBanHang() {
        return dotBanHang;
    }

    public void setDotBanHang(String dotBanHang) {
        this.dotBanHang = dotBanHang;
    }

    public Integer getHtBhId() {
        return htBhId;
    }

    public void setHtBhId(Integer htBhId) {
        this.htBhId = htBhId;
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

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getNgayCn() {
        return ngayCn;
    }

    public void setNgayCn(String ngayCn) {
        this.ngayCn = ngayCn;
    }

    public String getNguoiCn() {
        return nguoiCn;
    }

    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }

    public String getIpCn() {
        return ipCn;
    }

    public void setIpCn(String ipCn) {
        this.ipCn = ipCn;
    }

    public Long getNhanVienId() {
        return nhanVienId;
    }

    public void setNhanVienId(Long nhanVienId) {
        this.nhanVienId = nhanVienId;
    }

    public String getHinhThucBh() {
        return hinhThucBh;
    }

    public void setHinhThucBh(String hinhThucBh) {
        this.hinhThucBh = hinhThucBh;
    }
}
