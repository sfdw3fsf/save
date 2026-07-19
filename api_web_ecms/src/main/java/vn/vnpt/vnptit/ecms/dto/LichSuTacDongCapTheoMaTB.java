package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LichSuTacDongCapTheoMaTB {
    @JsonProperty("vma_tb")
    private String vma_tb;

    @JsonProperty("vtungay")
    private String vtungay;

    @JsonProperty("vdenngay")
    private String vdenngay;

    public String getVma_tb() {
        return vma_tb;
    }

    public void setVma_tb(String vma_tb) {
        this.vma_tb = vma_tb;
    }

    public String getVtungay() {
        return vtungay;
    }

    public void setVtungay(String vtungay) {
        this.vtungay = vtungay;
    }

    public String getVdenngay() {
        return vdenngay;
    }

    public void setVdenngay(String vdenngay) {
        this.vdenngay = vdenngay;
    }
}
