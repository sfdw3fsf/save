package vn.vnptit.sapi.oneapp.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 2. Lấy danh sách thuê bao ảnh hưởng của 1 sự cố
 */
public class DanhSachThueBaoBiAnhHuongRequest {
    @JsonProperty("vsuco_id")
    private Integer vsuco_id;

    public Integer getVsuco_id() {
        return vsuco_id;
    }

    public void setVsuco_id(Integer vsuco_id) {
        this.vsuco_id = vsuco_id;
    }
}
