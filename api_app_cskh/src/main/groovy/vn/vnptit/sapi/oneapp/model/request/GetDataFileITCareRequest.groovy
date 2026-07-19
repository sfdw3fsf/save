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
public class GetDataFileITCareRequest {
    @JsonProperty("vkieu_id")
    private Integer vkieu_id;

    @JsonProperty("vloai_id")
    private Integer vloai_id;

    @JsonProperty("vgr_id")
    private Integer vgr_id;

    @JsonProperty("vbieumau_id")
    private Integer vbieumau_id;

}
