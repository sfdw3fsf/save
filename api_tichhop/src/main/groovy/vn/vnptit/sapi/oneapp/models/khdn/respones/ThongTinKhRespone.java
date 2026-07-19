package vn.vnptit.sapi.oneapp.models.khdn.respones;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import vn.vnptit.sapi.oneapp.models.khdn.models.KhdnBaseResponse;
import vn.vnptit.sapi.oneapp.models.khdn.models.ThongTinKhachHang;
import vn.vnptit.sapi.oneapp.models.smcs.model.Agent;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class ThongTinKhRespone extends KhdnBaseResponse {

    @SerializedName("result")
    @Expose
    private List<ThongTinKhachHang> result;

    public List<ThongTinKhachHang> getResult() {
        return result;
    }

    public void setResult(List<ThongTinKhachHang> result) {
        this.result = result;
    }
}
