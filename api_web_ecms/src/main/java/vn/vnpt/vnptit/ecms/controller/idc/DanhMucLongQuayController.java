package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucLongQuayDtoIn;
import vn.vnpt.vnptit.ecms.service.idc.LongQuayService;

import javax.validation.Valid;

@RestController
@RequestMapping("long-quay")
public class DanhMucLongQuayController {
	private final LongQuayService service;

	public DanhMucLongQuayController(LongQuayService service) {
		this.service = service;
	}

	@Operation(summary = "Thêm mới/Cập nhật Lồng Quầy")
	@PostMapping("/save")
	public ResponseEntity<ApiResult> save(@RequestBody @Valid DanhMucLongQuayDtoIn input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.saveLongQuay(input));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Danh sách Lồng Quầy")
	@PostMapping("/danh-sach")
	public ResponseEntity<ApiResult> getDanhSachLongQuay(@RequestBody @Valid DanhMucLongQuayDtoIn input) {
		ApiResult apiResult = new ApiResult();
		try {
			apiResult.setData(service.getListLongQuay(input));
		} catch (AppSqlException e) {
			apiResult.setException(e);
		}
		return apiResult.getResponseEntity();
	}

	@Operation(summary = "Lấy chi tiết lồng quây")
	@PostMapping("/chitiet/{id}")
	public ResponseEntity<ApiResult> getChiTietLongQuay(@PathVariable("id") Long id) {
		ApiResult apiResult = new ApiResult();
		try {
			apiResult.setData(service.getChiTietLongQuay(id));
		} catch (AppSqlException e) {
			apiResult.setException(e);
		}
		return apiResult.getResponseEntity();
	}

	@Operation(summary = "Xóa lồng quây")
	@PostMapping(value = "/delete/{id}")
	public ResponseEntity<ApiResult> deleteKetNoi(@PathVariable("id") Long id) {
		ApiResult apiResult = new ApiResult();
		try {
			apiResult.setData(service.deleteLongQuay(id));
		} catch (AppSqlException e) {
			apiResult.setException(e);
		}
		return apiResult.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách rack theo IDC chưa lắp đặt vào lồng quây")
	@PostMapping("/danhsach-rack/{id}")
	public ResponseEntity<ApiResult> getListRackChuaSD(@PathVariable("id") Long id) {
		ApiResult apiResult = new ApiResult();
		try {
			apiResult.setData(service.getListRackChuaSD(id));
		} catch (AppSqlException e) {
			apiResult.setException(e);
		}
		return apiResult.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách Phon/Zone theo Mặt sàn")
	@PostMapping("/danhsach-phong/{id}")
	public ResponseEntity<ApiResult> getListPhongByMatSan(@PathVariable("id") Long id) {
		ApiResult apiResult = new ApiResult();
		try {
			apiResult.setData(service.getListPhongByMatSan(id));
		} catch (AppSqlException e) {
			apiResult.setException(e);
		}
		return apiResult.getResponseEntity();
	}

	@Operation(summary = "Tháo gỡ Rack khỏi lồng quây")
	@PostMapping(value = "/thaogo-rack/{id}")
	public ResponseEntity<ApiResult> thaoGoRack(@PathVariable("id") Long id) {
		ApiResult apiResult = new ApiResult();
		try {
			apiResult.setData(service.thaoGoRack(id));
		} catch (AppSqlException e) {
			apiResult.setException(e);
		}
		return apiResult.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách Racks trong Lồng quây")
	@PostMapping("/rack-theo-longquay/{id}")
	public ResponseEntity<ApiResult> getListRackInLongQuay(@PathVariable("id") Long id) {
		ApiResult apiResult = new ApiResult();
		try {
			apiResult.setData(service.getListRackInLongQuay(id));
		} catch (AppSqlException e) {
			apiResult.setException(e);
		}
		return apiResult.getResponseEntity();
	}

	@Operation(summary = "Danh sách Sở hữu Rack")
	@PostMapping("/danhsach-sohuu-rack")
	public ResponseEntity<ApiResult> getListSoHuuRack() {
		ApiResult apiResult = new ApiResult();
		try {
			apiResult.setData(service.getListSoHuuRack());
		} catch (AppSqlException e) {
			apiResult.setException(e);
		}
		return apiResult.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách thiết bị trong Lồng quây")
	@PostMapping("/thietbi-theo-longquay/{id}")
	public ResponseEntity<ApiResult> getListTBiInLongQuay(@PathVariable("id") Long id) {
		ApiResult apiResult = new ApiResult();
		try {
			apiResult.setData(service.getListTBiInLongQuay(id));
		} catch (AppSqlException e) {
			apiResult.setException(e);
		}
		return apiResult.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách thanh nguồn trong Lồng quây")
	@PostMapping("/thanhnguon-theo-longquay/{id}")
	public ResponseEntity<ApiResult> getListThanhNguonInLongQuay(@PathVariable("id") Long id) {
		ApiResult apiResult = new ApiResult();
		try {
			apiResult.setData(service.getListThanhNguonInLongQuay(id));
		} catch (AppSqlException e) {
			apiResult.setException(e);
		}
		return apiResult.getResponseEntity();
	}

	@Operation(summary = "Lịch sử thay đổi Lồng quây")
	@PostMapping("/lichsu-thaydoi-longquay")
	public ResponseEntity<ApiResult> getLichSuLongQuay(@RequestBody DanhMucLongQuayDtoIn dto) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.getLichSuLongQuay(dto.getLongQuayId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Danh sách khác hàng")
	@PostMapping("/khachhang")
	public ResponseEntity<ApiResult> getListKhachHang(@RequestBody DanhMucLongQuayDtoIn dto) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.getListKhachHang(dto.getLongQuayId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}
}
