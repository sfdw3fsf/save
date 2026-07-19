package io.swagger.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-23")
@Schema
public class SaveThongTinCauHoiCauTLRequest {
    private Long p_cauhoi_id;
    private Long p_loaich_id;

    private Long p_nhomch_id;

    private String p_cauhoi;

    private String p_traloi;

    public Long getP_cauhoi_id() {
        return p_cauhoi_id;
    }

    public void setP_cauhoi_id(Long p_cauhoi_id) {
        this.p_cauhoi_id = p_cauhoi_id;
    }

    public Long getP_loaich_id() {
        return p_loaich_id;
    }

    public void setP_loaich_id(Long p_loaich_id) {
        this.p_loaich_id = p_loaich_id;
    }

    public Long getP_nhomch_id() {
        return p_nhomch_id;
    }

    public void setP_nhomch_id(Long p_nhomch_id) {
        this.p_nhomch_id = p_nhomch_id;
    }

    public String getP_cauhoi() {
        return p_cauhoi;
    }

    public void setP_cauhoi(String p_cauhoi) {
        this.p_cauhoi = p_cauhoi;
    }

    public String getP_traloi() {
        return p_traloi;
    }

    public void setP_traloi(String p_traloi) {
        this.p_traloi = p_traloi;
    }
}
