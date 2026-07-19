package vn.vnpt.vnptit.ecms.dto.idc;

public class NhomHaTangRequestDTO {
    private Number id;
    private String ten;
    private String tentat;
    private String mota;
    private String ghichu;
    private Number sudung;
    private Number stt;

    public NhomHaTangRequestDTO(Number id, String ten, String tentat, String mota, String ghichu, Number sudung, Number stt) {
        this.id = id;
        this.ten = ten;
        this.tentat = tentat;
        this.mota = mota;
        this.ghichu = ghichu;
        this.sudung = sudung;
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

    public String getTentat() {
        return tentat;
    }

    public void setTentat(String tentat) {
        this.tentat = tentat;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
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

    public Number getStt() {
        return stt;
    }

    public void setStt(Number stt) {
        this.stt = stt;
    }
}
