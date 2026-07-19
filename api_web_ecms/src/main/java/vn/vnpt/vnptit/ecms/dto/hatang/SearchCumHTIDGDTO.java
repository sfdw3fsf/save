package vn.vnpt.vnptit.ecms.dto.hatang;

public class SearchCumHTIDGDTO {
    private String maCumht;
    private String tenCumht;
    private String linkQuantri;
    private Long ipId;
    private Long congngheId;
    private Long donviQlId;
    private String regionId;
    private Long trangthaivhId;
    private Long trangthaiccdvId;
    private Long idcId;
    private Long phanloaihtId;
    private String canhanQl;
    private Long hieuLucId;
    private String rancherCode;
    private Long capdohtId;
    private Long loaihtId;
    private Long[] loaiDichVuIds;

    public Long getTrangthaivhId() {
        return trangthaivhId;
    }

    public void setTrangthaivhId(Long trangthaivhId) {
        this.trangthaivhId = trangthaivhId;
    }

    public String getMaCumht() {
        return maCumht;
    }

    public void setMaCumht(String maCumht) {
        this.maCumht = maCumht;
    }

    public String getTenCumht() {
        return tenCumht;
    }

    public void setTenCumht(String tenCumht) {
        this.tenCumht = tenCumht;
    }

    public String getLinkQuantri() {
        return linkQuantri;
    }

    public void setLinkQuantri(String linkQuantri) {
        this.linkQuantri = linkQuantri;
    }

    public Long getIpId() {
        return ipId;
    }

    public Long getCongngheId() { return congngheId; }

    public void setCongngheId(Long congngheId) {this.congngheId = congngheId;}

    public void setIpId(Long ipId) {
        this.ipId = ipId;
    }

    public Long getDonviQlId() {
        return donviQlId;
    }

    public void setDonviQlId(Long donviQlId) {
        this.donviQlId = donviQlId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public Long getTrangthaiccdvId() {
        return trangthaiccdvId;
    }

    public void setTrangthaiccdvId(Long trangthaiccdvId) {
        this.trangthaiccdvId = trangthaiccdvId;
    }

    public Long getIdcId() {
        return idcId;
    }

    public void setIdcId(Long idcId) {
        this.idcId = idcId;
    }

    public Long getPhanloaihtId() {
        return phanloaihtId;
    }

    public void setPhanloaihtId(Long phanloaihtId) {
        this.phanloaihtId = phanloaihtId;
    }

    public String getCanhanQl() {
        return canhanQl;
    }

    public void setCanhanQl(String canhanQl) {
        this.canhanQl = canhanQl;
    }

    public Long getHieuLucId() {
        return hieuLucId;
    }

    public void setHieuLucId(Long hieuLucId) {
        this.hieuLucId = hieuLucId;
    }

    public String getRancherCode() {
        return rancherCode;
    }

    public void setRancherCode(String rancherCode) {
        this.rancherCode = rancherCode;
    }

    public Long getCapdohtId() {
        return capdohtId;
    }

    public void setCapdohtId(Long capdohtId) {
        this.capdohtId = capdohtId;
    }

    public Long getLoaihtId() {
        return loaihtId;
    }

    public void setLoaihtId(Long loaihtId) {
        this.loaihtId = loaihtId;
    }

    public Long[] getLoaiDichVuIds() {
        return loaiDichVuIds;
    }

    public void setLoaiDichVuIds(Long[] loaiDichVuIds) {
        this.loaiDichVuIds = loaiDichVuIds;
    }
}
