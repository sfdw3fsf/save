package vn.vnptit.sapi.oneapp.models.vnface

import javax.validation.constraints.NotBlank

class VnFaceAccessTokenRequest {
    @NotBlank(message = "userCode khong duoc de trong")
    String userCode
}
