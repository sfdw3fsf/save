package vn.vnpt.vnptit.qlsc.dto.TienTrinhSuCo;
public class sendSMSDtoIn {
    private String  msisdn;
    private String  content;
    private String  mt_type;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMt_type() {
        return mt_type;
    }

    public void setMt_type(String mt_type) {
        this.mt_type = mt_type;
    }
}
