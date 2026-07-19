package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Giao phiếu báo hỏng
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class ThueBaoKH extends BaseDTO {
    @JsonProperty("maTb")
    private String maTb = null;
    @JsonProperty("thueBaoId")
    private Long thueBaoId = null;
    @JsonProperty("thanhToanId")
    private Long thanhToanId = null;
    @JsonProperty("khachHangId")
    private Long khachHangId = null;

    public String getMaTb() {
        return maTb;
    }

    public void setMaTb(String maTb) {
        this.maTb = maTb;
    }

    public Long getThueBaoId() {
        return thueBaoId;
    }

    public void setThueBaoId(Long thueBaoId) {
        this.thueBaoId = thueBaoId;
    }

    public Long getThanhToanId() {
        return thanhToanId;
    }

    public void setThanhToanId(Long thanhToanId) {
        this.thanhToanId = thanhToanId;
    }

    public Long getKhachHangId() {
        return khachHangId;
    }

    public void setKhachHangId(Long khachHangId) {
        this.khachHangId = khachHangId;
    }
}
