package io.swagger.model.request;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.annotation.Generated;
import java.sql.Date;

/**
 * Trả phiếu
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class TraCuuDanhBaRequest {

    @JsonProperty("id")
    private Long id; // 2: khách hàng, 3: thanh toán, 4: thuê bao

    @JsonProperty("dieuKien")
    private String dieuKien;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDieuKien() {
        return dieuKien;
    }

    public void setDieuKien(String dieuKien) {
        this.dieuKien = dieuKien;
    }
}
