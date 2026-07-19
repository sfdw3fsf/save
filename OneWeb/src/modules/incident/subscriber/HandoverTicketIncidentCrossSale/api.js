export default {
    CSS_KIEU_HD: async (axios, data,header) => axios.get('/web-quantri/danhmuc-chung/CSS_KIEU_HD', data, header),
    CSS_KENHTHU: async (axios, data,header) => axios.get('/web-quantri/danhmuc-chung/CSS_KENHTHU', data, header),
    CSS_LOAI_HD: (axios, data, header) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_HD', data, header), 
    CSS_LOAIHINH_TB: (axios, data, header) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB', data, header), 
    lay_ds_quytrinh_v2: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_quytrinh_v2', data, header),
    sp_lay_huonggiao_theo_quytrinh: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/sp_lay_huonggiao_theo_quytrinh', data, header),
    lay_luong_thaotac: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_luong_thaotac', data, header),
    lay_ct_thaotac_control: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ct_thaotac_control', data, header),
    lay_ds_hdtb_didong: async (axios, data,header) => axios.post('/web-tratruoc/tra-truoc/lay_ds_hdtb_didong', data, header), 

    sp_ht_dichvuvt_all: async (axios, data,header) => axios.get('/web-hopdong/bancheo-gpph-bh/sp_ht_dichvuvt_all', data, header), 
    lay_ds_loaihinh_thuebao_dichvu: async (axios, data,header) => axios.get('/web-thicong/danhmuc/lay_ds_loaihinh_thuebao_dichvu', data, header), 
    Lay_ds_huonggiao_baohong_v2: async (axios, data,header) => axios.post('/web-bancheo/tracuu/Lay_ds_huonggiao_baohong_v2', data, header), 
    lay_ds_tb_baohong_chua_nt_v3: async (axios, data,header) => axios.post('/web-hopdong/bancheo-gpph-bh/lay_ds_tb_baohong_chua_nt_v3', data, header), 
    lay_ds_donvi_baohong: async (axios, data,header) => axios.post('/web-hopdong/bancheo-gpph-bh/lay_ds_donvi_baohong', data, header), 
    lay_danhsach_phieuth_bh_net: async (axios, data,header) => axios.post('/web-hopdong/bancheo-gpph-bh/lay_danhsach_phieuth_bh_net', data, header),  

    giaophieu_baohong_ph_tinh: async (axios, data,header) => axios.post('/web-hopdong/bancheo-gpph-bh/giaophieu_baohong_ph_tinh', data, header),    
    fn_bancheo_huygiao_xuly_baohong: async (axios, data) => axios.post('/web-hopdong/bancheo-gpph-bh/fn_bancheo_huygiao_xuly_baohong', data),  
    bancheo_hoanthanh_baohong_v3: async (axios, data,header) => axios.post('/web-hopdong/bancheo-gpph-bh/bancheo_hoanthanh_baohong_v3', data, header),  
    giaophieu_bh: (axios, { vbaohong_id, vdonvi_giao_id, vdonvi_nhan_id, vhuongiao_id, vma_nd, vnd_giao, vphieu_cha_id, vttbh_id }) => axios.post('/web-baohong/TiepNhanBaoHong/giaophieu_bh', { vbaohong_id, vdonvi_giao_id, vdonvi_nhan_id, vhuongiao_id, vma_nd, vnd_giao, vphieu_cha_id, vttbh_id }),

    getTrangThaiThueBao: async (axios, thuebaoId) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_thuebao', {type:10, param: thuebaoId}),  
    getBHId_TC: (axios, baohong_id ) => axios.post('web-tratruoc/thongtin_tratruoc_dn/baohong_fn_tt_baohong_bc', { param: baohong_id, type: 3 }),
    getKey: (axios, data) => axios.post('web-hopdong/khoiphucthanhly/get_keys', data),
    
    
    getGiaoPhieuBaoHongTheoBHid: async (axios, {vbaohong_id,vhuonggiao_id, vdonvi_nhan_id}) => axios.post('web-baohong/baohong/sp_lay_giaophieu_baohong_theo_baohong_id', {vbaohong_id,vhuonggiao_id, vdonvi_nhan_id}),    
   
    kiemtraBaoHongBCChuyenTinh: async (axios, vbaohong_id) => axios.get(`web-baohong/baohong/sp_kiemtra_baohong_bc_chuyetinh?vbaohong_id=${vbaohong_id}`),    
    bancheo_sms_baohong : async (axios, {vbaohong_id, vphanvung_id}) => axios.post('web-baohong/TiepNhanBaoHong/bancheo_sms_baohong', {vbaohong_id, vphanvung_id}),    
    tiepNhanBaoHong: (axios, {tinh_tc, vloaitb_id, vthutu, vthuebao_id,vbaohong_id, dactrung, net_giao, ds,  dsBH_BC})=> 
        axios.post('web-baohong/TiepNhanBaoHong/nhan_hong', {tinh_tc, vloaitb_id, vthutu, vthuebao_id,vbaohong_id, dactrung, net_giao, ds,  dsBH_BC}),

    SP_UPDATE_BAOHONG_BC_BAOHONG_ID : async (axios, {vbaohong_id_tc, vbaohong_id_ban}) => axios.post('web-baohong/TiepNhanBaoHong/sp_update_baohong_bc_baohong_id', {vbaohong_id_tc, vbaohong_id_ban}),    
    SP_UPDATE_BAOHONG_BC_TRANGTHAI : async (axios, vbaohong_id_ban) => axios.post('web-baohong/TiepNhanBaoHong/sp_update_baohong_bc_trangthai', {vbaohong_id_ban}),    

    getKeyBH_BC: async (axios, {tinhthicong_id, keyname}) => axios.post('web-bancheo/tracuu/get_key', {tinhthicong_id, keyname}),    
    layGoiKTId: async (axios, {vthuebao_id,vhdtb_id}) => axios.post('web-hopdong/tien_trinh_bao_hong/lay_goikt_id',  {vthuebao_id,vhdtb_id}),
    layHuongGiaoTheoThuTu: async (axios, { VPHANVUNG_ID, VLOAITB_ID, VTHUTU}) => axios.post('web-hopdong/tien_trinh_bao_hong/smp_layhuonggiao_baohong_theothutu',  {VPHANVUNG_ID, VLOAITB_ID, VTHUTU}),

    getNhanVienId:  async (axios, param ) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_dm_tinh_ketnoi', { param , type:2 }),    
    
    getDonViId : async (axios, param ) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_dm_tinh_ketnoi', { param , type: 3 }),
    giaoPhieuDonViTiepTheo_BC: async (axios, data) => axios.post(`web-bancheo/tracuu/bancheo_giao_donvi_tieptheo`, data),
    
    giaoPhieuBaoHong_V2: (axios, data) => axios.post(`/web-bancheo/tracuu/giaophieu_bh_v2`, data),

}