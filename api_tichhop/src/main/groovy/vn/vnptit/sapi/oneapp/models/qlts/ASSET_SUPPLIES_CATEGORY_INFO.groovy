package vn.vnptit.sapi.oneapp.models.qlts

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import javax.validation.constraints.Pattern

@JsonIgnoreProperties(["metaClass"])
class ASSET_SUPPLIES_CATEGORY_INFO {
    @JsonProperty("code")
    public String code
    @JsonProperty("apply_date")
    @Pattern(regexp = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])\$", message = "Chưa đúng định dạng yyyy-mm-dd")
    public String apply_date
    @JsonProperty("apply_status")
    public String apply_status
    @JsonProperty("page_index")
    public Long page_index
    @JsonProperty("page_size")
    public Long page_size
    @JsonProperty("write_date")
    @Pattern(regexp = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])\$", message = "Chưa đúng định dạng yyyy-mm-dd")
    public String write_date
}
