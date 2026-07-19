package vn.vnptit.sapi.oneapp.models.fmis

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

class FXML_CTVT_NH_REQ {
    String b_ma_dvi
    String b_ma_nsd
    String b_pass
    String id_ct_nh
    List dataChungTu
    List dataCT_ChungTu
    String keyCall
}

class FXML_CTVT_NH1_REQ{
    String b_ma_dvi
    String b_ma_nsd
    String b_pass
    String id_ct_nh
    List dataChungTu
    List dataCT_ChungTu
    List dataFlie
    String keyCall
}

class FXML_CTVT_XOA_REQ {
    String b_ma_dvi
    String b_ma_nsd
    String b_pass
    String b_so_id
}

class FS_KTDH_DHSXKD_TTGD_XOA_REQ {
    String user
    String pas
    String ma_dvi
    String nsd
    String mat_khau
    String data
}

class FS_KTDH_DHSXKD_REQ {
    String user
    String pas
    String ma_dvi
    String nsd
    String mat_khau
    String id_phieu
    String so_phieu
    String ngay_ht
    String loai
    String noi_dung
    String nguoi_lap
    String email
    String so_phieu_dh
    List dataCT_ChungTu
}

class FS_BSS_KTDH_DHSXKD_REQ{
    String user
    String pas
    String ma_dvi
    String nsd
    String mat_khau
    String id_phieu
    String so_phieu
    String ngay_ht
    String loai
    String noi_dung
    String nguoi_lap
    String email
    String so_phieu_dh
    List data_chitiet
    List data_file
}