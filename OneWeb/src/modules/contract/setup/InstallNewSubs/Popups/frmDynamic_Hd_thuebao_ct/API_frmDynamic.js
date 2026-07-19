import axios from 'axios'
export default {
  lay_danhsach_thuoctinh_datmoi_hd_thuebao_ct: async (data, header) => axios.post('web-hopdong/lapdatmoi/lay_danhsach_thuoctinh_datmoi_hd_thuebao_ct', data, header),
}
