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
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.RackLongQuayDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ThietBiLongQuayDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ToaDoDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ToaDoLongQuayDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ToaDoPhongDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ViTriLongQuayBatchDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ViTriLongQuayDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ViTriPhongBatchDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ViTriPhongDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ViTriRackBatchDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ViTriRackDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ViTriThietBiBatchDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ViTriThietBiDto;
import vn.vnpt.vnptit.ecms.service.idc.KhongGianService;

@RestController
@RequestMapping("tainguyen-idc/khong-gian")
public class KhongGianController {
	private final KhongGianService service;

	public KhongGianController(KhongGianService service) {
		this.service = service;
	}

	@Operation(summary = "Lấy thông tin IDC ID của nhân viên")
	@PostMapping("/get-idc-theo-nhanvien")
	public ResponseEntity<ApiResult> getIDCTheoNhanVien() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(this.service.getIDCTheoNhanVien());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách rack chưa lắp đặt theo phòng")
	@PostMapping("/get-ds-rack-chua-lapdat")
	public ResponseEntity<ApiResult> getDsRackChuaLapDat(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(this.service.getDsRackChuaLapDat(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách thiết bị chưa có vị trí lắp đặt theo phòng")
	@PostMapping("/get-ds-thietbi-chuaco-vitri")
	public ResponseEntity<ApiResult> getDsThietBiChuaCoViTri(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(this.service.getDsThietBiChuaCoViTri(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách phòng chưa có vị trí đặt theo mặt sàn")
	@PostMapping("/get-ds-phong-chuaco-vitri")
	public ResponseEntity<ApiResult> getDsPhongChuaCoViTri(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(this.service.getDsPhongChuaCoViTri(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách lồng quay chưa có vị trí đặt theo phòng")
	@PostMapping("/get-ds-longquay-chuaco-vitri")
	public ResponseEntity<ApiResult> getDsLongQuayChuaCoViTri(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(this.service.getDsLongQuayChuaCoViTri(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách rack đã lắp đặt theo phòng")
	@PostMapping("/get-ds-rack-da-lapdat")
	public ResponseEntity<ApiResult> getDsRackDaLapDat(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(this.service.getDsRackDaLapDat(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách thiết bị đã có vị trí lắp đặt theo phòng")
	@PostMapping("/get-ds-thietbi-daco-vitri")
	public ResponseEntity<ApiResult> getDsThietBiDaCoViTri(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(this.service.getDsThietBiDaCoViTri(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách phòng đã có vị trí đặt theo mặt sàn")
	@PostMapping("/get-ds-phong-daco-vitri")
	public ResponseEntity<ApiResult> getDsPhongDaCoViTri(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(this.service.getDsPhongDaCoViTri(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy danh sách lồng quay đã có vị trí đặt theo phòng")
	@PostMapping("/get-ds-longquay-daco-vitri")
	public ResponseEntity<ApiResult> getDsLongQuayDaCoViTri(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(this.service.getDsLongQuayDaCoViTri(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật lồng quay cho rack")
	@PostMapping("/capnhat-longquay-rack")
	public ResponseEntity<ApiResult> capNhatLongQuayChoRack(@Valid @RequestBody RackLongQuayDto item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(this.service.capNhatLongQuayChoRack(item.getRackID(), item.getLongQuayID())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Xóa lồng quay cho rack")
	@PostMapping("/xoa-longquay-rack")
	public ResponseEntity<ApiResult> xoaLongQuayChoRack(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(this.service.xoaLongQuayChoRack(item.getId())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật lồng quay cho thiết bị")
	@PostMapping("/capnhat-longquay-thietbi")
	public ResponseEntity<ApiResult> capNhatLongQuayChoThietBi(@Valid @RequestBody ThietBiLongQuayDto item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(
					this.service.capNhatLongQuayChoThietBi(item.getThietBiID(), item.getLongQuayID())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Xóa lồng quay cho thiết bị")
	@PostMapping("/xoa-longquay-thietbi")
	public ResponseEntity<ApiResult> xoaLongQuayChoThietBi(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(this.service.xoaLongQuayChoThietBi(item.getId())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy cây sơ đồ IDC")
	@PostMapping("/get-cay-sodo-idc")
	public ResponseEntity<ApiResult> getCaySoDoIDC() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(this.service.getCaySoDoIDC(null));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	/**
	 * #region Vị trí tọa độ phòng
	 */

	@Operation(summary = "Lấy danh sách vị trí tọa độ trong tập điểm của phòng")
	@PostMapping("/get-toado-phong")
	public ResponseEntity<ApiResult> getToaDoPhong(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(this.service.getToaDoPhong(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Thêm thông tin vị trí tọa độ trong tập điểm của phòng")
	@PostMapping("/insert-toado-phong")
	public ResponseEntity<ApiResult> insertToaDoPhong(@Valid @RequestBody ToaDoPhongDto item) {
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
				rs.setData(new ResultOutput(this.service.insertToaDoPhong(item)));
			else
				rs.setDataInvalidMessage(msg);
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật thông tin vị trí tọa độ trong tập điểm của phòng")
	@PostMapping("/update-toado-phong")
	public ResponseEntity<ApiResult> updateToaDoPhong(@Valid @RequestBody ToaDoPhongDto item) {
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
				rs.setData(new ResultOutput(this.service.updateToaDoPhong(item)));
			else
				rs.setDataInvalidMessage(msg);
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Xóa thông tin vị trí tọa độ trong tập điểm của phòng")
	@PostMapping("/delete-toado-phong")
	public ResponseEntity<ApiResult> deleteToaDoPhong(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(this.service.deleteToaDoPhong(item.getId())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật thông tin vị trí của phòng")
	@PostMapping("/update-vitri-phong")
	public ResponseEntity<ApiResult> updateViTriPhong(@Valid @RequestBody ViTriPhongDto item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(
					this.service.updateViTriPhong(item.getPhongID(), item.getToaDoX(), item.getToaDoY())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật hàng loạt thông tin vị trí của phòng")
	@PostMapping("/update-vitri-phong-batch")
	public ResponseEntity<ApiResult> updateViTriPhongBatch(@Valid @RequestBody ViTriPhongBatchDto item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(this.service.updateViTriPhongBatch(item)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Xóa thông tin vị trí của phòng")
	@PostMapping("/delete-vitri-phong")
	public ResponseEntity<ApiResult> deleteViTriPhong(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(this.service.deleteViTriPhong(item.getId())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	/**
	 * #endregion Vị trí phòng
	 */

	/**
	 * #region Vị trí tọa độ lồng quay
	 */

	@Operation(summary = "Lấy danh sách vị trí tọa độ trong tập điểm của lồng quay ")
	@PostMapping("/get-toado-longquay")
	public ResponseEntity<ApiResult> getToaDoLongQuay(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(this.service.getToaDoLongQuay(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Thêm thông tin vị trí tọa độ trong tập điểm của lồng quay ")
	@PostMapping("/insert-toado-longquay")
	public ResponseEntity<ApiResult> insertToaDoLongQuay(@Valid @RequestBody ToaDoLongQuayDto item) {
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
				rs.setData(new ResultOutput(this.service.insertToaDoLongQuay(item)));
			else
				rs.setDataInvalidMessage(msg);
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật thông tin vị trí tọa độ trong tập điểm của lồng quay ")
	@PostMapping("/update-toado-longquay")
	public ResponseEntity<ApiResult> updateToaDoLongQuay(@Valid @RequestBody ToaDoLongQuayDto item) {
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
				rs.setData(new ResultOutput(this.service.updateToaDoLongQuay(item)));
			else
				rs.setDataInvalidMessage(msg);
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Xóa thông tin vị trí tọa độ trong tập điểm của lồng quay ")
	@PostMapping("/delete-toado-longquay")
	public ResponseEntity<ApiResult> deleteToaDoLongQuay(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(this.service.deleteToaDoLongQuay(item.getId())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật thông tin vị trí của lồng quay")
	@PostMapping("/update-vitri-longquay")
	public ResponseEntity<ApiResult> updateViTriLongQuay(@Valid @RequestBody ViTriLongQuayDto item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(
					this.service.updateViTriLongQuay(item.getLongQuayID(), item.getToaDoX(), item.getToaDoY())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật hàng loạt thông tin vị trí của lồng quay")
	@PostMapping("/update-vitri-longquay-batch")
	public ResponseEntity<ApiResult> updateViTriLongQuayBatch(@Valid @RequestBody ViTriLongQuayBatchDto item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(this.service.updateViTriLongQuayBatch(item)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Xóa thông tin vị trí của lồng quay")
	@PostMapping("/delete-vitri-longquay")
	public ResponseEntity<ApiResult> deleteViTriLongQuay(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(this.service.deleteViTriLongQuay(item.getId())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	/**
	 * #endregion Vị trí lồng quay
	 */

	/**
	 * #region Vị trí rack và thiết bị
	 */

	@Operation(summary = "Cập nhật thông tin vị trí của rack")
	@PostMapping("/update-vitri-rack")
	public ResponseEntity<ApiResult> updateViTriRack(@Valid @RequestBody ViTriRackDto item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(this.service.updateViTriRack(item.getRackID(), item.getToaDoX(),
					item.getToaDoY(), item.getHuongNgang() ? 1 : 0)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật hàng loạt thông tin vị trí của rack")
	@PostMapping("/update-vitri-rack-batch")
	public ResponseEntity<ApiResult> updateViTriRackBatch(@Valid @RequestBody ViTriRackBatchDto item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(this.service.updateViTriRackBatch(item)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Xóa thông tin vị trí của rack")
	@PostMapping("/delete-vitri-rack")
	public ResponseEntity<ApiResult> deleteViTriRack(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(this.service.deleteViTriRack(item.getId())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật thông tin vị trí của thiết bị")
	@PostMapping("/update-vitri-thietbi")
	public ResponseEntity<ApiResult> updateViTriThietBi(@Valid @RequestBody ViTriThietBiDto item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(
					this.service.updateViTriThietBi(item.getThietBiID(), item.getToaDoX(), item.getToaDoY())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật hàng loạt thông tin vị trí của thiết bị")
	@PostMapping("/update-vitri-thietbi-batch")
	public ResponseEntity<ApiResult> updateViTriThietBiBatch(@Valid @RequestBody ViTriThietBiBatchDto item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(this.service.updateViTriThietBiBatch(item)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Xóa thông tin vị trí của thiết bị")
	@PostMapping("/delete-vitri-thietbi")
	public ResponseEntity<ApiResult> deleteViTriThietBi(@Valid @RequestBody DataKeyInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(this.service.deleteViTriThietBi(item.getId())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	/**
	 * #endregion Vị trí rack và thiết bị
	 */

}
