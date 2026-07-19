package vn.vnpt.vnptit.ecms.dto.idc;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

public class IDCRackRequestDTO {
    private Number stt;
    private String id;
    private String ten;
    private Number loairack_id;
    private String the_ts;

    private String ma_duan;

    private Number ma_ts;
    private Date ngay_sd;
    private String ma_tb;
    private Number khonggian_lapdat;
    private String hang_sx;
    private String donvi_idc;

    private String idc;
    private String toanha;

    private String matsan;
    private String phong;
    private String toado_matsan;

    private Number ma_ttsd;
    private String donvi_sohuu;

    private String donvi_ql;
    private String phong_ql;
    private String nhanvien_qls;
    private String ghichu;
    private Number hangsx_id;
    private Number idc_id;
    private Number toadomatsan_id;
    private Number phong_id;
    private Number donvisohuu_id;
    private Number donviql_id;
    private Number phongql_id;
    private String nhanvienql_ids;

    private Number toado_x;
    private Number toado_y;

    public Number getToado_x() {
        return toado_x;
    }

    public void setToado_x(Number toado_x) {
        this.toado_x = toado_x;
    }

    public Number getToado_y() {
        return toado_y;
    }

    public void setToado_y(Number toado_y) {
        this.toado_y = toado_y;
    }

    public String getDonvi_sohuu() {
        return donvi_sohuu;
    }

    public void setDonvi_sohuu(String donvi_sohuu) {
        this.donvi_sohuu = donvi_sohuu;
    }

    public Number getDonvisohuu_id() {
        return donvisohuu_id;
    }

    public void setDonvisohuu_id(Number donvisohuu_id) {
        this.donvisohuu_id = donvisohuu_id;
    }

    public Number getStt() {
        return stt;
    }

    public void setStt(Number stt) {
        this.stt = stt;
    }

    public Number getKhonggian_lapdat() {
        return khonggian_lapdat;
    }

    public void setKhonggian_lapdat(Number khonggian_lapdat) {
        this.khonggian_lapdat = khonggian_lapdat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Number getLoairack_id() {
        return loairack_id;
    }

    public void setLoairack_id(Number loairack_id) {
        this.loairack_id = loairack_id;
    }

    public String getThe_ts() {
        return the_ts;
    }

    public void setThe_ts(String the_ts) {
        this.the_ts = the_ts;
    }

    public String getMa_duan() {
        return ma_duan;
    }

    public void setMa_duan(String ma_duan) {
        this.ma_duan = ma_duan;
    }

    public Number getMa_ts() {
        return ma_ts;
    }

    public void setMa_ts(Number ma_ts) {
        this.ma_ts = ma_ts;
    }

    public Date getNgay_sd() {
        return ngay_sd;
    }

    public void setNgay_sd(Date ngay_sd) {
        this.ngay_sd = ngay_sd;
    }

    public String getMa_tb() {
        return ma_tb;
    }

    public void setMa_tb(String ma_tb) {
        this.ma_tb = ma_tb;
    }

    public String getHang_sx() {
        return hang_sx;
    }

    public void setHang_sx(String hang_sx) {
        this.hang_sx = hang_sx;
    }

    public String getDonvi_idc() {
        return donvi_idc;
    }

    public void setDonvi_idc(String donvi_idc) {
        this.donvi_idc = donvi_idc;
    }

    public String getIdc() {
        return idc;
    }

    public void setIdc(String idc) {
        this.idc = idc;
    }

    public String getToanha() {
        return toanha;
    }

    public void setToanha(String toanha) {
        this.toanha = toanha;
    }

    public String getMatsan() {
        return matsan;
    }

    public void setMatsan(String matsan) {
        this.matsan = matsan;
    }

    public String getPhong() {
        return phong;
    }

    public void setPhong(String phong) {
        this.phong = phong;
    }

    public String getToado_matsan() {
        return toado_matsan;
    }

    public void setToado_matsan(String toado_matsan) {
        this.toado_matsan = toado_matsan;
    }

    public Number getMa_ttsd() {
        return ma_ttsd;
    }

    public void setMa_ttsd(Number ma_ttsd) {
        this.ma_ttsd = ma_ttsd;
    }

    public String getDonvi_ql() {
        return donvi_ql;
    }

    public void setDonvi_ql(String donvi_ql) {
        this.donvi_ql = donvi_ql;
    }

    public String getPhong_ql() {
        return phong_ql;
    }

    public void setPhong_ql(String phong_ql) {
        this.phong_ql = phong_ql;
    }

    public String getNhanvien_qls() {
        return nhanvien_qls;
    }

    public void setNhanvien_qls(String nhanvien_qls) {
        this.nhanvien_qls = nhanvien_qls;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public Number getHangsx_id() {
        return hangsx_id;
    }

    public void setHangsx_id(Number hangsx_id) {
        this.hangsx_id = hangsx_id;
    }

    public Number getIdc_id() {
        return idc_id;
    }

    public void setIdc_id(Number idc_id) {
        this.idc_id = idc_id;
    }

    public Number getToadomatsan_id() {
        return toadomatsan_id;
    }

    public void setToadomatsan_id(Number toadomatsan_id) {
        this.toadomatsan_id = toadomatsan_id;
    }

    public Number getPhong_id() {
        return phong_id;
    }

    public void setPhong_id(Number phong_id) {
        this.phong_id = phong_id;
    }

    public Number getDonviql_id() {
        return donviql_id;
    }

    public void setDonviql_id(Number donviql_id) {
        this.donviql_id = donviql_id;
    }

    public Number getPhongql_id() {
        return phongql_id;
    }

    public void setPhongql_id(Number phongql_id) {
        this.phongql_id = phongql_id;
    }

    public String getNhanvienql_ids() {
        return nhanvienql_ids;
    }

    public void setNhanvienql_ids(String nhanvienql_ids) {
        this.nhanvienql_ids = nhanvienql_ids;
    }

    public IDCRackRequestDTO(String ten, String the_ts, String ma_duan, Number ma_ts, Date ngay_sd, Number ma_ttsd, String ghichu, Number hangsx_id, Number idc_id, Number toadomatsan_id, Number phong_id, Number donviql_id, Number phongql_id, String nhanvienql_ids) {
        this.ten = ten;
        this.the_ts = the_ts;
        this.ma_duan = ma_duan;
        this.ma_ts = ma_ts;
        this.ngay_sd = ngay_sd;
        this.ma_ttsd = ma_ttsd;
        this.ghichu = ghichu;
        this.hangsx_id = hangsx_id;
        this.idc_id = idc_id;
        this.toadomatsan_id = toadomatsan_id;
        this.phong_id = phong_id;
        this.donviql_id = donviql_id;
        this.phongql_id = phongql_id;
        this.nhanvienql_ids = nhanvienql_ids;
    }

    public IDCRackRequestDTO(String ten, String the_ts, String ma_duan, Number ma_ts, Date ngay_sd, String hang_sx, String donvi_idc, String idc, String toanha, String matsan, String phong, String toado_matsan, Number ma_ttsd, String donvi_ql, String phong_ql, String nhanvien_qls, String ghichu) {
        this.ten = ten;
        this.the_ts = the_ts;
        this.ma_duan = ma_duan;
        this.ma_ts = ma_ts;
        this.ngay_sd = ngay_sd;
        this.hang_sx = hang_sx;
        this.donvi_idc = donvi_idc;
        this.idc = idc;
        this.toanha = toanha;
        this.matsan = matsan;
        this.phong = phong;
        this.toado_matsan = toado_matsan;
        this.ma_ttsd = ma_ttsd;
        this.donvi_ql = donvi_ql;
        this.phong_ql = phong_ql;
        this.nhanvien_qls = nhanvien_qls;
        this.ghichu = ghichu;
    }

    public IDCRackRequestDTO() {
    }
}
