package vn.vnpt.vnptit.ecms.dto.idc;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO xóa nhiều chủng loại tủ đĩa")
public class DeleteChungLoaiTuDiaDto {

    @Schema(description = "Danh sách ID cần xóa, truyền dạng mảng")
    private Long[] ids;

    public Long[] getIds() { return ids; }
    public void setIds(Long[] ids) { this.ids = ids; }
}


