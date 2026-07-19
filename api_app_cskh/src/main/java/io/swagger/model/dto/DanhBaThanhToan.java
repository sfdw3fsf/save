package io.swagger.model.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Danh bạ thanh toán
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class DanhBaThanhToan extends DanhBaBase{

    @JsonProperty("thanhToanId")
    private Long thanhToanId;

    @JsonProperty("maTt")
    private String maTt;

    @JsonProperty("tenTt")
    private String tenTt;

    @JsonProperty("diaChiTt")
    private String diaChiTt;

    @JsonProperty("mst")
    private String mst;

    @JsonProperty("maTbDd")
    private String maTbDd;

    @JsonProperty("khachHangId")
    private Long khachHangId;

    @JsonProperty("htttId")
    private Long htttId;

    @JsonProperty("nganHangId")
    private Long nganHangId;

    @JsonProperty("donViId")
    private Long donViId;


    @JsonProperty("diaChiCt")
    private String diaChiCt;

    @JsonProperty("dvQhns")
    private String dvQhns;

    @JsonProperty("maChuong")
    private String maChuong;

    @JsonProperty("mnKt")
    private String mnKt;

    @JsonProperty("maNdkt")
    private String maNdkt;

    @JsonProperty("mnNsnn")
    private String mnNsnn;

    @JsonProperty("tienDm")
    private Long tienDm;

    @JsonProperty("tuyenThuId")
    private Long tuyenThuId;

    @JsonProperty("maTtCu")
    private String maTtCu;

    @JsonProperty("thanhToanCuId")
    private Long thanhToanCuId;

    @JsonProperty("diaChiBc")
    private String diaChiBc;

    @JsonProperty("thuTuIn")
    private Long thuTuIn;

    @JsonProperty("dotGiaoId")
    private Long dotGiaoId;

    @JsonProperty("migId")
    private Long migId;

    @JsonProperty("mainHd")
    private String mainHd;

    @JsonProperty("ttdtId")
    private Long ttdtId;

    @JsonProperty("htGiaoId")
    private Long htGiaoId;

    @JsonProperty("chuTk")
    private String chuTk;

    @JsonProperty("sysNc00038")
    private String sysNc00038;

    @JsonProperty("maLt")
    private String maLt;

    @JsonProperty("httcId")
    private Long httcId;

    public DanhBaThanhToan convertDataFromDatabase(ResultSet rs) throws SQLException {
        if(rs != null) {
            stt = rs.getLong("STT");
            thanhToanId = rs.getLong("THANHTOAN_ID");
            maTt = rs.getString("MA_TT");
            tenTt = rs.getString("TEN_TT");
            diaChiTt = rs.getString("DIACHI_TT");
            mst = rs.getString("MST");
            stk = rs.getString("STK");
            maTbDd = rs.getString("MATB_DD");
            khachHangId = rs.getLong("KHACHHANG_ID");
            htttId = rs.getLong("HTTT_ID");
            nganHangId = rs.getLong("NGANHANG_ID");
            donViId = rs.getLong("DONVI_ID");
            ketQuaCh = rs.getString("KETQUA_CH");
            sdt = rs.getString("SO_DT");
            email = rs.getString("EMAIL");
            diaChiCt = rs.getString("DIACHI_CT");
            dvQhns = rs.getString("DV_QHNS");
            maChuong = rs.getString("MACHUONG");
            mnKt = rs.getString("MN_KT");
            maNdkt = rs.getString("MA_NDKT");
            mnNsnn = rs.getString("MN_NSNN");
            tienDm = rs.getLong("TIEN_DM");
            tuyenThuId = rs.getLong("TUYENTHU_ID");
            ghiChu = rs.getString("GHICHU");
            maTtCu = rs.getString("MATT_CU");
            mayCn = rs.getString("MAY_CN");
            ngayCn = rs.getString("NGAY_CN");
            nguoiCn = rs.getString("NGUOI_CN");
            thanhToanCuId = rs.getLong("THANHTOANCU_ID");
            diaChiBc = rs.getString("DIACHI_BC");
            thuTuIn = rs.getLong("THUTU_IN");
            dotGiaoId = rs.getLong("DOTGIAO_ID");
            migId = rs.getLong("MIG_ID");
            mainHd = rs.getString("MAIN_HD");
            ttdtId = rs.getLong("TTDT_ID");
            htGiaoId = rs.getLong("HTGIAO_ID");
            chuTk = rs.getString("CHU_TK");
            sysNc00038 = rs.getString("SYS_NC00038$");
            maLt = rs.getString("MA_LT");
            httcId = rs.getLong("HTTC_ID");
            tinhId = rs.getLong("TINHID");
            quanId = rs.getLong("QUANID");
            phuongId = rs.getLong("PHUONGID");
            phoId = rs.getLong("PHOID");
            apId = rs.getLong("APID");
            khuId = rs.getLong("KHUID");
            dacDiemId = rs.getLong("DACDIEMID");
            diaChi = rs.getString("DIACHI");
            soNha = rs.getString("SONHA");
            datt = rs.getLong("DATT");
            return this;
        } else {
            return null;
        }
    }

    public Long getThanhToanId() {
        return thanhToanId;
    }

    public void setThanhToanId(Long thanhToanId) {
        this.thanhToanId = thanhToanId;
    }

    public String getMaTt() {
        return maTt;
    }

    public void setMaTt(String maTt) {
        this.maTt = maTt;
    }

    public String getTenTt() {
        return tenTt;
    }

    public void setTenTt(String tenTt) {
        this.tenTt = tenTt;
    }

    public String getDiaChiTt() {
        return diaChiTt;
    }

    public void setDiaChiTt(String diaChiTt) {
        this.diaChiTt = diaChiTt;
    }

    public String getMst() {
        return mst;
    }

    public void setMst(String mst) {
        this.mst = mst;
    }

    public String getMaTbDd() {
        return maTbDd;
    }

    public void setMaTbDd(String maTbDd) {
        this.maTbDd = maTbDd;
    }

    public Long getKhachHangId() {
        return khachHangId;
    }

    public void setKhachHangId(Long khachHangId) {
        this.khachHangId = khachHangId;
    }

    public Long getHtttId() {
        return htttId;
    }

    public void setHtttId(Long htttId) {
        this.htttId = htttId;
    }

    public Long getNganHangId() {
        return nganHangId;
    }

    public void setNganHangId(Long nganHangId) {
        this.nganHangId = nganHangId;
    }

    public Long getDonViId() {
        return donViId;
    }

    public void setDonViId(Long donViId) {
        this.donViId = donViId;
    }

    public String getDiaChiCt() {
        return diaChiCt;
    }

    public void setDiaChiCt(String diaChiCt) {
        this.diaChiCt = diaChiCt;
    }

    public String getDvQhns() {
        return dvQhns;
    }

    public void setDvQhns(String dvQhns) {
        this.dvQhns = dvQhns;
    }

    public String getMaChuong() {
        return maChuong;
    }

    public void setMaChuong(String maChuong) {
        this.maChuong = maChuong;
    }

    public String getMnKt() {
        return mnKt;
    }

    public void setMnKt(String mnKt) {
        this.mnKt = mnKt;
    }

    public String getMaNdkt() {
        return maNdkt;
    }

    public void setMaNdkt(String maNdkt) {
        this.maNdkt = maNdkt;
    }

    public String getMnNsnn() {
        return mnNsnn;
    }

    public void setMnNsnn(String mnNsnn) {
        this.mnNsnn = mnNsnn;
    }

    public Long getTienDm() {
        return tienDm;
    }

    public void setTienDm(Long tienDm) {
        this.tienDm = tienDm;
    }

    public Long getTuyenThuId() {
        return tuyenThuId;
    }

    public void setTuyenThuId(Long tuyenThuId) {
        this.tuyenThuId = tuyenThuId;
    }

    public String getMaTtCu() {
        return maTtCu;
    }

    public void setMaTtCu(String maTtCu) {
        this.maTtCu = maTtCu;
    }

    public Long getThanhToanCuId() {
        return thanhToanCuId;
    }

    public void setThanhToanCuId(Long thanhToanCuId) {
        this.thanhToanCuId = thanhToanCuId;
    }

    public String getDiaChiBc() {
        return diaChiBc;
    }

    public void setDiaChiBc(String diaChiBc) {
        this.diaChiBc = diaChiBc;
    }

    public Long getThuTuIn() {
        return thuTuIn;
    }

    public void setThuTuIn(Long thuTuIn) {
        this.thuTuIn = thuTuIn;
    }

    public Long getDotGiaoId() {
        return dotGiaoId;
    }

    public void setDotGiaoId(Long dotGiaoId) {
        this.dotGiaoId = dotGiaoId;
    }

    public Long getMigId() {
        return migId;
    }

    public void setMigId(Long migId) {
        this.migId = migId;
    }

    public String getMainHd() {
        return mainHd;
    }

    public void setMainHd(String mainHd) {
        this.mainHd = mainHd;
    }

    public Long getTtdtId() {
        return ttdtId;
    }

    public void setTtdtId(Long ttdtId) {
        this.ttdtId = ttdtId;
    }

    public Long getHtGiaoId() {
        return htGiaoId;
    }

    public void setHtGiaoId(Long htGiaoId) {
        this.htGiaoId = htGiaoId;
    }

    public String getChuTk() {
        return chuTk;
    }

    public void setChuTk(String chuTk) {
        this.chuTk = chuTk;
    }

    public String getSysNc00038() {
        return sysNc00038;
    }

    public void setSysNc00038(String sysNc00038) {
        this.sysNc00038 = sysNc00038;
    }

    public String getMaLt() {
        return maLt;
    }

    public void setMaLt(String maLt) {
        this.maLt = maLt;
    }

    public Long getHttcId() {
        return httcId;
    }

    public void setHttcId(Long httcId) {
        this.httcId = httcId;
    }
}
