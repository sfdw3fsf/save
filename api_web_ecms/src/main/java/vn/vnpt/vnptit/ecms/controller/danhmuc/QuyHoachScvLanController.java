package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.Message;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.QuyHoachScvLanDtoIn;
import vn.vnpt.vnptit.ecms.service.danhmuc.QuyHoachScvLanService;

import javax.validation.Valid;

@RestController
@RequestMapping("/danhmuc/quyhoach_scvlan")
public class QuyHoachScvLanController {
    private final QuyHoachScvLanService quyHoachScvLanService;
    int phanvung_id =0;
    public QuyHoachScvLanController(QuyHoachScvLanService quyHoachScvLanService)
    {
        this.quyHoachScvLanService = quyHoachScvLanService;
    }
    @Operation(summary = "Danh sách Quy hoạch scvLan")
    @RequestMapping(value = "/danhsach", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dsQuyHoachscvLan(@Valid  @RequestBody QuyHoachScvLanDtoIn.QuyHoachScvLanTraCuu input) {
        ApiResult rs = new ApiResult();
        try {

            phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(quyHoachScvLanService.dsQuyHoachscvLan(phanvung_id,input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Danh sách trạm thiết bị")
    @RequestMapping(value = "/dstram_tbi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsTramThietBi() {
        ApiResult rs = new ApiResult();
        try {
            phanvung_id= AppRequestContext.getCurrent().getPhanVungID();;
            rs.setData(quyHoachScvLanService.dsTramThietBi(phanvung_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Danh mục hướng kết nối")
    @RequestMapping(value = "/dmhuongketnoi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dmHuongKetNoi() {
        ApiResult rs = new ApiResult();
        try {
            phanvung_id= AppRequestContext.getCurrent().getPhanVungID();;
            rs.setData(quyHoachScvLanService.dmHuongKetNoi(phanvung_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Danh mục thiết bị")
    @RequestMapping(value = "/dmthietbi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dmThietBi(@Valid @RequestBody QuyHoachScvLanDtoIn.danhMucThietBiDto input) {
        ApiResult rs = new ApiResult();
        try {
            phanvung_id= AppRequestContext.getCurrent().getPhanVungID();;
            rs.setData(quyHoachScvLanService.dmThietBi(phanvung_id,input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Danh mục hướng kết nối")
    @RequestMapping(value = "/dshuongketnoi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dsHuongKetNoi(@Valid @RequestBody QuyHoachScvLanDtoIn.danhMucHuongKNDto input) {
        ApiResult rs = new ApiResult();
        try {
            phanvung_id= AppRequestContext.getCurrent().getPhanVungID();;
            rs.setData(quyHoachScvLanService.dsHuongKetnoi(phanvung_id,input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Thêm quy hoạch SCVlan")
    @RequestMapping(value = "/them", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> quyhoachScvLanIns(@Valid @RequestBody QuyHoachScvLanDtoIn.QuyHoachScvLanIns input) {
        ApiResult rs = new ApiResult();
        try {
            if (input.getMethod() > 1 || input.getMethod() < 0)
            {
                rs.setMessageDetail("method "+ input.getMethod() +" không tồn tại");
                return rs.getResponseEntity(Message.Codes.BSS_00009491);
            }
            phanvung_id= AppRequestContext.getCurrent().getPhanVungID();;
            rs.setData(quyHoachScvLanService.quyhoachScvLanIns(phanvung_id,input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Cập nhật quy hoạch SCVlan")
    @RequestMapping(value = "/sua", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> quyhoachScvLanUpd(@Valid @RequestBody QuyHoachScvLanDtoIn.QuyHoachScvLanUpd input) {
        ApiResult rs = new ApiResult();
        try {
            if (input.getMethod() > 1 || input.getMethod() < 0)
            {
                rs.setMessageDetail("method "+ input.getMethod() +" không tồn tại");
                return rs.getResponseEntity(Message.Codes.BSS_00009491);
            }
            phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(quyHoachScvLanService.quyhoachScvLanUpd(phanvung_id,input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Xóa quy hoạch SCVlan")
    @RequestMapping(value = "/xoa/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> quyhoachScvLanDel(@PathVariable int id) {
        ApiResult rs = new ApiResult();
        try {
            phanvung_id= AppRequestContext.getCurrent().getPhanVungID();;
            rs.setData(new ResultOutput(quyHoachScvLanService.quyhoachScvLanDel(phanvung_id,id)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Chi tiết quy hoạch SCVlan")
    @RequestMapping(value = "/chitiet/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> quyhoachScvLanDetail(@PathVariable int id) {
        ApiResult rs = new ApiResult();
        try {
            phanvung_id= AppRequestContext.getCurrent().getPhanVungID();;
            rs.setData(quyHoachScvLanService.quyhoachScvLanDetail(phanvung_id,id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Danh sách method")
    @RequestMapping(value = "/dsMethod", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsMethod() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quyHoachScvLanService.dsMethod());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
