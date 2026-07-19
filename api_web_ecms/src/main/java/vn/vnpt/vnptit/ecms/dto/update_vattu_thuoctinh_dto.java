package vn.vnpt.vnptit.ecms.dto;

public class update_vattu_thuoctinh_dto {

    private Long thietbi_id;
    private Long ttvt_id;
    private Long gttt_id;
    private Long kieutt_id;
    private String giatri;

    public Long getThietbi_id() {
        return thietbi_id;
    }

    public void setThietbi_id(Long thietbi_id) {
        this.thietbi_id = thietbi_id;
    }

    public Long getTtvt_id() {
        return ttvt_id;
    }

    public void setTtvt_id(Long ttvt_id) {
        this.ttvt_id = ttvt_id;
    }

    public Long getGttt_id() {
        return gttt_id;
    }

    public void setGttt_id(Long gttt_id) {
        this.gttt_id = gttt_id;
    }

    public Long getKieutt_id() {
        return kieutt_id;
    }

    public void setKieutt_id(Long kieutt_id) {
        this.kieutt_id = kieutt_id;
    }

    public String getGiatri() {
        return giatri;
    }

    public void setGiatri(String giatri) {
        this.giatri = giatri;
    }
}
