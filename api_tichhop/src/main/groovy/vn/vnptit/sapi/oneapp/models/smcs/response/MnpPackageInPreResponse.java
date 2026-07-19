
package vn.vnptit.sapi.oneapp.models.smcs.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import vn.vnptit.sapi.oneapp.models.smcs.model.MnpPackageIn;
import vn.vnptit.sapi.oneapp.models.smcs.model.MnpPackageInPre;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class MnpPackageInPreResponse extends SmcsBaseResponse {

    @SerializedName("result")
    @Expose
    private List<MnpPackageInPre> result;

    public List<MnpPackageInPre> getResult() {
        return result;
    }

    public void setResult(List<MnpPackageInPre> result) {
        this.result = result;
    }
}
