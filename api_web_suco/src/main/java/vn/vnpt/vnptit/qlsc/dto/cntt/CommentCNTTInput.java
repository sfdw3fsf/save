package vn.vnpt.vnptit.qlsc.dto.cntt;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CommentCNTTInput {

    @NotNull(message = ModelValidation.NOT_NULL)
    private Long suco_id;

    public Long getSuco_id() {
        return suco_id;
    }

    public void setSuco_id(Long suco_id) {
        this.suco_id = suco_id;
    }

    private Long phanvung_sc_id;

    public Long getPhanvung_sc_id() {
        return phanvung_sc_id;
    }

    public void setPhanvung_sc_id(Long phanvung_sc_id) {
        this.phanvung_sc_id = phanvung_sc_id;
    }

    private Long comment_id;
    @NotNull(message = ModelValidation.NOT_NULL)
    private String comment_content;

    public Long getComment_id() {
        return this.comment_id;
    }

    public void setComment_id(Long comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment) {
        this.comment_content = comment;
    }

private String attach_files;
    public void setAttach_files(String attach_files) { this.attach_files = attach_files;}

    public String getAttach_files() { return this.attach_files;}
}
