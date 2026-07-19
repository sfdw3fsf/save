package vn.vnpt.vnptit.ecms.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.getThongTinTocDoADSLTheoDslamInput;
import vn.vnpt.vnptit.ecms.dto.idKieuInput;
import vn.vnpt.vnptit.ecms.dto.kiemTraChonPortInput;
import vn.vnpt.vnptit.ecms.service.ThongSoInternetService;

@RestController
@RequestMapping("thong-so-internet")
public class ThongSoInternetController {
	private final ThongSoInternetService service;

	public ThongSoInternetController(ThongSoInternetService service) {
		this.service = service;
	}

	@Operation(summary = "Kiểm tra chọn PORT")
	@RequestMapping(value = "/kiemtra_chon_port", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> kiemTraChonPort(@Valid @RequestBody kiemTraChonPortInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.kiemTraChonPort(item.getHdtbID(), item.getThueBaoID(), item.getPortID(), item.getVciVpiID()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy thông tin tốc độ ADSL")
	@RequestMapping(value = "/getThongTinTocDoADSL", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getThongTinTocDoADSL(@Valid @RequestBody idKieuInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.getThongTinTocDoADSL(item.getId(), item.getKieu()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy thông tin tốc độ ADSL theo DSLAM")
	@RequestMapping(value = "/getThongTinTocDoADSLTheoDslam", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getThongTinTocDoADSLTheoDslam(@Valid @RequestBody getThongTinTocDoADSLTheoDslamInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.getThongTinTocDoADSLTheoDslam(item.getId(), item.getKieu(), item.getDslamID()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy thông tin đối tượng thuê bao")
	@RequestMapping(value = "/getDoiTuongID", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getDoiTuongID(@Valid @RequestBody idKieuInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(service.getDoiTuongID(item.getId(), item.getKieu()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

}
