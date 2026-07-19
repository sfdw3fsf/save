package vn.vnpt.vnptit.ecms.dto.idc;

public class ChungLoaiServerDto {
    private Number chungloaisvrId;
    private String maVt;
    private String modelThietBi;
    private Number hangsxId;
    private Number congSuat;
    private Number fullDepth;
    private Number cpuSoKhe;
    private Number cpuSoSocket;
    private String cpuModel;
    private Number sokheRam;
    private Number sokheSsd;
    private Number sokheHdd;
    private Number sokheNvme;
    private String gpuModel;
    private Number gpuSoLuong;
    private Number soslotFan;
    private Number soslotPci;
    private Number dpSoCong;
    private String dpTenCong;
    private String dpLoaiCong;
    private Number dpBangThong;
    private Number dpTtmoduleId;
    private Number dpMucdichsdId;
    private Number ppSoCong;
    private String ppTenCong;
    private Number ppDongDien;
    private Number ppDienAp;
    private Number ppLoaiNd;
    private Number ppChuandnId;
    private Number cpMucdichsdId;
    private String cpLoaiCong;
    private Number cpSl;
    private String cpTenCong;
    private String moTa;
    private Boolean suDung;
    private String ghiChu;

    public void isValid() {
        // Có thể thêm validation nếu cần
    }

    public Number getChungloaisvrId() {
        return chungloaisvrId;
    }

    public void setChungloaisvrId(Number chungloaisvrId) {
        this.chungloaisvrId = chungloaisvrId;
    }

    public String getMaVt() {
        return maVt != null ? maVt.trim() : null;
    }

    public void setMaVt(String maVt) {
        this.maVt = maVt;
    }

    public String getModelThietBi() {
        return modelThietBi != null ? modelThietBi.trim() : null;
    }

    public void setModelThietBi(String modelThietBi) {
        this.modelThietBi = modelThietBi;
    }

    public Number getHangsxId() {
        return hangsxId;
    }

    public void setHangsxId(Number hangsxId) {
        this.hangsxId = hangsxId;
    }

    public Number getCongSuat() {
        return congSuat;
    }

    public void setCongSuat(Number congSuat) {
        this.congSuat = congSuat;
    }

    public Number getFullDepth() {
        return fullDepth;
    }

    public void setFullDepth(Number fullDepth) {
        this.fullDepth = fullDepth;
    }

    public Number getCpuSoKhe() {
        return cpuSoKhe;
    }

    public void setCpuSoKhe(Number cpuSoKhe) {
        this.cpuSoKhe = cpuSoKhe;
    }

    public Number getCpuSoSocket() {
        return cpuSoSocket;
    }

    public void setCpuSoSocket(Number cpuSoSocket) {
        this.cpuSoSocket = cpuSoSocket;
    }

    public String getCpuModel() {
        return cpuModel != null ? cpuModel.trim() : null;
    }

    public void setCpuModel(String cpuModel) {
        this.cpuModel = cpuModel;
    }

    public Number getSokheRam() {
        return sokheRam;
    }

    public void setSokheRam(Number sokheRam) {
        this.sokheRam = sokheRam;
    }

    public Number getSokheSsd() {
        return sokheSsd;
    }

    public void setSokheSsd(Number sokheSsd) {
        this.sokheSsd = sokheSsd;
    }

    public Number getSokheHdd() {
        return sokheHdd;
    }

    public void setSokheHdd(Number sokheHdd) {
        this.sokheHdd = sokheHdd;
    }

    public Number getSokheNvme() {
        return sokheNvme;
    }

    public void setSokheNvme(Number sokheNvme) {
        this.sokheNvme = sokheNvme;
    }

    public String getGpuModel() {
        return gpuModel != null ? gpuModel.trim() : null;
    }

    public void setGpuModel(String gpuModel) {
        this.gpuModel = gpuModel;
    }

    public Number getGpuSoLuong() {
        return gpuSoLuong;
    }

    public void setGpuSoLuong(Number gpuSoLuong) {
        this.gpuSoLuong = gpuSoLuong;
    }

    public Number getSoslotFan() {
        return soslotFan;
    }

    public void setSoslotFan(Number soslotFan) {
        this.soslotFan = soslotFan;
    }

    public Number getSoslotPci() {
        return soslotPci;
    }

    public void setSoslotPci(Number soslotPci) {
        this.soslotPci = soslotPci;
    }

    public Number getDpSoCong() {
        return dpSoCong;
    }

    public void setDpSoCong(Number dpSoCong) {
        this.dpSoCong = dpSoCong;
    }

    public String getDpTenCong() {
        return dpTenCong != null ? dpTenCong.trim() : null;
    }

    public void setDpTenCong(String dpTenCong) {
        this.dpTenCong = dpTenCong;
    }

    public String getDpLoaiCong() {
        return dpLoaiCong != null ? dpLoaiCong.trim() : null;
    }

    public void setDpLoaiCong(String dpLoaiCong) {
        this.dpLoaiCong = dpLoaiCong;
    }

    public Number getDpBangThong() {
        return dpBangThong;
    }

    public void setDpBangThong(Number dpBangThong) {
        this.dpBangThong = dpBangThong;
    }

    public Number getDpTtmoduleId() {
        return dpTtmoduleId;
    }

    public void setDpTtmoduleId(Number dpTtmoduleId) {
        this.dpTtmoduleId = dpTtmoduleId;
    }

    public Number getDpMucdichsdId() {
        return dpMucdichsdId;
    }

    public void setDpMucdichsdId(Number dpMucdichsdId) {
        this.dpMucdichsdId = dpMucdichsdId;
    }

    public Number getPpSoCong() {
        return ppSoCong;
    }

    public void setPpSoCong(Number ppSoCong) {
        this.ppSoCong = ppSoCong;
    }

    public String getPpTenCong() {
        return ppTenCong != null ? ppTenCong.trim() : null;
    }

    public void setPpTenCong(String ppTenCong) {
        this.ppTenCong = ppTenCong;
    }

    public Number getPpDongDien() {
        return ppDongDien;
    }

    public void setPpDongDien(Number ppDongDien) {
        this.ppDongDien = ppDongDien;
    }

    public Number getPpDienAp() {
        return ppDienAp;
    }

    public void setPpDienAp(Number ppDienAp) {
        this.ppDienAp = ppDienAp;
    }

    public Number getPpLoaiNd() {
        return ppLoaiNd;
    }

    public void setPpLoaiNd(Number ppLoaiNd) {
        this.ppLoaiNd = ppLoaiNd;
    }

    public Number getPpChuandnId() {
        return ppChuandnId;
    }

    public void setPpChuandnId(Number ppChuandnId) {
        this.ppChuandnId = ppChuandnId;
    }

    public Number getCpMucdichsdId() {
        return cpMucdichsdId;
    }

    public void setCpMucdichsdId(Number cpMucdichsdId) {
        this.cpMucdichsdId = cpMucdichsdId;
    }

    public String getCpLoaiCong() {
        return cpLoaiCong != null ? cpLoaiCong.trim() : null;
    }

    public void setCpLoaiCong(String cpLoaiCong) {
        this.cpLoaiCong = cpLoaiCong;
    }

    public Number getCpSl() {
        return cpSl;
    }

    public void setCpSl(Number cpSl) {
        this.cpSl = cpSl;
    }

    public String getCpTenCong() {
        return cpTenCong != null ? cpTenCong.trim() : null;
    }

    public void setCpTenCong(String cpTenCong) {
        this.cpTenCong = cpTenCong;
    }

    public String getMoTa() {
        return moTa != null ? moTa.trim() : null;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Boolean getSuDung() {
        return suDung != null ? suDung : true;
    }

    public void setSuDung(Boolean suDung) {
        this.suDung = suDung != null ? suDung : true;
    }

    public String getGhiChu() {
        return ghiChu != null ? ghiChu.trim() : null;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
