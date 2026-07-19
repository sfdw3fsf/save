package io.swagger.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DVITCareLayThongtinBaotriTonghopResponse {

    public List getD_hd() {
        return d_hd;
    }

    public void setD_hd(List d_hd) {
        this.d_hd = d_hd;
    }

    public List getDs_dt() {
        return ds_dt;
    }

    public void setDs_dt(List ds_dt) {
        this.ds_dt = ds_dt;
    }

    @JsonProperty("d_hd")
    protected List d_hd;

    @JsonProperty("ds_dt")
    protected List ds_dt;
}
