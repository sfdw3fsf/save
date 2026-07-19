package vn.vnpt.vnptit.ecms.dto.idc;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO cho việc xóa nhiều loại vật liệu kết nối")
public class DeleteLoaiVatLieuKetNoiDto {
    
    @Schema(description = "Danh sách ID loại vật liệu kết nối cần xóa")
    private Long[] ids;
    
    public DeleteLoaiVatLieuKetNoiDto() {}
    
    public DeleteLoaiVatLieuKetNoiDto(Long[] ids) {
        this.ids = ids;
    }
    
    public Long[] getIds() {
        return ids;
    }
    
    public void setIds(Long[] ids) {
        this.ids = ids;
    }
}
