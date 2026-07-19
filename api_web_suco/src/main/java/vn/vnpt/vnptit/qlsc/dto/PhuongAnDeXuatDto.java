package vn.vnpt.vnptit.qlsc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * DTO nhận phuongan_dexuat từ FE theo format TTS API (camelCase / snake_case).
 * Method toGroovyMap() chuyển sang Map key UPPERCASE để Groovy bên tichhop đọc
 * được:
 * parsedData.PHUONGAN_DEXUAT → [ item.ID_YEU_CAU, item.ID_BUOC, item.PHIEU_ID,
 * ... ]
 */
public class PhuongAnDeXuatDto {

    // --- TTS API format (FE gửi theo đây) ---
    @JsonProperty("idYeuCau")
    private String idYeuCau;

    @JsonProperty("idBuoc")
    private String idBuoc;

    @JsonProperty("phieu_id")
    private Integer phieuId;

    @JsonProperty("phanloai_padx_id")
    private Integer phanloaiPadxId;

    @JsonProperty("padx_id")
    private Integer padxId;

    @JsonProperty("file_dokiem")
    private String fileDokiem;

    @JsonProperty("file_dinhkem")
    private Object fileDinhkem;

    @JsonProperty("ghichu")
    private String ghichu;

    @JsonProperty("loai_crs_hientruong")
    private String loaiCrsHientruong;

    @JsonProperty("phuongan_dexuat")
    private String phuonganDexuat;

    @JsonProperty("docao_hientai")
    private String docaoHientai;

    @JsonProperty("docao_dexuat")
    private String docaoDexuat;

    @JsonProperty("huong_hientai")
    private String huongHientai;

    @JsonProperty("huong_dexuat")
    private String huongDexuat;

    @JsonProperty("mtilt_hientai")
    private String mtiltHientai;

    @JsonProperty("mtilt_dexuat")
    private String mtiltDexuat;

    @JsonProperty("etilt_hientai")
    private String etiltHientai;

    @JsonProperty("etilt_dexuat")
    private String etiltDexuat;

    @JsonProperty("ten_thamso")
    private String tenThamso;

    @JsonProperty("ynghia_thamso")
    private String ynghiaThamso;

    @JsonProperty("giatri_macdinh")
    private String giatriMacdinh;

    @JsonProperty("giatri_hientai")
    private String giatriHientai;

    @JsonProperty("giatri_dexuat")
    private String giatriDexuat;

    @JsonProperty("loai_phuongan")
    private String loaiPhuongan;

    @JsonProperty("muc_dich")
    private String mucDich;

    @JsonProperty("loai_tram")
    private String loaiTram;

    @JsonProperty("quy_hoach")
    private String quyHoach;

    @JsonProperty("ma_csht_qh")
    private String maCshtQh;

    @JsonProperty("csht_long")
    private String cshtLong;

    @JsonProperty("csht_lat")
    private String cshtLat;

    @JsonProperty("bang_tan")
    private String bangTan;

    @JsonProperty("cauhinh_dexuat")
    private String cauhinhDexuat;

    @JsonProperty("goc_huong")
    private String gocHuong;

    @JsonProperty("antena_high")
    private String antenaHigh;

    @JsonProperty("kc_tram_gannhat")
    private String kcTramGannhat;

    @JsonProperty("congnghe")
    private String congnghe;

    @JsonProperty("vendor")
    private String vendor;

    @JsonProperty("ten_vattu")
    private String tenVattu;

    @JsonProperty("so_luong")
    private String soLuong;

    @JsonProperty("dvt")
    private String dvt;

    @JsonProperty("nguyennhan")
    private String nguyenNhan;

    @JsonProperty("mucdich_bosung")
    private String mucdichBosung;

    @JsonProperty("ten_license")
    private String tenLicense;

    @JsonProperty("cauhinh")
    private String cauhinh;

    @JsonProperty("mucdich")
    private String mucdich;

    @JsonProperty("loaitram")
    private String loaitram;

    @JsonProperty("chitiet_phuongan")
    private String chitietPhuongan;

    @JsonProperty("quyhoach")
    private String quyhoach;

    @JsonProperty("ma_csht_quyhoach")
    private String maCshtQuyhoach;

    @JsonProperty("long_tbi")
    private String longTbi;

    @JsonProperty("lat_tbi")
    private String latTbi;

    @JsonProperty("bangtan")
    private String bangtan;

    @JsonProperty("goc_tbi")
    private String gocTbi;

    @JsonProperty("antena_tbi")
    private String antenaTbi;

    @JsonProperty("trafic_hientai")
    private String traficHientai;

    @JsonProperty("tsd")
    private String tsd;

    // ===== Chuyển sang Map UPPERCASE để Groovy đọc đúng =====
    // Groovy: parsedData.PHUONGAN_DEXUAT → item.ID_YEU_CAU, item.ID_BUOC,
    // item.PHIEU_ID...
    public Map<String, Object> toGroovyMap() {
        Map<String, Object> m = new HashMap<>();
        m.put("ID_YEU_CAU", idYeuCau != null ? idYeuCau : "");
        m.put("ID_BUOC", idBuoc != null ? idBuoc : "");
        m.put("PHIEU_ID", phieuId != null ? phieuId : 0);
        m.put("PHANLOAI_PADX_ID", phanloaiPadxId != null ? phanloaiPadxId : 0);
        m.put("PADX_ID", padxId != null ? padxId : 0);
        m.put("FILE_DOKIEM", fileDokiem != null ? fileDokiem : "");
        m.put("FILE_DINHKEM", fileDinhkem != null ? fileDinhkem : "");
        m.put("GHICHU", ghichu != null ? ghichu : "");
        m.put("LOAI_CRS_HIENTRUONG", loaiCrsHientruong != null ? loaiCrsHientruong : "");
        m.put("PHUONGAN_DEXUAT", phuonganDexuat != null ? phuonganDexuat : "");
        m.put("DOCAO_HIENTAI", docaoHientai != null ? docaoHientai : "");
        m.put("DOCAO_DEXUAT", docaoDexuat != null ? docaoDexuat : "");
        m.put("HUONG_HIENTAI", huongHientai != null ? huongHientai : "");
        m.put("HUONG_DEXUAT", huongDexuat != null ? huongDexuat : "");
        m.put("MTILT_HIENTAI", mtiltHientai != null ? mtiltHientai : "");
        m.put("MTILT_DEXUAT", mtiltDexuat != null ? mtiltDexuat : "");
        m.put("ETILT_HIENTAI", etiltHientai != null ? etiltHientai : "");
        m.put("ETILT_DEXUAT", etiltDexuat != null ? etiltDexuat : "");
        m.put("TEN_THAMSO", tenThamso != null ? tenThamso : "");
        m.put("YNGHIA_THAMSO", ynghiaThamso != null ? ynghiaThamso : "");
        m.put("GIATRI_MACDINH", giatriMacdinh != null ? giatriMacdinh : "");
        m.put("GIATRI_HIENTAI", giatriHientai != null ? giatriHientai : "");
        m.put("GIATRI_DEXUAT", giatriDexuat != null ? giatriDexuat : "");
        m.put("LOAI_PHUONGAN", loaiPhuongan != null ? loaiPhuongan : "");
        m.put("MUC_DICH", mucDich != null ? mucDich : "");
        m.put("LOAI_TRAM", loaiTram != null ? loaiTram : "");
        m.put("QUY_HOACH", quyHoach != null ? quyHoach : "");
        m.put("MA_CSHT_QH", maCshtQh != null ? maCshtQh : "");
        m.put("CSHT_LONG", cshtLong != null ? cshtLong : "");
        m.put("CSHT_LAT", cshtLat != null ? cshtLat : "");
        m.put("BANG_TAN", bangTan != null ? bangTan : "");
        m.put("CAUHINH_DEXUAT", cauhinhDexuat != null ? cauhinhDexuat : "");
        m.put("GOC_HUONG", gocHuong != null ? gocHuong : "");
        m.put("ANTENA_HIGH", antenaHigh != null ? antenaHigh : "");
        m.put("KC_TRAM_GANNHAT", kcTramGannhat != null ? kcTramGannhat : "");
        m.put("CONGNGHE", congnghe != null ? congnghe : "");
        m.put("VENDOR", vendor != null ? vendor : "");
        m.put("TEN_VATTU", tenVattu != null ? tenVattu : "");
        m.put("SO_LUONG", soLuong != null ? soLuong : "");
        m.put("DVT", dvt != null ? dvt : "");
        m.put("NGUYENNHAN", nguyenNhan != null ? nguyenNhan : "");
        m.put("MUCDICH_BOSUNG", mucdichBosung != null ? mucdichBosung : "");
        m.put("TEN_LICENSE", tenLicense != null ? tenLicense : "");
        m.put("CAUHINH", cauhinh != null ? cauhinh : "");
        m.put("MUCDICH", mucdich != null ? mucdich : "");
        m.put("LOAITRAM", loaitram != null ? loaitram : "");
        m.put("CHITIET_PHUONGAN", chitietPhuongan != null ? chitietPhuongan : "");
        m.put("QUYHOACH", quyhoach != null ? quyhoach : "");
        m.put("MA_CSHT_QUYHOACH", maCshtQuyhoach != null ? maCshtQuyhoach : "");
        m.put("LONG_TBI", longTbi != null ? longTbi : "");
        m.put("LAT_TBI", latTbi != null ? latTbi : "");
        m.put("BANGTAN", bangtan != null ? bangtan : "");
        m.put("GOC_TBI", gocTbi != null ? gocTbi : "");
        m.put("ANTENA_TBI", antenaTbi != null ? antenaTbi : "");
        m.put("TRAFIC_HIENTAI", traficHientai != null ? traficHientai : "");
        m.put("TSD", tsd != null ? tsd : "");
        return m;
    }

    // ===== Getters & Setters =====

    public String getIdYeuCau() {
        return idYeuCau;
    }

    public void setIdYeuCau(String idYeuCau) {
        this.idYeuCau = idYeuCau;
    }

    public String getIdBuoc() {
        return idBuoc;
    }

    public void setIdBuoc(String idBuoc) {
        this.idBuoc = idBuoc;
    }

    public Integer getPhieuId() {
        return phieuId;
    }

    public void setPhieuId(Integer phieuId) {
        this.phieuId = phieuId;
    }

    public Integer getPhanloaiPadxId() {
        return phanloaiPadxId;
    }

    public void setPhanloaiPadxId(Integer phanloaiPadxId) {
        this.phanloaiPadxId = phanloaiPadxId;
    }

    public Integer getPadxId() {
        return padxId;
    }

    public void setPadxId(Integer padxId) {
        this.padxId = padxId;
    }

    public String getFileDokiem() {
        return fileDokiem;
    }

    public void setFileDokiem(String fileDokiem) {
        this.fileDokiem = fileDokiem;
    }

    public Object getFileDinhkem() {
        return fileDinhkem;
    }

    public void setFileDinhkem(Object fileDinhkem) {
        this.fileDinhkem = fileDinhkem;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getLoaiCrsHientruong() {
        return loaiCrsHientruong;
    }

    public void setLoaiCrsHientruong(String loaiCrsHientruong) {
        this.loaiCrsHientruong = loaiCrsHientruong;
    }

    public String getPhuonganDexuat() {
        return phuonganDexuat;
    }

    public void setPhuonganDexuat(String phuonganDexuat) {
        this.phuonganDexuat = phuonganDexuat;
    }

    public String getDocaoHientai() {
        return docaoHientai;
    }

    public void setDocaoHientai(String docaoHientai) {
        this.docaoHientai = docaoHientai;
    }

    public String getDocaoDexuat() {
        return docaoDexuat;
    }

    public void setDocaoDexuat(String docaoDexuat) {
        this.docaoDexuat = docaoDexuat;
    }

    public String getHuongHientai() {
        return huongHientai;
    }

    public void setHuongHientai(String huongHientai) {
        this.huongHientai = huongHientai;
    }

    public String getHuongDexuat() {
        return huongDexuat;
    }

    public void setHuongDexuat(String huongDexuat) {
        this.huongDexuat = huongDexuat;
    }

    public String getMtiltHientai() {
        return mtiltHientai;
    }

    public void setMtiltHientai(String mtiltHientai) {
        this.mtiltHientai = mtiltHientai;
    }

    public String getMtiltDexuat() {
        return mtiltDexuat;
    }

    public void setMtiltDexuat(String mtiltDexuat) {
        this.mtiltDexuat = mtiltDexuat;
    }

    public String getEtiltHientai() {
        return etiltHientai;
    }

    public void setEtiltHientai(String etiltHientai) {
        this.etiltHientai = etiltHientai;
    }

    public String getEtiltDexuat() {
        return etiltDexuat;
    }

    public void setEtiltDexuat(String etiltDexuat) {
        this.etiltDexuat = etiltDexuat;
    }

    public String getTenThamso() {
        return tenThamso;
    }

    public void setTenThamso(String tenThamso) {
        this.tenThamso = tenThamso;
    }

    public String getYnghiaThamso() {
        return ynghiaThamso;
    }

    public void setYnghiaThamso(String ynghiaThamso) {
        this.ynghiaThamso = ynghiaThamso;
    }

    public String getGiatriMacdinh() {
        return giatriMacdinh;
    }

    public void setGiatriMacdinh(String giatriMacdinh) {
        this.giatriMacdinh = giatriMacdinh;
    }

    public String getGiatriHientai() {
        return giatriHientai;
    }

    public void setGiatriHientai(String giatriHientai) {
        this.giatriHientai = giatriHientai;
    }

    public String getGiatriDexuat() {
        return giatriDexuat;
    }

    public void setGiatriDexuat(String giatriDexuat) {
        this.giatriDexuat = giatriDexuat;
    }

    public String getLoaiPhuongan() {
        return loaiPhuongan;
    }

    public void setLoaiPhuongan(String loaiPhuongan) {
        this.loaiPhuongan = loaiPhuongan;
    }

    public String getMucDich() {
        return mucDich;
    }

    public void setMucDich(String mucDich) {
        this.mucDich = mucDich;
    }

    public String getLoaiTram() {
        return loaiTram;
    }

    public void setLoaiTram(String loaiTram) {
        this.loaiTram = loaiTram;
    }

    public String getQuyHoach() {
        return quyHoach;
    }

    public void setQuyHoach(String quyHoach) {
        this.quyHoach = quyHoach;
    }

    public String getMaCshtQh() {
        return maCshtQh;
    }

    public void setMaCshtQh(String maCshtQh) {
        this.maCshtQh = maCshtQh;
    }

    public String getCshtLong() {
        return cshtLong;
    }

    public void setCshtLong(String cshtLong) {
        this.cshtLong = cshtLong;
    }

    public String getCshtLat() {
        return cshtLat;
    }

    public void setCshtLat(String cshtLat) {
        this.cshtLat = cshtLat;
    }

    public String getBangTan() {
        return bangTan;
    }

    public void setBangTan(String bangTan) {
        this.bangTan = bangTan;
    }

    public String getCauhinhDexuat() {
        return cauhinhDexuat;
    }

    public void setCauhinhDexuat(String cauhinhDexuat) {
        this.cauhinhDexuat = cauhinhDexuat;
    }

    public String getGocHuong() {
        return gocHuong;
    }

    public void setGocHuong(String gocHuong) {
        this.gocHuong = gocHuong;
    }

    public String getAntenaHigh() {
        return antenaHigh;
    }

    public void setAntenaHigh(String antenaHigh) {
        this.antenaHigh = antenaHigh;
    }

    public String getKcTramGannhat() {
        return kcTramGannhat;
    }

    public void setKcTramGannhat(String kcTramGannhat) {
        this.kcTramGannhat = kcTramGannhat;
    }

    public String getCongnghe() {
        return congnghe;
    }

    public void setCongnghe(String congnghe) {
        this.congnghe = congnghe;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getTenVattu() {
        return tenVattu;
    }

    public void setTenVattu(String tenVattu) {
        this.tenVattu = tenVattu;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getDvt() {
        return dvt;
    }

    public void setDvt(String dvt) {
        this.dvt = dvt;
    }

    public String getNguyenNhan() {
        return nguyenNhan;
    }

    public void setNguyenNhan(String nguyenNhan) {
        this.nguyenNhan = nguyenNhan;
    }

    public String getMucdichBosung() {
        return mucdichBosung;
    }

    public void setMucdichBosung(String mucdichBosung) {
        this.mucdichBosung = mucdichBosung;
    }

    public String getTenLicense() {
        return tenLicense;
    }

    public void setTenLicense(String tenLicense) {
        this.tenLicense = tenLicense;
    }

    public String getCauhinh() {
        return cauhinh;
    }

    public void setCauhinh(String cauhinh) {
        this.cauhinh = cauhinh;
    }

    public String getMucdich() {
        return mucdich;
    }

    public void setMucdich(String mucdich) {
        this.mucdich = mucdich;
    }

    public String getLoaitram() {
        return loaitram;
    }

    public void setLoaitram(String loaitram) {
        this.loaitram = loaitram;
    }

    public String getChitietPhuongan() {
        return chitietPhuongan;
    }

    public void setChitietPhuongan(String chitietPhuongan) {
        this.chitietPhuongan = chitietPhuongan;
    }

    public String getQuyhoach() {
        return quyhoach;
    }

    public void setQuyhoach(String quyhoach) {
        this.quyhoach = quyhoach;
    }

    public String getMaCshtQuyhoach() {
        return maCshtQuyhoach;
    }

    public void setMaCshtQuyhoach(String maCshtQuyhoach) {
        this.maCshtQuyhoach = maCshtQuyhoach;
    }

    public String getLongTbi() {
        return longTbi;
    }

    public void setLongTbi(String longTbi) {
        this.longTbi = longTbi;
    }

    public String getLatTbi() {
        return latTbi;
    }

    public void setLatTbi(String latTbi) {
        this.latTbi = latTbi;
    }

    public String getBangtan() {
        return bangtan;
    }

    public void setBangtan(String bangtan) {
        this.bangtan = bangtan;
    }

    public String getGocTbi() {
        return gocTbi;
    }

    public void setGocTbi(String gocTbi) {
        this.gocTbi = gocTbi;
    }

    public String getAntenaTbi() {
        return antenaTbi;
    }

    public void setAntenaTbi(String antenaTbi) {
        this.antenaTbi = antenaTbi;
    }

    public String getTraficHientai() {
        return traficHientai;
    }

    public void setTraficHientai(String traficHientai) {
        this.traficHientai = traficHientai;
    }

    public String getTsd() {
        return tsd;
    }

    public void setTsd(String tsd) {
        this.tsd = tsd;
    }
}
