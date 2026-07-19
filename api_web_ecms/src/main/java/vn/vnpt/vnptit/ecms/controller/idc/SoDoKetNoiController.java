package vn.vnpt.vnptit.ecms.controller.idc;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.SoDoKetNoiDto;
import vn.vnpt.vnptit.ecms.service.idc.SoDoKetNoiService;

@RestController
@RequestMapping("/sodo-ketnoi")
public class SoDoKetNoiController {

    @Autowired
    SoDoKetNoiService service;

    @Operation(summary = "Lưu bố cục sơ đồ kết nối")
    @PostMapping("/luu-bocuc-sodo")
    public ResponseEntity<ApiResult> luuBoCucSoDo(@Valid @RequestBody SoDoKetNoiDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.luuBoCucSoDo(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy bố cục sơ đồ kết nối")
    @PostMapping("/lay-bocuc-sodo-ketnoi")
    public ResponseEntity<ApiResult> layBoCucSoDoKetNoi(@Valid @RequestBody SoDoKetNoiDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.layBoCucSoDoKetNoi(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}