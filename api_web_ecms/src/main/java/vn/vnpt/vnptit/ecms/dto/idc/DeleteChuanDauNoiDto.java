package vn.vnpt.vnptit.ecms.dto.idc;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO cho việc xóa nhiều chuẩn đấu nối")
public class DeleteChuanDauNoiDto {
    
    @Schema(description = "Danh sách ID chuẩn đấu nối cần xóa")
    private Long[] ids;
    
    public DeleteChuanDauNoiDto() {}
    
    public DeleteChuanDauNoiDto(Long[] ids) {
        this.ids = ids;
    }
    
    public Long[] getIds() {
        return ids;
    }
    
    public void setIds(Long[] ids) {
        this.ids = ids;
    }
}

