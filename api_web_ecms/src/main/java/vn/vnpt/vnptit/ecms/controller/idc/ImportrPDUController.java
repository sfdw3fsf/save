package vn.vnpt.vnptit.ecms.controller.idc;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.ImportrPDUDto;
import vn.vnpt.vnptit.ecms.service.hatang.ImportrPDUService;

@RestController
@RequestMapping("/idc/ImportrPDU")

public class ImportrPDUController {

    private final ImportrPDUService importrPDUService;

    public ImportrPDUController(ImportrPDUService service) {
        this.importrPDUService = service;
    }

    @Operation(summary = "Import rPDU")
    @RequestMapping(value = "/Import", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> importPDU(@RequestBody List<ImportrPDUDto> inputs) {
        ApiResult rs = new ApiResult();
        rs.setData(importrPDUService.importPDU(inputs));
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/dataValidate", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dataValidate(@RequestBody List<ImportrPDUDto> inputs) {
        ApiResult rs = new ApiResult();
        if (inputs == null || inputs.isEmpty()) {
            rs.setError("Danh sách kiểm tra rỗng");
            return rs.getResponseEntity();
        }
        rs.setData(importrPDUService.dataValidate(inputs));
        return rs.getResponseEntity();
    }

}
