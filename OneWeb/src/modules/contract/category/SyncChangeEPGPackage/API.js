export default {
    getPackageEPG: async (axios, data) => axios.post('/tichhop/vasc/getPackageEPG',data),
    lay_ds_thuebao_dongbo_epg: async (axios, data) => axios.post('/web-quantri/epg/lay_ds_thuebao_dongbo_epg',data),
    kiemtra_thuebao_lap_hopdong_khac: async (axios, data) => axios.post('/web-hopdong/hopdong/kiemtra_thuebao_lap_hopdong_khac',data),
    fn_dongbo_thaydoi_epg: async (axios, data) => axios.post('/web-quantri/epg/fn_dongbo_thaydoi_epg',data),
    kt_epg_dk_goi: async (axios, data) => axios.get('/web-quantri/epg/kt_epg_dk_goi',data),
    thongtin_nguoidung2: async (axios, data) => axios.post('/quantri/user/thongtin_nguoidung2',data),
}