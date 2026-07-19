package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.util.Date;

@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-23")
@Schema
public class BaoCaoTyLeChonTraLoiRequest {
    private Long p_cauhoi_id;
    private Long p_donvi_id;
    private Long p_nhomch_id;
    private Long p_nhanvien_id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date p_tungay;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date p_denngay;

    public Long getP_nhanvien_id() {
        return p_nhanvien_id;
    }

    public void setP_nhanvien_id(Long p_nhanvien_id) {
        this.p_nhanvien_id = p_nhanvien_id;
    }

    public Long getP_cauhoi_id() {
        return p_cauhoi_id;
    }

    public void setP_cauhoi_id(Long p_cauhoi_id) {
        this.p_cauhoi_id = p_cauhoi_id;
    }

    public Long getP_donvi_id() {
        return p_donvi_id;
    }

    public void setP_donvi_id(Long p_donvi_id) {
        this.p_donvi_id = p_donvi_id;
    }

    public Long getP_nhomch_id() {
        return p_nhomch_id;
    }

    public void setP_nhomch_id(Long p_nhomch_id) {
        this.p_nhomch_id = p_nhomch_id;
    }

    public Date getP_tungay() {
        return p_tungay;
    }

    public void setP_tungay(Date p_tungay) {
        this.p_tungay = p_tungay;
    }

    public Date getP_denngay() {
        return p_denngay;
    }

    public void setP_denngay(Date p_denngay) {
        this.p_denngay = p_denngay;
    }
}
