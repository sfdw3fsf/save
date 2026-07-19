package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import vn.vnptit.sapi.oneapp.util.Constant;

import javax.annotation.Generated;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * CauHoiKS
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class CauHoiKS {
    @JsonProperty("tltbId")
    private Long tltbId;
    @JsonProperty("cauHoiId")
    private Long cauHoiId;
    @JsonProperty("tieuChuanId")
    private Long tieuChuanId;
    @JsonProperty("traLoiId")
    private Long traLoiId;
    @JsonProperty("noiDung")
    private String noiDung;
    @JsonProperty("hinhThuc")
    private String hinhThuc;
    @JsonProperty("thuTu")
    private Integer thuTu;
    @JsonProperty("noiDungTL")
    private String noiDungTL;
    @JsonProperty("loai")
    private String loai;
    @JsonProperty("thuTuTL")
    private Integer thuTuTL;
    @JsonProperty("loaiCHId")
    private Long loaiCHId;
    @JsonProperty("kieu")
    private String kieu;
    @JsonProperty("dieuLai")
    private String dieuLai;
    @JsonProperty("dieuLaiXN")
    private String dieuLaiXN;
    @JsonProperty("cauHoiPhuId")
    private Long cauHoiPhuId;
    @JsonProperty("rchId")
    private Long rchId;
    @JsonProperty("rchId1")
    private Long rchId1;
    @JsonProperty("dieuKN")
    private String dieuKN;
    @JsonProperty("dsTraLoi")
    private List<CauHoiKS> dsTraLoi = new ArrayList<>();
    @JsonProperty("dsCauHoiPhu")
    private List<CauHoiKS> dsCauHoiPhu = new ArrayList<>();
    @JsonProperty("dapan_id")
    private Long dapan_id;

    public Long getDapan_id() {
        return dapan_id;
    }

    public void setDapan_id(Long dapan_id) {
        this.dapan_id = dapan_id;
    }

    public List<CauHoiKS> getDsCauHoiPhu() {
        return dsCauHoiPhu;
    }

    public void setDsCauHoiPhu(List<CauHoiKS> dsCauHoiPhu) {
        this.dsCauHoiPhu = dsCauHoiPhu;
    }

    public CauHoiKS convertDataFromDatabase(ResultSet rs) throws SQLException {
        if (rs != null) {
            DateFormat df = new SimpleDateFormat(Constant.FORMAT_DATETIME2);
            cauHoiId = rs.getLong("CAUHOI_ID");
            tieuChuanId = rs.getLong("TIEUCHUAN_ID");
            traLoiId = rs.getLong("TRALOI_ID");
            noiDung = rs.getString("NOIDUNG");
            hinhThuc = rs.getString("HINHTHUC");
            thuTu = rs.getInt("THUTU");
            noiDungTL = rs.getString("NOIDUNG_TL");
            loai = rs.getString("LOAI");
            thuTuTL = rs.getInt("THUTU_TL");
            loaiCHId = rs.getLong("LOAICH_ID");
            cauHoiPhuId = rs.getLong("CAUHOI_PHU_ID");
            rchId = rs.getLong("RCH_ID");
            rchId1 = rs.getLong("RCH_ID1");
            dapan_id = rs.getLong("DAPAN_ID");
            kieu = rs.getString("KIEU");
            dieuLai = rs.getString("DIEULAI");
            dieuLaiXN = rs.getString("DIEULAI_XN");
            dieuKN = rs.getString("DIEU_KN");
            return this;
        } else {
            return null;
        }
    }

    public CauHoiKS convertDataFromPojo(CauHoiKS item) {
        if (item != null) {
            DateFormat df = new SimpleDateFormat(Constant.FORMAT_DATETIME2);
            cauHoiId = item.getCauHoiId();
            tieuChuanId = item.getTieuChuanId();
            traLoiId = item.getTraLoiId();
            noiDung = item.getNoiDung();
            hinhThuc = item.getHinhThuc();
            thuTu = item.getThuTu();
            noiDungTL = item.getNoiDungTL();
            loai = item.getLoai();
            thuTuTL = item.getThuTuTL();
            loaiCHId = item.getLoaiCHId();
            cauHoiPhuId = item.getCauHoiPhuId();
            rchId = item.getRchId();
            rchId1 = item.getRchId1();
            dapan_id = item.getDapan_id();
            kieu = item.getKieu();
            dieuLai = item.getDieuLai();
            dieuLaiXN = item.getDieuLaiXN();
            dieuKN = item.getDieuKN();
            return this;
        } else {
            return null;
        }
    }

    public Long getTltbId() {
        return tltbId;
    }

    public void setTltbId(Long tltbId) {
        this.tltbId = tltbId;
    }

    public Long getCauHoiId() {
        return cauHoiId;
    }

    public void setCauHoiId(Long cauHoiId) {
        this.cauHoiId = cauHoiId;
    }

    public Long getTieuChuanId() {
        return tieuChuanId;
    }

    public void setTieuChuanId(Long tieuChuanId) {
        this.tieuChuanId = tieuChuanId;
    }

    public Long getTraLoiId() {
        return traLoiId;
    }

    public void setTraLoiId(Long traLoiId) {
        this.traLoiId = traLoiId;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getHinhThuc() {
        return hinhThuc;
    }

    public void setHinhThuc(String hinhThuc) {
        this.hinhThuc = hinhThuc;
    }

    public Integer getThuTu() {
        return thuTu;
    }

    public void setThuTu(Integer thuTu) {
        this.thuTu = thuTu;
    }

    public String getNoiDungTL() {
        return noiDungTL;
    }

    public void setNoiDungTL(String noiDungTL) {
        this.noiDungTL = noiDungTL;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public Integer getThuTuTL() {
        return thuTuTL;
    }

    public void setThuTuTL(Integer thuTuTL) {
        this.thuTuTL = thuTuTL;
    }

    public Long getLoaiCHId() {
        return loaiCHId;
    }

    public void setLoaiCHId(Long loaiCHId) {
        this.loaiCHId = loaiCHId;
    }

    public String getKieu() {
        return kieu;
    }

    public void setKieu(String kieu) {
        this.kieu = kieu;
    }

    public String getDieuLai() {
        return dieuLai;
    }

    public void setDieuLai(String dieuLai) {
        this.dieuLai = dieuLai;
    }

    public String getDieuLaiXN() {
        return dieuLaiXN;
    }

    public void setDieuLaiXN(String dieuLaiXN) {
        this.dieuLaiXN = dieuLaiXN;
    }

    public Long getCauHoiPhuId() {
        return cauHoiPhuId;
    }

    public void setCauHoiPhuId(Long cauHoiPhuId) {
        this.cauHoiPhuId = cauHoiPhuId;
    }

    public Long getRchId() {
        return rchId;
    }

    public void setRchId(Long rchId) {
        this.rchId = rchId;
    }

    public Long getRchId1() {
        return rchId1;
    }

    public void setRchId1(Long rchId1) {
        this.rchId1 = rchId1;
    }

    public String getDieuKN() {
        return dieuKN;
    }

    public void setDieuKN(String dieuKN) {
        this.dieuKN = dieuKN;
    }

    public List<CauHoiKS> getDsTraLoi() {
        return dsTraLoi;
    }

    public void setDsTraLoi(List<CauHoiKS> dsTraLoi) {
        this.dsTraLoi = dsTraLoi;
    }
}
