package vn.vnptit.sapi.oneapp.models.vnface

import org.springframework.web.multipart.MultipartFile

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

class VnFaceUpdateAccountRequest {
    @NotBlank(message = "userCode khong duoc de trong")
    String userCode

    @NotBlank(message = "fullName khong duoc de trong")
    String fullName

    @Email(message = "email khong dung dinh dang")
    String email

    @Pattern(regexp = '^(MALE|FEMALE)$', message = "gender chi nhan MALE hoac FEMALE")
    String gender

    String phoneNumber
    MultipartFile imageFile
    String newUserCode
    List<String> groupCodes = []
    String accountChannels

    @Pattern(regexp = '^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}:\\d{2}$', message = "activeDate sai dinh dang dd/MM/yyyy HH:mm:ss")
    String activeDate

    @Pattern(regexp = '^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}:\\d{2}$', message = "expireDate sai dinh dang dd/MM/yyyy HH:mm:ss")
    String expireDate

    String accountReason
}
