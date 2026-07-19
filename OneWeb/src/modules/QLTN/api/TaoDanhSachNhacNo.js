import axios from "axios"

export default{
  getDSLanTaoNhacNo:  async (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/ds-lan-tao-nhac-no', { params : data}),
  getDSTBDaNhac:  async (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/ds-nhac-no', { params : data}),
  addDSNhacNo: async (axios, data) => axios.post('/web-thuno/api/thu-no/xu-ly-thue-bao/ds-nhac-no', data),
  updateDSNhacNo: async (axios, data) => axios.put('/web-thuno/api/thu-no/xu-ly-thue-bao/ds-nhac-no', data),
  addTBRiengLe: async (axios, data) => axios.post('/web-thuno/api/thu-no/xu-ly-thue-bao/nhac-no-le', data),
  updateTBRiengLe: async (axios, data) => axios.put('/web-thuno/api/thu-no/xu-ly-thue-bao/nhac-no-le', data),
  addChuyenDSNhacNo: async (axios, data) => axios.post('/web-thuno/api/thu-no/xu-ly-thue-bao/chuyen-ds-nhac-no', data),
  updateChuyenDSNhacNo: async (axios, data) => axios.put('/web-thuno/api/thu-no/xu-ly-thue-bao/chuyen-ds-nhac-no', data),
  xoaTBNhacNo: async (axios, data) => axios.delete('/web-thuno/api/thu-no/xu-ly-thue-bao/ds-nhac-no', { data: data}),
  xoaLanNhacNo: async (axios, kyCuoc, lanTaoNNId) => axios.post('/web-thuno/api/thu-no/xu-ly-thue-bao/xoa-lan-tao-nhac-no?kyCuoc=' + kyCuoc + "&lanTaoNNId=" + lanTaoNNId),
  getDSMailServer:  async (axios, data) => axios.get('/web-thuno/api/thu-no/common/mail-server'),
  getDSMessage:  async (axios, data) => axios.get('/web-thuno/api/thu-no/common/message'),
  getDSNhacNoEMail:  async (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/ds-nhac-no-email', { params : data}),
  sendEMail:  async (axios, data) => axios.post('/web-thuno/api/thu-no/xu-ly-thue-bao/gui-email', data),
}
