package vn.vnptit.sapi.oneapp.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * StbFromActiveRequest
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class StbFromActiveRequest {
    @JsonProperty("isFirst")
    private Boolean isFirst;
    @JsonProperty("note")
    private String note;
    @JsonProperty("program")
    private String program;
    @JsonProperty("stbDate")
    private String stbDate;
    @JsonProperty("stbSerial")
    private String stbSerial;
    @JsonProperty("stbType")
    private String stbType;
    @JsonProperty("username")
    private String username;

    public StbFromActiveRequest(Boolean isFirst, String note, String program, String stbDate, String stbSerial, String stbType, String username) {
        this.isFirst = isFirst;
        this.note = note;
        this.program = program;
        this.stbDate = stbDate;
        this.stbSerial = stbSerial;
        this.stbType = stbType;
        this.username = username;
    }

    public StbFromActiveRequest() {
    }

    public Boolean getFirst() {
        return isFirst;
    }

    public void setFirst(Boolean first) {
        isFirst = first;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getStbDate() {
        return stbDate;
    }

    public void setStbDate(String stbDate) {
        this.stbDate = stbDate;
    }

    public String getStbSerial() {
        return stbSerial;
    }

    public void setStbSerial(String stbSerial) {
        this.stbSerial = stbSerial;
    }

    public String getStbType() {
        return stbType;
    }

    public void setStbType(String stbType) {
        this.stbType = stbType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
