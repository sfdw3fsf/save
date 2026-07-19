
package vn.vnptit.sapi.oneapp.models.smcs.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class XNTuChoiMnpResponse extends SmcsBaseResponse {

    @SerializedName("result")
    @Expose
    private Object result;
    @SerializedName("reg_pack_result")
    @Expose
    private Object regPackResult;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getRegPackResult() {
        return regPackResult;
    }

    public void setRegPackResult(Object regPackResult) {
        this.regPackResult = regPackResult;
    }
}
