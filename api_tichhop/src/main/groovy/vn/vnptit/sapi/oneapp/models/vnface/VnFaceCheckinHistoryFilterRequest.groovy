package vn.vnptit.sapi.oneapp.models.vnface

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

class VnFaceCheckinHistoryFilterRequest {
    @NotBlank(message = "startDate khong duoc de trong")
    @Pattern(regexp = '^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}:\\d{2}$', message = "startDate dung dinh dang dd/MM/yyyy HH:mm:ss")
    String startDate

    @NotBlank(message = "endDate khong duoc de trong")
    @Pattern(regexp = '^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}:\\d{2}$', message = "endDate dung dinh dang dd/MM/yyyy HH:mm:ss")
    String endDate

    @NotNull(message = "page khong duoc null")
    @Min(value = 0L, message = "page phai >= 0")
    Integer page

    @NotNull(message = "maxSize khong duoc null")
    @Min(value = 1L, message = "maxSize phai > 0")
    Integer maxSize

    String userCodes

    @NotNull(message = "filterMode khong duoc null")
    @Min(value = 1L, message = "filterMode chi nhan 1 hoac 2")
    @Max(value = 2L, message = "filterMode chi nhan 1 hoac 2")
    Integer filterMode
}