package vn.vnptit.sapi.oneapp.models.qlts

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import javax.validation.constraints.Pattern

@JsonIgnoreProperties(["metaClass"])
class ASSET_SUMMARY_STATION {
    @JsonProperty("ma_dvi")
    @Pattern(regexp = "^(\\[').*?('])\$", message = "Chưa đúng định dạng")
    public String ma_dvi
    @JsonProperty("den_ngay")
    @Pattern(regexp = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])\$", message = "Chưa đúng định dạng yyyy-mm-dd")
    public String den_ngay
    @JsonProperty("ma_csht")
    @Pattern(regexp = "^(\\[').*?('])\$", message = "Chưa đúng định dạng")
    public String ma_csht
    @JsonProperty("loai_ts")
    @Pattern(regexp = "^(\\[').*?('])\$", message = "Chưa đúng định dạng")
    public String loai_ts
    @JsonProperty("trang_thai")
    @Pattern(regexp = "^(\\[').*?('])\$", message = "Chưa đúng định dạng")
    public String trang_thai
    @JsonProperty("page_index")
    public Long page_index
    @JsonProperty("page_size")
    public Long page_size
}