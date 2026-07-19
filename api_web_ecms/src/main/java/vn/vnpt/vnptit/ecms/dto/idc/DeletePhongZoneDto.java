package vn.vnpt.vnptit.ecms.dto.idc;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO cho việc xóa nhiều phòng/zone")
public class DeletePhongZoneDto {
    
    @Schema(description = "Danh sách ID phòng/zone cần xóa")
    private Long[] ids;
    
    public DeletePhongZoneDto() {}
    
    public DeletePhongZoneDto(Long[] ids) {
        this.ids = ids;
    }
    
    public Long[] getIds() {
        return ids;
    }
    
    public void setIds(Long[] ids) {
        this.ids = ids;
    }
}
