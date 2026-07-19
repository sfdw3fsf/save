
package vn.vnptit.sapi.oneapp.models.smcs.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import vn.vnptit.sapi.oneapp.models.smcs.model.InfoPi;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class PackagePiRespone extends SmcsBaseResponse {

    @SerializedName("result")
    @Expose
    private String result;

    @SerializedName("price")
    @Expose
    private String price;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
