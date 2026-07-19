package vn.vnpt.vnptit.qlsc.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.qlsc.dto.StorageInput;
import vn.vnpt.vnptit.qlsc.service.StorageService;

@RestController
@RequestMapping("/storage")
public class StorageController {
	private final StorageService storageService;

	public StorageController(StorageService storageService) {
		this.storageService = storageService;
	}

	@Operation(summary = "Đăng tải tập tin")
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> uploadFile(@RequestParam MultipartFile file) {
		ApiResult rs = new ApiResult();
		try {
			if(!file.isEmpty()) {
				rs.setData(storageService.uploadFile(file.getOriginalFilename(), file.getBytes()));
			}
			else
				rs.setDataInvalidMessage("Không có tệp tin đính kèm");
		} catch (IOException e) {
			rs.setMessageDetail(e.getMessage());
		}
		return rs.getResponseEntity();
	}

////	@Operation(summary = "Xem hình ảnh")
//	@RequestMapping(value = "/viewImage", method = RequestMethod.GET, produces = { MediaType.IMAGE_JPEG_VALUE,
//			MediaType.IMAGE_PNG_VALUE })
//	public @ResponseBody byte[] viewImage(@RequestParam String imageSource) {
//		return storageService.getFile(imageSource);
//	}

	@Operation(summary = "Tải tập tin")
	@RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
	public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam(required = true) String fileSource) {
		byte[] data = storageService.getFile(fileSource);
		ByteArrayResource resource = new ByteArrayResource(data);
		return ResponseEntity.ok().contentLength(resource.contentLength())
				.header("Content-type", "application/octet-stream")
				.header("Content-disposition", "attachment; filename=\"" + fileSource + "\"").body(resource);
	}

	@Operation(summary = "Xóa tập tin")
	@RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> deleteFile(@Valid @RequestBody StorageInput item) {
		ApiResult rs = new ApiResult();
		rs.setData(storageService.deleteFile(item.getFileSource()));
		return rs.getResponseEntity();
	}

	@Operation(summary = "Kiểm tra tồn tại của tập tin")
	@RequestMapping(value = "/checkExistsFile", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> checkExistsFile(@Valid @RequestBody StorageInput item) {
		ApiResult rs = new ApiResult();
		rs.setData(new ResultOutput(storageService.checkExistsFile(item.getFileSource())));
		return rs.getResponseEntity();
	}

	@Operation(summary = "Lấy đường dẫn đã xác thực")
	@RequestMapping(value = "/getPresignedUrl", method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getPresignedUrl(@Valid @RequestBody StorageInput item) {
		ApiResult rs = new ApiResult();
		rs.setData(new ResultOutput(storageService.getPresignedUrl(item.getFileSource())));
		return rs.getResponseEntity();
	}

}