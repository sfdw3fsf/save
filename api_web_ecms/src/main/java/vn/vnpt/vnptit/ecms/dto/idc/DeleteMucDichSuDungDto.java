package vn.vnpt.vnptit.ecms.dto.idc;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO cho việc xóa nhiều mục đích sử dụng")
public class DeleteMucDichSuDungDto {
    
    @Schema(description = "Danh sách ID mục đích sử dụng cần xóa")
    private Long[] ids;
    
    public DeleteMucDichSuDungDto() {}
    
    public DeleteMucDichSuDungDto(Long[] ids) {
        this.ids = ids;
    }
    
    public Long[] getIds() {
        return ids;
    }
    
    public void setIds(Long[] ids) {
        this.ids = ids;
    }
}

