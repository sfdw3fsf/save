package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.dto.CNHoSoKN;
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
public class CNHoSoKhieuNaiRequest {

    @JsonProperty("dsFile")
    private List<CNHoSoKN> dsFile;

    public List<CNHoSoKN> getDsFile() {
        return dsFile;
    }

    public void setDsFile(List<CNHoSoKN> dsFile) {
        this.dsFile = dsFile;
    }
}
