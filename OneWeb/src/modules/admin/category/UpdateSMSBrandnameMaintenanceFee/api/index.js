export default{
    getComboLinhVuc: (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_LINHVUC', data),
    getComboDichVuVT: (axios, data) => axios.post('/web-tracuu/phiduytri_smsbrandname/sp_lay_ds_dvvt_with_dvgt', data),
    DanhSach_ChuaCoPhi: (axios, data) => axios.post('/web-tracuu/phiduytri_smsbrandname/sp_lay_ds_dvgt_chuacophi_by_dvvt', data),
    DanhSach_DaCoPhi: (axios, data) => axios.post('/web-tracuu/phiduytri_smsbrandname/sp_lay_ds_dvgt_dacophi_by_dvvt', data),
    EditData: (axios, data) => axios.post('/web-tracuu/phiduytri_smsbrandname/sp_gantien_linhvuc_dvgt', data),
    //1 la insert 2 update  3 delete
    // {
    //     "data": [{"LINHVUC_ID":"4","DICHVUGT_ID":"775","TIEN":"50000"},{"LINHVUC_ID":"4","DICHVUGT_ID":"776","TIEN":"50000"}],
    //     "linhvuc_id": 4,
    //     "kieu": 1
    // }
  }