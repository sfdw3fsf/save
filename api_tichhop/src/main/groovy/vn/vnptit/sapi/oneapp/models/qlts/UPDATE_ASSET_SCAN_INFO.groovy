package vn.vnptit.sapi.oneapp.models.qlts

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import javax.validation.constraints.NotNull

class UPDATE_ASSET_SCAN_INFO_REQ{
    String id_chi_tiet
    String ma_vt_hh_dv
    String ma_csht
    String qr_code_gtri
}

@JsonIgnoreProperties(["metaClass"])
class UPDATE_ASSET_SCAN_INFO {
    @JsonProperty("list")
    @NotNull
    public UPDATE_ASSET_SCAN_INFO_REQ[] list
}
