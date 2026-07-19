export default{
    danhmuc: (axios, data) => axios.post('/web-thicong/tracnghiem-atvsld/danhmuc', data),
    lay_ds_baithi_theo_nvql: (axios, data) => axios.post('/web-quantri/tracnghiem/lay_ds_baithi_theo_nvql', data),
    fn_lay_ketqua_baithi_html: (axios, data, tonghop_id) => axios.get('/web-quantri/tracnghiem/fn_lay_ketqua_baithi_html?vtonghop_id='+tonghop_id+'', data),
    duyet_baithi_tracnghiem: (axios, data) => axios.post('/web-quantri/tracnghiem/duyet_baithi_tracnghiem', data),
    
    
  }