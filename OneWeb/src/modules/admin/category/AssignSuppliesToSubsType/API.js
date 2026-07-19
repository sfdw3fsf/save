
export default{
    getDSDichVu: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
    getLoaiHinhTB: async (axios,id) =>axios.get('/web-thicong/danhmuc/lay_ds_loaihinh_thuebao_dichvu?dichVuVtId='+id),
    getDSVTTheoLHTB: async (axios, data) => axios.post('/web-quantri/vattu/ds_vat_tu_theo_lhtbid',data),
    ganGoVTLHTB: async (axios, data) => axios.post('/web-quantri/vattu/capnhat_vattu_lhtb',data)
}