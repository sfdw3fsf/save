package vn.vnptit.sapi.oneapp.models.smcs.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListHDChamSocUSSDResponse extends SmcsBaseResponse {

    @SerializedName("result")
    @Expose
    private List<HDChamSocUSSD> result;

    public List<HDChamSocUSSD> getResult() {
        return result;
    }

    public void setResult(List<HDChamSocUSSD> result) {
        this.result = result;
    }
}