package vn.vnptit.sapi.oneapp.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * 5) Lấy danh sách lịch sử hỏng
 */
public class DanhSachLichSuHongRequest {
    @JsonProperty("vma_tb")
    private String vma_tb;

    @JsonProperty("vdichvuvt_id")
    private String vdichvuvt_id;

    public String getVma_tb() {
        return vma_tb;
    }

    public void setVma_tb(String vma_tb) {
        this.vma_tb = vma_tb;
    }

    public String getVdichvuvt_id() {
        return vdichvuvt_id;
    }

    public void setVdichvuvt_id(String vdichvuvt_id) {
        this.vdichvuvt_id = vdichvuvt_id;
    }
}
