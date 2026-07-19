package vn.vnptit.sapi.oneapp.models.visacom

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@JsonIgnoreProperties(["metaClass"])
class ChangeMacAddressReg {
    @NotNull
    @NotBlank
    @JsonProperty("accountName")
    public String accountName
    @NotNull
    @NotBlank
    @JsonProperty("macAddress")
    public String macAddress
    @JsonProperty("note")
    public String note
}