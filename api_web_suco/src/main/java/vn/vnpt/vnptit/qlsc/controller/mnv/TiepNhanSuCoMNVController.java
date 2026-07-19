package vn.vnpt.vnptit.qlsc.controller.mnv;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.qlsc.dto.mnv.CapNhatTinhTrangSuCoMNVInput;
import vn.vnpt.vnptit.qlsc.dto.mnv.CapNhatTinhTrangSuCoTempMNVInput;
import vn.vnpt.vnptit.qlsc.dto.mnv.GiaoViecKiemTraSuCoInput;
import vn.vnpt.vnptit.qlsc.dto.mnv.TaoSuCoTamMNVInput;
import vn.vnpt.vnptit.qlsc.dto.mnv.TaoYeuCauHoTroMNVInput;
import vn.vnpt.vnptit.qlsc.service.mnv.TiepNhanSuCoMNVService;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/mnv")
public class TiepNhanSuCoMNVController {

    private final TiepNhanSuCoMNVService service;
    public TiepNhanSuCoMNVController(TiepNhanSuCoMNVService service) {
        this.service = service;
    }

    @Operation(summary = "Giao nhiệm vụ kiểm tra sự cố")
    @RequestMapping(value = "/giaoviec-kiemtra-suco", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> giaoviec_kiemtrasuco(@RequestBody GiaoViecKiemTraSuCoInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.giaoviec_kiemtrasuco(input, AppRequestContext.getCurrent().getPhanVungID(), AppRequestContext.getCurrent().getNhanVienID())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Tạo sự cố tạm MNV")
    @RequestMapping(value = "/capnhat-sucotam-mnv", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> taoSuCoTamMNV(@Valid @RequestBody TaoSuCoTamMNVInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.taoSuCoTamMNV(item)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

	@Operation(summary = "Cập nhật tình trạng sự cố MNV")
	@RequestMapping(value = "/capNhatTinhTrangSuCoMNV", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> capNhatTinhTrangSuCoMNV(@Valid @RequestBody CapNhatTinhTrangSuCoMNVInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(service.capNhatTinhTrangSuCoMNV(item)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật tình trạng sự cố tạm MNV")
	@RequestMapping(value = "/capNhatTinhTrangSuCoTempMNV", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> capNhatTinhTrangSuCoTempMNV(@Valid @RequestBody CapNhatTinhTrangSuCoTempMNVInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(service.capNhatTinhTrangSuCoTempMNV(item)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

    @Operation(summary = "Tạo yêu cầu hỗ trợ MNV")
    @RequestMapping(value = "/taoYeuCauHoTroMNV", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> taoYeuCauHoTroMNV(@Valid @RequestBody TaoYeuCauHoTroMNVInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.taoYeuCauHoTroMNV(item)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
