package vn.vnptit.sapi.oneapp.models.qlts

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

@JsonIgnoreProperties(["metaClass"])
class FORMING_NEW_ASSET {
    @JsonProperty("ma_du_an")
    @Pattern(regexp = "^(\\[').*?('])\$", message = "Chưa đúng định dạng")
    @NotNull
    public String ma_du_an
    @JsonProperty("from_date")
    @Pattern(regexp = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])\$", message = "Chưa đúng định dạng yyyy-mm-dd")
    public String from_date
}