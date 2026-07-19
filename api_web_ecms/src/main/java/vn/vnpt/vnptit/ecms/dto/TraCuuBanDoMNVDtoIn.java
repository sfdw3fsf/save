package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class TraCuuBanDoMNVDtoIn {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Double lat;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Double lng;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long bankinh;
    private Long phanloaikc_id;
    private Long laykccon;

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public Long getBankinh() {
        return bankinh;
    }

    public Long getPhanloaikc_id() {
        return phanloaikc_id;
    }

    public Long getLaykccon() {
        return laykccon;
    }
}
