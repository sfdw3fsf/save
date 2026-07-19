package vn.vnptit.sapi.oneapp.models.vnface

import javax.validation.constraints.AssertTrue
import javax.validation.constraints.NotEmpty

class VnFaceDeleteAccountListRequest {
    @NotEmpty(message = "userCodes khong duoc de trong")
    List<String> userCodes = []

    @AssertTrue(message = "userCodes khong duoc chua gia tri rong")
    boolean isUserCodesValid() {
        if (userCodes == null || userCodes.isEmpty()) {
            return true
        }
        return userCodes.every { it != null && !it.trim().isEmpty() }
    }
}
