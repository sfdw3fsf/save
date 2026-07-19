package io.swagger.model.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Danh bạ khách hàng
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class DanhBaKhachHang extends DanhBaBase{

    @JsonProperty("khachHangId")
    private Long khachHangId;

    @JsonProperty("maKh")
    private String maKh;

    @JsonProperty("tenKh")
    private String tenKh;

    @JsonProperty("diaChiKh")
    private String diaChiKh;

    @JsonProperty("sdt")
    private String sdt;

    @JsonProperty("soFax")
    private String soFax;

    @JsonProperty("email")
    private String email;

    @JsonProperty("website")
    private String website;

    @JsonProperty("ngaySn")
    private String ngaySn;

    @JsonProperty("soGt")
    private String soGt;

    @JsonProperty("ngayCap")
    private String ngayCap;

    @JsonProperty("noiCap")
    private String noiCap;

    @JsonProperty("khLonId")
    private Long khLonId;

    @JsonProperty("khachHangChaId")
    private Long khachHangChaId;

    @JsonProperty("loaiGtId")
    private Long loaiGtId;

    @JsonProperty("maHd")
    private String maHd;

    @JsonProperty("maKhCu")
    private String maKhCu;

    private String nguoiCn;

    @JsonProperty("ngayLapHd")
    private String ngayLapHd;

    @JsonProperty("khKt3")
    private Long khKt3;

    @JsonProperty("kmHd")
    private Long kmHd;

    @JsonProperty("hoKhau")
    private String hoKhau;

    @JsonProperty("loaiKhId")
    private Long loaiKhId;

    @JsonProperty("noiCapHk")
    private String noiCapHk;

    @JsonProperty("ngayHk")
    private String ngayHk;

    @JsonProperty("nguoiDd")
    private String nguoiDd;

    @JsonProperty("chucDanh")
    private String chucDanh;

    @JsonProperty("kyHd")
    private Long kyHd;

    @JsonProperty("kieuKhId")
    private Long kieuKhId;

    @JsonProperty("gioiTinh")
    private Long gioiTinh;

    @JsonProperty("danTocId")
    private Long danTocId;

    @JsonProperty("quocTichId")
    private Long quocTichId;

    @JsonProperty("nganhNgheId")
    private Long nganhNgheId;

    @JsonProperty("diaChiNdd")
    private String diaChiNdd;

    @JsonProperty("ngayHetHanGt")
    private String ngayHetHanGt;

    @JsonProperty("mst")
    private String mst;

    @JsonProperty("nganHangId")
    private Long nganHangId;

    @JsonProperty("dacTrung")
    private String dacTrung;

    @JsonProperty("tnc1Id")
    private Long tnc1Id;

    @JsonProperty("tnc2Id")
    private Long tnc2Id;

    @JsonProperty("tnc3Id")
    private Long tnc3Id;

    @JsonProperty("tnc4Id")
    private Long tnc4Id;

    @JsonProperty("khachHangCuId")
    private Long khachHangCuId;

    @JsonProperty("ctvId")
    private Long ctvId;

    @JsonProperty("maHdCu")
    private String maHdCu;

    @JsonProperty("phanLoaiKhId")
    private Long phanLoaiKhId;

    @JsonProperty("maKhWeb")
    private String maKhWeb;

    @JsonProperty("maKhCrm")
    private String maKhCrm;

    @JsonProperty("kinhDo")
    private Float kinhDo;

    @JsonProperty("viDo")
    private Float viDo;


    public DanhBaKhachHang convertDataFromDatabase(ResultSet rs) throws SQLException {
        if(rs != null) {
            stt = rs.getLong("STT");
            khachHangId = rs.getLong("KHACHHANG_ID");
            maKh = rs.getString("MA_KH");
            tenKh = rs.getString("TEN_KH");
            diaChiKh = rs.getString("DIACHI_KH");
            sdt = rs.getString("SO_DT");
            soFax = rs.getString("SO_FAX");
            email = rs.getString("EMAIL");
            website = rs.getString("WEBSITE");
            ngaySn = rs.getString("NGAY_SN");
            soGt = rs.getString("SO_GT");
            ngayCap = rs.getString("NGAYCAP");
            noiCap = rs.getString("NOICAP");
            ghiChu = rs.getString("GHICHU");
            khLonId = rs.getLong("KHLON_ID");
            khachHangChaId = rs.getLong("KHACHHANG_CHA_ID");
            loaiGtId = rs.getLong("LOAIGT_ID");
            maHd = rs.getString("MA_HD");
            maKhCu = rs.getString("MAKH_CU");
            mayCn = rs.getString("MAY_CN");
            ngayCn = rs.getString("NGAY_CN");
            nguoiCn = rs.getString("NGUOI_CN");
            ngayLapHd = rs.getString("NGAYLAP_HD");
            khKt3 = rs.getLong("KH_KT3");
            kmHd = rs.getLong("KM_HD");
            hoKhau = rs.getString("HOKHAU");
            loaiKhId = rs.getLong("LOAIKH_ID");
            noiCapHk = rs.getString("NOICAP_HK");
            ngayHk = rs.getString("NGAY_HK");
            nguoiDd = rs.getString("NGUOI_DD");
            chucDanh = rs.getString("CHUCDANH");
            kyHd = rs.getLong("KY_HD");
            kieuKhId = rs.getLong("KIEUKH_ID");
            ketQuaCh = rs.getString("KETQUA_CH");
            gioiTinh = rs.getLong("GIOITINH");
            danTocId = rs.getLong("DANTOC_ID");
            quocTichId = rs.getLong("QUOCTICH_ID");
            nganhNgheId = rs.getLong("NGANHNGHE_ID");
            diaChiNdd = rs.getString("DIACHI_NDD");
            ngayHetHanGt = rs.getString("NGAYHETHAN_GT");
            mst = rs.getString("MST");
            stk = rs.getString("STK");
            nganHangId = rs.getLong("NGANHANG_ID");
            dacTrung = rs.getString("DACTRUNG");
            tnc1Id = rs.getLong("TNC1_ID");
            tnc2Id = rs.getLong("TNC2_ID");
            tnc3Id = rs.getLong("TNC3_ID");
            tnc4Id = rs.getLong("TNC4_ID");
            khachHangCuId = rs.getLong("KHACHHANGCU_ID");
            ctvId = rs.getLong("CTV_ID");
            maHdCu = rs.getString("MA_HD_CU");
            phanLoaiKhId = rs.getLong("PHANLOAIKH_ID");
            maKhWeb = rs.getString("MA_KH_WEB");
            maKhCrm = rs.getString("MA_KH_CRM");
            tinhId = rs.getLong("TINHID");
            quanId = rs.getLong("QUANID");
            phuongId = rs.getLong("PHUONGID");
            phoId = rs.getLong("PHOID");
            apId = rs.getLong("APID");
            khuId = rs.getLong("KHUID");
            dacDiemId = rs.getLong("DACDIEMID");
            diaChi = rs.getString("DIACHI");
            soNha = rs.getString("SONHA");
            kinhDo = rs.getFloat("KINHDO");
            viDo = rs.getFloat("VIDO");
            datt = rs.getLong("DATT");
            return this;
        } else {
            return null;
        }
    }

    public Long getKhachHangId() {
        return khachHangId;
    }

    public void setKhachHangId(Long khachHangId) {
        this.khachHangId = khachHangId;
    }

    public String getMaKh() {
        return maKh;
    }

    public void setMaKh(String maKh) {
        this.maKh = maKh;
    }

    public String getTenKh() {
        return tenKh;
    }

    public void setTenKh(String tenKh) {
        this.tenKh = tenKh;
    }

    public String getDiaChiKh() {
        return diaChiKh;
    }

    public void setDiaChiKh(String diaChiKh) {
        this.diaChiKh = diaChiKh;
    }

    @Override
    public String getSdt() {
        return sdt;
    }

    @Override
    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getSoFax() {
        return soFax;
    }

    public void setSoFax(String soFax) {
        this.soFax = soFax;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getNgaySn() {
        return ngaySn;
    }

    public void setNgaySn(String ngaySn) {
        this.ngaySn = ngaySn;
    }

    public String getSoGt() {
        return soGt;
    }

    public void setSoGt(String soGt) {
        this.soGt = soGt;
    }

    public String getNgayCap() {
        return ngayCap;
    }

    public void setNgayCap(String ngayCap) {
        this.ngayCap = ngayCap;
    }

    public String getNoiCap() {
        return noiCap;
    }

    public void setNoiCap(String noiCap) {
        this.noiCap = noiCap;
    }


    public Long getKhLonId() {
        return khLonId;
    }

    public void setKhLonId(Long khLonId) {
        this.khLonId = khLonId;
    }

    public Long getKhachHangChaId() {
        return khachHangChaId;
    }

    public void setKhachHangChaId(Long khachHangChaId) {
        this.khachHangChaId = khachHangChaId;
    }

    public Long getLoaiGtId() {
        return loaiGtId;
    }

    public void setLoaiGtId(Long loaiGtId) {
        this.loaiGtId = loaiGtId;
    }

    public String getMaHd() {
        return maHd;
    }

    public void setMaHd(String maHd) {
        this.maHd = maHd;
    }

    public String getMaKhCu() {
        return maKhCu;
    }

    public void setMaKhCu(String maKhCu) {
        this.maKhCu = maKhCu;
    }

    public String getNgayLapHd() {
        return ngayLapHd;
    }

    public void setNgayLapHd(String ngayLapHd) {
        this.ngayLapHd = ngayLapHd;
    }

    public Long getKhKt3() {
        return khKt3;
    }

    public void setKhKt3(Long khKt3) {
        this.khKt3 = khKt3;
    }

    public Long getKmHd() {
        return kmHd;
    }

    public void setKmHd(Long kmHd) {
        this.kmHd = kmHd;
    }

    public String getHoKhau() {
        return hoKhau;
    }

    public void setHoKhau(String hoKhau) {
        this.hoKhau = hoKhau;
    }

    public Long getLoaiKhId() {
        return loaiKhId;
    }

    public void setLoaiKhId(Long loaiKhId) {
        this.loaiKhId = loaiKhId;
    }

    public String getNoiCapHk() {
        return noiCapHk;
    }

    public void setNoiCapHk(String noiCapHk) {
        this.noiCapHk = noiCapHk;
    }

    public String getNgayHk() {
        return ngayHk;
    }

    public void setNgayHk(String ngayHk) {
        this.ngayHk = ngayHk;
    }

    public String getNguoiDd() {
        return nguoiDd;
    }

    public void setNguoiDd(String nguoiDd) {
        this.nguoiDd = nguoiDd;
    }

    public String getChucDanh() {
        return chucDanh;
    }

    public void setChucDanh(String chucDanh) {
        this.chucDanh = chucDanh;
    }

    public Long getKyHd() {
        return kyHd;
    }

    public void setKyHd(Long kyHd) {
        this.kyHd = kyHd;
    }

    public Long getKieuKhId() {
        return kieuKhId;
    }

    public void setKieuKhId(Long kieuKhId) {
        this.kieuKhId = kieuKhId;
    }


    public Long getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(Long gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Long getDanTocId() {
        return danTocId;
    }

    public void setDanTocId(Long danTocId) {
        this.danTocId = danTocId;
    }

    public Long getQuocTichId() {
        return quocTichId;
    }

    public void setQuocTichId(Long quocTichId) {
        this.quocTichId = quocTichId;
    }

    public Long getNganhNgheId() {
        return nganhNgheId;
    }

    public void setNganhNgheId(Long nganhNgheId) {
        this.nganhNgheId = nganhNgheId;
    }

    public String getDiaChiNdd() {
        return diaChiNdd;
    }

    public void setDiaChiNdd(String diaChiNdd) {
        this.diaChiNdd = diaChiNdd;
    }

    public String getNgayHetHanGt() {
        return ngayHetHanGt;
    }

    public void setNgayHetHanGt(String ngayHetHanGt) {
        this.ngayHetHanGt = ngayHetHanGt;
    }

    public String getMst() {
        return mst;
    }

    public void setMst(String mst) {
        this.mst = mst;
    }

    public Long getNganHangId() {
        return nganHangId;
    }

    public void setNganHangId(Long nganHangId) {
        this.nganHangId = nganHangId;
    }

    public String getDacTrung() {
        return dacTrung;
    }

    public void setDacTrung(String dacTrung) {
        this.dacTrung = dacTrung;
    }

    public Long getTnc1Id() {
        return tnc1Id;
    }

    public void setTnc1Id(Long tnc1Id) {
        this.tnc1Id = tnc1Id;
    }

    public Long getTnc2Id() {
        return tnc2Id;
    }

    public void setTnc2Id(Long tnc2Id) {
        this.tnc2Id = tnc2Id;
    }

    public Long getTnc3Id() {
        return tnc3Id;
    }

    public void setTnc3Id(Long tnc3Id) {
        this.tnc3Id = tnc3Id;
    }

    public Long getTnc4Id() {
        return tnc4Id;
    }

    public void setTnc4Id(Long tnc4Id) {
        this.tnc4Id = tnc4Id;
    }

    public Long getKhachHangCuId() {
        return khachHangCuId;
    }

    public void setKhachHangCuId(Long khachHangCuId) {
        this.khachHangCuId = khachHangCuId;
    }

    public Long getCtvId() {
        return ctvId;
    }

    public void setCtvId(Long ctvId) {
        this.ctvId = ctvId;
    }

    public String getMaHdCu() {
        return maHdCu;
    }

    public void setMaHdCu(String maHdCu) {
        this.maHdCu = maHdCu;
    }

    public Long getPhanLoaiKhId() {
        return phanLoaiKhId;
    }

    public void setPhanLoaiKhId(Long phanLoaiKhId) {
        this.phanLoaiKhId = phanLoaiKhId;
    }

    public String getMaKhWeb() {
        return maKhWeb;
    }

    public void setMaKhWeb(String maKhWeb) {
        this.maKhWeb = maKhWeb;
    }

    public String getMaKhCrm() {
        return maKhCrm;
    }

    public void setMaKhCrm(String maKhCrm) {
        this.maKhCrm = maKhCrm;
    }

    public Float getKinhDo() {
        return kinhDo;
    }

    public void setKinhDo(Float kinhDo) {
        this.kinhDo = kinhDo;
    }

    public Float getViDo() {
        return viDo;
    }

    public void setViDo(Float viDo) {
        this.viDo = viDo;
    }
}
