package vn.vnptit.sapi.oneapp.models.vnface

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

class VnFaceGetAccountListRequest {
    @Pattern(regexp = '^\\d{2}/\\d{2}/\\d{4}$', message = "startDate sai dinh dang dd/MM/yyyy")
    String startDate

    @Pattern(regexp = '^\\d{2}/\\d{2}/\\d{4}$', message = "endDate sai dinh dang dd/MM/yyyy")
    String endDate

    @NotNull(message = "page khong duoc de trong")
    @Min(value = 0L, message = "page phai >= 0")
    Integer page

    @NotNull(message = "maxSize khong duoc de trong")
    @Min(value = 1L, message = "maxSize phai >= 1")
    Integer maxSize

    String uuidGroup
    String keySearch
}
