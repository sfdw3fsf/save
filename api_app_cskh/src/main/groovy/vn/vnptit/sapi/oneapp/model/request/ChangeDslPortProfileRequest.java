package vn.vnptit.sapi.oneapp.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * ChangeDslPortProfileRequest
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class ChangeDslPortProfileRequest {
    @JsonProperty("slot")
    private Integer slot;
    @JsonProperty("frame")
    private Integer frame;
    @JsonProperty("port")
    private Integer port;
    @JsonProperty("deviceIp")
    private String deviceIp;
    @JsonProperty("profileName")
    private String profileName;

    public ChangeDslPortProfileRequest(Integer slot, Integer frame, Integer port, String deviceIp, String profileName) {
        this.slot = slot;
        this.frame = frame;
        this.port = port;
        this.deviceIp = deviceIp;
        this.profileName = profileName;
    }

    public ChangeDslPortProfileRequest() {
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public Integer getFrame() {
        return frame;
    }

    public void setFrame(Integer frame) {
        this.frame = frame;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }
}
