
package vn.vnptit.sapi.oneapp.models.smcs.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import vn.vnptit.sapi.oneapp.models.smcs.model.MnpPackageIn;
import vn.vnptit.sapi.oneapp.models.smcs.model.TrangThai;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class MnpPackageInResponse extends SmcsBaseResponse {

    @SerializedName("result")
    @Expose
    private List<MnpPackageIn> result;

    public List<MnpPackageIn> getResult() {
        return result;
    }

    public void setResult(List<MnpPackageIn> result) {
        this.result = result;
    }
}
