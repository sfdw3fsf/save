package vn.vnpt.vnptit.ecms.dto.idc;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO request để lấy thuộc tính")
public class GetThuocTinhRequestDto {
    
    @JsonProperty("kieuTT")
    private String kieuTT;
    
    @JsonProperty("duLieuId")
    private Long duLieuId;
    
    public GetThuocTinhRequestDto() {
    }
    
    public GetThuocTinhRequestDto(String kieuTT, Long duLieuId) {
        this.kieuTT = kieuTT;
        this.duLieuId = duLieuId;
    }
    
    public String getKieuTT() {
        return kieuTT;
    }
    
    public void setKieuTT(String kieuTT) {
        this.kieuTT = kieuTT;
    }
    
    public Long getDuLieuId() {
        return duLieuId;
    }
    
    public void setDuLieuId(Long duLieuId) {
        this.duLieuId = duLieuId;
    }
}

