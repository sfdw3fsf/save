package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;

public class XulyPhieuYeuCauTTCRequestDto implements Serializable {
    private String ma_phieu;
    private Long xuly;

    public String getMa_phieu() {
        return ma_phieu;
    }

    public void setMa_phieu(String ma_phieu) {
        this.ma_phieu = ma_phieu;
    }

    public Long getXuly() {
        return xuly;
    }

    public void setXuly(Long xuly) {
        this.xuly = xuly;
    }
}
