package vn.vnptit.sapi.oneapp.model.request

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.validation.annotation.Validated

import javax.annotation.Generated

/**
 * STB_Rent_UpdateInfo_Request
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
class STB_Rent_UpdateInfo_Request {
    StbDTO stbDTO
    STB_Rent_UpdateInfo_Request(String rent_STATUS, String username) {
        stbDTO = new StbDTO()
        stbDTO.rent_STATUS = rent_STATUS
        stbDTO.username = username
    }
}

class StbDTO {
    String rent_STATUS
    String username
}
