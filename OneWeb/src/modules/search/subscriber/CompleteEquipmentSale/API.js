export default{
    //BSS-39001_002
    lay_danhmuc_thicong_internet: async (axios, data) => axios.post('/web-thicong/thicong-internet/lay_danhmuc_thicong_internet', data),
    //BSS-39001_003
    lay_ds_hoancong_ban_tbi: async (axios, data) => axios.post('/web-ccdv/hoan-cong-ban-thiet-bi/lay_ds_hoancong_ban_tbi', data),
    //BSS-39001_004
    lay_phuluc_vattu_capmienphi: async (axios, data) => axios.post('/web-ccdv/hoan-cong-ban-thiet-bi/lay_phuluc_vattu_capmienphi', data),
    //BSS-39001_005
    lay_ds_nhanvien_theo_phieu_id: async (axios, data) => axios.post('/web-ccdv/hoan-cong-ban-thiet-bi/lay_ds_nhanvien_theo_phieu_id', data),
    //BSS-39001_Button03
    hoancong_bantbi: async (axios, data) => axios.post('/web-ccdv/hoan-cong-ban-thiet-bi/hoancong_bantbi', data),
    getTTDanhBa_MaTB:async (axios, data) => axios.post('/web-hopdong/hopdong/lay_danhba_theo_matb', data),
}