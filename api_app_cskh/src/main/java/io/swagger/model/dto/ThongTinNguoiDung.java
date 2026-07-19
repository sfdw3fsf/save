package io.swagger.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Thông tin người dùng
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class ThongTinNguoiDung {

    private int nguoiDungId;
    private int nhomNdId;
    private String maNd;
    private String tenNd;
    private long nhanVienId;
    private int donViId;
    private int donViDlId;
    private String tenDv;
    private String tenDvDl;
    private String userVisa;
    private String passVisa;
    private String userVasc;
    private String passVasc;
    private String userVtn;
    private String passVtn;
    private String userHddt;
    private String passHddt;
    private int tinhId;
    private String tenTat;
    private String userBnm;
    private String passBnm;
    private String maNv;
    private String tenTatCcbs;
    private String tenTatVasc;
    private String diemGiaoDichVasc;
    private String userHddtTd;
    private String passHddtTd;
    private String userNeo;
    private String userNeoWs;
    private String passNeoWs;
    private String ngayEpg;

    public ThongTinNguoiDung convertDataFromDatabase(ResultSet rs) throws SQLException {
        if (rs != null) {
            nguoiDungId = rs.getInt("NGUOIDUNG_ID");
            nhomNdId = rs.getInt("NHOM_ND_ID");
            maNd = rs.getString("MA_ND");
            tenNd = rs.getString("TEN_ND");
            nhanVienId = rs.getLong("NHANVIEN_ID");
            donViId = rs.getInt("DONVI_ID");
            donViDlId = rs.getInt("DONVI_DL_ID");
            tenDv = rs.getString("TEN_DV");
            tenDvDl = rs.getString("TEN_DV_DL");
            userVisa = rs.getString("USER_VISA");
            passVisa = rs.getString("PASS_VISA");
            userVasc = rs.getString("USER_VASC");
            passVasc = rs.getString("PASS_VASC");
            userVtn = rs.getString("USER_VTN");
            passVtn = rs.getString("PASS_VTN");
            userHddt = rs.getString("USER_HDDT");
            passHddt = rs.getString("PASS_HDDT");
            tinhId = rs.getInt("TINH_ID");
            tenTat = rs.getString("TENTAT");
            userBnm = rs.getString("USER_BNM");
            passBnm = rs.getString("PASS_BNM");
            maNv = rs.getString("MA_NV");
            tenTatCcbs = rs.getString("TENTAT_CCBS");
            tenTatVasc = rs.getString("TENTAT_VASC");
            diemGiaoDichVasc = rs.getString("DIEMGIAODICH_VASC");
            userHddtTd = rs.getString("USER_HDDT_TD");
            passHddtTd = rs.getString("PASS_HDDT_TD");
            userNeo = rs.getString("USER_NEO");
            userNeoWs = rs.getString("USER_NEO_WS");
            passNeoWs = rs.getString("PASS_NEO_WS");
            ngayEpg = rs.getString("NGAY_EPG");
            return this;
        } else {
            return null;
        }
    }

    public int getNguoiDungId() {
        return nguoiDungId;
    }

    public void setNguoiDungId(int nguoiDungId) {
        this.nguoiDungId = nguoiDungId;
    }

    public int getNhomNdId() {
        return nhomNdId;
    }

    public void setNhomNdId(int nhomNdId) {
        this.nhomNdId = nhomNdId;
    }

    public String getMaNd() {
        return maNd;
    }

    public void setMaNd(String maNd) {
        this.maNd = maNd;
    }

    public String getTenNd() {
        return tenNd;
    }

    public void setTenNd(String tenNd) {
        this.tenNd = tenNd;
    }

    public long getNhanVienId() {
        return nhanVienId;
    }

    public void setNhanVienId(long nhanVienId) {
        this.nhanVienId = nhanVienId;
    }

    public int getDonViId() {
        return donViId;
    }

    public void setDonViId(int donViId) {
        this.donViId = donViId;
    }

    public int getDonViDlId() {
        return donViDlId;
    }

    public void setDonViDlId(int donViDlId) {
        this.donViDlId = donViDlId;
    }

    public String getTenDv() {
        return tenDv;
    }

    public void setTenDv(String tenDv) {
        this.tenDv = tenDv;
    }

    public String getTenDvDl() {
        return tenDvDl;
    }

    public void setTenDvDl(String tenDvDl) {
        this.tenDvDl = tenDvDl;
    }

    public String getUserVisa() {
        return userVisa;
    }

    public void setUserVisa(String userVisa) {
        this.userVisa = userVisa;
    }

    public String getPassVisa() {
        return passVisa;
    }

    public void setPassVisa(String passVisa) {
        this.passVisa = passVisa;
    }

    public String getUserVasc() {
        return userVasc;
    }

    public void setUserVasc(String userVasc) {
        this.userVasc = userVasc;
    }

    public String getPassVasc() {
        return passVasc;
    }

    public void setPassVasc(String passVasc) {
        this.passVasc = passVasc;
    }

    public String getUserVtn() {
        return userVtn;
    }

    public void setUserVtn(String userVtn) {
        this.userVtn = userVtn;
    }

    public String getPassVtn() {
        return passVtn;
    }

    public void setPassVtn(String passVtn) {
        this.passVtn = passVtn;
    }

    public String getUserHddt() {
        return userHddt;
    }

    public void setUserHddt(String userHddt) {
        this.userHddt = userHddt;
    }

    public String getPassHddt() {
        return passHddt;
    }

    public void setPassHddt(String passHddt) {
        this.passHddt = passHddt;
    }

    public int getTinhId() {
        return tinhId;
    }

    public void setTinhId(int tinhId) {
        this.tinhId = tinhId;
    }

    public String getTenTat() {
        return tenTat;
    }

    public void setTenTat(String tenTat) {
        this.tenTat = tenTat;
    }

    public String getUserBnm() {
        return userBnm;
    }

    public void setUserBnm(String userBnm) {
        this.userBnm = userBnm;
    }

    public String getPassBnm() {
        return passBnm;
    }

    public void setPassBnm(String passBnm) {
        this.passBnm = passBnm;
    }

    public String getMaNv() {
        return maNv;
    }

    public void setMaNv(String maNv) {
        this.maNv = maNv;
    }

    public String getTenTatCcbs() {
        return tenTatCcbs;
    }

    public void setTenTatCcbs(String tenTatCcbs) {
        this.tenTatCcbs = tenTatCcbs;
    }

    public String getTenTatVasc() {
        return tenTatVasc;
    }

    public void setTenTatVasc(String tenTatVasc) {
        this.tenTatVasc = tenTatVasc;
    }

    public String getDiemGiaoDichVasc() {
        return diemGiaoDichVasc;
    }

    public void setDiemGiaoDichVasc(String diemGiaoDichVasc) {
        this.diemGiaoDichVasc = diemGiaoDichVasc;
    }

    public String getUserHddtTd() {
        return userHddtTd;
    }

    public void setUserHddtTd(String userHddtTd) {
        this.userHddtTd = userHddtTd;
    }

    public String getPassHddtTd() {
        return passHddtTd;
    }

    public void setPassHddtTd(String passHddtTd) {
        this.passHddtTd = passHddtTd;
    }

    public String getUserNeo() {
        return userNeo;
    }

    public void setUserNeo(String userNeo) {
        this.userNeo = userNeo;
    }

    public String getUserNeoWs() {
        return userNeoWs;
    }

    public void setUserNeoWs(String userNeoWs) {
        this.userNeoWs = userNeoWs;
    }

    public String getPassNeoWs() {
        return passNeoWs;
    }

    public void setPassNeoWs(String passNeoWs) {
        this.passNeoWs = passNeoWs;
    }

    public String getNgayEpg() {
        return ngayEpg;
    }

    public void setNgayEpg(String ngayEpg) {
        this.ngayEpg = ngayEpg;
    }
}
