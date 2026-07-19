
package vn.vnptit.sapi.oneapp.models.smcs.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import vn.vnptit.sapi.oneapp.models.smcs.model.LoaiKenh;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class LoaiKenhResponse extends SmcsBaseResponse {

    @SerializedName("result")
    @Expose
    private List<LoaiKenh> result;

    public List<LoaiKenh> getResult() {
        return result;
    }

    public void setResult(List<LoaiKenh> result) {
        this.result = result;
    }
}
