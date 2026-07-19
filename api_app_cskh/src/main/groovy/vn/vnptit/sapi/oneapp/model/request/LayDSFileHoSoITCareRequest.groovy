package vn.vnptit.sapi.oneapp.model.request

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.validation.annotation.Validated

import javax.annotation.Generated

/**
 * Bổ sung hàm Hoàn thiện hd TĐTT (API phối hợp sử dụng hàm và gọi cập nhật các webservice liên quan) - ONEAPP-18133
 * https://cntt.vnpt.vn/browse/ONEAPP-15759
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class LayDSFileHoSoITCareRequest {
    @JsonProperty("vkieu")
    private Integer vkieu;

    @JsonProperty("vdb_id")
    private Integer vdb_id;

}
