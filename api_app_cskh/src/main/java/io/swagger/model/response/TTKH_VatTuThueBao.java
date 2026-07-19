package io.swagger.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.dto.*;

import java.util.List;

/**
 * TTKH_VatTuThueBao
 */
public class TTKH_VatTuThueBao {
    @JsonProperty("dsVatTu")
    protected List<TTKH_DsVatTu> dsVatTu;
    @JsonProperty("dsThueBao")
    protected List<TTKH_DsThueBao> dsThueBao;

    public List<TTKH_DsVatTu> getDsVatTu() {
        return dsVatTu;
    }

    public void setDsVatTu(List<TTKH_DsVatTu> dsVatTu) {
        this.dsVatTu = dsVatTu;
    }

    public List<TTKH_DsThueBao> getDsThueBao() {
        return dsThueBao;
    }

    public void setDsThueBao(List<TTKH_DsThueBao> dsThueBao) {
        this.dsThueBao = dsThueBao;
    }
}
