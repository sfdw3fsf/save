package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.hatang.*;
import vn.vnpt.vnptit.ecms.service.hatang.SearchPycTaiNguyenService;

@RestController
@RequestMapping("/hatang/popup-pyctn")
public class SearchPycTaiNguyenController {

    @Autowired
    private SearchPycTaiNguyenService searchPycTaiNguyenService;



    @Operation(summary = "Tìm kiếm thành phần theo type")
    @PostMapping("/tim-kiem-tainguyen")
    public ResponseEntity<ApiResult> timKiemThanhPhan(@RequestBody SearchPycTaiNguyenDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(searchPycTaiNguyenService.timKiemThanhPhan(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


}
