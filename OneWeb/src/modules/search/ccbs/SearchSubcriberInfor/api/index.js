export default {
    //Lấy danh mục
    getDanhmuc: (axios, data) => axios.post('/web-thicong/tracnghiem-atvsld/danhmuc', data),
    getUserNeo: (axios) => axios.get('/web-thuno/api/thu-no/common/user-neo'),
    //web-hopdong/tracuu-tt-thuebao/sp_lay_tt_nam
    sp_lay_tt_nam: (axios) => axios.get('web-hopdong/tracuu-tt-thuebao/sp_lay_tt_nam'),
    ///tracuu/ts_tracuu_laytt_tb
    ts_tracuu_laytt_tb: (axios, data) => axios.post('/ccbs/tracuu/ts_tracuu_laytt_tb', data),
    //app_tracuu_ls_dichvu3g
    app_tracuu_ls_dichvu3g: (axios, data) => axios.post('/ccbs/tracuu/app_tracuu_ls_dichvu3g', data),
    ///app_tracuu_cuocnong
    ts_tracuu_cuocnong: (axios, data) => axios.post('/ccbs/executor/ts_tracuu_cuocnong', data),    

    tracuu_ls_tb: (axios, data) => axios.post('/ccbs/executor/tracuu_ls_tb', data),  
/*
ccbs/executor/tracuu_ls_tb
{
  "page_num": "1",
  "so_tb": "84918486486",
  "page_rec": "100"
}
*/
    ts_tracuu_laytt_tb_dv: (axios, data) => axios.post('/ccbs/executor/ts_tracuu_laytt_tb_dv', data),  
/*
ts_tracuu_laytt_tb_dv =>/executor/ts_tracuu_laytt_tb_dv
{
 
  "so_tb": "84918486486"
}
*/
    ts_tracuu_laytt_tb_ts: (axios, data) => axios.post('/ccbs/executor/ts_tracuu_laytt_tb_ts', data),

    ts_tracuu_laytt_ls_3g: (axios, data) => axios.post('/ccbs/executor/ts_tracuu_laytt_ls_3g', data),
/*
{
 
  "so_tb": "84918486486"
}
*/  
    //ts_tracuu_ls_khbd
    ts_tracuu_ls_khbd: (axios, data) => axios.post('/ccbs/executor/ts_tracuu_ls_khbd', data),

    ts_subscriber_info: (axios, data) => axios.post('/ccbs/executor/ts_subscriber_info', data),
    //khoiphucthanhly/lay_ds_diachi_theo_dbtbid
    lay_ds_diachi_theo_dbtbid: async (axios, thuebao_id) => axios.post('/web-hopdong/thaydoiloaihinhtb/lay_ds_diachi_theo_dbtbid', {thuebao_id: thuebao_id}),
    lay_map_thamso: (axios, data) => axios.post('/web-quantri/thamso/map_id', data),
    //web-tracuu/tracuu-tt-thuebao/thongbao_capnhat_tratruoc
    thongbao_capnhat_tratruoc: (axios,thuebao_id) => axios.get('web-tracuu/tracuu-tt-thuebao/thongbao_capnhat_tratruoc',{"p_thuebao_id":thuebao_id}),
    capnhat_tt_tratruoc: (axios, data) => axios.post('web-tracuu/tracuu-tt-thuebao/capnhat_tt_tratruoc', data),
    ///tracuu-tt-thuebao/a_vnp_dongbo_ddtt
    a_vnp_dongbo_ddtt: (axios, data) => axios.post('web-tracuu/tracuu-tt-thuebao/a_vnp_dongbo_ddtt', data),
    tracuu_thongtin_taikhoan1: (axios, data) => axios.post('web-hopdong/tracuu-tt-thuebao/tracuu_thongtin_taikhoan1', data),

    ///didong/taikhoan-tien
    tracuu_taikhoan: (axios, data) => axios.post('ccbs/didong/taikhoan-tien', data),
    fn_tt_db_thuebao: (axios, data) => axios.post('web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_thuebao', data),
    fn_tt_tinh: (axios, data) => axios.post('web-tratruoc/thongtin_tratruoc_dn/fn_tt_tinh', data),
    fn_tt_quan: (axios, data) => axios.post('web-tratruoc/thongtin_tratruoc_dn/fn_tt_quan', data),
    fn_tt_phuong: (axios, data) => axios.post('web-tratruoc/thongtin_tratruoc_dn/fn_tt_phuong', data),
    fn_tt_pho: (axios, data) => axios.post('web-tratruoc/thongtin_tratruoc_dn/fn_tt_pho', data),
    fn_tt_dacdiem: (axios, data) => axios.post('web-tratruoc/thongtin_tratruoc_dn/fn_tt_dacdiem', data),
}
  