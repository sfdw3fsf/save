
package vn.vnptit.sapi.oneapp.models.smcs.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import vn.vnptit.sapi.oneapp.models.smcs.model.Agent;
import vn.vnptit.sapi.oneapp.models.smcs.model.Order;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class OrderListResponse extends SmcsBaseResponse {

    @SerializedName("result")
    @Expose
    private List<Order> result;

    public List<Order> getResult() {
        return result;
    }

    public void setResult(List<Order> result) {
        this.result = result;
    }
}
