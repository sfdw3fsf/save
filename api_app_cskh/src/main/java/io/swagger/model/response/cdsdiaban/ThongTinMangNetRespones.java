package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.SerializedName;
import io.swagger.model.dto.ThongTinMangNet;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class ThongTinMangNetRespones {

    @SerializedName("vnpt")
    private List<ThongTinMangNet> vnpt = null;

    @SerializedName("orther")
    private List<ThongTinMangNet> orther = null;

    public List<ThongTinMangNet> getVnpt() {
        return vnpt;
    }

    public void setVnpt(List<ThongTinMangNet> vnpt) {
        this.vnpt = vnpt;
    }

    public List<ThongTinMangNet> getOrther() {
        return orther;
    }

    public void setOrther(List<ThongTinMangNet> orther) {
        this.orther = orther;
    }
}
