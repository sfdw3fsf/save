package vn.vnpt.vnptit.ecms.dto.idc;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO cho việc xóa nhiều loại ổ cắm")
public class DeleteLoaiOcamDto {
    
    @Schema(description = "Danh sách ID loại ổ cắm cần xóa")
    private Long[] ids;
    
    public DeleteLoaiOcamDto() {}
    
    public DeleteLoaiOcamDto(Long[] ids) {
        this.ids = ids;
    }
    
    public Long[] getIds() {
        return ids;
    }
    
    public void setIds(Long[] ids) {
        this.ids = ids;
    }
}

