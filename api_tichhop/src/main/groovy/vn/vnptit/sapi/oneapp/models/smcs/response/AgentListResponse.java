
package vn.vnptit.sapi.oneapp.models.smcs.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import vn.vnptit.sapi.oneapp.models.smcs.model.Agent;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class AgentListResponse extends SmcsBaseResponse {

    @SerializedName("result")
    @Expose
    private List<Agent> result;

    public List<Agent> getResult() {
        return result;
    }

    public void setResult(List<Agent> result) {
        this.result = result;
    }
}
