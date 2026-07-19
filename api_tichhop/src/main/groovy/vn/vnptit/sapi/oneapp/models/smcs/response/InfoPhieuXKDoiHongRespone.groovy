package vn.vnptit.sapi.oneapp.models.smcs.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import vn.vnptit.sapi.oneapp.models.smcs.model.InfoBbbgDoiHong
import vn.vnptit.sapi.oneapp.models.smcs.model.InfoPhieuXKDoiHong

import javax.annotation.Generated

@Generated("jsonschema2pojo")
class InfoPhieuXKDoiHongRespone extends SmcsBaseResponse {

    @SerializedName("result")
    @Expose
    private InfoPhieuXKDoiHong result;

    public InfoPhieuXKDoiHong getResult() {
        return result;
    }

    public void setResult(InfoPhieuXKDoiHong result) {
        this.result = result;
    }

}
