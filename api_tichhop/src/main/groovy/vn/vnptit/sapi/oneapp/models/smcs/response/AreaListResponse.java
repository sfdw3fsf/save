
package vn.vnptit.sapi.oneapp.models.smcs.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import vn.vnptit.sapi.oneapp.models.smcs.model.Agent;
import vn.vnptit.sapi.oneapp.models.smcs.model.Area;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class AreaListResponse extends SmcsBaseResponse {

    @SerializedName("result")
    @Expose
    private List<Area> result;

    public List<Area> getResult() {
        return result;
    }

    public void setResult(List<Area> result) {
        this.result = result;
    }
}
