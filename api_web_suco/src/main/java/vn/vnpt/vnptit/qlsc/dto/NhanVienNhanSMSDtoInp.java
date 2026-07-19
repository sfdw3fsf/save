package vn.vnpt.vnptit.qlsc.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class NhanVienNhanSMSDtoInp {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer tinnhan_id;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer page_num;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer page_size;

    public Integer getTinnhan_id() {
        return tinnhan_id;
    }

    public void setTinnhan_id(Integer tinnhan_id) {
        this.tinnhan_id = tinnhan_id;
    }

    public Integer getPage_num() {
        return page_num;
    }

    public void setPage_num(Integer page_num) {
        this.page_num = page_num;
    }

    public Integer getPage_size() {
        return page_size;
    }

    public void setPage_size(Integer page_size) {
        this.page_size = page_size;
    }
}
