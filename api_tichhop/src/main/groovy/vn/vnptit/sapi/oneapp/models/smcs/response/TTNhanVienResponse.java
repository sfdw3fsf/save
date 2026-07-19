
package vn.vnptit.sapi.oneapp.models.smcs.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import vn.vnptit.sapi.oneapp.models.smcs.model.Staff;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class TTNhanVienResponse extends SmcsBaseResponse {

    @SerializedName("result")
    @Expose
    private Staff result;

    public Staff getResult() {
        return result;
    }

    public void setResult(Staff result) {
        this.result = result;
    }
}
