package vn.vnptit.sapi.oneapp.models.vnpplus

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@JsonIgnoreProperties(["metaClass"])
class HangHoiVienReq {
    @NotNull
    @NotEmpty
    @NotBlank
    @Size(max = 10)
    @JsonProperty("SoDienThoai")
    String SoDienThoai
}
