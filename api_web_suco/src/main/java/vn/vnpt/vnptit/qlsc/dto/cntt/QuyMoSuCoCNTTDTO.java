package vn.vnpt.vnptit.qlsc.dto.cntt;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuyMoSuCoCNTTDTO {
    @JsonProperty("QUYMO_SUCO")
    private int QUYMO_SUCO;
    @JsonProperty("QUYMO")
    private String QUYMO;

    public QuyMoSuCoCNTTDTO(int QUYMO_SUCO, String QUYMO) {
        this.QUYMO_SUCO = QUYMO_SUCO;
        this.QUYMO = QUYMO;
    }

}
