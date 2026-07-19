
package vn.vnptit.sapi.oneapp.models.smcs.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import vn.vnptit.sapi.oneapp.models.smcs.model.ChangeGoods;
import vn.vnptit.sapi.oneapp.models.smcs.model.StockGoods;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class ChangeStockGoodsResponse extends SmcsBaseResponse {

    @SerializedName("result")
    @Expose
    private List<ChangeGoods> result;

    public List<ChangeGoods> getResult() {
        return result;
    }

    public void setResult(List<ChangeGoods> result) {
        this.result = result;
    }
}
