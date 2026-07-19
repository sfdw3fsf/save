package vn.vnptit.sapi.oneapp.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical
import groovy.transform.ToString

/**
 * Mục đích email
 */
@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class LayThongTinBaoTriResponse {
    public List getDs_hd() {
        return ds_hd;
    }

    public void setDs_hd(List ds_hd) {
        this.ds_hd = ds_hd;
    }

    public List getDs_dt() {
        return ds_dt;
    }

    public void setDs_dt(List ds_dt) {
        this.ds_dt = ds_dt;
    }

    @JsonProperty("ds_hd")
    protected List ds_hd;

    @JsonProperty("ds_dt")
    protected List ds_dt;
}
