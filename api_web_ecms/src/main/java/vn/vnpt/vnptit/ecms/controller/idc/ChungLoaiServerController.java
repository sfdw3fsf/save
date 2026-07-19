package vn.vnpt.vnptit.ecms.controller.idc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.ChungLoaiServerDto;
import vn.vnpt.vnptit.ecms.service.idc.ChungLoaiServerService;

@RestController
@RequestMapping("/danhmuc/chungloai-server")
public class ChungLoaiServerController {

    @Autowired
    private ChungLoaiServerService chungLoaiServerService;

    /**
     * Tìm kiếm hoặc lấy danh sách tất cả chủng loại server
     * Nếu không có điều kiện search thì lấy toàn bộ dữ liệu
     */
    @PostMapping(value = "/search")
    public ResponseEntity<ApiResult> search(@RequestBody(required = false) JsonNode searchJsonData) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(chungLoaiServerService.searchChungLoaiServer(searchJsonData));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    /**
     * Tạo mới hoặc cập nhật chủng loại server
     */
    @PostMapping(value = "/create-or-update")
    public ResponseEntity<ApiResult> createOrUpdate(@RequestBody ChungLoaiServerDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            dto.isValid();
            apiResult.setData(chungLoaiServerService.saveChungLoaiServer(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    /**
     * Xóa chủng loại server
     */
    @PostMapping(value = "/delete")
    public ResponseEntity<ApiResult> delete(@RequestParam Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(chungLoaiServerService.delete(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }
}
