package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.dto.CNThueBaoChenNo;
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
public class CNChenNoRequest {

    @JsonProperty("chuKy")
    private String chuKy;
    @JsonProperty("kyHD")
    private String kyHD;
    @JsonProperty("kyKN")
    private String kyKN;
    @JsonProperty("dsThueBao")
    private List<CNThueBaoChenNo> dsThueBao;

    public String getChuKy() {
        return chuKy;
    }

    public void setChuKy(String chuKy) {
        this.chuKy = chuKy;
    }

    public String getKyKN() {
        return kyKN;
    }

    public void setKyKN(String kyKN) {
        this.kyKN = kyKN;
    }

    public String getKyHD() {
        return kyHD;
    }

    public void setKyHD(String kyHD) {
        this.kyHD = kyHD;
    }

    public List<CNThueBaoChenNo> getDsThueBao() {
        return dsThueBao;
    }

    public void setDsThueBao(List<CNThueBaoChenNo> dsThueBao) {
        this.dsThueBao = dsThueBao;
    }
}
