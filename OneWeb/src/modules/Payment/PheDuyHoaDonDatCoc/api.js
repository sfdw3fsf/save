export default {
  lay_ds_phieu_hen_thanhtoan: (axios, data) => axios.post('/web-tratruoc/pheduyet_hopdong_datcoc/lay_ds_phieu_hen_thanhtoan', data),
  lay_ds_phieu_hen_thanhtoan_v2: (axios, data) => axios.post('/web-tratruoc/pheduyet_hopdong_datcoc/lay_ds_phieu_hen_thanhtoan_v2', data),
  capnhat_kq_duyet_hen_tt: (axios, data) => axios.post('/web-tratruoc/pheduyet_hopdong_datcoc/capnhat_kq_duyet_hen_tt', data),
  lay_thuebao_co_hen_phieutt: (axios, data) => axios.post('/web-tratruoc/pheduyet_hopdong_datcoc/lay_thuebao_co_hen_phieutt', data),
  capnhat_kq_duyet_hen_theo_thuebao: (axios, data) => axios.post('/web-tratruoc/pheduyet_hopdong_datcoc/capnhat_kq_duyet_hen_theo_thuebao', data),
}
