package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.service.idc.CapPhatCongSuatDienService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("tainguyen-idc/capphat-congsuatdien")
public class CapPhatCongSuatDienController {

    @Autowired
    CapPhatCongSuatDienService service;

    @Operation(summary = "Lấy danh sách phiếu yêu cầu cấp phát")
    @RequestMapping(value = "/ds-yeucau-capphat", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lay_danhsach_yeucau(){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getList());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
