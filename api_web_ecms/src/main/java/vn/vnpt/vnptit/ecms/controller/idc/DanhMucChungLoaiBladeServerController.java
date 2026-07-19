package vn.vnpt.vnptit.ecms.controller.idc;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucBladeServerDto;
import vn.vnpt.vnptit.ecms.dto.idc.SearchDanhMucBladeServerDto;
import vn.vnpt.vnptit.ecms.service.idc.DanhMucChungLoaiBladeServerService;

@RestController
@RequestMapping("tainguyen-idc/chung-loai-blade-server")
public class DanhMucChungLoaiBladeServerController {
    private final DanhMucChungLoaiBladeServerService service;

    public DanhMucChungLoaiBladeServerController(DanhMucChungLoaiBladeServerService service) {
        this.service = service;
    }
    @PostMapping(value = "/all")
    public ApiResult getAll(
            @RequestBody SearchDanhMucBladeServerDto dto
            ) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.getAll(dto));
        } catch (Exception e) {
            res.setException((AppSqlException) e);
        }
        return res;
    }
    @PostMapping(value = "/insert-update")
    public ResponseEntity<ApiResult> insertOrUpdate(@RequestBody DanhMucBladeServerDto dto) {
        ApiResult res = new ApiResult();
        try {
            res = this.service.insertOrUpdate(dto);
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }
}