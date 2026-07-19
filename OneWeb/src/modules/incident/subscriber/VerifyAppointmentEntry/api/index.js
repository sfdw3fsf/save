export default {
    
    Get_DS_Lydohen: (axios, data) => axios.post('/web-baohong/xacminh-nhaphen/sp_lydohen_bh', data),
    Lay_thongbao_xacminh_hen: (axios, data) => axios.post('/web-baohong/xacminh-nhaphen/lay_thongbao_xacminh_hen', data),
    Lay_ds_hen_baohong_xm: (axios, data) => axios.post('/web-baohong/xacminh-nhaphen/lay_ds_hen_baohong_xm', data),
    Kiemtra_phieu_xacminh_henkh: (axios, data) => axios.post('/web-baohong/xacminh-nhaphen/sp_kiemtra_phieu_xacminh_henkh', data),
    Map_id: async (axios, data,header) => axios.post('/web-quantri/thamso/map_id', data,header),
    Huy_giuphieu_xacminh_henkh_v2: (axios, data) => axios.post('/web-baohong/xacminh-nhaphen/sp_huy_giuphieu_xacminh_henkh_v2', data),
    Capnhat_xacminh_hen_baohong_v3: (axios, data) => axios.post('/web-baohong/xacminh-nhaphen/capnhat_xacminh_hen_baohong_v3', data),
    Send_sms_ld: (axios, data) => axios.post('/web-baohong/xacminh-nhaphen/send_sms_ld', data),
    Huy_giuphieu_xacminh_henkh: (axios, data) => axios.post('/web-baohong/xacminh-nhaphen/sp_huy_giuphieu_xacminh_henkh', data),
    fn_tt_baohong: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_baohong', data),
    fn_tt_khieunai: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_khieunai', data),
    lay_ds_hen_xacminh_hopdong: (axios, data) => axios.get(`web-thicong/tienhtrinh_tcbh/lay_ds_hen_xacminh_hopdong?vtrangthai=${data.vtrangthai}&vdonvi_id=${data.vdonvi_id}`,data),
    giuphieu_xacminh_hen_ld: (axios, data) => axios.post('/web-baohong/xacminh-nhaphen/giuphieu_xacminh_hen_ld', data),
    capnhat_xacminh_hen_lapdat: (axios, data) => axios.post('/web-thicong/tienhtrinh_tcbh/capnhat_xacminh_hen_lapdat', data),
    
}
  