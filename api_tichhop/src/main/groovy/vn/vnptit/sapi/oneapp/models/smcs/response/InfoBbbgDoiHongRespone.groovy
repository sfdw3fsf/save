package vn.vnptit.sapi.oneapp.models.smcs.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import vn.vnptit.sapi.oneapp.models.smcs.model.HangKenh
import vn.vnptit.sapi.oneapp.models.smcs.model.InfoBbbgDoiHong

import javax.annotation.Generated

@Generated("jsonschema2pojo")
class InfoBbbgDoiHongRespone extends SmcsBaseResponse {

    @SerializedName("result")
    @Expose
    private InfoBbbgDoiHong result = null;

    public InfoBbbgDoiHong getResult() {
        return result;
    }

    public void setResult(InfoBbbgDoiHong result) {
        this.result = result;
    }

}
