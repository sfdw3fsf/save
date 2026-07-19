export default {
  getDSThueBao: (axios,data) => axios.post('/web-thicong/giaoviec-vip/lay_ds_thuebao_cungphien_bc',data), 
  getThongSoKyThuat: (axios,data) => axios.post('/web-ccdv/completion/bancheo_lay_ds_thongtin_kythuat',data),
  getDSPhieuTH: (axios,data) => axios.post('/web-thicong/giaoviec-vip/lay_danhsach_phieuth',data),
}