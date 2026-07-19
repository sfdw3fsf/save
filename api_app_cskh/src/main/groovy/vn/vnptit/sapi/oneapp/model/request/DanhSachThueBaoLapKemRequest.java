package vn.vnptit.sapi.oneapp.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * 4) Lấy danh sách thuê bao lắp kèm
 */
public class DanhSachThueBaoLapKemRequest {
    @JsonProperty("vma_tb")
    private String vma_tb;

    @JsonProperty("vdichvuvt_id")
    private Integer vdichvuvt_id;

    @JsonProperty("vkieu")
    private Integer vkieu;

    public String getVma_tb() {
        return vma_tb;
    }

    public void setVma_tb(String vma_tb) {
        this.vma_tb = vma_tb;
    }

    public Integer getVdichvuvt_id() {
        return vdichvuvt_id;
    }

    public void setVdichvuvt_id(Integer vdichvuvt_id) {
        this.vdichvuvt_id = vdichvuvt_id;
    }

    public Integer getVkieu() {
        return vkieu;
    }

    public void setVkieu(Integer vkieu) {
        this.vkieu = vkieu;
    }
}
