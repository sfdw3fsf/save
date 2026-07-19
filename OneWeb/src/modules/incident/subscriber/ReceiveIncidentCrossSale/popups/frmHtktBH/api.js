export default {
  getKyHoaDon: (axios) => axios.get('/web-thuno/api/thu-no/common/ky-cuoc-hien-hanh'),
  loadCbDichVu: (axios) => axios.get('web-baohong/tien_trinh_bao_hong/sp_ht_dichvuvt_combobox_bh'),
  lay_danhba_theo_matb: (axios, vma_tb) => axios.post(`/web-baohong/TiepNhanBaoHong/lay_danhba_theo_matb`, {vma_tb}),
  lay_tongnops_thuebao: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/lay_tongnops_thuebao', data),
  lay_ds_thongtin_kythuat: (axios, vthuebao_id) => axios.post('/web-baohong/TiepNhanBaoHong/lay_ds_thongtin_kythuat', {vthuebao_id}),
  lay_loaihinhtb_theo_dvvt: (axios, dichvuvt_id) => axios.post('/web-baohong/TiepNhanBaoHong/lay_loaihinhtb_theo_dvvt', {dichvuvt_id}),
}
