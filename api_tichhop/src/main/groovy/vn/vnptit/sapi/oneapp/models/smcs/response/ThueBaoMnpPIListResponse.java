
package vn.vnptit.sapi.oneapp.models.smcs.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import vn.vnptit.sapi.oneapp.models.smcs.model.ThueBaoMnp;
import vn.vnptit.sapi.oneapp.models.smcs.model.ThueBaoMnpPI;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class ThueBaoMnpPIListResponse extends SmcsBaseResponse {

    @SerializedName("result")
    @Expose
    private List<ThueBaoMnpPI> result;

    public List<ThueBaoMnpPI> getResult() {
        return result;
    }

    public void setResult(List<ThueBaoMnpPI> result) {
        this.result = result;
    }
}
