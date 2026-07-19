package vn.vnpt.vnptit.ecms.controller.idc;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.HanhLangDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ToaDoDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ToaDoHanhLangDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ViTriHanhLangBatchDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ViTriHanhLangDto;
import vn.vnpt.vnptit.ecms.service.idc.HanhLangService;

@RestController
@RequestMapping("tainguyen-idc/khong-gian-hanh-lang")
public class HanhLangController {
	private final HanhLangService service;

	public HanhLangController(HanhLangService service) {
		this.service = service;
	}

	@Operation(summary = "Lấy danh sách hành lang chưa có vị trí đặt theo phòng")
	@PostMapping("/get-ds-hanh-lang-chuaco-vitri")
	public ResponseEntity<ApiResult> getDsHanhLangChuaCoViTri(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(this.service.getDsHanhLangChuaCoViTri(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách hành lang đã có vị trí đặt theo phòng")
	@PostMapping("/get-ds-hanh-lang-daco-vitri")
	public ResponseEntity<ApiResult> getDsHanhLangDaCoViTri(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(this.service.getDsHanhLangDaCoViTri(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	/**
	 * #region Vị trí tọa độ hành lang
	 */

	@Operation(summary = "Lấy danh sách vị trí tọa độ trong tập điểm của hành lang ")
	@PostMapping("/get-toado-hanhlang")
	public ResponseEntity<ApiResult> getToaDoHanhLang(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(this.service.getToaDoHanhLang(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Thêm thông tin vị trí tọa độ trong tập điểm của hành lang ")
	@PostMapping("/insert-toado-hanhlang")
	public ResponseEntity<ApiResult> insertToaDoHanhLang(@Valid @RequestBody ToaDoHanhLangDto item) {
		ApiResult rs = new ApiResult();
		try {
			String msg = "";
			ToaDoDto[] ds = item.getDsToaDo();
			if (ds.length == 0)
				msg = "Danh sách tọa độ không được để trống";
			else {
				for (ToaDoDto toaDo : ds) {
					if (toaDo == null) {
						msg = "Danh sách tọa độ có thông tin không hợp lệ";
						break;
					}
				}
			}
			if (msg.equals(""))
				rs.setData(new ResultOutput(this.service.insertToaDoHanhLang(item)));
			else
				rs.setDataInvalidMessage(msg);
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật thông tin vị trí tọa độ trong tập điểm của hành lang ")
	@PostMapping("/update-toado-hanhlang")
	public ResponseEntity<ApiResult> updateToaDoHanhLang(@Valid @RequestBody ToaDoHanhLangDto item) {
		ApiResult rs = new ApiResult();
		try {
			String msg = "";
			ToaDoDto[] ds = item.getDsToaDo();
			if (ds.length == 0)
				msg = "Danh sách tọa độ không được để trống";
			else {
				for (ToaDoDto toaDo : ds) {
					if (toaDo == null) {
						msg = "Danh sách tọa độ có thông tin không hợp lệ";
						break;
					}
				}
			}
			if (msg.equals(""))
				rs.setData(new ResultOutput(this.service.updateToaDoHanhLang(item)));
			else
				rs.setDataInvalidMessage(msg);
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Xóa thông tin vị trí tọa độ trong tập điểm của hành lang ")
	@PostMapping("/delete-toado-hanhlang")
	public ResponseEntity<ApiResult> deleteToaDohanhlang(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(this.service.deleteToaDoHanhLang(item.getId())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật thông tin vị trí của hành lang")
	@PostMapping("/update-vitri-hanhlang")
	public ResponseEntity<ApiResult> updateViTriHanhLang(@Valid @RequestBody ViTriHanhLangDto item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(
					this.service.updateViTriHanhLang(item.getHanhLangID(), item.getToaDoX(), item.getToaDoY())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật hàng loạt thông tin vị trí của hành lang")
	@PostMapping("/update-vitri-hanhlang-batch")
	public ResponseEntity<ApiResult> updateViTriHanhLangBatch(@Valid @RequestBody ViTriHanhLangBatchDto item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(this.service.updateViTriHanhLangBatch(item)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Xóa thông tin vị trí của hành lang")
	@PostMapping("/delete-vitri-hanhlang")
	public ResponseEntity<ApiResult> deleteViTrihanhlang(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(this.service.deleteViTriHanhLang(item.getId())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy chi tiết hành lang theo id")
	@PostMapping("/get-detail-hanhlang")
	public ResponseEntity<ApiResult> getDetailHanhLang(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(this.service.getHanhLangById(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách hành lang theo phòng")
	@PostMapping("/get-ds-hanhlang-by-phong")
	public ResponseEntity<ApiResult> getDsHanhLangByPhong(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(this.service.getAllHanhLangByPhongId(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Xoá hành lang")
	@PostMapping("/delete-hanhlang")
	public ResponseEntity<ApiResult> deleteHanhLang(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(this.service.deleteHanhLang(item.getId())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Thêm/ cập nhật hành lang")
	@PostMapping("/upsert-hanhlang")
	public ResponseEntity<ApiResult> upsertHanhLang(@Valid @RequestBody HanhLangDto item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(this.service.upsertHanhLang(item)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

}
