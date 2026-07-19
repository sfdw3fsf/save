export default {
    getDanhSachBangKe: (axios, data) => axios.post("/web-bancheo/tracuu/sp_kb_lay_ds_bangke", data),
    getDanhSachCTBangKe: (axios, data) => axios.post("/web-bancheo/tracuu/sp_kb_lay_ds_ct_bangke", data),
    getDanhSachCTExcelBangKe: (axios, data) => axios.post("/web-bancheo/tracuu/sp_kb_ds_phieugachno_excel", data),
    getDanhSachChuKyNo: async (axios, data) => axios.get('/web-thuno/api/thu-no/quan-ly-dai-ly/danh-muc/CHUKYNO', { params: data}),
};
