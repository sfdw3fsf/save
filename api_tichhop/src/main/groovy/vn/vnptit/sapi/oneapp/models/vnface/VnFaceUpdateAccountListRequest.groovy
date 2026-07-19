package vn.vnptit.sapi.oneapp.models.vnface

import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

class VnFaceUpdateAccountListRequest {
    @NotEmpty(message = "externalAccounts khong duoc de trong")
    @Valid
    List<ExternalAccount> externalAccounts = []

    static class ExternalAccount {
        @NotBlank(message = "userCode khong duoc de trong")
        String userCode

        @NotBlank(message = "fullName khong duoc de trong")
        String fullName

        @NotBlank(message = "email khong duoc de trong")
        @Email(message = "email khong dung dinh dang")
        String email

        String gender
        String newUserCode
        String phoneNumber
        List<String> groupCodes = []

        @Valid
        List<AccountChannel> accountChannels = []
    }

    static class AccountChannel {
        String uuidChannelCategory

        @Min(value = 0L, message = "status chi nhan 0, 1, 2")
        @Max(value = 2L, message = "status chi nhan 0, 1, 2")
        Integer status

        @Min(value = 0L, message = "userOTT phai >= 0")
        Integer userOTT
    }
}