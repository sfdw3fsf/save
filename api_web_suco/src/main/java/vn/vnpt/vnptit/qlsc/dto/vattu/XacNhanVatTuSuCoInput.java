package vn.vnpt.vnptit.qlsc.dto.vattu;

public class XacNhanVatTuSuCoInput {
    private Long sucoId;
    private Integer isNet;
    private String noidungNVKT;
    private String dsVattu;

    public Long getSucoId() {
        return sucoId;
    }

    public void setSucoId(Long sucoId) {
        this.sucoId = sucoId;
    }

    public Integer getIsNet() {
        return isNet;
    }

    public void setIsNet(Integer isNet) {
        this.isNet = isNet;
    }

    public String getNoidungNVKT() {
        return noidungNVKT;
    }

    public void setNoidungNVKT(String noidungNVKT) {
        this.noidungNVKT = noidungNVKT;
    }

    public String getDsVattu() {
        return dsVattu;
    }

    public void setDsVattu(String dsVattu) {
        this.dsVattu = dsVattu;
    }
}
