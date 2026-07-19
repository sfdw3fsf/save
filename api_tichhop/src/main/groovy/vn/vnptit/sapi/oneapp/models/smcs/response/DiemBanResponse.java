
package vn.vnptit.sapi.oneapp.models.smcs.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import vn.vnptit.sapi.oneapp.models.smcs.model.DiemBanDetail;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class DiemBanResponse extends SmcsBaseResponse {

    @SerializedName("result")
    @Expose
    private DiemBanDetail result;

    public DiemBanDetail getResult() {
        return result;
    }

    public void setResult(DiemBanDetail result) {
        this.result = result;
    }
}
