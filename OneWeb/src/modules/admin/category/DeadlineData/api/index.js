export default{
    getDanhSach: (axios, data) => axios.post('/web-quantri/hansolieu/lay_ds_dvbh_theo_nv', data),
    CapNhat_Them_Xoa: (axios, data) => axios.post('/web-quantri/hansolieu/capnhat_han_nop_tien', data),
  }