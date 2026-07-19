import axios from "axios"
export default {
    xoa_maptb: (data) => axios.post('web-bancheo/tracuu/xoa_maptb',data),    
    Lay_ds_Tinh_v3: ( data) => axios.post(`web-bancheo/tracuu/danhsach_tinh_maptb`,data),tra_cuu_v2: ( data) => axios.post(`web-bancheo/tracuu/tracuu_maptb`,data), 
    tra_cuu_tb_map:(data)=> axios.post(`web-bancheo/tracuu/kiemtra_maptb`,data),
    map_tb_bancheo:(data)=> axios.post(`web-bancheo/tracuu/map_tb_bancheo`,data) 

}   