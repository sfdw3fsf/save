package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.Message;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.danhmuc.*;
import vn.vnpt.vnptit.ecms.service.danhmuc.PortChannelService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("danhmuc/PortChannel")
public class PortChannelController {
    private final PortChannelService _service;
    public PortChannelController(PortChannelService service) {
        this._service = service;
    }

    @Operation(summary = "Liệt kê danh sách port channel")
    @RequestMapping(value = "/DanhSach", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachPortChannel(Long tramTbId,
                                                         Long dslamId)
    {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.DsPortChannel(tramTbId, dslamId, AppRequestContext.getCurrent().getPhanVungID()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Liệt kê danh sách cổng port channel chưa gán/ đã gán")
    @RequestMapping(value = "/cong-port-channel", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachPortChannel(Long kenhId,
                                                         Long dslamId,
                                                         @RequestParam Integer kieu)
    {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.DsCongPortChannel(kenhId, dslamId, kieu, AppRequestContext.getCurrent().getPhanVungID()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Chi tiết port channel")
    @RequestMapping(value = "/ChiTiet", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> ChiTietPortChannel(@RequestParam Long kenhId)
    {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.ChiTietPortChannel(AppRequestContext.getCurrent().getPhanVungID(), kenhId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm port channel")
    @RequestMapping(value = "/Them", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> ThemPortChannel(@Valid @RequestBody ThemPortChannel input) throws AppSqlException {
        ApiResult rs = new ApiResult();
        Long kenhId = Long.valueOf(0);
        try {
            //rs.setData(_service.ThemKenh(input));
            ArrayList<Map<String, Object>> r = _service.ThemKenh(input, AppRequestContext.getCurrent().getPhanVungID());
            kenhId = Long.parseLong(r.get(0).get("KENH_ID").toString()) ;
            for (KenhPort item : input.getKenhPorts())
            {
                _service.ThemKenhPort(item, AppRequestContext.getCurrent().getPhanVungID(), kenhId);
            }
            rs.setData(_service.ChiTietPortChannel(AppRequestContext.getCurrent().getPhanVungID(), kenhId));
        } catch (AppSqlException e) {
            rs.setException(e);
            _service.XoaPortChannel(AppRequestContext.getCurrent().getPhanVungID(), kenhId);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật port channel")
    @RequestMapping(value = "/CapNhat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> CapNhatPortChannel(@Valid @RequestBody CapNhatPortChannel input) {
        ApiResult rs = new ApiResult();
        try {
            Map<String, Object> r = _service.ChiTietPortChannel(AppRequestContext.getCurrent().getPhanVungID(), input.getKenhId());
            if (r == null){
                rs.setMessageDetail("id "+ input.getKenhId() +" không tồn tại");
                return rs.getResponseEntity(Message.Codes.BSS_00009491);
            }
            _service.CapNhatKenh(input, AppRequestContext.getCurrent().getPhanVungID());
            _service.XoaKenhPortChannel(AppRequestContext.getCurrent().getPhanVungID(), input.getKenhId());
            for (KenhPort item : input.getKenhPorts())
            {
                _service.ThemKenhPort(item, AppRequestContext.getCurrent().getPhanVungID(), input.getKenhId());
            }
            rs.setData(_service.ChiTietPortChannel(AppRequestContext.getCurrent().getPhanVungID(), input.getKenhId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa port channel")
    @RequestMapping(value = "/Xoa", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> XoaPortChannel(@RequestParam Long kenhId)
    {
        ApiResult rs = new ApiResult();
        try {
            Map<String, Object> r = _service.ChiTietPortChannel(AppRequestContext.getCurrent().getPhanVungID(), kenhId);
            if (r == null){
                rs.setMessageDetail("id "+ kenhId +" không tồn tại");
                return rs.getResponseEntity(Message.Codes.BSS_00009491);
            }
            rs.setData(new ResultOutput(_service.XoaPortChannel(AppRequestContext.getCurrent().getPhanVungID(), kenhId)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lay ds TTVT")
    @RequestMapping(value = "/lay-ds-vetinh", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> laydsvetinh(@RequestParam long ttvt)
    {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.lay_ds_vetinh1(ttvt));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lay ds Tram TBi")
    @RequestMapping(value = "/lay-ds-thietbi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> laydsthietbi(@RequestParam Long tramtbi_id)
    {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.lay_ds_thietbi(tramtbi_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
