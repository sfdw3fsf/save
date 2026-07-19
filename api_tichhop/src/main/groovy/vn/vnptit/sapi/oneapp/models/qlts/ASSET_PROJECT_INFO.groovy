package vn.vnptit.sapi.oneapp.models.qlts

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import javax.validation.constraints.NotNull

@JsonIgnoreProperties(["metaClass"])
class ASSET_PROJECT_INFO {
    @JsonProperty("company_ktbd_code")
    @NotNull
    public String company_ktbd_code
    @JsonProperty("project_code")
    @NotNull
    public String project_code
    @JsonProperty("so_the")
    public String so_the
}
