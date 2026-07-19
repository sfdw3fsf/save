package vn.vnpt.vnptit.ecms.dto.hatang;

public class QuanLyVrfDto {
    private Number vrfId;
    private String tenVrf;
    private Number idcId;
    private String asn;
    private String rd;
    private String rtExport;
    private String rtImport;
    private Number hieuluc;
    private String ghichu;
    //private String ids;

    public QuanLyVrfDto() {
    }

    public QuanLyVrfDto(Number vrfId, String tenVrf, Number idcId, String asn, String rd,
                        String rtExport, String rtImport, Number hieuluc, String ghichu) {
        this.vrfId = vrfId;
        this.tenVrf = tenVrf;
        this.idcId = idcId;
        this.asn = asn;
        this.rd = rd;
        this.rtExport = rtExport;
        this.rtImport = rtImport;
        this.hieuluc = hieuluc;
        this.ghichu = ghichu;
    }

    public Number getVrfId() {
        return vrfId;
    }

    public void setVrfId(Number vrfId) {
        this.vrfId = vrfId;
    }

    public String getTenVrf() {
        return tenVrf;
    }

    public void setTenVrf(String tenVrf) {
        this.tenVrf = tenVrf;
    }

    public Number getIdcId() {
        return idcId;
    }

    public void setIdcId(Number idcId) {
        this.idcId = idcId;
    }

    public String getAsn() {
        return asn;
    }

    public void setAsn(String asn) {
        this.asn = asn;
    }

    public String getRd() {
        return rd;
    }

    public void setRd(String rd) {
        this.rd = rd;
    }

    public String getRtExport() {
        return rtExport;
    }

    public void setRtExport(String rtExport) {
        this.rtExport = rtExport;
    }

    public String getRtImport() {
        return rtImport;
    }

    public void setRtImport(String rtImport) {
        this.rtImport = rtImport;
    }

    public Number getHieuluc() {
        return hieuluc;
    }

    public void setHieuluc(Number hieuluc) {
        this.hieuluc = hieuluc;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }
}

