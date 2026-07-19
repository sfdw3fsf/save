package vn.vnptit.sapi.oneapp.model.response
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class PhieuPhoiHopKHDN {
    String KHIEUNAI_TINH_ID
String PHIEU_TINH_ID
String HUONGGIAO_ID
String DICHVUVT_ID
String MA_TB
String LOAITB_ID
String DICHVU
String LOAIHINH
String DIACHI_TB
String DIACHI_LD
String DOITUONG_TB
String NGAY_BN
String TEN_KH
String DIACHI_KH
String SO_DT_KN
String LOAI_KH
String NGUOIGIAO
String NGAYGIAO
String DV_GIAO
String MA_KH
String SO_DT_GIAO
String NGUOI_KN
String VIP_ID
String NET_ID
String MEDIA_ID
String VIP_NET
String TINH_ID
String TINH_LD
String ND_GIAO
String TTPH_ID
String THUEBAO_ID
String LOG_GIAO
String TEN_TB
String TINHTRANG_KN
String ND_KHIEUNAI
String NGAY_TN
String CONGVAN_XL
String NGUYENNHAN_KN
String MA_LT
String THOIGIAN_CK
String HENGQ_TU
String HENGQ_DEN
String NGAY_SD
String GHICHU_KN
String GIAO_VIP
String MA_KN
String TENCHUDE
String NHANVIEN_NHAN
}
