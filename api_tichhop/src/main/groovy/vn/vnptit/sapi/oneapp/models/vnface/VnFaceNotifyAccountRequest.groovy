package vn.vnptit.sapi.oneapp.models.vnface

import javax.validation.constraints.AssertTrue
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

class VnFaceNotifyAccountRequest {
    @NotEmpty(message = "userCodes khong duoc de trong")
    List<String> userCodes = []

    @NotEmpty(message = "channels khong duoc de trong")
    List<String> channels = []

    @NotBlank(message = "content khong duoc de trong")
    String content

    @AssertTrue(message = "userCodes khong duoc chua gia tri rong")
    boolean isUserCodesValid() {
        if (userCodes == null || userCodes.isEmpty()) {
            return true
        }
        return userCodes.every { it != null && !it.trim().isEmpty() }
    }

    @AssertTrue(message = "channels chi nhan telegram, viber, zalo, facebook")
    boolean isChannelsValid() {
        if (channels == null || channels.isEmpty()) {
            return true
        }
        Set<String> allowed = ["telegram", "viber", "zalo", "facebook"] as Set
        return channels.every { it != null && allowed.contains(it.trim().toLowerCase()) }
    }
}
