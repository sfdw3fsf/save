package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Phiếu xử lý
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class PhieuXuLy {

    @JsonProperty("chon")
    private String chon;

    @JsonProperty("suCoId")
    private Long suCoId;

    @JsonProperty("suCoChaId")
    private Long suCoChaId;

    @JsonProperty("maSc")
    private String maSc;

    @JsonProperty("phieuId")
    private Long phieuId;

    @JsonProperty("nguonScId")
    private Long nguonScId;

    @JsonProperty("nhomScId")
    private Long nhomScId;

    @JsonProperty("nhomSc")
    private String nhomSc;

    @JsonProperty("noiDungSc")
    private String noiDungSc;

    @JsonProperty("dsThietBi")
    private String dsThietBi;

    @JsonProperty("ngaySc")
    private String ngaySc;

    @JsonProperty("thoiGian")
    private String thoiGian;

    @JsonProperty("henSuaTu")
    private String henSuaTu;

    @JsonProperty("henSuaDen")
    private String henSuaDen;

    @JsonProperty("trangThaiSc")
    private String trangThaiSc;

    @JsonProperty("canhBaoDt")
    private Long canhBaoDt;

    @JsonProperty("mucDoId")
    private Long mucDoId;

    @JsonProperty("mucDoSc")
    private String mucDoSc;

    @JsonProperty("mauNen")
    private String mauNen;

    @JsonProperty("mauChu")
    private String mauChu;

    @JsonProperty("maQTNet")
    private String maQTNet;

    @JsonProperty("qtNet")
    private String qtNet;

    @JsonProperty("thongTinThieTbi")
    private String thongTinThieTbi;

    @JsonProperty("clearTime")
    private String clearTime;

    @JsonProperty("MA_CSHT")
    private String MA_CSHT;

    @JsonProperty("site_name")
    private String site_name;

    @JsonProperty("loai_canhbao")
    private String loai_canhbao;

    @JsonProperty("nguyen_nhan_tts")
    private String nguyen_nhan_tts;

    @JsonProperty("fms_clear_time")
    private String fms_clear_time;

    @JsonProperty("sla_time")
    private Long sla_time;

    @JsonProperty("heso_ki")
    private Long heso_ki;

    @JsonProperty("reopen_time")
    private String reopen_time;

    @JsonProperty("pending_time")
    private String pending_time;

    @JsonProperty("flg_thutuc")
    private String flg_thutuc;

    @JsonProperty("CONGNGHE")
    private String CONGNGHE;

    @JsonProperty("VENDOR")
    private String VENDOR;

    public PhieuXuLy convertDataFromDatabase(ResultSet rs) throws SQLException {
        if (rs != null) {
            chon = rs.getString("CHON");
            suCoId = rs.getLong("SUCO_ID");
            suCoChaId = rs.getLong("SUCO_CHA_ID");
            maSc = rs.getString("MA_SC");
            phieuId = rs.getLong("PHIEU_ID");
            nguonScId = rs.getLong("NGUONSC_ID");
            nhomScId = rs.getLong("NHOMSC_ID");
            nhomSc = rs.getString("NHOM_SC");
            noiDungSc = rs.getString("NOIDUNG_SC");
            dsThietBi = rs.getString("DS_THIETBI");
            ngaySc = rs.getString("NGAY_SC");
            thoiGian = rs.getString("THOIGIAN");
            henSuaTu = rs.getString("HENSUATU");
            henSuaDen = rs.getString("HENSUADEN");
            trangThaiSc = rs.getString("TRANGTHAI_SC");
            flg_thutuc = rs.getString("FLG_THUTUC");


            try {
                fms_clear_time = rs.getString("FMS_CLEAR_TIME");
            } catch (Exception e) {
                fms_clear_time = null;
            }
            try {
                nguyen_nhan_tts = rs.getString("NGUYEN_NHAN_TTS");
            } catch (Exception e) {
                nguyen_nhan_tts = null;
            }
            try {
                loai_canhbao = rs.getString("LOAI_CANHBAO");
            } catch (Exception e) {
                loai_canhbao = null;
            }
            try {
                site_name = rs.getString("SITE_NAME");
            } catch (Exception e) {
                site_name = null;
            }
            try {
                MA_CSHT = rs.getString("MA_CSHT");
            } catch (Exception e) {
                MA_CSHT = null;
            }

            try {
                canhBaoDt = rs.getLong("CANHBAO_DT");
            } catch (Exception e) {
                canhBaoDt = null;
            }
            mucDoId = rs.getLong("MUCDO_ID");
            mucDoSc = rs.getString("MUCDO_SC");
            mauNen = rs.getString("MAUNEN");
            mauChu = rs.getString("MAUCHU");
            try {
                maQTNet = rs.getString("MA_QT_NET");
            } catch (Exception e) {
                maQTNet = null;
            }
            try {
                qtNet = rs.getString("QUYTRINH_NET");
            } catch (Exception e) {
                qtNet = null;
            }
            try {
                thongTinThieTbi = rs.getString("THONGTINTHIETBI");
            } catch (Exception e) {
                thongTinThieTbi = null;
            }
            try {
                clearTime = rs.getString("CLEAR_TIME");
            } catch (Exception e) {
                clearTime = null;
            }
            try {
                sla_time = rs.getLong("SLA_TIME");
            } catch (Exception e) {
                sla_time = null;
            }
            try {
                heso_ki = rs.getLong("HESO_KI");
            } catch (Exception e) {
                heso_ki = null;
            }
            try {
                reopen_time = rs.getString("REOPEN_TIME");
            } catch (Exception e) {
                reopen_time = null;
            }
            try {
                pending_time = rs.getString("PENDING_TIME");
            } catch (Exception e) {
                pending_time = null;
            }
            try {
                flg_thutuc = rs.getString("FLG_THUTUC");
                CONGNGHE = rs.getString("CONGNGHE");
                VENDOR = rs.getString("VENDOR");
            } catch (Exception e) {
                flg_thutuc = null;
                CONGNGHE = null;
                VENDOR = null;

            }
            return this;
        } else {
            return null;
        }
    }

    public String getfms_clear_time() {
        return fms_clear_time;
    }

    public void setfms_clear_time(String fms_clear_time) {
        this.fms_clear_time = fms_clear_time;
    }

    public String getnguyen_nhan_tts() {
        return nguyen_nhan_tts;
    }

    public void setnguyen_nhan_tts(String nguyen_nhan_tts) {
        this.nguyen_nhan_tts = nguyen_nhan_tts;
    }

    public String getloai_canhbao() {
        return loai_canhbao;
    }

    public void setloai_canhbao(String loai_canhbao) {
        this.loai_canhbao = loai_canhbao;
    }

    public String getsite_name() {
        return site_name;
    }

    public void setsite_name(String site_name) {
        this.site_name = site_name;
    }

    public String getMA_CSHT() {
        return MA_CSHT;
    }

    public void setMA_CSHT(String MA_CSHT) {
        this.MA_CSHT = MA_CSHT;
    }

    public String getThongTinThieTbi() {
        return thongTinThieTbi;
    }

    public void setThongTinThieTbi(String thongTinThieTbi) {
        this.thongTinThieTbi = thongTinThieTbi;
    }

    public String getChon() {
        return chon;
    }

    public void setChon(String chon) {
        this.chon = chon;
    }

    public Long getSuCoId() {
        return suCoId;
    }

    public void setSuCoId(Long suCoId) {
        this.suCoId = suCoId;
    }

    public Long getSuCoChaId() {
        return suCoChaId;
    }

    public void setSuCoChaId(Long suCoChaId) {
        this.suCoChaId = suCoChaId;
    }

    public String getMaSc() {
        return maSc;
    }

    public void setMaSc(String maSc) {
        this.maSc = maSc;
    }

    public Long getPhieuId() {
        return phieuId;
    }

    public void setPhieuId(Long phieuId) {
        this.phieuId = phieuId;
    }

    public Long getNguonScId() {
        return nguonScId;
    }

    public void setNguonScId(Long nguonScId) {
        this.nguonScId = nguonScId;
    }

    public Long getNhomScId() {
        return nhomScId;
    }

    public void setNhomScId(Long nhomScId) {
        this.nhomScId = nhomScId;
    }

    public String getNhomSc() {
        return nhomSc;
    }

    public void setNhomSc(String nhomSc) {
        this.nhomSc = nhomSc;
    }

    public String getNoiDungSc() {
        return noiDungSc;
    }

    public void setNoiDungSc(String noiDungSc) {
        this.noiDungSc = noiDungSc;
    }

    public String getDsThietBi() {
        return dsThietBi;
    }

    public void setDsThietBi(String dsThietBi) {
        this.dsThietBi = dsThietBi;
    }

    public String getNgaySc() {
        return ngaySc;
    }

    public void setNgaySc(String ngaySc) {
        this.ngaySc = ngaySc;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getHenSuaTu() {
        return henSuaTu;
    }

    public void setHenSuaTu(String henSuaTu) {
        this.henSuaTu = henSuaTu;
    }

    public String getHenSuaDen() {
        return henSuaDen;
    }

    public void setHenSuaDen(String henSuaDen) {
        this.henSuaDen = henSuaDen;
    }

    public String getTrangThaiSc() {
        return trangThaiSc;
    }

    public void setTrangThaiSc(String trangThaiSc) {
        this.trangThaiSc = trangThaiSc;
    }

    public Long getCanhBaoDt() {
        return canhBaoDt;
    }

    public void setCanhBaoDt(Long canhBaoDt) {
        this.canhBaoDt = canhBaoDt;
    }

    public Long getMucDoId() {
        return mucDoId;
    }

    public void setMucDoId(Long mucDoId) {
        this.mucDoId = mucDoId;
    }

    public String getMucDoSc() {
        return mucDoSc;
    }

    public void setMucDoSc(String mucDoSc) {
        this.mucDoSc = mucDoSc;
    }

    public String getMauNen() {
        return mauNen;
    }

    public void setMauNen(String mauNen) {
        this.mauNen = mauNen;
    }

    public String getMauChu() {
        return mauChu;
    }

    public void setMauChu(String mauChu) {
        this.mauChu = mauChu;
    }

    public String getMaQTNet() {
        return maQTNet;
    }

    public void setMaQTNet(String maQTNet) {
        this.maQTNet = maQTNet;
    }

    public String getQtNet() {
        return qtNet;
    }

    public void setQtNet(String qtNet) {
        this.qtNet = qtNet;
    }

    public String getFlg_thutuc() {
        return flg_thutuc;
    }

    public void setFlg_thutuc(String flg_thutuc) {
        this.flg_thutuc = flg_thutuc;
    }

    public String getCONGNGHE() {
        return CONGNGHE;
    }

    public void setCONGNGHE(String cONGNGHE) {
        CONGNGHE = cONGNGHE;
    }

    public String getVENDOR() {
        return VENDOR;
    }

    public void setVENDOR(String vENDOR) {
        VENDOR = vENDOR;
    }

}
