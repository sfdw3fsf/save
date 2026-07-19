export default {
  lay_ds_doi_soat: (axios, data) => axios.post('/web-tracuu/doisoatdulieu/sp_lay_ds_doisoat_ccbs_onebss', data),
  get_ds_chuky_no: (axios) => axios.get('/web-thuno/api/thu-no/common/chu-ky-no'),
  lay_danh_muc: (axios,data) => axios.post('/web-tracuu/doisoatdulieu/sp_lay_danhmuc_doisoat_ccbs_onebss',data),
}