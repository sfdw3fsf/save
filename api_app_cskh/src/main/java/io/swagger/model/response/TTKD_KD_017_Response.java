
package io.swagger.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class TTKD_KD_017_Response {

    @SerializedName("datas")
    @Expose
    private List<TTKD_KD_017_GW> datas;

    public List<TTKD_KD_017_GW> getDatas() {
        return datas;
    }

    public void setDatas(List<TTKD_KD_017_GW> datas) {
        this.datas = datas;
    }
}
