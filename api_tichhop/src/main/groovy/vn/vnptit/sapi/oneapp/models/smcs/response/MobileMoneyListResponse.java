
package vn.vnptit.sapi.oneapp.models.smcs.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import vn.vnptit.sapi.oneapp.models.smcs.model.MobileMoney;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class MobileMoneyListResponse extends SmcsBaseResponse {

    @SerializedName("result")
    @Expose
    private List<MobileMoney> result;
    @SerializedName("xml")
    @Expose
    private Object xml;

    public List<MobileMoney> getResult() {
        return result;
    }

    public void setResult(List<MobileMoney> result) {
        this.result = result;
    }

    public Object getXml() {
        return xml;
    }

    public void setXml(Object xml) {
        this.xml = xml;
    }
}
