package vn.vnpt.vnptit.ecms.dto;

public class TracuuMDFPhienDocDtoIn {
    private Long phanvung_id;
    private int vkieu;
    private String vthuebao_id;
    private String vtongdai;

    public void setPhanvung_id(Long phanvung_id) {
        this.phanvung_id = phanvung_id;
    }

    public void setVkieu(int vkieu) {
        this.vkieu = vkieu;
    }

    public void setVthuebao_id(String vthuebao_id) {
        this.vthuebao_id = vthuebao_id;
    }

    public void setVtongdai(String vtongdai) {
        this.vtongdai = vtongdai;
    }

    public void setVkyhieupd(String vkyhieupd) {
        this.vkyhieupd = vkyhieupd;
    }

    public void setVjumper(String vjumper) {
        this.vjumper = vjumper;
    }

    private String vkyhieupd;

    public Long getPhanvung_id() {
        return phanvung_id;
    }

    public int getVkieu() {
        return vkieu;
    }

    public String getVthuebao_id() {
        return vthuebao_id;
    }

    public String getVtongdai() {
        return vtongdai;
    }

    public String getVkyhieupd() {
        return vkyhieupd;
    }

    public String getVjumper() {
        return vjumper;
    }

    private String vjumper;
}
