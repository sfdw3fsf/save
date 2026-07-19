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
import vn.vnpt.vnptit.ecms.dto.WebRegistry.GetValueInput;
import vn.vnpt.vnptit.ecms.dto.WebRegistry.SetValueInput;
import vn.vnpt.vnptit.ecms.service.WebRegistryService;

@RestController
@RequestMapping("/webRegistry")
public class WebRegistryController {

    private final WebRegistryService webRegistryService;

    public WebRegistryController(WebRegistryService webRegistryService) {
        this.webRegistryService = webRegistryService;
    }
    
	@Operation(summary = "Lấy giá trị của một registry")
	@RequestMapping(value = "/getValue", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> getValue(@Valid @RequestBody GetValueInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(webRegistryService.getValue(input.getKeyName()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
	}

	@Operation(summary = "Gán giá trị cho một registry")
	@RequestMapping(value = "/setValue", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseEntity<ApiResult> setValue(@Valid @RequestBody SetValueInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(webRegistryService.setValue(input.getKeyName(), input.getValue()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
	}
}