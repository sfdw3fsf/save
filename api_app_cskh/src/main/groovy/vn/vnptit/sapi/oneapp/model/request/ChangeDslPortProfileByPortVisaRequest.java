package vn.vnptit.sapi.oneapp.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * ChangeDslPortProfileByPortVisaRequest
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class ChangeDslPortProfileByPortVisaRequest {
    @JsonProperty("visaString")
    private String visaString;
    @JsonProperty("profileName")
    private String profileName;

    public ChangeDslPortProfileByPortVisaRequest(String visaString, String profileName) {
        this.visaString = visaString;
        this.profileName = profileName;
    }

    public ChangeDslPortProfileByPortVisaRequest() {
    }

    public String getVisaString() {
        return visaString;
    }

    public void setVisaString(String visaString) {
        this.visaString = visaString;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }
}
