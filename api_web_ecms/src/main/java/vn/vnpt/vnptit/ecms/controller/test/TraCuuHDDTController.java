package vn.vnpt.vnptit.ecms.controller.test;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.config.MiddlewarePropeties;
import vn.vnpt.vnptit.ecms.dto.test.TraCuuHDDTPaginationInput;
import vn.vnpt.vnptit.ecms.service.client.ExternalApiFactory;
import vn.vnpt.vnptit.ecms.service.test.TraCuuHDDTService;

import java.net.*;
import java.util.*;

@RestController
@RequestMapping("/test/tracuu-hddt")
public class TraCuuHDDTController {

	@Autowired
	private TraCuuHDDTService traCuuHDDTService;
	@Autowired
	private ExternalApiFactory factory;

	@Operation(summary = "Lấy danh sách tiêu chí tìm kiếm")
	@RequestMapping(value = "/lay-ds-tieuchi", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> lay_ds_tieuchi_timkiem() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(traCuuHDDTService.lay_ds_tieuchi_timkiem());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "lấy danh sách hóa đơn tìm kiếm đã phân trang")
	@RequestMapping(value = "/lay-ds-hoadon-paging", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> lay_ds_hoadon_paging(@Valid @RequestBody TraCuuHDDTPaginationInput item) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(traCuuHDDTService.lay_ds_hoadon_paging(item.getTieuchi(), item.getNoidung(), item.getPageIndex(),
					item.getPageSize()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@GetMapping("/api-ecs")
	public Object testApiECS() {
		Object result = factory.getClient(MiddlewarePropeties.SystemType.ECS).call(
				"/object/namespaces",
				HttpMethod.GET,
				null, Object.class);
		return result;
	}
	@GetMapping("/api-vcenter")
	public Object testApiVcenter() {
		Object result = factory.getClient(MiddlewarePropeties.SystemType.VCENTER).call(
				"/datastores",
				HttpMethod.GET,
				null, Object.class);
		return result;
	}
	@GetMapping("/api-smartcloud")
	public Object testApiSmartCloud() {
		Object result = factory.getClient(MiddlewarePropeties.SystemType.SMARTCLOUD_EXTERNAL).call(
				"/subscription/vpcs?regionCode=region_hn4",
				HttpMethod.GET,
				null, Object.class);
		return result;
	}


}
