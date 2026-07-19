export default{

    xemtruoc_hoadon: async (axios, data) => axios.post('web-thicong/xuathoadon/xemtruoc_hoadon', data),
    importAndPublishInv: async (axios, data) => axios.post('/tichhop/vnptinvoice/importAndPublishInv', data),
    //importAndPublishInv: async (axios, data) => axios.post('https://api-dev-onebss.vnpt.vn/tichhop/vnptinvoice/importAndPublishInv', data),
    kiemtra_ds_ma_tt_can_phathanh: async (axios, data) => axios.post('web-thicong/xuathoadon/kiemtra_ds_ma_tt_can_phathanh', data),
    capnhat_trangthai_phathanh: async (axios, data) => axios.post('web-thicong/xuathoadon/capnhat_trangthai_phathanh', data),
  
}