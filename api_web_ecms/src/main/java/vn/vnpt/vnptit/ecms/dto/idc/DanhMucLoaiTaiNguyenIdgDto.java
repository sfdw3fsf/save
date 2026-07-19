package vn.vnpt.vnptit.ecms.dto.idc;

public class DanhMucLoaiTaiNguyenIdgDto {

    private Long loaiTnId;
    private String ten;
    private String tenTat;
    private String moTa;
    private Integer suDung;
    private String ghiChu;
    private String maSpdv;
    private Integer vcpu;
    private Integer vram;
    private Integer vstorage;
    private Integer nhomTaiNguyen;

    public String getTenTat() {
        return tenTat;
    }

    public void setTenTat(String tenTat) {
        this.tenTat = tenTat;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Long getLoaiTnId() {
        return loaiTnId;
    }

    public void setLoaiTnId(Long loaiTnId) {
        this.loaiTnId = loaiTnId;
    }


    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Integer getSuDung() {
        return suDung;
    }

    public void setSuDung(Integer suDung) {
        this.suDung = suDung;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMaSpdv() {
        return maSpdv;
    }

    public void setMaSpdv(String maSpdv) {
        this.maSpdv = maSpdv;
    }

    public Integer getVcpu() {
        return vcpu;
    }

    public void setVcpu(Integer vcpu) {
        this.vcpu = vcpu;
    }

    public Integer getVram() {
        return vram;
    }

    public void setVram(Integer vram) {
        this.vram = vram;
    }

    public Integer getVstorage() {
        return vstorage;
    }

    public void setVstorage(Integer vstorage) {
        this.vstorage = vstorage;
    }

    public Integer getNhomTaiNguyen() {
        return nhomTaiNguyen;
    }

    public void setNhomTaiNguyen(Integer nhomTaiNguyen) {
        this.nhomTaiNguyen = nhomTaiNguyen;
    }
}

