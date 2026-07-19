package vn.vnptit.sapi.oneapp.model.request

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.validation.annotation.Validated

import javax.annotation.Generated

/**
 *  Dịch vụ IT Care
 *  Danh sách phiếu yêu cầu khảo sát
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class HuyGanHoSoITCareRequest {
    @JsonProperty("vkieu")
    private Integer vkieu;

    @JsonProperty("vdb_id")
    private Integer vdb_id;

    @JsonProperty("vhoso_id")
    private Integer vhoso_id;

    @JsonProperty("vfile_id")
    private String vfile_id;
}
