
package vn.vnptit.sapi.oneapp.models.smcs.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import vn.vnptit.sapi.oneapp.models.smcs.model.InfoPi;
import vn.vnptit.sapi.oneapp.models.smcs.model.ThueBaoMnpPI;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class MnpPiItemResponse extends SmcsBaseResponse {

    @SerializedName("result")
    @Expose
    private InfoPi result;

    public InfoPi getResult() {
        return result;
    }

    public void setResult(InfoPi result) {
        this.result = result;
    }
}
