package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.Message;
import vn.vnpt.vnptit.ecms.model.DanhMucDongBoModel;
import vn.vnpt.vnptit.ecms.service.danhmuc.DanhMucDongBoService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("danhmuc")
public class DanhMucDongBoController {

    @Autowired
    private DanhMucDongBoService danhMucDongBoService;

    @Operation(summary = "Lấy danh sách danh mục đồng bộ")
    @GetMapping("list-dmdongbo")
    public ResponseEntity<ApiResult> GetListDMDongBo(@RequestParam(value = "tendanhmuc", required = false) String tendanhmuc) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucDongBoService.GetListDMDongBo(tendanhmuc));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm danh mục đồng bộ")
    @PostMapping("insert-dmdongbo")
    public ResponseEntity<ApiResult> InsertDMDongBo(@Valid @RequestBody DanhMucDongBoModel danhmuc) {
        ApiResult rs = new ApiResult();
        try {
            Map<String, Object> kq = danhMucDongBoService.InsertDMDongBo(danhmuc.getDong_bo());
            if(kq.size() < 2 && Integer.parseInt(kq.get("KETQUA").toString()) == -1) {
                kq = new HashMap<>();
                kq.put("dong_bo", "Giá trị tham số không được lớn hơn 50");
                rs.setMessage(Message.Codes.BSS_00009490);
            }
            rs.setData(kq);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Update danh mục đồng bộ")
    @PostMapping("update-dmdongbo/{id}")
    public ResponseEntity<ApiResult> UpdateMDongBo(@PathVariable(value = "id", required = true)int id, @Valid @RequestBody DanhMucDongBoModel danhmuc) {
        ApiResult rs = new ApiResult();
        try {
            Map<String, Object> data = danhMucDongBoService.UpdateDMDongBo(id, danhmuc.getDong_bo());
            if(Integer.parseInt(data.get("KETQUA").toString()) == 1) {
                data.put("KETQUA", true);
                rs.setMessageDetail("Cập nhật thành công");
            }
            else if(Integer.parseInt(data.get("KETQUA").toString()) == 0) {
                data.put("KETQUA", false);
                rs.setMessageDetail("Không tồn tại id danh mục");
                rs.setMessage(Message.Codes.BSS_00009490);
            }
            else {
                data.put("KETQUA", false);
                rs.setMessageDetail("Giá trị tham số không được lớn hơn 50");
                rs.setMessage(Message.Codes.BSS_00009490);
            }
            rs.setData(data);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Delete danh mục đồng bộ")
    @PostMapping("delete-dmdongbo/{id}")
    public ResponseEntity<ApiResult> DeleteMDongBo(@PathVariable(value = "id", required = true)int id) {
        ApiResult rs = new ApiResult();
        try {
            Map<String, Object> data = danhMucDongBoService.DeleteDMDongBo(id);
            if(Boolean.parseBoolean(data.get("KETQUA").toString()) == true) {
                rs.setMessageDetail("Xóa thành công");
            }
            else {
                rs.setMessageDetail("Không tồn tại id danh mục");
                rs.setMessage(Message.Codes.BSS_00009490);
            }
            rs.setData(data);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy chi tiết danh mục đồng bộ")
    @GetMapping("get-dmdongbo/{id}")
    public ResponseEntity<ApiResult> GetMDongBo(@PathVariable(value = "id", required = true)int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucDongBoService.GetDMDongBo(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
