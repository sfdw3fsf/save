export default {
    lay_ds_tinh: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TINH'),
    nhanban_goi_dadv: (axios, data) => axios.post('web-quantri/khaibao-muccuoc/nhanban_goi_dadv', data),
    nhanban_goi_dadv_tinhkhac: (axios, data) => axios.post('web-quantri/khaibao-muccuoc/nhanban_goi_dadv_tinhkhac', data),
    sp_cbb_congvan: async (axios) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_cbb_congvan', {
        loai_cv: 4
    }),
    sp_cbb_maghep: async (axios) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_cbb_maghep', {}),

    sp_grv_goida_dv: async (axios) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_grv_goida_dv', {
        //Tạm thời
        pagenumber: 1,
        pagesize: 100000000
    }),

    lay_danhsach_dichvu_vt: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),

    sp_cbb_lhtb: async (axios, dichvuvt_id) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_cbb_lhtb', {
        dichvuvt_id: dichvuvt_id
    }),

    sp_cbb_goidatg: async (axios, loaitb_id) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_cbb_goidatg', {
        loaitb_id: loaitb_id
    }),

    sp_cbb_goivnp: async (axios, congvan_id) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_cbb_goivnp', {
        congvan_id: congvan_id
    }),

    sp_grv_loaihinh: async (axios, goi_id, dichvuvt_id, loaitb_id, type) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_grv_loaihinh', {
        goi_id: goi_id,
        dichvuvt_id: dichvuvt_id,
        loaitb_id: loaitb_id,
        type: type,
        pagenumber: 1,
        pagesize: 100000000
    }),

    //tab đối tượng tb
    sp_grv_doituong_tb: async (axios, goi_id, type) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_grv_doituong_tb', {
        goi_id: goi_id,
        type: type,
        pagenumber: 1,
        pagesize: 100000000
    }),

    //tab hướng gọi
    sp_grv_huonggoi: async (axios, rg_id, type) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_grv_huonggoi', {
        rg_id: rg_id,
        type: type,
        pagenumber: 1,
        pagesize: 100000000
    }),

    //tab dịch vụ gt
    sp_grv_dichvu_gt: async (axios, dvvt_id, rg_id, type) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_grv_dichvu_gt', {
        dvvt_id: dvvt_id,
        rg_id: rg_id,
        type: type,
        pagenumber: 1,
        pagesize: 100000000
    }),
    //tab nhóm tích hợp
    sp_grv_dvvt: async (axios, rg_id, type) => axios.post('web-hopdong/thaydoiloaihinhtb/sp_grv_dvvt', {
        rg_id: rg_id,
        type: type,
        pagenumber: 1,
        pagesize: 100000000
    }),
    //tab trang bị
    sp_grv_trangbi_goi: async (axios, goi_id, type) => axios.post('web-hopdong/thaydoiloaihinhtb/sp_grv_trangbi_goi', {
        goi_id: goi_id,
        type: type,
        pagenumber: 1,
        pagesize: 100000000
    }),

    //xoá gói
    sp_del_goidadv: async (axios, goi_id) => axios.post('web-hopdong/thaydoiloaihinhtb/sp_del_goidadv', {
        goi_id: goi_id
    }),

    //Ghi dữ liệu
    sp_upd_goitichhop: async (axios, input) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_upd_goitichhop', {
        is_themmoi: input.is_themmoi,
        congvan_id: input.congvan_id,
        goi_id: input.goi_id,
        rg_id: input.rg_id,
        rg_id_xoa: input.rg_id_xoa,
        json_dadv_lhtb: input.json_dadv_lhtb,
        json_doituong: input.json_doituong,
        json_dvgt: input.json_dvgt,
        json_goidadv: input.json_goidadv,
        json_goidadv_tcdv: input.json_goidadv_tcdv,
        json_goidadv_trangbi: input.json_goidadv_trangbi,
        json_huonggoi: input.json_huonggoi,
    }),

    //Popup cam kết
    sp_cbb_loaihinh_tb: async (axios, goi_id) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_cbb_loaihinh_tb', {
        goi_id: goi_id
    }),
    sp_cbb_tieuchi: async (axios) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_cbb_tieuchi', {}),

    sp_grv_camket: async (axios, goi_id) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_grv_camket', {
        goi_id: goi_id,
        pagenumber: 1,
        pagesize: 100000000
    }),

    //"[{\"GOI_ID\":1730,\"LOAITB_ID\":1,\"TIEUCHI_ID\":1,\"TG_CK\": 24,\"MOTA_CK\": \"cam kết sử dụng dịch vụ tối thiểu 24 tháng.\"}]"
    sp_upd_camket: async (axios, goi_id, loaitb_id, tieuchi_id, json_camket) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_upd_camket', {
        goi_id: goi_id,
        loaitb_id: loaitb_id,
        tieuchi_id: tieuchi_id,
        json_camket: json_camket
    }),

    sp_del_camket: async (axios, goi_id, loaitb_id, tieuchi_id) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_del_camket', {
        goi_id: goi_id,
        loaitb_id: loaitb_id,
        tieuchi_id: tieuchi_id
    }),

    //Popup Công văn
    sp_grv_congvan: async (axios, goi_id) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_grv_congvan', {
        goi_id: goi_id,
        pagenumber: 1,
        pagesize: 100000000
    }),

    upload_file: async (axios, data, header) => axios.post('/web-quantri/upload', data, header),

    sp_upd_congvan: async (axios, goi_id, tenfile, json_congvan) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_upd_congvan', {
        goi_id: goi_id,
        tenfile: tenfile,
        json_congvan: json_congvan
    }),

    getKey: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/get_keys', data),
    KT_RG_ID_Huonggoi: async (axios, data) => axios.post('/web-quantri/lay-dulieu/sp_tt_goi_dadv_tc', data),

    sp_lay_ds_toanha_theo_goi: async (axios, goi_id, kieu) => axios.post('web-ccdv/hopdong/sp_lay_ds_toanha_theo_goi', {
        p_goi_id:  goi_id, 
        p_kieu :  kieu
    }),

    sp_layds_loainha_theo_goi: async (axios, phanvung_id, goi_id, kieu) => axios.post('web-thicong/capnhatthongtin/sp_lay_ds_loainha_goi_dadv', {
        p_phanvung_id: phanvung_id,
        p_goi_id: goi_id,
        p_kieu: kieu
    }),

    sp_upd_goitichhop_v2: async (axios, input) => axios.post('web-ccdv/hopdong/sp_upd_goitichhop_v2', {
        p_is_themmoi: input.is_themmoi,
        p_congvan_id: input.congvan_id,
        p_goi_id: input.goi_id,
        p_rg_id: input.rg_id,
        p_rg_id_xoa: input.rg_id_xoa,
        p_json_dadv_lhtb: input.json_dadv_lhtb,
        p_json_doituong: input.json_doituong,
        p_json_dvgt: input.json_dvgt,
        p_json_goidadv: input.json_goidadv,
        p_json_goidadv_tcdv: input.json_goidadv_tcdv,
        p_json_goidadv_trangbi: input.json_goidadv_trangbi,
        p_json_huonggoi: input.json_huonggoi,
        p_json_toanha: input.json_toanha,
        p_json_loainha: input.json_loainha,
    }), 
}