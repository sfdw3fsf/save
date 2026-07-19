export default{
  upload_file_hoso: async (axios, data,header) => axios.post('/web-quantri/upload', data,header),
  ds_quyen_nguoidung: (axios, data) => axios.get('/web-quantri/quyen/ds_quyen_nguoidung', data),
  trangthai_bo_hoso: (axios, data) => axios.get('/web-quantri/danhmuc/hoso/trangthai_bo_hoso', data),
  kho_hoso: (axios, data) => axios.get('/web-quantri/danhmuc/hoso/kho_hoso', data),
  nhom_file: (axios, data) => axios.get('/web-quantri/danhmuc/hoso/nhom_file', data),
  loai_file: (axios, data) => axios.get('/web-quantri/danhmuc/hoso/loai_file', data),
  layds_hopdong_chuaco_hoso: (axios, data) => axios.post('/web-quantri/hopdong/layds_hopdong_chuaco_hoso', data),
  layds_theo_matb: (axios, data) => axios.post('/web-quantri/hopdong/layds_theo_matb', data),
  lay_ds_bo_hs: (axios, data) => axios.post('/web-quantri/hopdong/lay_ds_bo_hs', data),
  capnhat_file_hoso: (axios, data) => axios.post('/web-hopdong/hopdong/capnhat_file_hoso', data),
  capnhat_bohoso: (axios, data) => axios.post('/web-quantri/hoso/capnhat_bohoso', data),
  lay_ds_hd_tb_theo_hdkh_id: (axios, data) => axios.post('/web-quantri/danhmuc/lay_ds_hd_tb_theo_hdkh_id', data),
  layds_file_trong_hoso: (axios, data) => axios.post('/web-quantri/hoso/layds_file_trong_hoso', data),
  layds_donvi_nhan_hoso: (axios, data) => axios.post('/web-quantri/hoso/layds_donvi_nhan_hoso', data),
  sp_lay_file_hs_nhom_id: (axios, data) => axios.post('/web-quantri/hoso/sp_lay_file_hs_nhom_id', data),
  fn_capnhat_file_hs: (axios, data) => axios.post('/web-quantri/hoso/fn_capnhat_file_hs', data),
  xoa_file: (axios, data) => axios.post('/web-quantri/upload/xoa_file', data),
  xoa_file_bsh_theo_fileid: (axios, data) => axios.post('/web-hopdong/hopdong/xoa_file_bsh_theo_fileid', data),
  them_hs_thue_bao: (axios, data) => axios.post('/web-hopdong/hopdong/them_hs_thue_bao', data),
  capnhat_filehs_ds: (axios, data) => axios.post('/web-hopdong/hopdong/capnhat_filehs_ds', data),


  lay_ds_filehs_theo_kh: (axios, data,vhdkh_id) => axios.get('/web-hopdong/QLBoHS/lay_ds_filehs_theo_kh?vhdkh_id='+vhdkh_id, data),
  sp_capnhat_file_hs: (axios, data) => axios.post('/web-quantri/hoso/sp_capnhat_file_hs', data),

 


  //thoain hot fix

  lay_ds_hd_tb_theo_hdkh_id: (axios, {vhdkh_id}) => axios.post('/web-hopdong/hopdong/lay_ds_hd_tb_theo_hdkh_id_v2', {vhdkh_id}),
 
 /**
  * 
  * @param {
  vkieu
  vhoso_id
  vloaifile_id
  vfile_cn
  vbohs_id
  vurl
  vghichu
  vfile_clob
  vds_hs_thuebao
} axios 
  * @param {*} data 
  * @returns 
  */
  fn_capnhat_file_hstb:(axios,data) => axios.post('/web-hopdong/hopdong/fn_capnhat_file_hstb', data),

  }