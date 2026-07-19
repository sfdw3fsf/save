package vn.vnpt.vnptit.ecms.dto.hatang;

public class DongBoInputDTO {
    private Long id;
    private String code;
    private String region_id;   // dùng cho đồng bộ cụm hạ tầng IDG

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }
}
