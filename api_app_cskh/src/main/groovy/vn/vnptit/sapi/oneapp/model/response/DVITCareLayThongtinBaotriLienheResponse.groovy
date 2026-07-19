package io.swagger.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DVITCareLayThongtinBaotriLienheResponse {


    public List getZalo() {
        return zalo;
    }

    public void setZalo(List zalo) {
        this.zalo = zalo;
    }

    public List getEmail() {
        return email;
    }

    public void setEmail(List email) {
        this.email = email;
    }

    @JsonProperty("zalo")
    protected List zalo;

    @JsonProperty("email")
    protected List email;
}
