
package vn.vnptit.sapi.oneapp.models.smcs.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import vn.vnptit.sapi.oneapp.models.smcs.model.GoiCuocMnp;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class GoiCuocMnpListResponse extends SmcsBaseResponse {

    @SerializedName("result")
    @Expose
    private List<GoiCuocMnp> result;

    public List<GoiCuocMnp> getResult() {
        return result;
    }

    public void setResult(List<GoiCuocMnp> result) {
        this.result = result;
    }
}
