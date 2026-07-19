export default {
    getcboDichVuVT: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
    getcboDichVuKhac: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_KHAC'),
    getThongtinDoiTac: async (axios, data) => axios.get('/web-thicong/quanlydonhang/lay_thongtin_doitac'),
    getThongTinThueBao: async (axios, p_ma_tb) => axios.post('/web-thicong/quanlydonhang/lay_thongtin_hdbs_vnpti', { p_ma_tb }),
    saveThongTinThueBao: async (axios, { p_thuebao_id, p_diachi_ld_sub, p_diachi_pop, p_dvkhac_id, p_dichvuvt_id, p_doitac_id }) => axios.post('/web-thicong/quanlydonhang/capnhat_thongtin_hdbs_vnpti', { p_thuebao_id, p_diachi_ld_sub, p_diachi_pop, p_dvkhac_id, p_dichvuvt_id, p_doitac_id }),
    saveThongTinThueBao_v2: async (axios, { p_thuebao_id, p_diachi_ld_sub, p_diachi_pop, p_dvkhac_id, p_dichvuvt_id, p_doitac_id, p_po_number, p_order_id, p_circuit_id, p_ten_tb, p_ma_hd }) => axios.post('web-thicong/capnhatthongtin/capnhat_thongtin_hdbs_vnpti_v2', { p_thuebao_id, p_diachi_ld_sub, p_diachi_pop, p_dvkhac_id, p_dichvuvt_id, p_doitac_id, p_po_number, p_order_id, p_circuit_id, p_ten_tb, p_ma_hd }),

}