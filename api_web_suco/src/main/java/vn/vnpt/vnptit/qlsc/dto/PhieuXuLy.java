package vn.vnpt.vnptit.qlsc.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

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

    @JsonProperty("thoiGianNghi")
    private Long thoiGianNghi;

    @JsonProperty("ngayGiao")
    private String ngayGiao;

    @JsonProperty("slaTaiBuoc")
    private Long slaTaiBuoc;

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

    @JsonProperty("sla_process_time")
    private Long sla_process_time;

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

    @JsonProperty("HUONG_DAN_XU_LY")
    private String HUONG_DAN_XU_LY;

    @JsonProperty("THONG_TIN_CB")
    private String THONG_TIN_CB;

    @JsonProperty("account")
    private String account;

    @JsonProperty("nguoiCn")
    private String nguoiCn;

    @JsonProperty("nguoiTn")
    private String nguoiTn;

    @JsonProperty("ngayTn")
    private String ngayTn;

    @JsonProperty("ngayBd")
    private String ngayBd;

    @JsonProperty("ngayKt")
    private String ngayKt;

    @JsonProperty("trangThaiPhieu")
    private String trangThaiPhieu;

    @JsonProperty("trangThaiHT")
    private String trangThaiHT;

    @JsonProperty("ngaySuCo")
    private String ngaySuCo;

    public Long getThoiGianNghi() {
        return thoiGianNghi;
    }

    public void setThoiGianNghi(Long thoiGianNghi) {
        this.thoiGianNghi = thoiGianNghi;
    }

    public String getNgayGiao() {
        return ngayGiao;
    }

    public void setNgayGiao(String ngayGiao) {
        this.ngayGiao = ngayGiao;
    }

    public Long getSlaTaiBuoc() {
        return slaTaiBuoc;
    }

    public void setSlaTaiBuoc(Long slaTaiBuoc) {
        this.slaTaiBuoc = slaTaiBuoc;
    }

    public String getHUONG_DAN_XU_LY() {
        return HUONG_DAN_XU_LY;
    }

    public void setHUONG_DAN_XU_LY(String HUONG_DAN_XU_LY) {
        this.HUONG_DAN_XU_LY = HUONG_DAN_XU_LY;
    }

    public String getTHONG_TIN_CB() {
        return THONG_TIN_CB;
    }

    public void setTHONG_TIN_CB(String THONG_TIN_CB) {
        this.THONG_TIN_CB = THONG_TIN_CB;
    }

    public PhieuXuLy convert(Map<String, Object> obj) {
        try {
            chon = (String) obj.get("CHON");
            suCoId = obj != null ? ((Number) obj.get("SUCO_ID")).longValue() : null;

            maSc = (String) obj.get("MA_SC");
            phieuId = obj != null ? ((Number) obj.get("PHIEU_ID")).longValue() : null;
            nguonScId = obj != null ? ((Number) obj.get("NGUONSC_ID")).longValue() : null;
            nhomScId = obj != null ? ((Number) obj.get("NHOMSC_ID")).longValue() : null;
            nhomSc = (String) obj.get("NHOM_SC");
            noiDungSc = (String) obj.get("NOIDUNG_SC");
            dsThietBi = (String) obj.get("DS_THIETBI");
            ngaySc = (String) obj.get("NGAY_SC");
            thoiGian = (String) obj.get("THOIGIAN");
            henSuaTu = (String) obj.get("HENSUATU");
            henSuaDen = (String) obj.get("HENSUADEN");
            trangThaiSc = (String) obj.get("TRANGTHAI_SC");
            thoiGianNghi = obj != null ? ((Number) obj.get("THOIGIAN_NGHI")).longValue() : null;
            ngayGiao = (String) obj.get("NGAYGIAO");
            slaTaiBuoc = obj != null ? ((Number) obj.get("SLA_TAIBUOC")).longValue() : null;

            fms_clear_time = (String) obj.get("FMS_CLEAR_TIME");
            nguyen_nhan_tts = (String) obj.get("NGUYEN_NHAN_TTS");
            loai_canhbao = (String) obj.get("LOAI_CANHBAO");
            site_name = (String) obj.get("SITE_NAME");
            MA_CSHT = (String) obj.get("MA_CSHT");

            maQTNet = (String) obj.get("MA_QT_NET");
            qtNet = (String) obj.get("QUYTRINH_NET");
            thongTinThieTbi = (String) obj.get("THONGTINTHIETBI");
            clearTime = (String) obj.get("CLEAR_TIME");

            reopen_time = (String) obj.get("REOPEN_TIME");
            pending_time = (String) obj.get("PENDING_TIME");

            HUONG_DAN_XU_LY = (String) obj.get("HUONG_DAN_XU_LY");
            THONG_TIN_CB = (String) obj.get("THONG_TIN_CB");
            nguoiCn = (String) obj.get("NGUOI_CN");
            nguoiTn = (String) obj.get("NGUOI_TN");
            ngayTn = (String) obj.get("NGAY_TN");
            ngayBd = (String) obj.get("NGAY_BD");
            ngayKt = (String) obj.get("NGAY_KT");
            trangThaiPhieu = (String) obj.get("TRANGTHAI_PH");
            trangThaiHT = (String) obj.get("TRANGTHAI_HT");
            ngaySuCo = (String) obj.get("NGAY_SUCO");
            mucDoSc = (String) obj.get("MUCDO_SC");
        } catch (Exception e) {
            return null;
        }

        try {
            sla_process_time = obj.get("SLA_PROCESS_TIME") != null ? ((Number) obj.get("SLA_PROCESS_TIME")).longValue() : null;
        } catch (Exception e) {
            sla_process_time = null;
        }

        try {
            suCoChaId = obj != null ? ((Number) obj.get("SUCO_CHA_ID")).longValue() : null;
        } catch (Exception e) {
            suCoChaId = null;
        }

        try {
            mucDoId = obj != null ? ((Number) obj.get("MUCDO_ID")).longValue() : null;
        } catch (Exception e) {
            mucDoId = null;
        }

        try {
            flg_thutuc = obj.get("FLG_THUTUC") != null ? obj.get("FLG_THUTUC").toString() : null;

        } catch (Exception e) {
            flg_thutuc = null;
            VENDOR = null;
        }
        try {
            CONGNGHE = (String) obj.get("CONGNGHE");
        } catch (Exception e) {
            CONGNGHE = null;
        }
        try {
            VENDOR = (String) obj.get("VENDOR");
        } catch (Exception e) {
            VENDOR = null;
        }
        return this;
    }

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
            nguoiCn = rs.getString("NGUOI_CN");
            nguoiTn = rs.getString("NGUOI_TN");
            ngayTn = rs.getString("NGAY_TN");
            ngayBd = rs.getString("NGAY_BD");
            ngayKt = rs.getString("NGAY_KT");
            trangThaiPhieu = rs.getString("TRANGTHAI_PH");
            trangThaiHT = rs.getString("TRANGTHAI_HT");
            ngaySuCo = rs.getString("NGAY_SUCO");
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
                sla_process_time = rs.getLong("SLA_PROCESS_TIME");
            } catch (Exception e) {
                sla_process_time = null;
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
            try {
                HUONG_DAN_XU_LY = rs.getString("HUONG_DAN_XU_LY");
                THONG_TIN_CB = rs.getString("HUONG_DAN_XU_LY");
            } catch (Exception e) {
                HUONG_DAN_XU_LY = null;
                THONG_TIN_CB = null;

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

    public String getNguoiCn() {
        return nguoiCn;
    }

    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }

    public String getNguoiTn() {
        return nguoiTn;
    }

    public void setNguoiTn(String nguoiTn) {
        this.nguoiTn = nguoiTn;
    }

    public String getNgayTn() {
        return ngayTn;
    }

    public void setNgayTn(String ngayTn) {
        this.ngayTn = ngayTn;
    }

    public String getNgayBd() {
        return ngayBd;
    }

    public void setNgayBd(String ngayBd) {
        this.ngayBd = ngayBd;
    }
    public Long getSla_process_time() {
        return sla_process_time;
    }

    public String getNgayKt() {
        return ngayKt;
    }
    public void setSla_process_time(Long sla_process_time) {
        this.sla_process_time = sla_process_time;
    }

    public void setNgayKt(String ngayKt) {
        this.ngayKt = ngayKt;
    }

    public String getTrangThaiPhieu() {
        return trangThaiPhieu;
    }

    public void setTrangThaiPhieu(String trangThaiPhieu) {
        this.trangThaiPhieu = trangThaiPhieu;
    }

    public String getTrangThaiHT() {
        return trangThaiHT;
    }

    public void setTrangThaiHT(String trangThaiHT) {
        this.trangThaiHT = trangThaiHT;
    }

    public String getNgaySuCo() {
        return ngaySuCo;
    }

    public void setNgaySuCo(String ngaySuCo) {
        this.ngaySuCo = ngaySuCo;
    }
}
