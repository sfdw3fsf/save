package vn.vnpt.vnptit.ecms.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.EncryptionDtoInput;
import vn.vnpt.vnptit.ecms.service.EncryptionService;

@RestController
@RequestMapping("/encryption")
public class EncryptionController {
	private final EncryptionService encryptionService;

	public EncryptionController(EncryptionService encryptionService) {
		this.encryptionService = encryptionService;
	}
	
    @Operation(summary = "Mã hóa dựa trên thuật toán mã hóa phức hợp, có thể giải mã sử dụng hàm Decrypt. Sau khi mã hóa, sử dụng thuật toán encode Base64 để lưu dưới dạng Text")
    @RequestMapping(value = "/encrypt", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> encrypt(@Valid @RequestBody EncryptionDtoInput item) {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(encryptionService.encrypt(item.getContent()));
		} catch (Exception e) {
			rs.setDataInvalidMessage(e.getMessage());
		}
        return rs.getResponseEntity();
    }
	
    @Operation(summary = "decrypt")
    @RequestMapping(value = "/decrypt", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> decrypt(@Valid @RequestBody EncryptionDtoInput item) {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(encryptionService.decrypt(item.getContent()));
		} catch (Exception e) {
			rs.setDataInvalidMessage(e.getMessage());
		}
        return rs.getResponseEntity();
    }
}
