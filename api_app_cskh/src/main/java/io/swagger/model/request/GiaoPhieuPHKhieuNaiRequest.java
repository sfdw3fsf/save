package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.dto.PhieuPhoiHop;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.util.List;

/**
 * Báo tồn
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class GiaoPhieuPHKhieuNaiRequest {

    @JsonProperty("dsPhieu")
    private List<PhieuPhoiHop> dsPhieu;

    public List<PhieuPhoiHop> getDsPhieu() {
        return dsPhieu;
    }

    public void setDsPhieu(List<PhieuPhoiHop> dsPhieu) {
        this.dsPhieu = dsPhieu;
    }
}
