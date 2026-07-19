package vn.vnpt.vnptit.ecms.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyGenericInput;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.DongBoPortThueBao.CapNhatDongBoPortInput;
import vn.vnpt.vnptit.ecms.dto.DongBoPortThueBao.DemThueBaoDongBoDto;
import vn.vnpt.vnptit.ecms.dto.DongBoPortThueBao.DemThueBaoTrongDanhBaDto;
import vn.vnpt.vnptit.ecms.dto.DongBoPortThueBao.DongBoPort;
import vn.vnpt.vnptit.ecms.dto.DongBoPortThueBao.DongBoPortDto;
import vn.vnpt.vnptit.ecms.dto.DongBoPortThueBao.DsThueBaoDongBoDto;
import vn.vnpt.vnptit.ecms.dto.DongBoPortThueBao.DsThueBaoTrongDanhBaDto;
import vn.vnpt.vnptit.ecms.dto.DongBoPortThueBao.ThemDongBoPortDto;
import vn.vnpt.vnptit.ecms.dto.DongBoPortThueBao.ThemDongBoPortInput;
import vn.vnpt.vnptit.ecms.model.ThongTinNguoiDungModel;
import vn.vnpt.vnptit.ecms.service.DongBoPortThueBaoService;

@RestController
@RequestMapping("DongBoPortThueBao")
public class DongBoPortThueBaoController {
	private final DongBoPortThueBaoService service;
	private ThongTinNguoiDungModel ttnd = new ThongTinNguoiDungModel();

	public DongBoPortThueBaoController(DongBoPortThueBaoService service) {
		this.service = service;
	}

	@Operation(summary = "Danh sách Bras")
	@RequestMapping(value = "/danh-sach-bras", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> DanhSachBras() {
		ApiResult rs = new ApiResult();
		try {
			ttnd.setPhanVungId(AppRequestContext.getCurrent().getPhanVungID());
			rs.setData(service.DsBras(ttnd.getPhanVungId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Danh sách Dslam theo bras")
	@RequestMapping(value = "/danh-sach-dslam-theo-bras", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> DanhSachDslamTheoBras(@RequestParam Long brasId) {
		ApiResult rs = new ApiResult();
		try {
			ttnd.setPhanVungId(AppRequestContext.getCurrent().getPhanVungID());
			rs.setData(service.DsDslamTheoBras(ttnd.getPhanVungId(), brasId));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Danh sách thuê bao trong danh bạ")
	@RequestMapping(value = "/danh-sach-thue-bao-trong-danh-ba", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> DanhSachThueBaoTrongDanhBa(@RequestParam Long dslamId,
			@RequestParam Integer pageSize, @RequestParam Integer pageNum) {
		ApiResult rs = new ApiResult();
		try {
			ttnd.setPhanVungId(AppRequestContext.getCurrent().getPhanVungID());
			rs.setData(service.DsThueBaoTrongDanhBa(ttnd.getPhanVungId(), dslamId, pageSize, pageNum));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Danh sách thuê bao đang đồng bộ")
	@RequestMapping(value = "/danh-sach-thue-bao-dang-dong-bo", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> DanhSachThueBaoDangDongBo(@RequestParam Long dslamId,
			@RequestParam Integer pageSize, @RequestParam Integer pageNum) {
		ApiResult rs = new ApiResult();
		try {
			ttnd.setPhanVungId(AppRequestContext.getCurrent().getPhanVungID());
			rs.setData(service.DsThueBaoDangDongBo(ttnd.getPhanVungId(), dslamId, pageSize, pageNum));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Thêm đồng bộ port thuê bao")
	@RequestMapping(value = "/them-dong-bo-port-thue-bao", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> ThemDongBoPortThueBao(@Valid @RequestBody ThemDongBoPortInput input) {
		ApiResult rs = new ApiResult();
		try {
			ttnd.setPhanVungId(AppRequestContext.getCurrent().getPhanVungID());
			ttnd.setNhanVienId(AppRequestContext.getCurrent().getNhanVienID());
			ttnd.setDonViId(AppRequestContext.getCurrent().getDonViID());
			ttnd.setIpCN(AppRequestContext.getCurrent().getClientIpAddress());
			ttnd.setMayCN(AppRequestContext.getCurrent().getClientName());
			ttnd.setUserName(AppRequestContext.getCurrent().getUserName());
			for (DongBoPort item : input.getDsDongBoPort()) {
				service.ThemDongBoPort(item, input.getDslamId(), ttnd);
			}
			rs.setData(new ResultOutput(true));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Kích hoạt đồng bộ port")
	@RequestMapping(value = "/kich-hoat-dong-bo-port-thue-bao", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> KichHoatDongBoPort(@Valid @RequestBody CapNhatDongBoPortInput input) {
		ApiResult rs = new ApiResult();
		try {
			ttnd.setPhanVungId(AppRequestContext.getCurrent().getPhanVungID());
			for (Long dongBoPortId : input.getDsDongBoPortId()) {
				service.CapNhatDongBoPort(ttnd.getPhanVungId(), dongBoPortId, 1);
			}
			rs.setData(new ResultOutput(true));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Hoàn thiện đồng bộ port")
	@RequestMapping(value = "/hoan-thien-dong-bo-port-thue-bao", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> HoanThienDongBoPortThueBao(@Valid @RequestBody CapNhatDongBoPortInput input) {
		ApiResult rs = new ApiResult();
		try {
			ttnd.setPhanVungId(AppRequestContext.getCurrent().getPhanVungID());
			for (Long dongBoPortId : input.getDsDongBoPortId()) {
				service.CapNhatDongBoPort(ttnd.getPhanVungId(), dongBoPortId, 2);
			}
			rs.setData(new ResultOutput(true));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Xóa đồng bộ port thuê bao")
	@RequestMapping(value = "/xoa-dong-bo-port-thue-bao", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> XoaDongBoPortThueBao(@Valid @RequestBody CapNhatDongBoPortInput input) {
		ApiResult rs = new ApiResult();
		try {
			ttnd.setPhanVungId(AppRequestContext.getCurrent().getPhanVungID());
			for (Long dongBoPortId : input.getDsDongBoPortId()) {
				service.XoaDongBoPort(ttnd.getPhanVungId(), dongBoPortId);
			}
			rs.setData(new ResultOutput(true));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	/**
	 * Update 2025
	 */
	@Operation(summary = "Danh sách thuê bao trong danh bạ theo DSLAM")
	@RequestMapping(value = "/get-ds-thuebao-trong-danhba", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> GetDsThueBaoTrongDanhBa(@Valid @RequestBody DsThueBaoTrongDanhBaDto input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.GetDsThueBaoTrongDanhBa(input.getDslamID(), input.getMaThueBao(), input.getExcludeTB(),
					input.getPageSize(), input.getPageIndex()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Đếm số thuê bao trong danh bạ theo DSLAM")
	@RequestMapping(value = "/get-so-thuebao-trong-danhba", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> CountThueBaoTrongDanhBa(@Valid @RequestBody DemThueBaoTrongDanhBaDto input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(
					service.CountThueBaoTrongDanhBa(input.getDslamID(), input.getMaThueBao(), input.getExcludeTB())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Danh sách thuê bao đồng bộ")
	@RequestMapping(value = "/get-ds-thuebao-dongbo", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> GetDsThueBaoDongBo(@Valid @RequestBody DsThueBaoDongBoDto input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.GetDsThueBaoDongBo(input.getBrasID(), input.getDslamID(), input.getMaThueBao(), input.getTrangThaiID(),
					input.getPageSize(), input.getPageIndex()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Đếm số thuê bao đồng bộ")
	@RequestMapping(value = "/get-so-thuebao-dongbo", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> CountThueBaoDongBo(@Valid @RequestBody DemThueBaoDongBoDto input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(
					service.CountThueBaoDongBo(input.getBrasID(), input.getDslamID(), input.getMaThueBao(), input.getTrangThaiID())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Thêm đồng bộ port thuê bao")
	@RequestMapping(value = "/them-dongbo-port-thuebao", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> ThemDongBoPortTB(@Valid @RequestBody ThemDongBoPortDto input) {
		ApiResult rs = new ApiResult();
		try {
			int count = 0;
			for (DongBoPortDto item : input.getDsDongBoPort()) {
				if (service.ThemDongBoPortTB(item))
					count++;
			}
			rs.setData(new ResultOutput(count));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}
	
	@Operation(summary = "Xóa đồng bộ port thuê bao")
	@RequestMapping(value = "/xoa-dongbo-port-thuebao", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> XoaDongBoPortTB(@Valid @RequestBody DataKeyGenericInput<Number> input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(service.XoaDongBoPortTB(input.getId())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}
	
    @Operation(summary = "Xóa danh sách đồng bộ port thuê bao")
    @RequestMapping(value = "/xoa-ds-dongbo-port-thuebao", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> XoaDsDongBoPortTB(@Valid @RequestBody DataKeyGenericInput<Number[]> item) {
        ApiResult rs = new ApiResult();
        try {
			String msg = "";
			Number[] ids = item.getId();
			if (ids.length == 0)
				msg = "Danh sách đồng bộ không được để trống";
			else {
				for (Number id : ids) {
					if (id == null || id.intValue() < 1) {
						msg = "Danh sách đồng bộ có thông tin không hợp lệ";
						break;
					}
				}
			}
			if (msg.equals(""))
	            rs.setData(new ResultOutput(this.service.XoaDsDongBoPortTB(ids)));
			else
				rs.setDataInvalidMessage(msg);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
	
	@Operation(summary = "Hoàn thiện đồng bộ port thuê bao")
	@RequestMapping(value = "/hoanthien-dongbo-port-thuebao", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> HoanThienDongBoPortTB(@Valid @RequestBody DataKeyGenericInput<Number> input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(service.HoanThienDongBoPortTB(input.getId())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}
	
    @Operation(summary = "Hoàn thiện danh sách đồng bộ port thuê bao")
    @RequestMapping(value = "/hoanthien-ds-dongbo-port-thuebao", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> HoanThienDsDongBoPortTB(@Valid @RequestBody DataKeyGenericInput<Number[]> item) {
        ApiResult rs = new ApiResult();
        try {
			String msg = "";
			Number[] ids = item.getId();
			if (ids.length == 0)
				msg = "Danh sách đồng bộ không được để trống";
			else {
				for (Number id : ids) {
					if (id == null || id.intValue() < 1) {
						msg = "Danh sách đồng bộ có thông tin không hợp lệ";
						break;
					}
				}
			}
			if (msg.equals(""))
	            rs.setData(new ResultOutput(this.service.HoanThienDsDongBoPortTB(ids)));
			else
				rs.setDataInvalidMessage(msg);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
	
	@Operation(summary = "Kích hoạt đồng bộ port thuê bao")
	@RequestMapping(value = "/kichhoat-dongbo-port-thuebao", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> KichHoatDongBoPortTB(@Valid @RequestBody DataKeyGenericInput<Number> input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(new ResultOutput(service.KichHoatDongBoPortTB(input.getId())));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}
	
    @Operation(summary = "Kích hoạt danh sách đồng bộ port thuê bao")
    @RequestMapping(value = "/kichhoat-ds-dongbo-port-thuebao", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> KichHoatDsDongBoPortTB(@Valid @RequestBody DataKeyGenericInput<Number[]> item) {
        ApiResult rs = new ApiResult();
        try {
			String msg = "";
			Number[] ids = item.getId();
			if (ids.length == 0)
				msg = "Danh sách đồng bộ không được để trống";
			else {
				for (Number id : ids) {
					if (id == null || id.intValue() < 1) {
						msg = "Danh sách đồng bộ có thông tin không hợp lệ";
						break;
					}
				}
			}
			if (msg.equals(""))
	            rs.setData(new ResultOutput(this.service.KichHoatDsDongBoPortTB(ids)));
			else
				rs.setDataInvalidMessage(msg);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
	
	@Operation(summary = "Danh sách bras")
	@RequestMapping(value = "/get-ds-bras", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> GetDsBras() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.GetDsBras());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}
	
	@Operation(summary = "Danh sách dslam theo bras")
	@RequestMapping(value = "/get-ds-dslam-theo-bras", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> GetDsDslam(@Valid @RequestBody DataKeyGenericInput<Number> input) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.GetDsDslam(input.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}
	
}