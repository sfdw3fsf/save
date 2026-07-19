package vn.vnpt.vnptit.ecms.dto.hatang;

public class NhaCungCapV2RequestDTO {
    private Number id;
    private String ten;
    private String tentat;
    private String diachi;
    private String dienthoai;
    private String email;
    private String website;
    private String so_tk;
    private String nguoi_dd;
    private String ghichu;
    private Number sudung;
    private Number stt;

    public NhaCungCapV2RequestDTO(Number id, String ten, String tentat, String diachi, String dienthoai, String email, String website, String so_tk, String nguoi_dd, String ghichu, Number sudung) {
        this.id = id;
        this.ten = ten;
        this.tentat = tentat;
        this.diachi = diachi;
        this.dienthoai = dienthoai;
        this.email = email;
        this.website = website;
        this.so_tk = so_tk;
        this.nguoi_dd = nguoi_dd;
        this.ghichu = ghichu;
        this.sudung = sudung;
    }

    public Number getStt() {
        return stt;
    }

    public void setStt(Number stt) {
        this.stt = stt;
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String gettentat() {
        return tentat;
    }

    public void settentat(String tentat) {
        this.tentat = tentat;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getDienthoai() {
        return dienthoai;
    }

    public void setDienthoai(String dienthoai) {
        this.dienthoai = dienthoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getSo_tk() {
        return so_tk;
    }

    public void setSo_tk(String so_tk) {
        this.so_tk = so_tk;
    }

    public String getNguoi_dd() {
        return nguoi_dd;
    }

    public void setNguoi_dd(String nguoi_dd) {
        this.nguoi_dd = nguoi_dd;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public Number getSudung() {
        return sudung;
    }

    public void setSudung(Number sudung) {
        this.sudung = sudung;
    }
}
