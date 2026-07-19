export default {
    getDSBTSNodeb: (axios, data) => axios.post('/web-ecms/bts/lay-ds-bts-nodeb',data),
    getThongTinThietBiBTS: (axios, data) => axios.post('/web-ecms/bts/lay-tt-thietbi-bts',data),
    getDanhMucBTS: (axios, data) => axios.get('/web-ecms/bts/lay-danhmuc-bts-nodeb'),
    CapNhatThongTinBTS: (axios, data) => axios.post('/web-ecms/bts/capnhat-thongtin-bts',data),
    getChiTietBTS: (axios, data) => axios.post('/web-ecms/bts/lay-chitiet-bts',data),

    sp_import_update_khuvuc_bts: (axios, data) => axios.post('web-quantri/khai-bao-bts/sp_import_update_khuvuc_bts',data),
    
    
}