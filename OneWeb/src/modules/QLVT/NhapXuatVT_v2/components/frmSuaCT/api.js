export default {
  lay_ds_vattu_chungtu_hcm: (axios,data ) => axios.get(`/web-qlvt/api/tra-cuu-chung-tu/lay-ds-vattu-chungtu-hcm/${data.chungtu_id}`),
  lay_ds_kieu_phieu: (axios,data ) => axios.get(`/web-qlvt/api/tra-cuu-chung-tu/lay-ds-kieu-phieu/${data.loaict_id}`),
  ds_thong_tin_ngay_ct: (axios,data ) => axios.get(`/web-qlvt/api/sua-ct/ds-thong-tin-ngay-ct/${data.chungtu_id}`),
  ds_ma_muc_dich_theo_muc_dich: (axios,data ) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-ma-muc-dich-theo-muc-dich/${data.mucdich_id}`),
  chat_luong: (axios,data ) => axios.get(`/web-qlvt/api/danh-sach-vat-tu-ccdc/chat-luong`),
  ds_loai_kho_td: (axios,data ) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-loai-kho-td/${data.mucdich_id}`),
  kiem_tra_loai_ma_kho: (axios,data ) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-loai-ma-kho`,data),
  cap_nhat_ct_co_quyen: (axios,data ) => axios.post(`/web-qlvt/api/sua-ct/cap-nhat-ct-co-quyen`,data),
  
  //thoaiupdate
  getCheckFile: (axios,data ) => axios.get(`/web-qlvt/api/sua-ct/kiem-tra-ct-file-cv/${data.chungTuId}`),
  uploadFile: (axios,data,header ) => axios.post(`/web-qlvt/api/minioStorage/uploadFile`,data,header),
  updateFileCongVan: (axios,data ) => axios.post(`/web-qlvt/api/sua-ct/cap-nhat-upload-file-cv`,data),

}
    