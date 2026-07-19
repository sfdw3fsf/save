package vn.vnptit.sapi.oneapp.resources

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.vnface.VnFaceAccessTokenRequest
import vn.vnptit.sapi.oneapp.models.vnface.VnFaceCheckinHistoryFilterRequest
import vn.vnptit.sapi.oneapp.models.vnface.VnFaceCreateAccountRequest
import vn.vnptit.sapi.oneapp.models.vnface.VnFaceDeleteAccountListRequest
import vn.vnptit.sapi.oneapp.models.vnface.VnFaceDeviceLicenseListRequest
import vn.vnptit.sapi.oneapp.models.vnface.VnFaceGetAccountListRequest
import vn.vnptit.sapi.oneapp.models.vnface.VnFaceLockAccountRequest
import vn.vnptit.sapi.oneapp.models.vnface.VnFaceNotifyAccountRequest
import vn.vnptit.sapi.oneapp.models.vnface.VnFaceUpdateAccountRequest
import vn.vnptit.sapi.oneapp.models.vnface.VnFaceUpdateAccountListRequest
import vn.vnptit.sapi.oneapp.service.vnface.VnFaceIntegrationService

import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("vnface")
class VnFaceService {

    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    VnFaceIntegrationService vnFaceIntegrationService

    @GetMapping("/checkin-history")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getCheckinHistory(
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "maxSize", defaultValue = "100") Integer maxSize,
            @RequestParam(value = "userCodes", required = false) String userCodes,
            @RequestParam(value = "filterMode", defaultValue = "1") Integer filterMode
    ) {
        try {
            VnFaceCheckinHistoryFilterRequest req = new VnFaceCheckinHistoryFilterRequest()
            req.startDate = startDate
            req.endDate = endDate
            req.page = page
            req.maxSize = maxSize
            req.userCodes = userCodes
            req.filterMode = filterMode

            def data = vnFaceIntegrationService.getCheckinHistory(req)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (IllegalArgumentException ex) {
            loggingManaged.error("[VnFaceService] getCheckinHistory validate error: " + ex.message)
            return new CommonResponse(errorCode: 1, faultCode: "VALIDATION", faultString: ex.message, data: null)
        } catch (IllegalStateException ex) {
            loggingManaged.error("[VnFaceService] getCheckinHistory vnFace error: " + ex.message)
            return new CommonResponse(errorCode: 1, faultCode: "HTTP_ERROR", faultString: ex.message, data: null)
        } catch (Exception ex) {
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @PutMapping("/account/list")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse updateAccountList(
            @Valid @RequestBody VnFaceUpdateAccountListRequest request,
            BindingResult bindingResult
    ) {
        try {
            if (bindingResult.hasErrors()) {
                String message = bindingResult.allErrors.collect { it.defaultMessage }.join("; ")
                return new CommonResponse(errorCode: 1, faultCode: "VALIDATION", faultString: message, data: null)
            }

            def data = vnFaceIntegrationService.updateEmployeeList(request)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (IllegalArgumentException ex) {
            loggingManaged.error("[VnFaceService] updateAccountList validate error: " + ex.message)
            return new CommonResponse(errorCode: 1, faultCode: "VALIDATION", faultString: ex.message, data: null)
        } catch (IllegalStateException ex) {
            loggingManaged.error("[VnFaceService] updateAccountList vnFace error: " + ex.message)
            return new CommonResponse(errorCode: 1, faultCode: "HTTP_ERROR", faultString: ex.message, data: null)
        } catch (Exception ex) {
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @PostMapping("/account")
    @Consumes([MediaType.MULTIPART_FORM_DATA])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse createAccount(
            @Valid @ModelAttribute VnFaceCreateAccountRequest request,
            BindingResult bindingResult
    ) {
        try {
            if (bindingResult.hasErrors()) {
                String message = bindingResult.allErrors.collect { it.defaultMessage }.join("; ")
                return new CommonResponse(errorCode: 1, faultCode: "VALIDATION", faultString: message, data: null)
            }

            def data = vnFaceIntegrationService.createEmployee(request)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (IllegalArgumentException ex) {
            loggingManaged.error("[VnFaceService] createAccount validate error: " + ex.message)
            return new CommonResponse(errorCode: 1, faultCode: "VALIDATION", faultString: ex.message, data: null)
        } catch (IllegalStateException ex) {
            loggingManaged.error("[VnFaceService] createAccount vnFace error: " + ex.message)
            return new CommonResponse(errorCode: 1, faultCode: "HTTP_ERROR", faultString: ex.message, data: null)
        } catch (Exception ex) {
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @DeleteMapping("/account/list")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse deleteAccountList(
            @Valid @RequestBody VnFaceDeleteAccountListRequest request,
            BindingResult bindingResult
    ) {
        try {
            if (bindingResult.hasErrors()) {
                String message = bindingResult.allErrors.collect { it.defaultMessage }.join("; ")
                return new CommonResponse(errorCode: 1, faultCode: "VALIDATION", faultString: message, data: null)
            }

            def data = vnFaceIntegrationService.deleteEmployeeList(request)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (IllegalArgumentException ex) {
            loggingManaged.error("[VnFaceService] deleteAccountList validate error: " + ex.message)
            return new CommonResponse(errorCode: 1, faultCode: "VALIDATION", faultString: ex.message, data: null)
        } catch (IllegalStateException ex) {
            loggingManaged.error("[VnFaceService] deleteAccountList vnFace error: " + ex.message)
            return new CommonResponse(errorCode: 1, faultCode: "HTTP_ERROR", faultString: ex.message, data: null)
        } catch (Exception ex) {
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @GetMapping("/account/list")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getAccountList(
            @Valid @ModelAttribute VnFaceGetAccountListRequest request,
            BindingResult bindingResult
    ) {
        try {
            if (bindingResult.hasErrors()) {
                String message = bindingResult.allErrors.collect { it.defaultMessage }.join("; ")
                return new CommonResponse(errorCode: 1, faultCode: "VALIDATION", faultString: message, data: null)
            }

            def data = vnFaceIntegrationService.getEmployeeList(request)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (IllegalArgumentException ex) {
            loggingManaged.error("[VnFaceService] getAccountList validate error: " + ex.message)
            return new CommonResponse(errorCode: 1, faultCode: "VALIDATION", faultString: ex.message, data: null)
        } catch (IllegalStateException ex) {
            loggingManaged.error("[VnFaceService] getAccountList vnFace error: " + ex.message)
            return new CommonResponse(errorCode: 1, faultCode: "HTTP_ERROR", faultString: ex.message, data: null)
        } catch (Exception ex) {
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @PostMapping("/notify/account")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse notifyAccount(
            @Valid @RequestBody VnFaceNotifyAccountRequest request,
            BindingResult bindingResult
    ) {
        try {
            if (bindingResult.hasErrors()) {
                String message = bindingResult.allErrors.collect { it.defaultMessage }.join("; ")
                return new CommonResponse(errorCode: 1, faultCode: "VALIDATION", faultString: message, data: null)
            }

            def data = vnFaceIntegrationService.notifyAccount(request)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (IllegalStateException ex) {
            loggingManaged.error("[VnFaceService] notifyAccount vnFace error: " + ex.message)
            return new CommonResponse(errorCode: 1, faultCode: "HTTP_ERROR", faultString: ex.message, data: null)
        } catch (Exception ex) {
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @PostMapping("/account/list")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse createAccountList(
            @Valid @RequestBody VnFaceUpdateAccountListRequest request,
            BindingResult bindingResult
    ) {
        try {
            if (bindingResult.hasErrors()) {
                String message = bindingResult.allErrors.collect { it.defaultMessage }.join("; ")
                return new CommonResponse(errorCode: 1, faultCode: "VALIDATION", faultString: message, data: null)
            }

            def data = vnFaceIntegrationService.createEmployeeList(request)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (IllegalStateException ex) {
            loggingManaged.error("[VnFaceService] createAccountList vnFace error: " + ex.message)
            return new CommonResponse(errorCode: 1, faultCode: "HTTP_ERROR", faultString: ex.message, data: null)
        } catch (Exception ex) {
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @PostMapping("/account/update")
    @Consumes([MediaType.MULTIPART_FORM_DATA])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse updateAccount(
            @Valid @ModelAttribute VnFaceUpdateAccountRequest request,
            BindingResult bindingResult
    ) {
        try {
            if (bindingResult.hasErrors()) {
                String message = bindingResult.allErrors.collect { it.defaultMessage }.join("; ")
                return new CommonResponse(errorCode: 1, faultCode: "VALIDATION", faultString: message, data: null)
            }

            def data = vnFaceIntegrationService.updateEmployee(request)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (IllegalStateException ex) {
            loggingManaged.error("[VnFaceService] updateAccount vnFace error: " + ex.message)
            return new CommonResponse(errorCode: 1, faultCode: "HTTP_ERROR", faultString: ex.message, data: null)
        } catch (Exception ex) {
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @PostMapping("/account/access-token")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getAccountAccessToken(
            @Valid @RequestBody VnFaceAccessTokenRequest request,
            BindingResult bindingResult
    ) {
        try {
            if (bindingResult.hasErrors()) {
                String message = bindingResult.allErrors.collect { it.defaultMessage }.join("; ")
                return new CommonResponse(errorCode: 1, faultCode: "VALIDATION", faultString: message, data: null)
            }

            def data = vnFaceIntegrationService.getAccountAccessToken(request)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (IllegalStateException ex) {
            loggingManaged.error("[VnFaceService] getAccountAccessToken vnFace error: " + ex.message)
            return new CommonResponse(errorCode: 1, faultCode: "HTTP_ERROR", faultString: ex.message, data: null)
        } catch (Exception ex) {
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @PostMapping("/device-license/list")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getDeviceLicenseList(
            @Valid @RequestBody VnFaceDeviceLicenseListRequest request,
            BindingResult bindingResult
    ) {
        try {
            if (bindingResult.hasErrors()) {
                String message = bindingResult.allErrors.collect { it.defaultMessage }.join("; ")
                return new CommonResponse(errorCode: 1, faultCode: "VALIDATION", faultString: message, data: null)
            }

            def data = vnFaceIntegrationService.getDeviceLicenseList(request)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (IllegalArgumentException ex) {
            loggingManaged.error("[VnFaceService] getDeviceLicenseList validate error: " + ex.message)
            return new CommonResponse(errorCode: 1, faultCode: "VALIDATION", faultString: ex.message, data: null)
        } catch (IllegalStateException ex) {
            loggingManaged.error("[VnFaceService] getDeviceLicenseList vnFace error: " + ex.message)
            return new CommonResponse(errorCode: 1, faultCode: "HTTP_ERROR", faultString: ex.message, data: null)
        } catch (Exception ex) {
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @GetMapping("/his-checkin/trans-id/{transId}")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getCheckinHistoryByTransId(
            @PathVariable("transId") String transId
    ) {
        try {
            def data = vnFaceIntegrationService.getCheckinHistoryByTransId(transId)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (IllegalArgumentException ex) {
            loggingManaged.error("[VnFaceService] getCheckinHistoryByTransId validate error: " + ex.message)
            return new CommonResponse(errorCode: 1, faultCode: "VALIDATION", faultString: ex.message, data: null)
        } catch (IllegalStateException ex) {
            loggingManaged.error("[VnFaceService] getCheckinHistoryByTransId vnFace error: " + ex.message)
            return new CommonResponse(errorCode: 1, faultCode: "HTTP_ERROR", faultString: ex.message, data: null)
        } catch (Exception ex) {
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @PutMapping("/account/lock")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse lockAccount(
            @Valid @RequestBody VnFaceLockAccountRequest request,
            BindingResult bindingResult
    ) {
        try {
            if (bindingResult.hasErrors()) {
                String message = bindingResult.allErrors.collect { it.defaultMessage }.join("; ")
                return new CommonResponse(errorCode: 1, faultCode: "VALIDATION", faultString: message, data: null)
            }

            def data = vnFaceIntegrationService.lockAccount(request)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (IllegalArgumentException ex) {
            loggingManaged.error("[VnFaceService] lockAccount validate error: " + ex.message)
            return new CommonResponse(errorCode: 1, faultCode: "VALIDATION", faultString: ex.message, data: null)
        } catch (IllegalStateException ex) {
            loggingManaged.error("[VnFaceService] lockAccount vnFace error: " + ex.message)
            return new CommonResponse(errorCode: 1, faultCode: "HTTP_ERROR", faultString: ex.message, data: null)
        } catch (Exception ex) {
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @GetMapping("/account/{userCode}")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getAccountDetail(
            @PathVariable("userCode") String userCode
    ) {
        try {
            def data = vnFaceIntegrationService.getAccountDetail(userCode)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (IllegalArgumentException ex) {
            loggingManaged.error("[VnFaceService] getAccountDetail validate error: " + ex.message)
            return new CommonResponse(errorCode: 1, faultCode: "VALIDATION", faultString: ex.message, data: null)
        } catch (IllegalStateException ex) {
            loggingManaged.error("[VnFaceService] getAccountDetail vnFace error: " + ex.message)
            return new CommonResponse(errorCode: 1, faultCode: "HTTP_ERROR", faultString: ex.message, data: null)
        } catch (Exception ex) {
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @GetMapping("/group")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getGroups() {
        try {
            def data = vnFaceIntegrationService.getGroups()
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (IllegalStateException ex) {
            loggingManaged.error("[VnFaceService] getGroups vnFace error: " + ex.message)
            return new CommonResponse(errorCode: 1, faultCode: "HTTP_ERROR", faultString: ex.message, data: null)
        } catch (Exception ex) {
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @GetMapping("/shifts")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getShifts() {
        try {
            def data = vnFaceIntegrationService.getShifts()
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (IllegalStateException ex) {
            loggingManaged.error("[VnFaceService] getShifts vnFace error: " + ex.message)
            return new CommonResponse(errorCode: 1, faultCode: "HTTP_ERROR", faultString: ex.message, data: null)
        } catch (Exception ex) {
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @GetMapping("/shifts/{uuidShift}")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getShiftDetail(
            @PathVariable("uuidShift") String uuidShift
    ) {
        try {
            def data = vnFaceIntegrationService.getShiftDetail(uuidShift)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (IllegalArgumentException ex) {
            loggingManaged.error("[VnFaceService] getShiftDetail validate error: " + ex.message)
            return new CommonResponse(errorCode: 1, faultCode: "VALIDATION", faultString: ex.message, data: null)
        } catch (IllegalStateException ex) {
            loggingManaged.error("[VnFaceService] getShiftDetail vnFace error: " + ex.message)
            return new CommonResponse(errorCode: 1, faultCode: "HTTP_ERROR", faultString: ex.message, data: null)
        } catch (Exception ex) {
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }

    @GetMapping("/his-checkin/{uuidHisCheckin}")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getCheckinHistoryByUuid(
            @PathVariable("uuidHisCheckin") String uuidHisCheckin
    ) {
        try {
            def data = vnFaceIntegrationService.getCheckinHistoryByUuid(uuidHisCheckin)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (IllegalArgumentException ex) {
            loggingManaged.error("[VnFaceService] getCheckinHistoryByUuid validate error: " + ex.message)
            return new CommonResponse(errorCode: 1, faultCode: "VALIDATION", faultString: ex.message, data: null)
        } catch (IllegalStateException ex) {
            loggingManaged.error("[VnFaceService] getCheckinHistoryByUuid vnFace error: " + ex.message)
            return new CommonResponse(errorCode: 1, faultCode: "HTTP_ERROR", faultString: ex.message, data: null)
        } catch (Exception ex) {
            return CommonResponse.resolveException(ex, loggingManaged)
        }
    }
}