
package vn.vnptit.sapi.oneapp.models.smcs.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import vn.vnptit.sapi.oneapp.models.smcs.model.TrangThai;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class StatusListResponse extends SmcsBaseResponse {

    @SerializedName("result")
    @Expose
    private List<TrangThai> result;

    public List<TrangThai> getResult() {
        return result;
    }

    public void setResult(List<TrangThai> result) {
        this.result = result;
    }
}
