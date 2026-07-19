package vn.vnpt.vnptit.ecms.dto.hatang;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class DMLienQuanVlanDtoIn implements Serializable {
    @NotNull(message = ModelValidation.NOT_NULL)
    private String danhmuc;

    @NotNull(message = ModelValidation.NOT_NULL)
    private Long id;

    public String getDanhmuc() {
        return danhmuc;
    }

    public void setDanhmuc(String danhmuc) {
        this.danhmuc = danhmuc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
