export default {
  lay_thongtin_table: (axios, data) =>
    axios.post("web-hopdong/chuyen-dich-ban-cheo/laythongtin_diachi", data),
  lay_ds_tinh: (axios, data) =>
    axios.post("web-bancheo/danhmuc/lay_ds_tinh", data),
  sp_lay_ds_thamso_md: (axios, data) =>
    axios.post("/web-ccdv/khaibaotsl/sp_lay_ds_thamso_md", data),
  lay_ds_quan: (axios, data) =>
    axios.post("web-bancheo/danhmuc/lay_ds_quan", data),
  lay_ds_dacdiem: (axios, data) =>
    axios.post("/web-ccdv/tien_trinh_bao_hong/lay_ds_dacdiem", data),
  lay_ds_phuong: (axios, data) =>
    axios.post("web-bancheo/danhmuc/lay_ds_phuong", data),
  lay_ds_pho: (axios, data) =>
    axios.post("web-bancheo/danhmuc/lay_ds_pho", data)
};
