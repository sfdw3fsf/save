
package vn.vnptit.sapi.oneapp.models.smcs.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import vn.vnptit.sapi.oneapp.models.smcs.model.HangKenh;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class HangKenhResponse extends SmcsBaseResponse {

    @SerializedName("result")
    @Expose
    private List<HangKenh> result;

    public List<HangKenh> getResult() {
        return result;
    }

    public void setResult(List<HangKenh> result) {
        this.result = result;
    }
}
