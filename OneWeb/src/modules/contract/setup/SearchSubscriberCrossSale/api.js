import axios from "axios"
export default {
    tracuu_TB_BC: ( data) => axios.post('web-bancheo/tracuu/tracuu_map_matb_bancheo',data),
    tracuu_maptb_ban: (data) => axios.post('web-bancheo/tracuu/tracuu_maptb_ban',data),
    tracuu_maptb_tc: (data) => axios.post('web-bancheo/tracuu/tracuu_maptb_tc',data),
    xoa_maptb: (data) => axios.post('web-bancheo/tracuu/xoa_maptb',data),
    Lay_ds_Tinh: ( data) => axios.get(`web-hopdong/hopdong/sp_lay_cbx_tinh`,data),
    // Lay_ds_Tinh_v2: ( data) => axios.post(`web-net/tracuuKenhLuong/dsTinh`,data),
    Lay_ds_Tinh_v3: ( data) => axios.post(`web-bancheo/tracuu/danhsach_tinh_maptb`,data),


    map_tb_tc: ( data) => axios.post(`web-bancheo/tracuu/map_tb_tc`,data),
    map_tb_ban: ( data) => axios.post(`web-bancheo/tracuu/map_tb_ban`,data),

    tra_cuu_v2: ( data) => axios.post(`web-bancheo/tracuu/tracuu_maptb`,data), 
    tra_cuu_tb_map:(data)=> axios.post(`web-bancheo/tracuu/kiemtra_maptb`,data),
    map_tb_bancheo:(data)=> axios.post(`web-bancheo/tracuu/map_tb_bancheo`,data) 

}  