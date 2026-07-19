package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.model.PortSwitchModel;
import vn.vnpt.vnptit.ecms.service.danhmuc.PortSwitchService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("danhmuc")
public class PortSwitchController {

    @Autowired
    public PortSwitchService portSwitchService;

    @Operation(summary = "Lấy danh sách port switch theo phân vùng")
    @GetMapping("port-switch")
    public ResponseEntity<ApiResult> getListPortSwitch(@RequestParam("turackid") int turackid, @RequestParam("dslamid") int dslamid) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(portSwitchService.getListPortSwitch(turackid, dslamid));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm port switch")
    @PostMapping("port-switch")
    public ResponseEntity<ApiResult> addPortSwitch(@RequestBody PortSwitchModel portSwitchModel) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(portSwitchService.addPortSwitch(portSwitchModel));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Chỉnh sửa một port switch")
    @PostMapping("update-port-switch/{id}")
    public ResponseEntity<ApiResult> updatePortSwitchById(@RequestBody PortSwitchModel portSwitchModel, @PathVariable("id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(portSwitchService.updatePortSwitch(portSwitchModel, id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa một port switch")
    @PostMapping("delete-port-switch/{id}")
    public ResponseEntity<ApiResult> deletePortSwitchById(@PathVariable("id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(portSwitchService.deletePortSwtich(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thuê bao của port switch")
    @GetMapping("port-switch/ds-thue-bao/{port_switch_id}")
    public ResponseEntity<ApiResult> getdsThueBaoTheoPortID(@PathVariable int port_switch_id) {
        ApiResult rs = new ApiResult();
        try {

            rs.setData(portSwitchService.getdsThueBaoTheoPortID(port_switch_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
