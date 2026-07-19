
package vn.vnptit.sapi.oneapp.models.smcs.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import vn.vnptit.sapi.oneapp.models.smcs.model.DiemBan;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class DiemBanListResponse extends SmcsBaseResponse {

    @SerializedName("result")
    @Expose
    private List<DiemBan> result;

    public List<DiemBan> getResult() {
        return result;
    }

    public void setResult(List<DiemBan> result) {
        this.result = result;
    }
}
