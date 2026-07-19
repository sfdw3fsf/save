package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.danhmuc.VlanDtoIn;
import vn.vnpt.vnptit.ecms.dto.hatang.DMLienQuanVlanDtoIn;
import vn.vnpt.vnptit.ecms.service.danhmuc.VlanService;

import javax.validation.Valid;

@RestController
@RequestMapping("/vlan")
public class VlanController {
    @Autowired
    VlanService vlanService;

    @Operation(summary = "Lấy danh sách vlan")
    @RequestMapping(value = "/lay-danhsach", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_danhsach() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vlanService.layDs());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "insert and update vlan")
    @RequestMapping(value = "/capnhat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> tao(@Valid @RequestBody VlanDtoIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(vlanService.updateInsert(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "xóa vlan")
    @RequestMapping(value = "/xoa", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoa(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(vlanService.xoa(item.getId())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách danh mục liên quan")
    @RequestMapping(value = "/dm_lienquan", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsDmLienQuan(@Valid @RequestBody DMLienQuanVlanDtoIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vlanService.layDsDmLienQuan(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "lấy thông tin subnet từ vlan")
    @RequestMapping(value = "/tt_subnet", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> tt_subnet(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(vlanService.tt_subnet(item.getId())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
