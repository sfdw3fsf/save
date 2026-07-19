package vn.vnptit.sapi.oneapp.models.vnface

import javax.validation.constraints.AssertTrue
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class VnFaceLockAccountRequest {
    @NotBlank(message = "userCode khong duoc de trong")
    String userCode

    @NotNull(message = "locked khong duoc de trong")
    Integer locked

    String lockedReason

    @AssertTrue(message = "locked chi chap nhan 0 hoac 1")
    boolean isLockedValid() {
        if (locked == null) {
            return true
        }
        return locked == 0 || locked == 1
    }
}
