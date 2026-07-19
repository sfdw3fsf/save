package vn.vnptit.sapi.oneapp.model.request

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.validation.annotation.Validated

import javax.annotation.Generated

/**
 * Xây dựng API tra cứu thông tin thuê bao fiber trên tập myTVBeta - ONEAPP-18185
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class TraCuuTTTBFiberMyTVBetaRequest {
    @JsonProperty("ma_fiber")
    private String ma_fiber;

    public String getMa_fiber() {
        return ma_fiber;
    }

    public void setMa_fiber(String ma_fiber) {
        this.ma_fiber = ma_fiber;
    }
}

@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class TraCuuTTTBFiberMyTVBeta2Request {
    @JsonProperty("khachHangId")
    String khachHangId;

    @JsonProperty("longtitude")
    String longtitude;

    @JsonProperty("latitude")
    String latitude;
}
