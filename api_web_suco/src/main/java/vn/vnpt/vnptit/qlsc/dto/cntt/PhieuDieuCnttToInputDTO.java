package vn.vnpt.vnptit.qlsc.dto.cntt;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonProperty;

import vn.vnpt.message.ModelValidation;

public class PhieuDieuCnttToInputDTO {
    @JsonProperty("nhomsc_id")
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer nhomsc_id;
    public Integer getnhomsc_id() {
        return nhomsc_id;
    }


    @JsonProperty("ma_sc")
    private String ma_sc;
    public String getma_sc() {
        return ma_sc;
    }


    @JsonProperty("nguoibao_id")
    private Integer nguoibao_id;
    public Integer getnguoibao_id() {
        return nguoibao_id;
    }


    @JsonProperty("mucdokc_id")
    private Integer mucdokc_id;
    public Integer getmucdokc_id() {
        return mucdokc_id;
    }

    @JsonProperty("nguonsc_id")
    private Integer nguonsc_id;
    public Integer getnguonsc_id() {
        return nguonsc_id;
    }

    @JsonProperty("tinhtrang")
    private Integer tinhtrang;
    public Integer gettinhtrang() {
        return tinhtrang;
    }

    @JsonProperty("noidung_sc")
    private String noidung_sc;
    public String getnoidung_sc() {
        return noidung_sc;
    }

    @JsonProperty("noidung_giao")
    private String noidung_giao;
    public String getnoidung_giao() {
        return noidung_giao;
    }

    @JsonProperty("ngaysc_tu")
    private String ngaysc_tu;
    public String getngaysc_tu() {
        return ngaysc_tu;
    }


    @JsonProperty("ngaysc_den")
    private String ngaysc_den;
    public String getngaysc_den() {
        return ngaysc_den;
    }


    @JsonProperty("ngaybao_tu")
    private String ngaybao_tu;
    public String getngaybao_tu() {
        return ngaybao_tu;
    }

    @JsonProperty("ngaybao_den")
    private String ngaybao_den;
    public String getngaybao_den() {
        return ngaybao_den;
    }


    @JsonProperty("ngaygiao_tu")
    private String ngaygiao_tu;
    public String getngaygiao_tu() {
        return ngaygiao_tu;
    }


    @JsonProperty("ngaygiao_den")
    private String ngaygiao_den;
    public String getngaygiao_den() {
        return ngaygiao_den;
    }


    @JsonProperty("mucdosc_id")
    private Integer mucdosc_id;
    public Integer getmucdosc_id() {
        return mucdosc_id;
    }

    @JsonProperty("keodai_tu_op")
    private Integer keodai_tu_op;
    public Integer getkeodai_tu_op() {
        return keodai_tu_op;
    }


    @JsonProperty("keodai_tu")
    private Long keodai_tu;
    public Long getkeodai_tu() {
        return keodai_tu;
    }

    @JsonProperty("keodai_den_op")
    private Integer keodai_den_op;
    public Integer getkeodai_den_op() {
        return keodai_den_op;
    }

    @JsonProperty("keodai_den")
    private Long keodai_den;
    public Long getkeodai_den() {
        return keodai_den;
    }
}
