package vn.vnptit.sapi.oneapp.models.vnface

import javax.validation.constraints.AssertTrue
import javax.validation.constraints.NotEmpty

class VnFaceDeviceLicenseListRequest {
    @NotEmpty(message = "serialNumbers khong duoc de trong")
    List<String> serialNumbers = []

    @AssertTrue(message = "serialNumbers khong duoc chua gia tri rong")
    boolean isSerialNumbersValid() {
        if (serialNumbers == null || serialNumbers.isEmpty()) {
            return true
        }
        return serialNumbers.every { it != null && !it.trim().isEmpty() }
    }
}
