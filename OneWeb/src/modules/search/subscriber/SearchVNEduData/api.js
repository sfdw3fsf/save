export default{
    sp_lay_ds_tinh_theo_tinh_id_v2 : async(axios, data) => axios.post('web-ccdv/capnhat_loai_thietbi/sp_lay_ds_tinh_theo_tinh_id_v2',data),
    getListTruong : async(axios, id_tinh_edu) => axios.get('tichhop/edu/l1?call=app.doisoat.serv.api.getListTruong&tinh_id='+id_tinh_edu),
    getListSo : async(axios, data) => axios.post('/tichhop/general/callapi', data),
    themeList : async(axios, data) => axios.get('/tichhop/edu/themeList', data)
}
