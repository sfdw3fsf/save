
package vn.vnptit.sapi.oneapp.models.smcs.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import vn.vnptit.sapi.oneapp.models.smcs.model.Product;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class CheckPermissionResponse extends SmcsBaseResponse {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("xml")
    @Expose
    private Object xml;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getXml() {
        return xml;
    }

    public void setXml(Object xml) {
        this.xml = xml;
    }
}
