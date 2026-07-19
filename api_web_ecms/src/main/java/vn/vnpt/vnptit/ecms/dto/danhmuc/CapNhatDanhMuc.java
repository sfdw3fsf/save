package vn.vnpt.vnptit.ecms.dto.danhmuc;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.*;

public class CapNhatDanhMuc {
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String name;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer id;

    public String getName() {
        return name;
    }
    public void setName(String name) { this.name = name; }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) { this.id = id; }
}
