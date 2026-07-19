
package vn.vnptit.sapi.oneapp.models.smcs.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import vn.vnptit.sapi.oneapp.models.smcs.model.ChangeGoods;
import vn.vnptit.sapi.oneapp.models.smcs.model.ChangeGoodsDetail;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class DetailChangeStockGoodsResponse extends SmcsBaseResponse {

    @SerializedName("result")
    @Expose
    private ChangeGoodsDetail result;

    public ChangeGoodsDetail getResult() {
        return result;
    }

    public void setResult(ChangeGoodsDetail result) {
        this.result = result;
    }
}
