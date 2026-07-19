
package vn.vnptit.sapi.oneapp.models.smcs.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import vn.vnptit.sapi.oneapp.models.smcs.model.Product;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class SearchSerialResponse extends SmcsBaseResponse {

    @SerializedName("result")
    @Expose
    private Product result;

    public Product getResult() {
        return result;
    }

    public void setResult(Product result) {
        this.result = result;
    }
}
