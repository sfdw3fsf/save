package vn.vnptit.sapi.oneapp.models.vnface

import org.springframework.web.multipart.MultipartFile

import javax.validation.constraints.AssertTrue
import javax.validation.constraints.Email
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

class VnFaceCreateAccountRequest {
    @NotBlank(message = "userCode khong duoc de trong")
    String userCode

    @NotBlank(message = "fullName khong duoc de trong")
    String fullName

    @NotBlank(message = "email khong duoc de trong")
    @Email(message = "email khong dung dinh dang")
    String email

    @Pattern(regexp = '^(MALE|FEMALE)$', message = "gender chi nhan MALE hoac FEMALE")
    String gender

    String phoneNumber
    MultipartFile imageFile
    List<String> groupCodes = []
    String accountChannels
    String password

    @Min(value = 0L, message = "type chi nhan 0 hoac 5")
    @Max(value = 5L, message = "type chi nhan 0 hoac 5")
    Integer type = 0

    @Pattern(regexp = '^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}:\\d{2}$', message = "activeDate sai dinh dang dd/MM/yyyy HH:mm:ss")
    String activeDate

    @Pattern(regexp = '^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}:\\d{2}$', message = "expireDate sai dinh dang dd/MM/yyyy HH:mm:ss")
    String expireDate
    String accountReason

    @AssertTrue(message = "type chi nhan 0 hoac 5")
    boolean isTypeAllowed() {
        return type == null || type == 0 || type == 5
    }

    @AssertTrue(message = "activeDate, expireDate, accountReason khong duoc de trong khi type = 5")
    boolean isGuestFieldsValid() {
        if (type == null || type != 5) {
            return true
        }
        return hasText(activeDate) && hasText(expireDate) && hasText(accountReason)
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty()
    }
}
