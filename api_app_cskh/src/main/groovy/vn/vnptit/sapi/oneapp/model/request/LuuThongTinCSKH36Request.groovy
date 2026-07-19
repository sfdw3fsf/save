package vn.vnptit.sapi.oneapp.model.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.validation.annotation.Validated

import javax.annotation.Generated

/**
 * Lưu thông tin CSKH 36 tháng
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
@JsonIgnoreProperties(value = ["metaClass"])
class LuuThongTinCSKH36Request {
    long phieutn_id
    long khachhang_id
    long thuebao_id
    int hinhthuc_id
    int chatluongdv_id
    int is_baohong
    int is_baoduong
    PHIEU36TOBJ obj
}

@JsonIgnoreProperties(value = ["metaClass"])
class PHIEU36TOBJ {
    DS_APP[] ds_app
    NGUOI_DAIDIEN daidien
    NGUOI_KETOAN ketoan
    NGUOI_IT it
    INFOR_LH lh

}

@JsonIgnoreProperties(value = ["metaClass"])
class THONGTIN_LH {
    String ten_lh
    String dienthoai
    String email
    long ttlh_id
}

@JsonIgnoreProperties(value = ["metaClass"])
class LOAI_LH {
    Long db_id
    Long ttkh_id
    boolean ischeck
}

@JsonIgnoreProperties(value = ["metaClass"])
class MUCDICH_LH {
    int mucdich_id
    boolean ischeck
}

@JsonIgnoreProperties(value = ["metaClass"])
class MANGXH_LH {
    int loaimxh_id
    MUCDICH_LH[] lst_mucdich
    String nickname
}

@JsonIgnoreProperties(value = ["metaClass"])
class INFOR_LH {
    THONGTIN_LH thongtin
    LOAI_LH[] loai
    MANGXH_LH[] mxh
    MUCDICH_LH[] lst_email
}

@JsonIgnoreProperties(value = ["metaClass"])
class APP {
    int id
}

@JsonIgnoreProperties(value = ["metaClass"])
class DS_APP {
    APP app
    String so_dk
}

@JsonIgnoreProperties(value = ["metaClass"])
class NGUOI_DAIDIEN {
    String hoten
    String ngaysinh
    String sodt
    String email
}

@JsonIgnoreProperties(value = ["metaClass"])
class NGUOI_KETOAN {
    String hoten
    String sodt
    String email
}

@JsonIgnoreProperties(value = ["metaClass"])
class NGUOI_IT {
    String hoten
    String sodt
    String email
}

class DS_DB {
    long DB_ID
    long TTKH_ID
}

class DS_MXH {
    long RLH_ID
    long LOAIMXH_ID
    String MANG_XH
}

class DS_MD {
    long RLH_ID
    long MUCDICH_ID
}

class DS_MD_EMAIL {
    long MUCDICH_ID
}

class DS_SDT {
    String SO_DT
    int LOAI_APP
}
