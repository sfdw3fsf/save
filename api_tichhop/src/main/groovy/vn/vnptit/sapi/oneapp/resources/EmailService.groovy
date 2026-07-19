package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.ep.SendEmail
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.email.Email

import vn.vnptit.sapi.oneapp.models.email.VerifyEmail
import vn.vnptit.sapi.oneapp.util.Utils

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("email")
class EmailService {

    @Autowired
    LoggingManaged loggingManaged
    @Autowired
    SendEmail sendEmail
    @Value("\${spring.httpservices.verifyemail.base-url}")
    String baseUrl

    @Autowired
    EmailService(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper
        this.request = request
    }

    private final HttpServletRequest request
    private final ObjectMapper objectMapper

    @Operation(summary = "Gửi Email", description = "Gửi Email từ hệ thống")
    @PostMapping("/send")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse sendEmail(@RequestBody Email body) {
        try {
            String message
            if (body.getFile() != null && body.getFile().length() > 0) {
                message = sendEmail.sendMailAttachFile(body.getSubject(), body.getMessage(), body.getMailTo(), body.getMailCc(), body.getMailBcc(), body.getFile(), body.getFileName(), body.getContentType())
            } else {
                message = sendEmail.sendMailNotFile(body.getSubject(), body.getMessage(), body.getMailTo(), body.getMailCc(), body.getMailBcc())
            }
            if (message != null && message == "") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Thành công")
            } else {
                return new CommonResponse(errorCode: -1, faultCode: "", faultString: message)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @Operation(summary = "Gửi Email", description = "Gửi Email từ host port param")
    @PostMapping("/send-email")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse sendEmailUsingHostPortParam(@RequestBody Email body) {
        try {
            String message
            if (body.getFile() != null && body.getFile().length() > 0) {
                message = sendEmail.sendMailAttachFile(body.getSubject(), body.getMessage(), body.getMailTo(), body.
                        getMailCc(), body.getMailBcc(), body.getFile(), body.getFileName(), body.getContentType(),
                        body.getMailServerHost(),body.getMailServerPort(), body.getUsername(), body.getPassword(), body.getMailFrom())
            } else {
                message = sendEmail.sendMailNotFile(body.getSubject(), body.getMessage(), body.getMailTo(), body.getMailCc(), body.getMailBcc(),
                        body.getMailServerHost(), body.getMailServerPort(), body.getUsername(), body.getPassword(), body.getMailFrom())
            }
            if (message != null && message == "") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Thành công")
            } else {
                return new CommonResponse(errorCode: -1, faultCode: "", faultString: message)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @Operation(summary = "Gửi Email", description = "Gửi Email từ host port param")
    @PostMapping("/send-email-v2")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse sendEmailUsingHostPortParamV2(@RequestBody Email body) {
        try {
            String message
            message = sendEmail.sendMailHTML(body.getSubject(), body.getMessage(), body.getMailTo(), body.getMailCc(), body.getMailBcc(),
                    body.getMailServerHost(), body.getMailServerPort(), body.getUsername(), body.getPassword(), body.getMailFrom())
            if (message != null && message == "") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Thành công")
            } else {
                return new CommonResponse(errorCode: -1, faultCode: "", faultString: message)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @Operation(summary = "Xác thực Email", description = "Xác thực Email")
    @PostMapping("/verify")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse verify(@RequestBody VerifyEmail body) {
        try {
            String message
            if (body.getEmail() == null || body.getEmail().toString().length() == 0) {
                new CommonResponse(errorCode: -1, faultCode: "", faultString: "Không có địa chỉ Email!")
            } else if (!Utils.isEmail(body.getEmail())) {
                new CommonResponse(errorCode: -1, faultCode: "", faultString: "Email không đúng định dạng!")
            } else if (body.getType() == null || body.getType().toString().length() == 0) {
                new CommonResponse(errorCode: -1, faultCode: "", faultString: "Không xác định được kiểu xác thực!")
            }
            UUID uuid = UUID.randomUUID()
            String content = baseUrl + "/tichhop/email/active?key=" + uuid.toString() + "&email=" + body.getEmail() + "&type=" + body.getType()
            message = sendEmail.sendMailNotFile("", content, body.getEmail(), null, null)
            if (message != null && message == "") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Thành công")
            } else {
                return new CommonResponse(errorCode: -1, faultCode: "", faultString: message)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @Operation(summary = "Active Email", description = "Active Email")
    @GetMapping("/active")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse active(@NotNull @Parameter(in = ParameterIn.QUERY, description = "key", required = true, schema = @Schema()) @Valid @RequestParam(value = "key", required = true) String key,
                          @NotNull @Parameter(in = ParameterIn.QUERY, description = "email", required = true, schema = @Schema()) @Valid @RequestParam(value = "email", required = true) String email,
                          @NotNull @Parameter(in = ParameterIn.QUERY, description = "type", required = true, schema = @Schema()) @Valid @RequestParam(value = "type", required = true) String type) {
        try {
            String message
            if (email == null || email.toString().length() == 0) {
                new CommonResponse(errorCode: -1, faultCode: "", faultString: "Không có địa chỉ Email!")
            } else if (!Utils.isEmail(email)) {
                new CommonResponse(errorCode: -1, faultCode: "", faultString: "Email không đúng định dạng!")
            }
            if (key == null || key.toString().length() == 0) {
                new CommonResponse(errorCode: -1, faultCode: "", faultString: "Không thể xác thực Email!")
            }
            if (type == null || type.toString().length() == 0) {
                new CommonResponse(errorCode: -1, faultCode: "", faultString: "Không xác định được kiểu xác thực!")
            }
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Thành công")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }
}
