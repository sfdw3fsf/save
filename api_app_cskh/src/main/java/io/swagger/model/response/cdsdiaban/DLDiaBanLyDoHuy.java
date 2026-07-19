
package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class DLDiaBanLyDoHuy {

    @SerializedName("dichVu")
    @Expose
    private String dichVu;
    @SerializedName("lyDo")
    @Expose
    private String lyDo;
    @SerializedName("soLan")
    @Expose
    private String soLan;

    public String getDichVu() {
        return dichVu;
    }

    public void setDichVu(String dichVu) {
        this.dichVu = dichVu;
    }

    public String getLyDo() {
        return lyDo;
    }

    public void setLyDo(String lyDo) {
        this.lyDo = lyDo;
    }

    public String getSoLan() {
        return soLan;
    }

    public void setSoLan(String soLan) {
        this.soLan = soLan;
    }
}
