package vn.vnpt.vnptit.ecms.controller.hatang;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.service.hatang.CommonService;

@RestController
@RequestMapping("hatang/common")
public class CommonController {
	private final CommonService service;

	public CommonController(CommonService service) {
		this.service = service;
	}
	
    @Operation(summary = "Lấy tất cả phân loại cổng")
    @RequestMapping(value = "/get-ds-phanloai-cong", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDsPhanLoaiCong() {
        ApiResult rs = new ApiResult();
        rs.setData(this.service.getDsPhanLoaiCong());
        return rs.getResponseEntity();
    }
	
    @Operation(summary = "Lấy tất cả nhóm cổng")
    @RequestMapping(value = "/get-ds-nhomcong", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDsNhomCong() {
        ApiResult rs = new ApiResult();
        rs.setData(this.service.getDsNhomCong());
        return rs.getResponseEntity();
    }
}
