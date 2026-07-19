package vn.vnpt.vnptit.ecms.dto.hatang;

public class SearchVungDLDTO {
    private Long id;
    private String maVung;
    private Long loaiVungId;
    private String tenVung;
    private Long loaiHtId;
    private Long hatangLtId;
    private Long vungTuDiaId;
    private Long loaiOCungId;
    private String ipHost;
    private Long trangthaiId;
    private Long hieuLucId;
    private Long donViQlId;
    private String nguoiQl;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getLoaiOCungId() {
        return loaiOCungId;
    }

    public void setLoaiOCungId(Long loaiOCungId) {
        this.loaiOCungId = loaiOCungId;
    }

    public String getMaVung() {
        return maVung;
    }

    public void setMaVung(String maVung) {
        this.maVung = maVung;
    }

    public Long getLoaiVungId() {
        return loaiVungId;
    }

    public void setLoaiVungId(Long loaiVungId) {
        this.loaiVungId = loaiVungId;
    }

    public String getTenVung() {
        return tenVung;
    }

    public void setTenVung(String tenVung) {
        this.tenVung = tenVung;
    }

    public Long getLoaiHtId() {
        return loaiHtId;
    }

    public void setLoaiHtId(Long loaiHtId) {
        this.loaiHtId = loaiHtId;
    }

    public Long getHatangLtId() {
        return hatangLtId;
    }

    public void setHatangLtId(Long hatangLtId) {
        this.hatangLtId = hatangLtId;
    }

    public Long getVungTuDiaId() {
        return vungTuDiaId;
    }

    public void setVungTuDiaId(Long vungTuDiaId) {
        this.vungTuDiaId = vungTuDiaId;
    }

    public String getIpHost() {
        return ipHost;
    }

    public void setIpHost(String ipHost) {
        this.ipHost = ipHost;
    }

    public Long getTrangthaiId() {
        return trangthaiId;
    }

    public void setTrangthaiId(Long trangthaiId) {
        this.trangthaiId = trangthaiId;
    }

    public Long getHieuLucId() {
        return hieuLucId;
    }

    public void setHieuLucId(Long hieuLucId) {
        this.hieuLucId = hieuLucId;
    }

    public Long getDonViQlId() {
        return donViQlId;
    }

    public void setDonViQlId(Long donViQlId) {
        this.donViQlId = donViQlId;
    }

    public String getNguoiQl() {
        return nguoiQl;
    }

    public void setNguoiQl(String nguoiQl) {
        this.nguoiQl = nguoiQl;
    }
}
