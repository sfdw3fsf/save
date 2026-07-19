
package vn.vnptit.sapi.oneapp.models.smcs.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import vn.vnptit.sapi.oneapp.models.smcs.model.ThueBaoMnp;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class ThueBaoMnpListResponse extends SmcsBaseResponse {

    @SerializedName("result")
    @Expose
    private List<ThueBaoMnp> result;

    public List<ThueBaoMnp> getResult() {
        return result;
    }

    public void setResult(List<ThueBaoMnp> result) {
        this.result = result;
    }
}
