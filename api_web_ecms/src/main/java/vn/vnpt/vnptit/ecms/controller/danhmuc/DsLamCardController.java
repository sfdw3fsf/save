package vn.vnpt.vnptit.ecms.controller.danhmuc;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ModelValidation;
import vn.vnpt.vnptit.ecms.dto.DslamCardDtoIn;
import vn.vnpt.vnptit.ecms.service.danhmuc.DsLamCardService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("danhmuc/dslam-card")
public class DsLamCardController {

	private final DsLamCardService dsLamCardService;

	public DsLamCardController(DsLamCardService dsLamCardService) {
		this.dsLamCardService = dsLamCardService;
	}

	@Operation(summary = "Liệt kê danh sách DSLAM card")
	@RequestMapping(value = "/danh-sach", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> lietKe() {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamCardService.lietKe(AppRequestContext.getCurrent().getPhanVungID()));
		}
		catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Liệt kê danh sách DSLAM card theo ID")
	@RequestMapping(value = "/chi-tiet/{id}", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> chiTiet(@PathVariable("id") long id) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamCardService.chiTiet(AppRequestContext.getCurrent().getPhanVungID(), id));
		}
		catch (AppSqlException e) {
			// TODO Auto-generated catch block
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Thêm mới DSLAM card")
	@RequestMapping(value = "/them", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> them(@RequestBody @Valid DslamCardDtoIn dslamCardDtoIn) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamCardService.them(dslamCardDtoIn));
		}
		catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Cập nhật DSLAM card")
	@RequestMapping(value = "/cap-nhat", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> capNhat(@RequestBody @Valid DslamCardDtoIn.DslamCardDtoUpd loaiDSlamDtoIn) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamCardService.capNhat(loaiDSlamDtoIn));
		}
		catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Xóa DSLAM card")
	@RequestMapping(value = "/xoa/{id}", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> xoa(@PathVariable("id") long id) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamCardService.xoa(AppRequestContext.getCurrent().getPhanVungID(), id));
		}
		catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy port DSLAM card")
	@RequestMapping(value = "/get-port/{cardDslId}", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> getPort(@PathVariable("cardDslId") @Valid @Max(999999999999999l)
	@Positive(message = ModelValidation.POSITIVE_OR_ZERO)
	@NotNull(message = ModelValidation.NOT_NULL) Long cardDslId) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamCardService.getPort(AppRequestContext.getCurrent().getPhanVungID(), cardDslId));
		}
		catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy nhóm card theo loại tbi")
	@RequestMapping(value = "/get-nhomcard-by-loaitb_id/{id}", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> getNhomCardByLoaitbId(@PathVariable int id) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamCardService.getNhomCardByLoaitbId(AppRequestContext.getCurrent().getPhanVungID(), id));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy loại card theo id")
	@RequestMapping(value = "/get-loaicard-by-id/{id}", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> getLoaiCardById(@PathVariable int id) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamCardService.getLoaiCardById(AppRequestContext.getCurrent().getPhanVungID(), id));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy loại card theo nhóm card id")
	@RequestMapping(value = "/get-loaicard-by-nhomcardid", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> getLoaiCardByNhomCardId(@RequestParam("nhomcard_id") int nhomcard_id, @RequestParam("loaitb_id") int loaitb_id) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamCardService.getLoaiCardByNhomCardId(AppRequestContext.getCurrent().getPhanVungID(), nhomcard_id, loaitb_id));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy port carddslam đã sử dụng")
	@RequestMapping(value = "/get-port-carddslam-sudung", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> getPortCardDslamSuDung(@RequestParam("carddsl_id") int carddsl_id) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamCardService.getPortCardDslamSuDung(AppRequestContext.getCurrent().getPhanVungID(), carddsl_id));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy port DSLAM card")
	@RequestMapping(value = "/lay-port-theo-card/{cardDslId}", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> layPortTheoCard(@PathVariable("cardDslId") @Valid
											 @Positive(message = ModelValidation.POSITIVE_OR_ZERO)
											 @NotNull(message = ModelValidation.NOT_NULL) Long cardDslId) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamCardService.layPortTheoCard(cardDslId));
		}
		catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Tạo dữ liệu visa theo cardDslId")
	@RequestMapping(value = "/tao-dulieu-visa/{cardDslId}", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> taoDuLieuVisa(@PathVariable("cardDslId") @Valid
													 @Positive(message = ModelValidation.POSITIVE_OR_ZERO)
													 @NotNull(message = ModelValidation.NOT_NULL) Long cardDslId) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamCardService.taoDuLieuVisa(cardDslId));
		}
		catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}

	@Operation(summary = "Tạo port dsl card")
	@RequestMapping(value = "/tao-port-dslamcard/{cardDslId}", method = RequestMethod.GET)
	public ResponseEntity<ApiResult> taoPortDslCard(@PathVariable("cardDslId") @Valid
												   @Positive(message = ModelValidation.POSITIVE_OR_ZERO)
												   @NotNull(message = ModelValidation.NOT_NULL) Long cardDslId) {
		ApiResult rs = new ApiResult();
		try {
			rs.setData(dsLamCardService.taoPortDslCard(cardDslId));
		}
		catch (AppSqlException e) {
			rs.setException(e);
		}
		return rs.getResponseEntity();
	}
}
