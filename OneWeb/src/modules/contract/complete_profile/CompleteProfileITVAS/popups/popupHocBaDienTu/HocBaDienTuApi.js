export default{
    LayDSKhoi : async(axios, data) => axios.post(`/web-thicong/hocbadientu/laydskhoi`,data),
    LayDSLopHoc : async(axios, data) => axios.post(`/web-thicong/hocbadientu/laydslop`,data),
    LayDSHocSinh : async(axios, data) => axios.post(`/web-thicong/hocbadientu/laydshocsinh`,data),
    them_hd_thuebao_ct_common: async(axios, {vhdtb_id, vds_hdtb_ct}) => axios.post(`/web-thicong/hoinghi_truyenhinh/them_hd_thuebao_ct_common`, {vhdtb_id, vds_hdtb_ct}),
    lay_ds_hd_thuebao_ct_common: async(axios, {vhdtb_id}) => axios.post(`/web-quantri/chuyendoihoadon/lay_ds_hd_thuebao_ct_common`, {vhdtb_id}),
    kichhoat_dv_hocbadientu : async(axios, {hdtb_id})=> axios.post(`/web-thicong/hocbadientu/datmoi`,{hdtb_id}),
    LayDSHocSinhTheoDK : async(axios, data) => axios.post(`/web-thicong/hocbadientu/laydshocsinhtheodk`,data),
    SP_LAY_DS_DL_NGHIEPVU: async (axios, data) => axios.post(`/web-hopdong/quanly_khdn_mnp/sp_lay_ds_dl_nghiepvu`,data),
    huyhocsinh : async(axios, {hdtb_id})=> axios.post(`/web-thicong/hocbadientu/huyhocsinh`,{hdtb_id}),
    update_hdtb_cntt : async(axios, data)  => axios.post('/web-thicong/dvcntt/update_hdtb_cntt',data),
} 