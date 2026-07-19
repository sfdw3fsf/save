package vn.vnptit.sapi.oneapp.models.ivan

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical

@Canonical
@JsonIgnoreProperties(["metaClass"])
class A113 {
    String dhsxkd_ma_khach_hang
    String dhsxkd_ma_thue_bao
    String dhsxkd_ma_tinh
    String dhsxkd_loai_thue_bao_id
    String ma_don_vi
    String ma_so_thue
}
