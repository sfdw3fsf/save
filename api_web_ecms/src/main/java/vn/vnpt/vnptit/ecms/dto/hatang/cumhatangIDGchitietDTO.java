package vn.vnpt.vnptit.ecms.dto.hatang;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class cumhatangIDGchitietDTO {

    @JsonProperty("P_ID")
    private Long id;

    @JsonProperty("P_CUMHT_ID")
    private Long cumhtId;

    // ✅ constructor phải TRÙNG tên class
    public cumhatangIDGchitietDTO() {
    }

    public cumhatangIDGchitietDTO(Long id) {
        this.id = id;
    }

    // ===== getter/setter =====
    public Long getId() {
        return id;
    }

    public Long getCumhtId() {
        return cumhtId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCumhtId(Long cumhtId) {
        this.cumhtId = cumhtId;
    }
}