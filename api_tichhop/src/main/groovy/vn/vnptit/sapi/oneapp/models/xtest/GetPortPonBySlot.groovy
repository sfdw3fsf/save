package vn.vnptit.sapi.oneapp.models.xtest
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties([ "metaClass" ])
public class GetPortPonBySlot {

    @JsonProperty("IpAddress")
    public String IpAddress
    @JsonProperty("FrameNo")
    public int FrameNo
    @JsonProperty("SlotNo")
    public int SlotNo
}