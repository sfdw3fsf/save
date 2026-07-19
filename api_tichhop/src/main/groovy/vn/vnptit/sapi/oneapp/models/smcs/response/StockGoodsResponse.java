
package vn.vnptit.sapi.oneapp.models.smcs.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import vn.vnptit.sapi.oneapp.models.smcs.model.Agent;
import vn.vnptit.sapi.oneapp.models.smcs.model.StockGoods;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class StockGoodsResponse extends SmcsBaseResponse {

    @SerializedName("result")
    @Expose
    private List<StockGoods> result;

    public List<StockGoods> getResult() {
        return result;
    }

    public void setResult(List<StockGoods> result) {
        this.result = result;
    }
}
