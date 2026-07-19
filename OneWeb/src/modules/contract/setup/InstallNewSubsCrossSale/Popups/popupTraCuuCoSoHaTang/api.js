import axios from "axios"
export default {
    Lay_ds_CSHT: (data) => axios.get(`web-hopdong/hopdong/sp_lay_ds_csht?vtinh_id=${data.vtinh_id}`),
    Lay_ds_Tinh: (data) => axios.get(`web-hopdong/hopdong/sp_lay_cbx_tinh`,data),
}